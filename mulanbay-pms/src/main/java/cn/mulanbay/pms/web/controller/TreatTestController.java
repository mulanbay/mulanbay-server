package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.TreatOperation;
import cn.mulanbay.pms.persistent.domain.TreatTest;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.TreatTestResult;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.health.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 看病的检测结果
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/treatTest")
public class TreatTestController extends BaseController {

    private static Class<TreatTest> beanClass = TreatTest.class;

    @Autowired
    TreatService treatService;

    /**
     * 获取检测的分类列表
     *
     * @return
     */
    @RequestMapping(value = "/getTreatTestCategoryTree")
    public ResultBean getTreatTestCategoryTree(TreatTestCategorySearch sf) {
        try {
            List<String> categoryList = treatService.getTreatTestCategoryList(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            int i = 0;
            for (String gt : categoryList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt);
                tb.setText(gt);
                list.add(tb);
                i++;
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取检测的分类列表异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TreatTestSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", sf.getSort());
        pr.addSort(s);
        PageResult<TreatTest> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid TreatTestFormRequest formRequest) {
        TreatTest bean = new TreatTest();
        BeanCopy.copyProperties(formRequest, bean);
        TreatOperation treatOperation = this.getUserEntity(TreatOperation.class, formRequest.getTreatOperationId(), formRequest.getUserId());
        bean.setTreatOperation(treatOperation);
        if (bean.getResult() == null) {
            if ((bean.getMinValue() == null || bean.getMaxValue() == null) && StringUtil.isEmpty(bean.getReferScope())) {
                return callbackErrorInfo("没有参考范围值时，必须手动设置分析结果");
            } else {
                bean.setResult(getResult(bean));
            }
        }
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }

    /**
     * 计算检查结果
     *
     * @param bean
     * @return
     */
    private TreatTestResult getResult(TreatTest bean) {
        String v = bean.getValue();
        boolean isNum = NumberUtil.isNumber(v);
        if (isNum) {
            double b = Double.valueOf(v);
            if (b < bean.getMinValue()) {
                return TreatTestResult.LOWER;
            } else if (b > bean.getMaxValue()) {
                return TreatTestResult.HIGHER;
            } else {
                return TreatTestResult.NORMAL;
            }
        } else {
            String rc = bean.getReferScope();
            if (StringUtil.isNotEmpty(rc)) {
                if (v.equals(rc)) {
                    return TreatTestResult.NORMAL;
                } else {
                    return TreatTestResult.HIGHER;
                }
            }
            if ("正常".equals(v) || "阴性".equals(v)) {
                return TreatTestResult.NORMAL;
            } else {
                return TreatTestResult.HIGHER;
            }
        }
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        TreatTest bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid TreatTestFormRequest formRequest) {
        TreatTest bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        TreatOperation treatOperation = this.getUserEntity(TreatOperation.class, formRequest.getTreatOperationId(), formRequest.getUserId());
        bean.setTreatOperation(treatOperation);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 获取最近一次的检查
     *
     * @return
     */
    @RequestMapping(value = "/getLastTest", method = RequestMethod.GET)
    public ResultBean getLastTest(@Valid TreatTestLastGetRequest getRequest) {
        TreatTest bean = treatService.getLastTreatTest(getRequest.getName(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid TreatTestStatSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setPage(-1);
        pr.setBeanClass(beanClass);
        Sort s = new Sort("testDate", Sort.ASC);
        pr.addSort(s);
        PageResult<TreatTest> qr = baseService.getBeanResult(pr);
        List<TreatTest> list = qr.getBeanList();
        if (list.isEmpty()) {
            return callback(null);
        } else {
            String vt = list.get(0).getValue();
            Map res = new HashMap();
            if (NumberUtil.isNumber(vt)) {
                //柱状图
                res.put("chartType", ChartType.LINE);
                res.put("chartData", createStatBarData(list, sf.getName()));
            } else {
                //饼图
                res.put("chartType", ChartType.PIE);
                res.put("chartData", createStatPieData(list, sf.getName()));
            }
            return callback(res);
        }
    }

    /**
     * 封装检查报告的柱状图数据
     *
     * @param list
     * @return
     */
    private ChartData createStatBarData(List<TreatTest> list, String name) {
        ChartData chartData = new ChartData();
        chartData.setTitle("[" + name + "]的检查报告");
        chartData.setLegendData(new String[]{"值"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("值");
        for (TreatTest bean : list) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getTestDate(), DateUtil.FormatDay1));
            yData1.getData().add(bean.getValue());
        }
        chartData.getYdata().add(yData1);
        TreatTest t1 = list.get(0);
        if (t1.getMinValue() != null && t1.getMaxValue() != null) {
            String subTitle = "参考值范围:" + t1.getMinValue() + "~" + t1.getMaxValue();
            chartData.setSubTitle(subTitle);
        }
        return chartData;
    }

    /**
     * 封装检查报告的饼状图数据
     *
     * @param list
     * @return
     */
    private ChartPieData createStatPieData(List<TreatTest> list, String name) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("[" + name + "]的检查报告");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("次");
        Map<String, Integer> md = new HashMap<>();
        for (TreatTest bean : list) {
            Integer n = md.get(bean.getValue());
            if (n == null) {
                md.put(bean.getValue(), 1);
            } else {
                md.put(bean.getValue(), n + 1);
            }
        }
        for (String key : md.keySet()) {
            chartPieData.getXdata().add(key);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(key);
            dataDetail.setValue(md.get(key));
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }
}
