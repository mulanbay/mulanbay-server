package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.StringCoderUtil;
import cn.mulanbay.pms.handler.PmsScheduleHandler;
import cn.mulanbay.pms.persistent.service.PmsScheduleService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.schedule.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.schedule.TaskTriggerVo;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.ScheduleInfo;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import cn.mulanbay.schedule.para.ParaBuilder;
import cn.mulanbay.schedule.para.ParaDefine;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 调度触发器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/taskTrigger")
public class TaskTriggerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TaskTriggerController.class);

    private static Class<TaskTrigger> beanClass = TaskTrigger.class;

    @Autowired
    PmsScheduleHandler pmsScheduleHandler;

    @Autowired
    PmsScheduleService pmsScheduleService;

    /**
     * 获取调度触发器列表
     *
     * @return
     */
    @RequestMapping(value = "/getTaskTriggerTree")
    public ResultBean getTaskTriggerTree(String groupName, Boolean needRoot) {

        try {
            TaskTriggerSearch sf = new TaskTriggerSearch();
            sf.setPage(-1);
            sf.setGroupName(groupName);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort s1 = new Sort("groupName", Sort.ASC);
            Sort s2 = new Sort("nextExecuteTime", Sort.ASC);
            pr.addSort(s1);
            pr.addSort(s2);
            PageResult<TaskTrigger> qr = baseService.getBeanResult(pr);
            List<TaskTrigger> gtList = qr.getBeanList();

            List<TreeBean> result = new ArrayList<>();
            String current = gtList.get(0).getGroupName();
            TreeBean typeTreeBean = new TreeBean();
            typeTreeBean.setId("P_" + current);
            typeTreeBean.setText(current);
            int n = gtList.size();
            for (int i = 0; i < n; i++) {
                TaskTrigger pc = gtList.get(i);
                TreeBean tb = new TreeBean();
                tb.setId(pc.getId().toString());
                tb.setText(pc.getName());
                if (pc.getGroupName().equals(current)) {
                    typeTreeBean.addChild(tb);
                }
                if (!pc.getGroupName().equals(current)) {
                    current = pc.getGroupName();
                    result.add(typeTreeBean);
                    typeTreeBean = new TreeBean();
                    typeTreeBean.setId("P_" + current);
                    typeTreeBean.setText(current);
                    typeTreeBean.addChild(tb);
                }
                if (i == n - 1) {
                    //最后一个
                    result.add(typeTreeBean);
                }
            }
            return callback(TreeBeanUtil.addRoot(result, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取调度触发器列表异常",
                    e);
        }
    }

    /**
     * 获取名称或者分组的各种分类归类
     *
     * @return
     */
    @RequestMapping(value = "/getTaskTriggerCategoryTree")
    public ResultBean getTaskTriggerCategoryTree(TaskTriggerCategorySearch sf) {

        try {
            List<String> categoryList = pmsScheduleService.getTaskTriggerCategoryList(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            int i = 0;
            for (String ss : categoryList) {
                TreeBean tb = new TreeBean();
                tb.setId(ss);
                tb.setText(ss);
                list.add(tb);
                i++;
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取名称或者分组的各种分类归类异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TaskTriggerSearch sf) {
        boolean isSchedule = pmsScheduleHandler.isEnableSchedule();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("isSchedule", isSchedule);
        data.put("scheduleStatus", pmsScheduleHandler.getScheduleStatus());
        PageResult<TaskTrigger> pr = getTaskTrigger(sf);
        List<TaskTrigger> beanList = pr.getBeanList();
        List<TaskTriggerVo> newList = new ArrayList<>();
        for (TaskTrigger tt : beanList) {
            TaskTriggerVo tb = new TaskTriggerVo();
            BeanCopy.copyProperties(tt, tb);
            if (tt.getTriggerStatus() == TriggerStatus.ENABLE) {
                boolean executing = pmsScheduleHandler.isExecuting(tt.getId());
                tb.setExecuting(executing);
            }
            newList.add(tb);
        }
        data.put("total", pr.getMaxRow());
        data.put("rows", newList);
        data.put("currentlyExecutingJobsCount",
                pmsScheduleHandler.getCurrentlyExecutingJobsCount());
        data.put("scheduleJobsCount", pmsScheduleHandler.getScheduleJobsCount());
        return callback(data);
    }

    private PageResult<TaskTrigger> getTaskTrigger(TaskTriggerSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("triggerStatus", Sort.DESC);
        Sort s2 = new Sort("nextExecuteTime", Sort.ASC);
        pr.addSort(s1);
        pr.addSort(s2);
        PageResult<TaskTrigger> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid TaskTriggerFormRequest formRequest) {
        TaskTrigger bean = new TaskTrigger();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setFailCount(0L);
        bean.setTotalCount(0L);
        //默认是系统的
        bean.setUserId(0L);
        bean.setCreatedTime(new Date());
        String triggerParas = StringCoderUtil.decodeJson(formRequest.getTriggerParas());
        ;
        String execTimePeriods = StringCoderUtil.decodeJson(formRequest.getExecTimePeriods());
        bean.setTriggerParas(triggerParas);
        bean.setExecTimePeriods(execTimePeriods);
        baseService.saveObject(bean);
        return callback(null);
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        TaskTrigger br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid TaskTriggerFormRequest formRequest) {
        TaskTrigger bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        String triggerParas = StringCoderUtil.decodeJson(formRequest.getTriggerParas());
        ;
        String execTimePeriods = StringCoderUtil.decodeJson(formRequest.getExecTimePeriods());
        bean.setTriggerParas(triggerParas);
        bean.setExecTimePeriods(execTimePeriods);
        bean.setModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 手动执行
     *
     * @return
     */
    @RequestMapping(value = "/manualNew", method = RequestMethod.POST)
    public ResultBean manualNew(@RequestBody TaskManualNewRequest tmnr) {
        pmsScheduleHandler.manualNew(tmnr.getTaskTriggerId(), tmnr.getBussDate(), tmnr.isSync(), null, null);
        return callback(null);
    }

    /**
     * 获取参数定义
     *
     * @return
     */
    @RequestMapping(value = "/getParaDefine", method = RequestMethod.GET)
    public ResultBean getParaDefine(String className) {
        try {
            AbstractBaseJob job = (AbstractBaseJob) Class.forName(className).newInstance();
            Class cls = job.getParaDefine();
            if (cls == null) {
                return callbackErrorInfo("该Job没有参数配置项.");
            }
            List<ParaDefine> list = ParaBuilder.buildDefine(cls);
            if (StringUtil.isEmpty(list)) {
                return callbackErrorInfo("该调度没有参数定义，无需配置");
            }
            Object defaultBean = Class.forName(job.getParaDefine().getName()).newInstance();
            String defaultConfig = JsonUtil.beanToJson(defaultBean);
            Map res = new HashMap();
            res.put("paraDefine", list);
            res.put("defaultConfig", defaultConfig);
            return callback(res);
        } catch (Exception e) {
            logger.error("getParaDefine异常", e);
            return callbackErrorInfo(e.getMessage());
        }
    }


    /**
     * 设置调度状态
     *
     * @return
     */
    @RequestMapping(value = "/changeScheduleStatus", method = RequestMethod.POST)
    public ResultBean changeScheduleStatus(@RequestBody @Valid ChangeScheduleStatusRequest cssr) {
        boolean enableSchedule = pmsScheduleHandler.isEnableSchedule();
        if (!enableSchedule) {
            return callbackErrorInfo("调度未开启，无法进行设置状态");
        }
        pmsScheduleHandler.setScheduleStatus(cssr.isAfterStatus());
        return callback(null);
    }

    /**
     * 刷新调度
     *
     * @return
     */
    @RequestMapping(value = "/refreshSchedule", method = RequestMethod.POST)
    public ResultBean refreshSchedule(@RequestBody @Valid RefreshScheduleRequest rsr) {
        boolean enableSchedule = pmsScheduleHandler.isEnableSchedule();
        if (!enableSchedule) {
            return callbackErrorInfo("调度未开启，无法进行刷新");
        }
        boolean b;
        if (rsr.getTaskTriggerId() != null) {
            //单个
            TaskTrigger tt = pmsScheduleService.selectTaskTrigger(rsr.getTaskTriggerId());
            boolean cr = pmsScheduleHandler.checkCanRun(tt);
            if (!cr) {
                return callbackErrorCode(ScheduleErrorCode.TRIGGER_CANNOT_RUN_HERE);
            }
            b = pmsScheduleHandler.refreshTask(tt);
        } else {
            b = pmsScheduleHandler.checkAndRefreshSchedule(rsr.getForce());
        }
        if (b) {
            return callback(null);
        } else {
            return callbackErrorCode(ScheduleErrorCode.REFRESH_TRIGGER_FAIL);
        }
    }

    /**
     * 获取调度信息
     *
     * @return
     */
    @RequestMapping(value = "/getScheduleInfo", method = RequestMethod.GET)
    public ResultBean getScheduleInfo() {
        ScheduleInfo si = pmsScheduleHandler.getScheduleInfo();
        List<TaskTrigger> ceJobs = pmsScheduleHandler.getCurrentlyExecutingJobs();
        Map res = new HashMap();
        res.put("scheduleInfo", si);
        res.put("currentlyExecutingJobs", ceJobs);
        return callback(res);
    }

    /**
     * 重置调度总执行次数
     *
     * @return
     */
    @RequestMapping(value = "/resetTotalCount", method = RequestMethod.POST)
    public ResultBean resetTotalCount(@RequestBody @Valid CommonBeanGetRequest cssr) {
        //单个
        TaskTrigger tt = pmsScheduleService.selectTaskTrigger(cssr.getId());
        boolean cr = pmsScheduleHandler.checkCanRun(tt);
        if (!cr) {
            return callbackErrorCode(ScheduleErrorCode.TRIGGER_CANNOT_RUN_HERE);
        }
        pmsScheduleService.resetTaskTriggerTotalCount(cssr.getId());
        //需要重新加载
        pmsScheduleHandler.refreshTask(cssr.getId());
        return callback(null);
    }

    /**
     * 重置调度总失败次数
     *
     * @return
     */
    @RequestMapping(value = "/resetFailCount", method = RequestMethod.POST)
    public ResultBean resetFailCount(@RequestBody @Valid CommonBeanGetRequest cssr) {
        //单个
        TaskTrigger tt = pmsScheduleService.selectTaskTrigger(cssr.getId());
        boolean cr = pmsScheduleHandler.checkCanRun(tt);
        if (!cr) {
            return callbackErrorCode(ScheduleErrorCode.TRIGGER_CANNOT_RUN_HERE);
        }
        pmsScheduleService.resetTaskTriggerFailCount(cssr.getId());
        //需要重新加载
        pmsScheduleHandler.refreshTask(cssr.getId());
        return callback(null);
    }

    /**
     * 获取调度信息
     *
     * @return
     */
    @RequestMapping(value = "/getScheduleDetail", method = RequestMethod.GET)
    public ResultBean getScheduleDetail(Long id) {
        Map<String, Object> res = new HashMap<>();
        TaskTrigger dbInfo = pmsScheduleService.selectTaskTrigger(id);
        res.put("dbInfo", dbInfo);
        TaskTrigger scheduleInfo = pmsScheduleHandler.getScheduledTaskTrigger(dbInfo.getId(), dbInfo.getGroupName());
        res.put("scheduleInfo", scheduleInfo);
        Date addToScheduleTime = pmsScheduleHandler.getAddTime(dbInfo.getId(), dbInfo.getGroupName());
        res.put("addToScheduleTime", addToScheduleTime);
        boolean isExecuting = pmsScheduleHandler.isTaskTriggerExecuting(dbInfo.getId());
        res.put("isExecuting", isExecuting);
        return callback(res);
    }

}
