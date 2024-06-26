package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.TreatRecord;
import cn.mulanbay.pms.persistent.domain.UserSetting;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordRelationSearch;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * 看病记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/treatRecord")
public class TreatRecordController extends BaseController {

    private static Class<TreatRecord> beanClass = TreatRecord.class;

    @Autowired
    TreatService treatService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    DataService dataService;

    @Autowired
    AuthService authService;
    /**
     * 获取看病或者器官的各种分类归类
     *
     * @return
     */
    @RequestMapping(value = "/getTreatCategoryTree")
    public ResultBean getTreatCategoryTree(TreatCategorySearch sf) {
        try {
            Date[] ds = systemConfigHandler.getDateRange(sf.getDays(),"treat.category.days");
            sf.setStartDate(ds[0]);
            sf.setEndDate(ds[1]);

            List<String> categoryList = treatService.getTreatCategoryList(sf);
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
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取看病的各种分类归类异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TreatRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("treatDate", Sort.DESC);
        pr.addSort(s);
        PageResult<TreatRecord> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid TreatRecordFormRequest formRequest) {
        boolean syncToConsume = formRequest.getSyncToConsume()==null ? true : formRequest.getSyncToConsume();
        UserSetting us = authService.getUserSetting(formRequest.getUserId());
        this.checkConsumeSync(syncToConsume,us);
        TreatRecord bean = new TreatRecord();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        treatService.saveTreatRecord(bean,syncToConsume,us);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        TreatRecord bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 检查同步配置
     * @param syncToConsume
     * @param us
     */
    private void checkConsumeSync(boolean syncToConsume,UserSetting us){
        if(syncToConsume){
            if(us.getTreatBuyTypeId()==null||us.getTreatGoodsTypeId()==null){
                throw new ApplicationException(PmsErrorCode.TREAT_SYNC_UN_CONFIG);
            }
        }
    }
    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid TreatRecordFormRequest formRequest) {
        boolean syncToConsume = formRequest.getSyncToConsume()==null ? true : formRequest.getSyncToConsume();
        UserSetting us = authService.getUserSetting(formRequest.getUserId());
        this.checkConsumeSync(syncToConsume,us);
        TreatRecord bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        treatService.saveTreatRecord(bean,syncToConsume,us);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        for (String s : deleteRequest.getIds().split(",")) {
            TreatRecord bean = this.getUserEntity(beanClass, Long.valueOf(s), deleteRequest.getUserId());
            treatService.deleteTreatRecord(bean);
        }
        return callback(null);
    }


    /**
     * 总的概要统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(TreatRecordSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        TreatRecordSummaryStat data = treatService.statTreatRecord(sf);
        //统计医保、个人的支付比例
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("费用统计");
        chartPieData.setSubTitle(this.getDateTitle(sf));
        chartPieData.getXdata().add("医保支付");
        chartPieData.getXdata().add("个人支付");
        chartPieData.getXdata().add("挂号费");
        chartPieData.getXdata().add("药费");
        chartPieData.getXdata().add("手术费");
        //总体统计
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("医保/个人");
        ChartPieSerieDetailData medicalInsurancePaidFeeDataDetail = new ChartPieSerieDetailData();
        medicalInsurancePaidFeeDataDetail.setName("医保支付");
        medicalInsurancePaidFeeDataDetail.setValue(data.getTotalMedicalInsurancePaidFee());
        serieData.getData().add(medicalInsurancePaidFeeDataDetail);

        ChartPieSerieDetailData personalPaidFeeDataDetail = new ChartPieSerieDetailData();
        personalPaidFeeDataDetail.setName("个人支付");
        personalPaidFeeDataDetail.setValue(data.getTotalPersonalPaidFee());
        serieData.getData().add(personalPaidFeeDataDetail);

        //分类统计
        ChartPieSerieData serie2Data = new ChartPieSerieData();
        serie2Data.setName("分项费用");
        ChartPieSerieDetailData registeredFeeDataDetail = new ChartPieSerieDetailData();
        registeredFeeDataDetail.setName("挂号费");
        registeredFeeDataDetail.setValue(data.getTotalRegisteredFee());
        serie2Data.getData().add(registeredFeeDataDetail);

        ChartPieSerieDetailData drugFeeDataDetail = new ChartPieSerieDetailData();
        drugFeeDataDetail.setName("药费");
        drugFeeDataDetail.setValue(data.getTotalDrugFee());
        serie2Data.getData().add(drugFeeDataDetail);

        ChartPieSerieDetailData operationFeeDataDetail = new ChartPieSerieDetailData();
        operationFeeDataDetail.setName("手术费");
        operationFeeDataDetail.setValue(data.getTotalOperationFee());
        serie2Data.getData().add(operationFeeDataDetail);

        chartPieData.getDetailData().add(serieData);
        chartPieData.getDetailData().add(serie2Data);
        data.setPieData(chartPieData);
        return callback(data);
    }

    /**
     * 统计分析
     *
     * @return
     */
    @RequestMapping(value = "/analyseStat", method = RequestMethod.GET)
    public ResultBean analyseStat(TreatRecordAnalyseStatSearch sf) {
        if ("tags".equals(sf.getGroupField())) {
            //如果是按照疾病标签统计，那么tags字段需要不为空
            //TODO tags可能有多个标签，需要拆分，大部分情况下只有一个标签
            sf.setGroupTags(NullType.NOT_NULL);
        }
        List<TreatRecordAnalyseStat> list = treatService.treatRecordAnalyseStat(sf);
        if (sf.getChartType() == ChartType.MIX_LINE_BAR) {
            return callback(this.createAnalyseStatBarData(list, sf));
        } else {
            return callback(this.createAnalyseStatPieData(list, sf));
        }

    }

    /**
     * 封装看病记录分析的饼状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartPieData createAnalyseStatPieData(List<TreatRecordAnalyseStat> list, TreatRecordAnalyseStatSearch sf) {
        String unit;
        if (sf.getGroupType() == GroupType.COUNT) {
            unit = "次";
        } else {
            unit = "元";
        }
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("看病记录分析");
        chartPieData.setUnit(unit);
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName(sf.getGroupType().getName());
        serieData.setUnit(unit);
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (TreatRecordAnalyseStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            if (sf.getGroupType() == GroupType.COUNT) {
                dataDetail.setValue(bean.getTotalCount());
            } else {
                dataDetail.setValue(bean.getTotalFee());
            }
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            totalValue = totalValue.add(bean.getTotalFee());
            serieData.getData().add(dataDetail);
        }
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次，" + totalValue.doubleValue() + "元");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 封装看病记录分析的柱状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartData createAnalyseStatBarData(List<TreatRecordAnalyseStat> list, TreatRecordAnalyseStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("看病记录分析");
        chartData.setLegendData(new String[]{"费用","次数"});
        ChartYData yData1 = new ChartYData("费用","元");
        ChartYData yData2 = new ChartYData("次数","次");
        //混合图形下使用
        chartData.addYAxis("费用","元");
        chartData.addYAxis("次数","次");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (TreatRecordAnalyseStat bean : list) {
            chartData.getXdata().add(bean.getName());
            yData1.getData().add(bean.getTotalFee());
            yData2.getData().add(bean.getTotalCount());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            totalValue = totalValue.add(bean.getTotalFee());
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次，" + totalValue.doubleValue() + "元");
        chartData.setSubTitle(subTitle);
        return chartData;

    }

    /**
     * 基于日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat", method = RequestMethod.GET)
    public ResultBean dateStat(TreatRecordDateStatSearch sf) {
        switch (sf.getDateGroupType()){
            case DAYCALENDAR :
                //日历
                return callback(this.createChartCalandarDataDateStat(sf));
            case HOURMINUTE :
                //散点图
                PageRequest pr = sf.buildQuery();
                pr.setBeanClass(beanClass);
                List<Date> dateList = dataService.getDateList(pr,"treatDate");
                return callback(this.createHMChartData(dateList,"看病分析","看病时间点"));
            default:
                break;
        }
        List<TreatRecordDateStat> list = treatService.statDateTreatRecord(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("看病统计");
        chartData.setLegendData(new String[]{"费用","次数"});
        //混合图形下使用
        chartData.addYAxis("费用","元");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("费用","元");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (TreatRecordDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(bean.getTotalFee());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            totalValue = totalValue.add(bean.getTotalFee());
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次，" + totalValue.doubleValue() + "元");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    /**
     * 日历图
     *
     * @param sf
     * @return
     */
    private ChartCalendarData createChartCalandarDataDateStat(TreatRecordDateStatSearch sf) {
        List<TreatRecordDateStat> list = treatService.statDateTreatRecord(sf);
        ChartCalendarData calandarData = ChartUtil.createChartCalendarData("看病统计", "次数", "次", sf, list);
        if (!StringUtil.isEmpty(sf.getDisease())) {
            //跟踪疾病
            TreatRecordByDiseaseSearch trds = new TreatRecordByDiseaseSearch();
            BeanCopy.copyProperties(sf, trds);
            PageRequest pr = trds.buildQuery();
            pr.setBeanClass(beanClass);
            List<TreatRecord> dd = baseService.getBeanList(pr);
            for (TreatRecord tb : dd) {
                calandarData.addGraph(tb.getTreatDate(), 1);
            }

        } else {
            calandarData.setTop(3);
        }
        return calandarData;
    }

    /**
     * 同期比对统计
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid TreatRecordYoyStatSearch sf) {
        if (sf.getDateGroupType() == DateGroupType.DAY) {
            return callback(createChartCalandarMultiData(sf));
        }
        String unit;
        if (sf.getGroupType() == GroupType.COUNT) {
            unit = "次";
        } else {
            unit = sf.getGroupType().getUnit();
        }
        ChartData chartData = initYoyCharData(sf, "看病记录同期对比", null);
        chartData.setUnit(unit);
        String[] legendData = new String[sf.getYears().size()];
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            TreatRecordDateStatSearch monthStatSearch = this.generateSearch(sf.getYears().get(i), sf);
            ChartYData yData = new ChartYData(sf.getYears().get(i).toString(),unit);
            List<TreatRecordDateStat> list = treatService.statDateTreatRecord(monthStatSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (TreatRecordDateStat bean : list) {
                temp.addXData(bean, sf.getDateGroupType());
                if (sf.getGroupType() == GroupType.COUNT) {
                    yData.getData().add(bean.getTotalCount());
                } else {
                    yData.getData().add(bean.getTotalFee());
                }
            }
            //临时内容，作为补全用
            temp.getYdata().add(yData);
            monthStatSearch.setCompliteDate(true);
            temp = ChartUtil.completeDate(temp, monthStatSearch);
            //设置到最终的结果集中
            chartData.getYdata().add(temp.getYdata().get(0));
        }
        chartData.setLegendData(legendData);

        return callback(chartData);
    }

    private TreatRecordDateStatSearch generateSearch(int year, TreatRecordYoyStatSearch sf) {
        //数据,为了代码复用及统一，统计还是按照日期的统计
        TreatRecordDateStatSearch monthStatSearch = new TreatRecordDateStatSearch();
        monthStatSearch.setStartDate(DateUtil.getDate(year + "-01-01", DateUtil.FormatDay1));
        monthStatSearch.setEndDate(DateUtil.getDate(year + "-12-31", DateUtil.FormatDay1));
        monthStatSearch.setUserId(sf.getUserId());
        monthStatSearch.setFeeField(sf.getFeeField());
        monthStatSearch.setDateGroupType(sf.getDateGroupType());
        monthStatSearch.setName(sf.getName());
        monthStatSearch.setTreatType(sf.getTreatType());
        return monthStatSearch;
    }

    /**
     * 基于日历的热点图
     *
     * @param sf
     * @return
     */
    private ChartCalendarMultiData createChartCalandarMultiData(TreatRecordYoyStatSearch sf) {
        ChartCalendarMultiData data = new ChartCalendarMultiData();
        data.setTitle("看病记录同期对比");
        if (sf.getGroupType() == GroupType.COUNT) {
            data.setUnit("次");
        } else {
            data.setUnit("元");
        }
        for (int i = 0; i < sf.getYears().size(); i++) {
            TreatRecordDateStatSearch monthStatSearch = this.generateSearch(sf.getYears().get(i), sf);
            List<TreatRecordDateStat> list = treatService.statDateTreatRecord(monthStatSearch);
            for (TreatRecordDateStat bean : list) {
                String dateString = DateUtil.getFormatDateString(bean.getDateIndexValue().toString(), "yyyyMMdd", "yyyy-MM-dd");
                if (sf.getGroupType() == GroupType.COUNT) {
                    data.addData(sf.getYears().get(i), dateString, bean.getTotalCount());
                } else {
                    data.addData(sf.getYears().get(i), dateString, bean.getTotalFee().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        }
        return data;
    }


    /**
     * 关键字列表
     *
     * @return
     */
    @RequestMapping(value = "/getTagsTree")
    public ResultBean getTagsTree(TreatRecordTagsSearch sf) {
        List<TreeBean> list = new ArrayList<TreeBean>();
        Date[] ds = systemConfigHandler.getDateRange(sf.getDays(),"treat.tags.days");
        sf.setStartDate(ds[0]);
        sf.setEndDate(ds[1]);
        List<String> tagsList = treatService.getTagsList(sf);
        //去重,不需要，实际上每次看病只会是一个病，如果是两种病，肯定也是两个科室去看，会有两条看病记录
        //Set<String> tagsSet = deleteDuplicate(tagsList);
        for (String s : tagsList) {
            TreeBean tb = new TreeBean();
            tb.setText(s);
            tb.setId(s);
            list.add(tb);
        }
        return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
    }

    /**
     * 去重，每个关键字以英文逗号分隔
     *
     * @param keywordsList
     * @return
     */
    private Set<String> deleteDuplicate(List<String> keywordsList) {
        //去重
        Set<String> keywordsSet = new TreeSet<>();
        for (String s : keywordsList) {
            if (s == null || s.isEmpty()) {
                continue;
            }
            String[] ss = s.split(",");
            for (String key : ss) {
                keywordsSet.add(key);
            }
        }
        return keywordsSet;
    }

    /**
     * 疾病概况统计
     *
     * @return
     */
    @RequestMapping(value = "/fullStat", method = RequestMethod.GET)
    public ResultBean fullStat(TreatRecordFullStatSearch sf) {
        PageResult<TreatRecordFullStat> res = new PageResult(sf.getPage(), sf.getPageSize());
        List<TreatRecordFullStat> list = treatService.treatRecordFullStat(sf);
        res.setBeanList(list);
        if (!list.isEmpty()) {
            //求最大行数
            long n = treatService.getMaxRowOfTreatRecordFullStat(sf);
            res.setMaxRow(n);
            long idn = (sf.getPage() - 1) * sf.getPageSize();
            for (TreatRecordFullStat tr : list) {
                //只是作为显示使用，目前没实际用处
                tr.setId(++idn);
            }
        }
        return callbackDataGrid(res);
    }

    /**
     * 总体统计
     *
     * @return
     */
    @RequestMapping(value = "/overallStat")
    public ResultBean overallStat(@Valid TreatRecordOverallStatSearch sf) {
        ChartHeatmapData chartData = new ChartHeatmapData();
        chartData.setTitle("看病统计");
        DateGroupType dateGroupType = sf.getDateGroupType();
        int[] minMax = ChartUtil.getMinMax(dateGroupType,sf.getStartDate(),sf.getEndDate());
        int min = minMax[0];
        int max = minMax[1];
        List<String> xdata = ChartUtil.getStringXdataList(dateGroupType,min, max);
        chartData.setXdata(xdata);
        List<TreatRecordOverallStat> list = treatService.statOverallTreatRecord(sf);
        int stn = list.size();
        //Y轴
        Map<String,OverallYIndex> yMap = new HashMap<>();
        int yxi =0;
        for(int i=0;i<stn;i++){
            TreatRecordOverallStat tros = list.get(i);
            String key = tros.getName();
            if(yMap.get(key)!=null){
                continue;
            }
            yMap.put(key,new OverallYIndex(key,key,"次",(yxi++)));
            chartData.addYData(key);
        }
        GroupType groupType = sf.getGroupType();
        ChartHeatmapSerieData serieData = new ChartHeatmapSerieData(groupType.getName());
        for (int i=0;i<stn;i++) {
            TreatRecordOverallStat seos = list.get(i);
            int indexValue = seos.getIndexValue();
            if(dateGroupType==DateGroupType.DAY){
                indexValue = DateUtil.getDayOfYear(DateUtil.getDate(indexValue+"","yyyyMMdd"));
            }
            int xIndex = ChartUtil.getXIndex(dateGroupType,indexValue,min,xdata) ;
            OverallYIndex yi = yMap.get(seos.getName());
            int yIndex = yi.getIndex();
            double value =0;
            String unit ="次";
            if(groupType==GroupType.COUNT){
                value = seos.getTotalCount().doubleValue();
            }else{
                value = seos.getTotalFee().doubleValue();
                unit = "元";
            }
            chartData.updateMinMaxValue(value);
            serieData.addData(new Object[]{xIndex,yIndex,value,unit});
        }
        chartData.addSerieData(serieData);
        return callback(chartData);
    }

    /**
     * 关系图
     *
     * @return
     */
    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    public ResultBean relation(BuyRecordRelationSearch rs) {
        String tags = rs.getTags();
        List<TreatRecordUnionDto> list = treatService.getTreatList(tags,rs.getUserId(),rs.getStartDate(),rs.getEndDate());
        if(StringUtil.isNotEmpty(tags)){
            ChartGraphData data = this.createGraphData(tags,rs.getUnionAll(),list);
            return callback(data);
        }else{
            if(rs.getStartDate()==null||rs.getEndDate()==null){
                //数据过多导致图形显示复杂
                return callbackErrorInfo("请选择时间段");
            }
            ChartGraphData data = this.createGraphData(rs.getUnionAll(),list);
            return callback(data);
        }
    }

    /**
     * 指定标签
     * @param tags
     * @param unionAll
     * @param list
     * @return
     */
    private ChartGraphData createGraphData(String tags,boolean unionAll,List<TreatRecordUnionDto> list){
        int n = list.size();
        String root = tags;
        ChartGraphData chartGraphData = new ChartGraphData();
        chartGraphData.addItem(root,0);
        chartGraphData.setCategoryNames(new String[]{"根","医院","科室","确诊疾病","药品","手术"});
        for(int i=0;i<n;i++){
            TreatRecordUnionDto dto = list.get(i);
            //第一级：医院
            String hospital = dto.getHospital();
            chartGraphData.addLink(root,hospital);
            chartGraphData.addItem(hospital,1);

            //第二级：科室
            String department = dto.getDepartment();
            chartGraphData.addLink(hospital,department);
            chartGraphData.addItem(department,2);

            //第三级：确诊疾病
            String diagnosedDisease = dto.getDiagnosedDisease();
            chartGraphData.addLink(hospital,diagnosedDisease);
            chartGraphData.addItem(diagnosedDisease,3);

            //第四级：药品
            String drugName = dto.getDrugName();
            if(StringUtil.isNotEmpty(drugName)){
                chartGraphData.addLink(hospital,drugName);
                chartGraphData.addItem(drugName,4);
                if(unionAll){
                    chartGraphData.addLink(diagnosedDisease,drugName);
                }
            }

            //第五级：手术
            String operationName = dto.getOperationName();
            if(StringUtil.isNotEmpty(operationName)){
                chartGraphData.addLink(hospital,operationName);
                chartGraphData.addItem(operationName,5);
                if(unionAll){
                    chartGraphData.addLink(diagnosedDisease,operationName);
                }
            }
        }
        chartGraphData.setTitle("["+tags+"]疾病分析");
        return chartGraphData;
    }

    /**
     * 不指定标签
     * @param list
     * @return
     */
    private ChartGraphData createGraphData(boolean unionAll,List<TreatRecordUnionDto> list){
        String root = "我的疾病";
        int n = list.size();
        ChartGraphData chartGraphData = new ChartGraphData();
        chartGraphData.addItem(root,0);
        chartGraphData.setCategoryNames(new String[]{"根","确诊疾病","医院","科室","药品","手术"});
        for(int i=0;i<n;i++){
            TreatRecordUnionDto dto = list.get(i);

            //第一级：确诊疾病
            String diagnosedDisease = dto.getDiagnosedDisease();
            chartGraphData.addLink(root,diagnosedDisease);
            chartGraphData.addItem(diagnosedDisease,1);

            //第二级：医院
            String hospital = dto.getHospital();
            chartGraphData.addLink(diagnosedDisease,hospital);
            chartGraphData.addItem(hospital,2);

            //第三级：科室
            String department = dto.getDepartment();
            chartGraphData.addLink(hospital,department);
            chartGraphData.addItem(department,3);

            //第四级：药品
            String drugName = dto.getDrugName();
            if(StringUtil.isNotEmpty(drugName)){
                chartGraphData.addLink(hospital,drugName);
                chartGraphData.addItem(drugName,4);
                if(unionAll){
                    chartGraphData.addLink(diagnosedDisease,drugName);
                }
            }

            //第五级：手术
            String operationName = dto.getOperationName();
            if(StringUtil.isNotEmpty(operationName)){
                chartGraphData.addLink(hospital,operationName);
                chartGraphData.addItem(operationName,5);
                if(unionAll){
                    chartGraphData.addLink(diagnosedDisease,operationName);
                }
            }
        }
        chartGraphData.setTitle("我的疾病分析");
        return chartGraphData;
    }
}
