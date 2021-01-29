package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import cn.mulanbay.pms.persistent.dto.SystemLogAnalyseStat;
import cn.mulanbay.pms.persistent.enums.IdFieldType;
import cn.mulanbay.pms.persistent.service.LogService;
import cn.mulanbay.pms.web.bean.request.log.SystemLogAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.request.log.SystemLogSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieDetailData;
import cn.mulanbay.pms.web.bean.response.log.OperationBeanDetailResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 系统日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController {

    private static Class<SystemLog> beanClass = SystemLog.class;

    @Autowired
    LogService logService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SystemLogSearch sf) {
        return callbackDataGrid(getSystemLogResult(sf));
    }

    private PageResult<SystemLog> getSystemLogResult(SystemLogSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("occurTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<SystemLog> qr = baseService.getBeanResult(pr);
        return qr;
    }


    /**
     * 获取日志参数
     *
     * @return
     */
    @RequestMapping(value = "/getParas", method = RequestMethod.GET)
    public ResultBean getParas(Long id) {
        SystemLog br = logService.getSystemLog(id);
        return callback(br.getParas());
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(Long id) {
        SystemLog br = logService.getSystemLog(id);
        return callback(br);
    }

    /**
     * 查询被操作的业务对象的数据
     *
     * @param id 为操作日志的记录号
     * @return
     */
    @RequestMapping(value = "/getBeanDetail", method = RequestMethod.GET)
    public ResultBean getBeanDetail(Long id) {
        SystemLog br = baseService.getObject(beanClass, id);
        String idValue = br.getIdValue();
        if (StringUtil.isEmpty(idValue)) {
            throw new ApplicationException(PmsErrorCode.OPERATION_LOG_BEAN_ID_NULL);
        } else {
            OperationBeanDetailResponse response = new OperationBeanDetailResponse();
            response.setIdValue(idValue);
            response.setBeanName(br.getSystemFunction().getBeanName());
            Serializable bussId = formatIdValue(br.getSystemFunction().getIdFieldType(), idValue);
            Object o = baseService.getObject(br.getSystemFunction().getBeanName(), bussId, br.getSystemFunction().getIdField());
            response.setBeanData(o);
            return callback(response);
        }
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
     * 统计分析
     *
     * @return
     */
    @RequestMapping(value = "/analyseStat")
    public ResultBean analyseStat(SystemLogAnalyseStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("系统日志分析");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("次数");
        List<SystemLogAnalyseStat> list = logService.analyseStatDateOperationLog(sf);
        for (SystemLogAnalyseStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getTotalCount());
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }
}
