package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.OperationLog;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.dto.OperationLogDateStat;
import cn.mulanbay.pms.persistent.dto.OperationLogStat;
import cn.mulanbay.pms.persistent.dto.OperationLogTreeStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.IdFieldType;
import cn.mulanbay.pms.persistent.enums.LogCompareType;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.LogService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.log.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.log.OperationBeanDetailResponse;
import cn.mulanbay.pms.web.bean.response.log.OperationLogCompareResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 操作日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/operationLog")
public class OperationLogController extends BaseController {

    private static Class<OperationLog> beanClass = OperationLog.class;

    @Autowired
    AuthService authService;

    @Autowired
    LogService logService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(OperationLogSearch sf) {
        return callbackDataGrid(getOperationLogResult(sf));
    }

    private PageResult<OperationLog> getOperationLogResult(OperationLogSearch sf) {
        String beanName = sf.getBeanName();
        if (StringUtil.isNotEmpty(beanName)) {
            String beanNameSql = "systemFunction.id in (select id from SystemFunction where beanName='" + beanName + "' )";
            sf.setBeanName(beanNameSql);
        }
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("occurEndTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<OperationLog> qr = baseService.getBeanResult(pr);
        return qr;
    }


    /**
     * 获取请求参数
     *
     * @return
     */
    @RequestMapping(value = "/getParas", method = RequestMethod.GET)
    public ResultBean getParas(Long id) {
        OperationLog br = baseService.getObject(beanClass, id);
        return callback(br.getParas());
    }

    /**
     * 获取返回数据
     *
     * @return
     */
    @RequestMapping(value = "/getReturnData", method = RequestMethod.GET)
    public ResultBean getReturnData(Long id) {
        OperationLog br = baseService.getObject(beanClass, id);
        return callback(br.getReturnData());
    }

    /**
     * 查询被操作的业务对象的数据
     *
     * @param id 为操作日志的记录号
     * @return
     */
    @RequestMapping(value = "/getBeanDetail", method = RequestMethod.GET)
    public ResultBean getBeanDetail(Long id) {
        OperationLog br = baseService.getObject(beanClass, id);
        String idValue = br.getIdValue();
        if (StringUtil.isEmpty(idValue)) {
            throw new ApplicationException(PmsErrorCode.OPERATION_LOG_BEAN_ID_NULL);
        } else {
            OperationBeanDetailResponse response = new OperationBeanDetailResponse();
            response.setIdValue(idValue);
            response.setBeanName(br.getSystemFunction().getBeanName());
            Serializable bussId = formatIdValue(br.getSystemFunction().getIdFieldType(), idValue);
            String idFiled = this.formatIdField(br.getSystemFunction().getIdField());
            Object o = baseService.getObject(br.getSystemFunction().getBeanName(), bussId, idFiled);
            response.setBeanData(o);
            return callback(response);
        }
    }

    /**
     * 获取操作日志的比对数据：最新的数据、当前的数据、往前（或往后）的数据
     *
     * @param id 为OperationLog的主键
     * @return
     */
    @RequestMapping(value = "/getCompareData", method = RequestMethod.GET)
    public ResultBean getCompareData(Long id, LogCompareType compareType) {
        OperationLogCompareResponse response = new OperationLogCompareResponse();
        //获取当前日志记录的数据
        OperationLog br = baseService.getObject(beanClass, id);
        response.setCurrentData(br);
        response.setBussId(br.getIdValue());
        SystemFunction sf = br.getSystemFunction();
        String idValue = getAndUpdateIdValue(br);
        if (sf != null) {
            //获取业务表最新的数据
            Serializable bussId = formatIdValue(sf.getIdFieldType(), idValue);
            String idFiled = this.formatIdField(sf.getIdField());
            Object o = baseService.getObject(sf.getBeanName(), bussId, idFiled);
            if (o != null) {
                response.setLatestData(o);
            }
        }
        OperationLog nearest = logService.getNearestCompareLog(br, compareType);
        response.setCompareData(nearest);
        return callback(response);
    }

    /**
     * 获取某个具体业务bean的比对数据：最新的数据、当前的数据、往前（或往后）的数据
     *
     * @param gr:id 为beanName的主键
     * @return
     */
    @RequestMapping(value = "/getEditLogData", method = RequestMethod.GET)
    public ResultBean getEditLogData(@Valid OperationLogGetEditRequest gr) {
        OperationLogCompareResponse response = new OperationLogCompareResponse();
        SystemFunction sf = logService.getEditSystemFunction(gr.getBeanName());
        if (sf == null) {
            throw new ApplicationException(PmsErrorCode.SYSTEM_FUNCTION_NOT_DEFINE, gr.getBeanName() + "修改类功能点没有定义");
        }
        //获取业务表最新的数据
        Serializable bussId = formatIdValue(sf.getIdFieldType(), gr.getId());
        response.setBussId(gr.getId());
        String idFiled = this.formatIdField(sf.getIdField());
        Object o = baseService.getObject(sf.getBeanName(), bussId, idFiled);
        if (o != null) {
            response.setLatestData(o);
        }
        //获取最近一次修改
        OperationLog latest = logService.getLatestOperationLog(gr.getId(), gr.getBeanName());
        if (latest != null) {
            response.setCurrentData(latest);
            //获取最近的修改记录比较
            OperationLog nearest = logService.getNearestCompareLog(latest, gr.getCompareType());
            response.setCompareData(nearest);
        }
        return callback(response);
    }

    private Serializable formatIdValue(IdFieldType idFieldType, String idValue) {
        Serializable bussId = null;
        if (idFieldType == IdFieldType.LONG) {
            bussId = Long.valueOf(idValue);
        } else if (idFieldType == IdFieldType.INTEGER) {
            bussId = Integer.valueOf(idValue);
        } else if (idFieldType == IdFieldType.SHORT) {
            bussId = Short.valueOf(idValue);
        } else {
            bussId = idValue;
        }
        return bussId;
    }

    /**
     * 删除操作因为支持多个，索引会多加了s，实际上字段是id
     * @param idField
     * @return
     */
    private String formatIdField(String idField) {
        if("ids".equals(idField)){
            return "id";
        }
        return idField;
    }

    private String getAndUpdateIdValue(OperationLog br) {
        String idValue = br.getIdValue();
        if (StringUtil.isEmpty(idValue)) {
            //从paras重新获取
            Map map = (Map) JsonUtil.jsonToBean(br.getParas(), Map.class);
            Object o = map.get(br.getSystemFunction().getIdField());
            if (o != null) {
                idValue = o.toString();
                br.setIdValue(idValue);
                //更新
                baseService.updateObject(br);
            }
        }
        return idValue;
    }

    /**
     * 获取比对数据：最新的数据、当前的数据、往前（或往后）的数据
     *
     * @param currentCompareId 目前正在比较的OperationLog的主键(比较页面的中间那个区域)
     * @return
     */
    @RequestMapping(value = "/getNearstCompareData", method = RequestMethod.GET)
    public ResultBean getNearstCompareData(Long currentCompareId, LogCompareType compareType) {
        OperationLogCompareResponse response = new OperationLogCompareResponse();
        if (currentCompareId == null) {
            return callback(response);
        }
        OperationLog currentCompareLog = baseService.getObject(beanClass, currentCompareId);
        if (StringUtil.isEmpty(currentCompareLog.getIdValue())) {
            //idValue无法比较
            throw new ApplicationException(PmsErrorCode.OPERATION_LOG_COMPARE_ID_VALUE_NULL);
        }
        OperationLog nextCompareLog = logService.getNearestCompareLog(currentCompareLog, compareType);
        response.setCompareData(nextCompareLog);
        OperationLog currentLog = baseService.getObject(OperationLog.class, currentCompareId);
        response.setCurrentData(currentLog);
        return callback(response);
    }

    /**
     * 管理功能点ID
     *
     * @return
     */
    @RequestMapping(value = "/setFunctionId", method = RequestMethod.GET)
    public ResultBean setFunctionId(boolean needReSet) {
        logService.setOperationLogFunctionId(needReSet);
        return callback(null);
    }

    /**
     * 设置操作日志中的主键值
     *
     * @return
     */
    @RequestMapping(value = "/setIdValue", method = RequestMethod.GET)
    public ResultBean setIdValue() {
        List<OperationLog> list = logService.getOperationLogByIdValueWithNull();
        for (OperationLog log : list) {
            logService.setIdValue(log);
        }
        return callback(null);
    }


    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/treeStat", method = RequestMethod.GET)
    public ResultBean treeStat(OperationLogTreeStatSearch sf) {
        //用户查询
        if(StringUtil.isNotEmpty(sf.getUsername())){
            User user = authService.getUserByUsernameOrPhone(sf.getUsername());
            if (user == null) {
                return callbackErrorCode(ErrorCode.USER_NOTFOUND);
            }
            sf.setUserId(user.getId());
        }
        List<OperationLogTreeStat> list = logService.treeStatOperationLog(sf);
        ChartTreeDetailData data = new ChartTreeDetailData(0, "根");
        for (OperationLogTreeStat sl : list) {
            String parentName = sl.getPname();
            if (StringUtil.isEmpty(parentName)) {
                parentName = "未分类";
            }
            ChartTreeDetailData level1 = data.findChild(parentName);
            if (level1 == null) {
                level1 = new ChartTreeDetailData(0, parentName);
                level1.addChild(sl.getTotalCount().doubleValue(), sl.getName());
                data.addChild(level1);
            } else {
                level1.addChild(sl.getTotalCount().doubleValue(), sl.getName());
            }
        }
        ChartTreeData treeData = new ChartTreeData();
        treeData.setData(data);
        return callback(treeData);
    }

    /**
     * 基于分页的统计
     * @param sf
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(OperationLogStatSearch sf) {
        //用户查询
        if(StringUtil.isNotEmpty(sf.getUsername())){
            User user = authService.getUserByUsernameOrPhone(sf.getUsername());
            if (user == null) {
                return callbackErrorCode(ErrorCode.USER_NOTFOUND);
            }
            sf.setUserId(user.getId());
        }
        List<OperationLogStat> list = logService.statOperationLog(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("操作日志统计");
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        for (OperationLogStat bean : list) {
            chartData.getXdata().add(bean.getName());
            yData1.getData().add(bean.getTotalCount());
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(OperationLogDateStatSearch sf) {
        //用户查询
        if(StringUtil.isNotEmpty(sf.getUsername())){
            User user = authService.getUserByUsernameOrPhone(sf.getUsername());
            if (user == null) {
                return callbackErrorCode(ErrorCode.USER_NOTFOUND);
            }
            sf.setUserId(user.getId());
        }
        if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
            return callback(createChartCalendarData(sf));
        }
        List<OperationLogDateStat> list = logService.statDateOperationLog(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("操作日志统计");
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        for (OperationLogDateStat bean : list) {
            chartData.getIntXData().add(bean.getIndexValue());
            if (sf.getDateGroupType() == DateGroupType.MONTH) {
                chartData.getXdata().add(bean.getIndexValue() + "月份");
            } else if (sf.getDateGroupType() == DateGroupType.YEAR) {
                chartData.getXdata().add(bean.getIndexValue() + "年");
            } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
                chartData.getXdata().add("第" + bean.getIndexValue() + "周");
            } else {
                chartData.getXdata().add(bean.getIndexValue().toString());
            }
            yData1.getData().add(bean.getTotalCount());
        }
        chartData.getYdata().add(yData1);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    private ChartCalendarData createChartCalendarData(OperationLogDateStatSearch sf) {
        List<OperationLogDateStat> list = logService.statDateOperationLog(sf);
        ChartCalendarData calendarData = ChartUtil.createChartCalendarData("操作日志统计", "操作次数", "次", sf, list);
        calendarData.setTop(3);
        return calendarData;
    }

}
