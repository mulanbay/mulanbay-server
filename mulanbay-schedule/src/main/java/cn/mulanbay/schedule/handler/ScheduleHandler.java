package cn.mulanbay.schedule.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerCmd;
import cn.mulanbay.business.handler.HandlerInfo;
import cn.mulanbay.business.handler.HandlerResult;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.schedule.*;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.domain.TaskServer;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.RedoType;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.impl.LogNotifiableProcessor;
import cn.mulanbay.schedule.thread.QuartzMonitorThread;
import cn.mulanbay.schedule.thread.RedoThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 调度维护总入口
 * 如果自动发布的程序中需要关闭web容器（如tomcat），在启动前先要等待destroy方法中的时长，
      这样保证程序可以正常关闭、启动
 * 如果直接采用kill -9模式关闭程序，可能会导致正在执行的job异常。
 * 调度服务最好能独立出服务器，因为涉及到部署节点ScheduleHandler问题。
 * @author fenghong
 * @create 2017-10-19 21:43
 **/
public class ScheduleHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleHandler.class);

    /**
     * 调度触发器监控
     */
    private static QuartzMonitorThread quartzMonitorThread;

    /**
     * 调度服务
     */
    private static QuartzServer quartzServer;

    /**
     * 线程池(重做使用)
     */
    private static ExecutorService scheduledThreadPool;

    /**
     * 系统是否已经开启调度功能，由配置文件决定，无法手动重新开启
     */
    private boolean enableSchedule =false;

    /**
     * 调度资源
     */
    private QuartzSource quartzSource;

    int corePoolSize;

    /**
     * 调度触发器检查周期(秒)
     */
    long monitorInterval=60;

    public ScheduleHandler() {
        super("调度处理");
    }

    @Override
    public void init() {
        super.init();
        if(this.isEnableSchedule()){
            if(quartzSource==null){
                quartzSource = new QuartzSource();
                quartzSource.setNotifiableProcessor(new LogNotifiableProcessor());
            }
            //判断分布式支持
            if(quartzSource.isSupportDistri()&&quartzSource.getScheduleLocker()==null){
                throw new ApplicationException(ScheduleErrorCode.DISTRIBUTE_LOCK_NOT_FOUND);
            }
            quartzServer = new QuartzServer();
            quartzServer.setQuartzSource(quartzSource);
            logger.debug("初始化调度服务");
            quartzMonitorThread = new QuartzMonitorThread(this,monitorInterval);
            quartzMonitorThread.start();
            logger.debug("启动调度监控服务");
            //调度线程的线程池采用： 丢弃任务并抛出RejectedExecutionException异常。 (默认)
            ThreadFactory threadFactory = new CustomizableThreadFactory("scheduleHandler");
            scheduledThreadPool = new ThreadPoolExecutor(corePoolSize,100,10L,
                    TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(200),
                    threadFactory,new ThreadPoolExecutor.AbortPolicy());
            //添加启动信息
            //updateTaskServerStart();
        }else {
            logger.debug("该应用设置为不启动调度服务");
        }

    }

    /**
     * 添加/更新当前调度服务器信息
     */
    private void updateTaskServerStart(){
        try {
            SchedulePersistentProcessor persistentProcessor = quartzSource.getSchedulePersistentProcessor();
            TaskServer taskServer = persistentProcessor.selectTaskServer(quartzSource.getDeployId());
            if(taskServer==null){
                taskServer = new TaskServer();
            }
            taskServer.setStartTime(new Date());
            taskServer.setIpAddress(IPAddressUtil.getLocalIpAddress());
            taskServer.setSupportDistri(quartzSource.isSupportDistri());
            taskServer.setCejc(this.getCurrentlyExecutingJobsCount());
            taskServer.setSjc(this.getScheduleJobsCount());
            taskServer.setDeployId(quartzSource.getDeployId());
            taskServer.setLastUpdateTime(new Date());
            taskServer.setStatus(this.getScheduleStatus());
            taskServer.setShutdownTime(null);
            persistentProcessor.updateTaskServer(taskServer);
        } catch (Exception e) {
            logger.error("更新调度服务器信息异常",e);
        }
    }

    /**
     * 更新当前调度服务器停止信息
     */
    private void updateTaskServerShutdown(){
        try {
            SchedulePersistentProcessor persistentProcessor = quartzSource.getSchedulePersistentProcessor();
            TaskServer taskServer = persistentProcessor.selectTaskServer(quartzSource.getDeployId());
            taskServer.setCejc(0);
            taskServer.setSjc(0);
            taskServer.setSupportDistri(quartzSource.isSupportDistri());
            taskServer.setDeployId(quartzSource.getDeployId());
            taskServer.setLastUpdateTime(new Date());
            taskServer.setStatus(false);
            taskServer.setShutdownTime(new Date());
            persistentProcessor.updateTaskServer(taskServer);
        } catch (Exception e) {
            logger.error("更新调度服务器信息停止异常",e);
        }
    }

    @Override
    public Boolean selfCheck() {
        return super.selfCheck();
    }

    @Override
    public void destroy() {
        if(this.isEnableSchedule()){
            int activeJobs = quartzServer.getScheduleJobsCount();
            logger.warn("目前活跃的调度任务数:"+activeJobs);
            quartzServer.shutdown(shutDownWaitForJobsToComplete());
            quartzMonitorThread.stopThread();
            scheduledThreadPool.shutdown();
            try {
                long waitSeconds = this.getShutDownWaitSeconds();
                if(waitSeconds>0&&activeJobs>0){
                    Thread.sleep(getShutDownWaitSeconds()*1000);
                }
            } catch (InterruptedException e) {
                logger.error("destroy error:"+e.getMessage());
            }
            updateTaskServerShutdown();
        }
        super.destroy();
    }

    /**
     * 设置调度检查
     * @param check
     */
    public void setMonitorCheck(boolean check){
        quartzMonitorThread.setCheck(check);
    }

    public void setQuartzSource(QuartzSource quartzSource) {
        this.quartzSource = quartzSource;
    }

    public QuartzSource getQuartzSource() {
        return quartzSource;
    }

    /**
     * 根据 Job 调度日志来自动重做
     * @param logId
     * @param isSync 是否同步
     */
    public void manualRedo(long logId,boolean isSync) {
        if(!this.isEnableSchedule()){
            throw new ApplicationException(ScheduleErrorCode.SCHEDULE_NOT_ENABLED);
        }
        TaskLog taskLog=quartzSource.getSchedulePersistentProcessor().selectTaskLog(logId);
        if(taskLog.getTaskTrigger().getRedoType()== RedoType.CANNOT){
            throw new ApplicationException(ScheduleErrorCode.TRIGGER_CANNOT_REDO);
        }
        startRedoJob(taskLog,isSync,null);
    }

    /**
     * 手动执行一个配置的任务
     * @param triggerId 调度ID
     * @param bussDay 运营日
     * @param isSync 是否同步执行
     * @param extraPara 额外参数
     */
    public void manualNew(long triggerId,Date bussDay,boolean isSync,Object extraPara,String remark) {
        if(!this.isEnableSchedule()){
            throw new ApplicationException(ScheduleErrorCode.SCHEDULE_NOT_ENABLED);
        }
        TaskTrigger taskTrigger = quartzSource.getSchedulePersistentProcessor().selectTaskTrigger(triggerId);
        if(taskTrigger.getCheckUnique()){
            boolean b =quartzSource.getSchedulePersistentProcessor().isTaskLogExit(triggerId,bussDay);
            if(b){
                throw new ApplicationException(ScheduleErrorCode.SCHEDULE_ALREADY_EXECED);
            }
        }
        TaskLog taskLog = new TaskLog();
        taskLog.setBussDate(bussDay);
        taskLog.setTaskTrigger(taskTrigger);
        taskLog.setLogComment(remark);
        startRedoJob(taskLog,isSync,extraPara);
    }

    /**
     * 开启重做任务
     * todo 多用户并发操作下会导致被重复执行
     * 解决方式：1.该方法增加锁机制，2. AbstractBaseJob中对于redo类型增加锁机制（第二种更好些）
     * @param taskLog 重做的调度日志
     * @param isSync  是否同步执行
     * @param extraPara 额外参数
     */
    private void startRedoJob(TaskLog taskLog,boolean isSync,Object extraPara){
        RedoThread redoThread = new RedoThread(taskLog,true);
        redoThread.setExtraPara(extraPara);
        redoThread.setQuartzSource(quartzSource);
        if(!isSync){
            scheduledThreadPool.execute(redoThread);
            //redoThread.start();
            logger.debug("启动一个调度日志重做线程任务");
        }else{
            redoThread.run();
            logger.debug("执行一个调度日志重做线程任务");
        }
    }

    /**
     * 关闭时是否等待任务完成
     * 默认是需要等待
     * @return
     */
    public boolean shutDownWaitForJobsToComplete(){
        return true;
    }

    /**
     * 停止等待时间（有些正在运行的任务可能需要等待一会）
     * 默认5秒
     * @return
     */
    public long getShutDownWaitSeconds(){
        return 5;
    }

    public boolean isEnableSchedule() {
        return enableSchedule;
    }

    public void setEnableSchedule(boolean enableSchedule) {
        this.enableSchedule = enableSchedule;
    }

    /**
     * 当前正在运行的job数
     * @return
     */
    public int getCurrentlyExecutingJobsCount(){
        if(enableSchedule){
            return quartzServer.getCurrentlyExecutingJobsCount();
        }else {
            return 0;
        }
    }

    /**
     * 返回正在调度执行的job
     *
     * @return
     */
    public List<TaskTrigger> getCurrentlyExecutingJobs() {
        return quartzServer.getCurrentlyExecutingJobs();
    }

    /**
     * 触发器是否正在被调度执行
     * @param taskTriggerId
     * @return
     */
    public boolean isExecuting(Long taskTriggerId) {
        if(!enableSchedule){
            return false;
        }
        return quartzServer.isExecuting(taskTriggerId);
    }

    public int getScheduleJobsCount(){
        if(enableSchedule){
            return quartzServer.getScheduleJobsCount();
        }else {
            return 0;
        }
    }

    /**
     * 是否已经被调度
     *
     * @param taskTriggerId
     * @return
     */
    public boolean isTaskTriggerExecuting(long taskTriggerId) {
        return quartzServer.isTaskTriggerExecuting(taskTriggerId);
    }

    /**
     * 获取触发器被添加的时间
     *
     * @param taskTriggerId
     * @return
     */
    public Date getAddTime(Long taskTriggerId,String groupName) {
        return quartzServer.getAddTime(taskTriggerId,groupName);
    }

    /**
     * 获取调度系统里的触发器信息
     *
     * @param taskTriggerId
     * @return
     */
    public TaskTrigger getScheduledTaskTrigger(Long taskTriggerId,String groupName) {
        return quartzServer.getScheduledTaskTrigger(taskTriggerId,groupName);
    }

    /**
     * 设置调度状态
     * 只有enableSchedule=true情况，才可以设置调度状态
     * 这里的调度状态只是表明是否把job加入到调度队列中，
     * 无法ScheduleStatus=true或者false，调度都是在运行中
     * @param b
     */
    public void setScheduleStatus(boolean b){
        if(!this.isEnableSchedule()){
            throw new ApplicationException(ScheduleErrorCode.SCHEDULE_NOT_ENABLED);
        }
        quartzServer.setScheduleStatus(b);
    }

    /**
     * 获取调度状态
     * @return
     */
    public boolean getScheduleStatus(){
        if(!enableSchedule){
            return false;
        }
        return quartzServer.getScheduleStatus();
    }

    /**
     * 获取调度信息列表
     * @return
     */
    public ScheduleInfo getScheduleInfo() {
        ScheduleInfo si = new ScheduleInfo();
        si.setDeployId(quartzServer.getQuartzSource().getDeployId());
        si.setCheck(quartzMonitorThread.isCheck());
        si.setInterval(quartzMonitorThread.getInterval());
        si.setSchedule(enableSchedule);
        si.setScheduleJobsCount(quartzServer.getScheduleJobsCount());
        si.setCurrentlyExecutingJobsCount(quartzServer
                .getCurrentlyExecutingJobsCount());
        si.setSupportDistri(quartzSource.isSupportDistri());
        return si;
    }

    /**
     * 检查调度
     * @param isForce
     */
    public boolean checkAndRefreshSchedule(boolean isForce) {
        try {
            if (enableSchedule) {
                synchronized (quartzServer) {
                    quartzServer.refreshSchedule(isForce);
                }
            } else {
                logger.warn("调度服务没有开启");
            }
            return true;
        } catch (Exception e) {
            logger.error("检查调度异常",e);
            return false;
        }

    }

    /**
     * 刷新调度
     * @param tt
     */
    public boolean refreshTask(TaskTrigger tt) {
        return quartzServer.refreshTask(tt);
    }

    /**
     * 刷新调度
     * @param taskTriggerId
     */
    public boolean refreshTask(Long taskTriggerId) {
        TaskTrigger tt = quartzSource.getSchedulePersistentProcessor().selectTaskTrigger(taskTriggerId);
        return this.refreshTask(tt);
    }


    /**
     * 检查是否可以运行
     * @param tt
     * @return
     */
    public boolean checkCanRun(TaskTrigger tt){
        if(tt.getTriggerStatus()!=TriggerStatus.ENABLE){
            return false;
        }
        String deployId = this.quartzSource.getDeployId();
        boolean sd = this.quartzSource.isSupportDistri();
        //如果是部署点一致，可以运行
        if(deployId.equals(tt.getDeployId())){
            return true;
        }else if(sd&&tt.getDistriable()){
            //部署点不一致，必须服务器和调度本身都需要支持分布式
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<HandlerCmd> getSupportCmdList() {
        List<HandlerCmd> list = new ArrayList<>();
        list.add(new HandlerCmd("checkAndRefreshSchedule","刷新调度"));
        list.add(new HandlerCmd("setStatusTrue","启动调度"));
        list.add(new HandlerCmd("setStatusFalse","停止调度"));
        return list;
    }

    @Override
    public HandlerResult handle(String cmd) {
        if("reloadFunctions".equals(cmd)){
            checkAndRefreshSchedule(false);
        }else if("setStatusTrue".equals(cmd)){
            setScheduleStatus(true);
        }else if("setStatusFalse".equals(cmd)){
            setScheduleStatus(false);
        }
        return super.handle(cmd);
    }

    @Override
    public HandlerInfo getHandlerInfo() {
        HandlerInfo hi = super.getHandlerInfo();
        hi.addDetail("EnableSchedule",String.valueOf(enableSchedule));
        if(enableSchedule){
            hi.addDetail("ScheduleStatus",String.valueOf(getScheduleStatus()));
        }
        hi.addDetail("ScheduleJobsCount",String.valueOf(getScheduleJobsCount()));
        hi.addDetail("CurrentlyExecutingJobsCount",String.valueOf(getCurrentlyExecutingJobsCount()));
        return hi;
    }
}
