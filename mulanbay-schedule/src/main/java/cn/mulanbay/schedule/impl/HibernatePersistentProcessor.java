package cn.mulanbay.schedule.impl;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.SchedulePersistentProcessor;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.domain.TaskServer;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.enums.RedoType;
import cn.mulanbay.schedule.enums.TriggerStatus;

import java.util.Date;
import java.util.List;

/**
 * 调度持久层的hibernate实现
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class HibernatePersistentProcessor  extends BaseHibernateDao implements SchedulePersistentProcessor {

    /**
     * 更新新的调度执行后的调度
     *
     * @param taskTrigger
     */
    @Override
    public void updateTaskTriggerForNewJob(TaskTrigger taskTrigger) {
        try {
            TaskTrigger dbBean = this.selectTaskTrigger(taskTrigger.getId());
            dbBean.setTotalCount(taskTrigger.getTotalCount());
            dbBean.setFailCount(taskTrigger.getFailCount());
            dbBean.setLastExecuteResult(taskTrigger.getLastExecuteResult());
            dbBean.setLastExecuteTime(taskTrigger.getLastExecuteTime());
            dbBean.setNextExecuteTime(taskTrigger.getNextExecuteTime());
            this.updateEntity(dbBean);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,"更新新的调度执行后的调度失败！",e);
        }
    }

    /**
     * 保存调度日志
     * @param taskLog
     */
    @Override
    public void saveTaskLog(TaskLog taskLog) {
        try {
            this.saveEntity(taskLog);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,"保存调度日志失败！",e);
        }
    }

    /**
     * 获取调度日志
     * @param logId
     * @return
     */
    @Override
    public TaskLog selectTaskLog(Long logId) {
        try {
            return (TaskLog) this.getEntityById(TaskLog.class,logId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,"获取调度日志失败！",e);
        }
    }

    /**
     * 获取调度器
     * @param triggerId
     * @return
     */
    @Override
    public TaskTrigger selectTaskTrigger(Long triggerId) {
        try {
            return (TaskTrigger) this.getEntityById(TaskTrigger.class,triggerId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,"获取调度器失败！",e);
        }
    }

    /**
     * 更新调度日志
     * @param taskLog
     */
    @Override
    public void updateTaskLog(TaskLog taskLog) {
        try {
            this.updateEntity(taskLog);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,"更新调度日志失败！",e);
        }
    }

    /**
     * 更新重做后的调度执行后的调度
     * @param taskTrigger
     */
    @Override
    public void updateTaskTriggerForRedoJob(TaskTrigger taskTrigger) {
        this.updateTaskTriggerForNewJob(taskTrigger);
    }

    /**
     * 更新调度状态
     * @param triggerId
     * @param status
     */
    @Override
    public void updateTaskTriggerStatus(Long triggerId, TriggerStatus status) {
        try {
            String hql="update TaskTrigger set status=?0 where id=?1";
            this.updateEntities(hql,status,triggerId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,"更新调度状态失败！",e);

        }
    }

    /**
     * 获取有效的调度器
     * @param deployId
     * @param supportDistri
     * @return
     */
    @Override
    public List<TaskTrigger> getActiveTaskTrigger(String deployId, boolean supportDistri) {
        try {
            String hql="from TaskTrigger where triggerStatus=?0 ";
            if(supportDistri){
                hql+="and (deployId=?1 or distriable=1)";
            }else {
                hql+="and deployId=?1 ";
            }
            return this.getEntityListNoPageHQL(hql,TriggerStatus.ENABLE,deployId);
        } catch (BaseException e) {
            throw new PersistentException(ScheduleErrorCode.TRIGGER_GET_ACTIVE_LIST_ERROR,"获取有效的调度器失败！",e);
        }
    }

    /**
     * 检查调度日志是否存在
     * @param taskTriggerId
     * @param bussDate
     * @return
     */
    @Override
    public boolean isTaskLogExit(Long taskTriggerId, Date bussDate) {
        try {
            String hql="select count(0) from TaskLog where taskTrigger.id=?0 and bussDate=?1 ";
            long n = this.getCount(hql,taskTriggerId,bussDate);
            return n>0 ? true : false;
        } catch (BaseException e) {
            throw new PersistentException(ScheduleErrorCode.TRIGGER_LOG_CHECK_ERROR,"检查调度日志是否存在失败！",e);
        }
    }

    @Override
    public boolean isTaskLogExit(String scheduleIdentityId) {
        try {
            String hql="select count(0) from TaskLog where scheduleIdentityId=?0";
            long n = this.getCount(hql,scheduleIdentityId);
            return n>0 ? true : false;
        } catch (BaseException e) {
            throw new PersistentException(ScheduleErrorCode.TRIGGER_LOG_CHECK_ERROR,"检查调度日志是否存在失败！",e);
        }
    }

    /**
     * 获取自动重做的调度任务
     * @param deployId
     * @param supportDistri
     * @param startDate 最小开始时间
     * @param endDate 最大开始时间
     * @return
     */
    @Override
    public List<TaskLog> getAutoRedoTaskLogs(String deployId, boolean supportDistri, Date startDate,Date endDate) {

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select tl from TaskLog tl,TaskTrigger tt where tl.taskTrigger.id = tt.id and tl.executeResult=?0 ");
            sb.append("and (tt.redoType=?1 or tt.redoType=?2) ");
            if(supportDistri){
                sb.append("and (tt.deployId=?3 or tt.distriable=1) ");
            }else{
                sb.append("and tt.deployId=?3 ");
            }
            sb.append("and tl.startTime>=?4 and tl.startTime<=?5 ");
            sb.append("and tl.redoTimes < tt.allowedRedoTimes ");
            return this.getEntityListNoPageHQL(sb.toString(), JobExecuteResult.FAIL,
                    RedoType.AUTO_REDO,RedoType.ALL_REDO,deployId,startDate,endDate);
        } catch (BaseException e) {
            throw new PersistentException(ScheduleErrorCode.TRIGGER_GET_AUTO_REDO_LOG_ERROR,"获取自动重做的调度任务失败！",e);

        }
    }

    /**
     * 更新获取保存
     * @param taskServer
     */
    @Override
    public void updateTaskServer(TaskServer taskServer) {
        try {
            this.saveAndUpdateEntity(taskServer);
        } catch (BaseException e) {
            throw new PersistentException(ScheduleErrorCode.UPDATE_TASK_SERVER_ERROR,"更新调度服务器信息异常！",e);
        }
    }

    @Override
    public TaskServer selectTaskServer(String deployId) {
        try {
            String hql="from TaskServer where deployId=?0 ";
            return (TaskServer) this.getEntityForOne(hql,deployId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,"获取调度器服务器信息ß失败！",e);
        }
    }
}
