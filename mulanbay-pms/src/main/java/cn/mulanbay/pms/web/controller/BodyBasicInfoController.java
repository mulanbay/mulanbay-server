package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.BodyBasicInfo;
import cn.mulanbay.pms.persistent.dto.BodyBasicInfoDateStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoDateStatSearch;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoFormRequest;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoSearch;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoYoyStatSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 身体基本情况
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/bodyBasicInfo")
public class BodyBasicInfoController extends BaseController {

    private static Class<BodyBasicInfo> beanClass = BodyBasicInfo.class;

    @Autowired
    TreatService treatService;

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BodyBasicInfoSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("recordDate", Sort.DESC);
        pr.addSort(sort);
        PageResult<BodyBasicInfo> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BodyBasicInfoFormRequest formRequest) {
        BodyBasicInfo bean = new BodyBasicInfo();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        bean.setBmi(caleBmi(formRequest));
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
        BodyBasicInfo br = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BodyBasicInfoFormRequest formRequest) {
        BodyBasicInfo bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        bean.setBmi(caleBmi(formRequest));
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 计算BMI=体重（千克数） / 身高的平方（米数）
     *
     * @param formRequest
     * @return
     */
    private double caleBmi(BodyBasicInfoFormRequest formRequest) {
        double h = formRequest.getHeight() / 100.0;
        double w = formRequest.getWeight();
        double b = w / (h * h);
        BigDecimal bmi = new BigDecimal(b);
        return bmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
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
     * 基于日期的统计
     * 界面上使用echarts展示图表，后端返回的是核心模块的数据，不再使用Echarts的第三方jar包封装（比较麻烦）
     *
     * @return
     */
    @RequestMapping(value = "/dateStat", method = RequestMethod.GET)
    public ResultBean dateStat(@Valid BodyBasicInfoDateStatSearch sf) {
        List<BodyBasicInfoDateStat> list = treatService.statDateBodyBasicInfo(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("身体基本情况统计");
        chartData.setLegendData(new String[]{"身高", "体重", "BMI"});
        //混合图形下使用
        chartData.addYAxis("身高/体重","");
        chartData.addYAxis("BMI指数","");
        ChartYData yData1 = new ChartYData();
        yData1.setName("身高");
        ChartYData yData2 = new ChartYData();
        yData2.setName("体重");
        ChartYData yData3 = new ChartYData();
        yData3.setName("BMI");
        for (BodyBasicInfoDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(NumberUtil.getAverageValue(bean.getTotalHeight().doubleValue(), bean.getTotalCount().intValue(), 0));
            yData2.getData().add(NumberUtil.getAverageValue(bean.getTotalWeight().doubleValue(), bean.getTotalCount().intValue(), 1));
            yData3.getData().add(NumberUtil.getAverageValue(bean.getTotalBmi().doubleValue(), bean.getTotalCount().intValue(), 1));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData3);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    /**
     * 按照日期统计同期对比
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid BodyBasicInfoYoyStatSearch sf) {
        ChartData chartData = initYoyCharData(sf, "身体基本情况同期对比", null);
        String[] legendData = new String[sf.getYears().size()];
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            //数据,为了代码复用及统一，统计还是按照日期的统计
            BodyBasicInfoDateStatSearch dateSearch = new BodyBasicInfoDateStatSearch();
            dateSearch.setDateGroupType(sf.getDateGroupType());
            dateSearch.setStartDate(DateUtil.getDate(sf.getYears().get(i) + "-01-01", DateUtil.FormatDay1));
            dateSearch.setEndDate(DateUtil.getDate(sf.getYears().get(i) + "-12-31", DateUtil.FormatDay1));
            dateSearch.setUserId(sf.getUserId());
            ChartYData yData = new ChartYData();
            yData.setName(sf.getYears().get(i).toString());
            List<BodyBasicInfoDateStat> list = treatService.statDateBodyBasicInfo(dateSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (BodyBasicInfoDateStat bean : list) {
                temp.getIntXData().add(bean.getIndexValue());
                if (sf.getDateGroupType() == DateGroupType.MONTH) {
                    temp.getXdata().add(bean.getIndexValue() + "月份");
                } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
                    temp.getXdata().add("第" + bean.getIndexValue() + "周");
                } else {
                    temp.getXdata().add(bean.getIndexValue().toString());
                }
                if (sf.getGroupType() == GroupType.COUNT) {
                    yData.getData().add(bean.getTotalCount());
                } else if (sf.getGroupType() == GroupType.WEIGHT) {
                    yData.getData().add(NumberUtil.getAverageValue(bean.getTotalWeight().doubleValue(), bean.getTotalCount().intValue(), 1));
                } else if (sf.getGroupType() == GroupType.HEIGHT) {
                    yData.getData().add(NumberUtil.getAverageValue(bean.getTotalHeight().doubleValue(), bean.getTotalCount().intValue(), 1));
                } else if (sf.getGroupType() == GroupType.BMI) {
                    yData.getData().add(NumberUtil.getAverageValue(bean.getTotalBmi().doubleValue(), bean.getTotalCount().intValue(), 1));
                }
            }
            //临时内容，作为补全用
            temp.getYdata().add(yData);
            dateSearch.setCompliteDate(true);
            temp = ChartUtil.completeDate(temp, dateSearch);
            //设置到最终的结果集中
            chartData.getYdata().add(temp.getYdata().get(0));
        }
        chartData.setLegendData(legendData);
        chartData.setUnit(sf.getGroupType().getUnit());

        return callback(chartData);
    }


}
