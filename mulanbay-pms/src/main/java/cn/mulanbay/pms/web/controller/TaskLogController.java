package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.PmsScheduleHandler;
import cn.mulanbay.pms.persistent.service.PmsScheduleService;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.schedule.TaskLogCostTimeStatSearch;
import cn.mulanbay.pms.web.bean.request.schedule.TaskLogSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 调度日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/taskLog")
public class TaskLogController extends BaseController {

    private static Class<TaskLog> beanClass = TaskLog.class;

    @Autowired
    PmsScheduleHandler pmsScheduleHandler;

    @Autowired
    PmsScheduleService pmsScheduleService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TaskLogSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("startTime", Sort.DESC);
        pr.addSort(s1);
        PageResult<TaskLog> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 重做
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = RequestMethod.GET)
    public ResultBean redo(Long id) {
        pmsScheduleHandler.manualRedo(id, false);
        return callback(null);
    }

    /**
     * 花费时间统计
     *
     * @return
     */
    @RequestMapping(value = "/costTimeStat")
    public ResultBean costTimeStat(TaskLogCostTimeStatSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("startTime", Sort.ASC);
        pr.addSort(s1);
        PageResult<TaskLog> qr = baseService.getBeanResult(pr);
        TaskTrigger tt = baseService.getObject(TaskTrigger.class, sf.getTaskTriggerId());
        ChartData chartData = new ChartData();
        chartData.setTitle("[" + tt.getName() + "]执行时间统计");
        chartData.setUnit("毫秒");
        chartData.setLegendData(new String[]{"时长(毫秒)"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("时长(毫秒)");
        List<TaskLog> list = qr.getBeanList();
        for (TaskLog bean : list) {
            //chartData.getIntXData().add(1);
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getStartTime(), DateUtil.Format24Datetime));
            yData1.getData().add(bean.getCostTime());
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }

    /**
     * 获取最近一次的调度日志
     *
     * @return
     */
    @RequestMapping(value = "/getLastTaskLog", method = RequestMethod.GET)
    public ResultBean getLastTaskLog(Long taskTriggerId) {
        return callback(pmsScheduleService.getLastTaskLog(taskTriggerId));
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        TaskLog br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

}
