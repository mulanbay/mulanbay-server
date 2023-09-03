package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.BodyAbnormalRecord;
import cn.mulanbay.pms.persistent.dto.BodyAbnormalRecordDateStat;
import cn.mulanbay.pms.persistent.dto.BodyAbnormalRecordStat;
import cn.mulanbay.pms.persistent.dto.BodyBasicInfoAvgStat;
import cn.mulanbay.pms.persistent.dto.TreatRecordAnalyseDetailStat;
import cn.mulanbay.pms.persistent.enums.BodyAbnormalRecordGroupType;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.bodyabnormal.*;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoDateStatSearch;
import cn.mulanbay.pms.web.bean.request.health.TreatRecordAnalyseDetailStatSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.health.BodyAbnormalRecordAnalyseResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 身体不适记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/bodyAbnormalRecord")
public class BodyAbnormalRecordController extends BaseController {

    private static Class<BodyAbnormalRecord> beanClass = BodyAbnormalRecord.class;

    @Autowired
    TreatService treatService;

    /**
     * 获取症状或者器官的各种分类归类
     *
     * @return
     */
    @RequestMapping(value = "/getAbnormalCategoryTree")
    public ResultBean getAbnormalCategoryTree(BodyAbnormalCategorySearch sf) {

        try {
            List<String> categoryList = treatService.getBodyAbnormalCategoryList(sf);
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
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取症状或者器官的各种分类归类异常",
                    e);
        }
    }

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BodyAbnormalRecordSearch sf) {
        return callbackDataGrid(getBodyAbnormalRecordResult(sf));
    }

    private PageResult<BodyAbnormalRecord> getBodyAbnormalRecordResult(BodyAbnormalRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("occurDate", Sort.DESC);
        pr.addSort(sort);
        PageResult<BodyAbnormalRecord> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BodyAbnormalFormRequest formRequest) {
        BodyAbnormalRecord bean = new BodyAbnormalRecord();
        BeanCopy.copyProperties(formRequest, bean);
        calAndSetLastDays(bean);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        BodyAbnormalRecord br = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BodyAbnormalFormRequest formRequest) {
        BodyAbnormalRecord bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        calAndSetLastDays(bean);
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 计算持续时间
     * @param bean
     */
    private void calAndSetLastDays(BodyAbnormalRecord bean) {
        if(bean.getLastDays()==null){
            int days = DateUtil.getIntervalDays(bean.getOccurDate(), bean.getFinishDate());
            bean.setLastDays(days + 1);
        }
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
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid BodyAbnormalRecordStatSearch sf) {
        //通过年份设置开始结束日期
        List<BodyAbnormalRecordStat> list = treatService.bodyAbnormalRecordStat(sf);
        BaseChartData data = null;
        if (!list.isEmpty()) {
            if (sf.getChartType() == ChartType.BAR) {
                data = this.createStatBarData(list, sf);
            } else {
                data = this.createStatPieData(list, sf);
            }
        }
        return callback(data);
    }

    /**
     * 封装日统计的饼状图数据
     *
     * @param list
     * @return
     */
    private ChartPieData createStatPieData(List<BodyAbnormalRecordStat> list, BodyAbnormalRecordStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("身体不适统计");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("次数");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (BodyAbnormalRecordStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getTotalCount());
            serieData.getData().add(dataDetail);
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
        }
        chartPieData.getDetailData().add(serieData);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次");
        chartPieData.setSubTitle(subTitle);
        return chartPieData;
    }

    /**
     * 统计柱状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartData createStatBarData(List<BodyAbnormalRecordStat> list, BodyAbnormalRecordStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("身体不适统计");
        chartData.setUnit("次");
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData = new ChartYData("次数","次");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (BodyAbnormalRecordStat bean : list) {
            chartData.getXdata().add(bean.getName());
            yData.getData().add(bean.getTotalCount());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
        }
        chartData.getYdata().add(yData);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次");
        chartData.setSubTitle(subTitle);
        return chartData;
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(@Valid BodyAbnormalRecordDateStatSearch sf) {
        List<BodyAbnormalRecordDateStat> list = treatService.statDateBodyAbnormalRecord(sf);
        if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
            return callback(createChartCalandarDataDateStat(sf));
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("身体不适统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"持续天数","次数"});
        //混合图形下使用
        chartData.addYAxis("持续天数","天");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("持续天数","天");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (BodyAbnormalRecordDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(bean.getTotalLastDays());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    private ChartCalendarData createChartCalandarDataDateStat(BodyAbnormalRecordDateStatSearch sf) {
        List<BodyAbnormalRecordDateStat> list = treatService.statDateBodyAbnormalRecord(sf);
        ChartCalendarData calandarData = ChartUtil.createChartCalendarData("身体不适统计", "持续天数", "天", sf, list);
        if (!StringUtil.isEmpty(sf.getDisease())) {
            BodyAbnormalRecordByDiseaseSearch bards = new BodyAbnormalRecordByDiseaseSearch();
            BeanCopy.copyProperties(sf, bards);
            PageRequest pr = bards.buildQuery();
            pr.setBeanClass(beanClass);
            List<BodyAbnormalRecord> dd = baseService.getBeanList(pr);
            for (BodyAbnormalRecord bar : dd) {
                calandarData.addGraph(bar.getOccurDate(), bar.getLastDays());
            }
        } else {
            calandarData.setTop(3);
        }
        return calandarData;
    }

    /**
     * 分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid BodyAbnormalRecordStatSearch sf) {
        //通过年份设置开始结束日期
        List<BodyAbnormalRecordAnalyseResponse> responseList = new ArrayList<>();
        List<BodyAbnormalRecordStat> list = treatService.bodyAbnormalRecordStat(sf);
        BodyBasicInfoDateStatSearch bbidss = new BodyBasicInfoDateStatSearch();
        bbidss.setStartDate(sf.getStartDate());
        bbidss.setEndDate(sf.getEndDate());
        bbidss.setUserId(sf.getUserId());
        TreatRecordAnalyseDetailStatSearch tradss = new TreatRecordAnalyseDetailStatSearch();
        BeanCopy.copyProperties(sf, tradss);
        BodyBasicInfoAvgStat statAvg = treatService.statAvgBodyBasicInfo(bbidss);
        long id = 1;
        for (BodyAbnormalRecordStat stat : list) {
            BodyAbnormalRecordAnalyseResponse response = new BodyAbnormalRecordAnalyseResponse();
            BeanCopy.copyProperties(stat, response);
            if (sf.getGroupField() == BodyAbnormalRecordGroupType.ORGAN) {
                //根据不同类型选择不同的查询方式
                tradss.setOrgan(stat.getName());
            } else {
                tradss.setDisease(stat.getName());
            }
            TreatRecordAnalyseDetailStat ss = treatService.statDetailTreatRecordAnalyse(tradss);
            response.setTreatRecordStat(ss);
            //获取个人基本信息
            response.setAvgHeight(statAvg.getAvgHeight());
            response.setAvgWeight(statAvg.getAvgWeight());
            response.setId(id);
            responseList.add(response);
            id++;
        }
        PageResult<BodyAbnormalRecordAnalyseResponse> result = new PageResult();
        result.setBeanList(responseList);
        result.setMaxRow(responseList.size());
        return callbackDataGrid(result);
    }

}
