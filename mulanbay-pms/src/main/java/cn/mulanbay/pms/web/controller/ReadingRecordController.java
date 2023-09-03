package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.BookCategory;
import cn.mulanbay.pms.persistent.domain.Country;
import cn.mulanbay.pms.persistent.domain.ReadingRecord;
import cn.mulanbay.pms.persistent.domain.ReadingRecordDetail;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ReadingStatus;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.ReadingRecordService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.read.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 阅读记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/readingRecord")
public class ReadingRecordController extends BaseController {

    private static Class<ReadingRecord> beanClass = ReadingRecord.class;

    @Autowired
    ReadingRecordService readingRecordService;

    @Autowired
    DataService dataService;

    /**
     * 获取列表列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ReadingRecordSearch sf) {
        return callbackDataGrid(getReadingRecordResult(sf));
    }

    private PageResult<ReadingRecord> getReadingRecordResult(ReadingRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort1 = new Sort("status", Sort.ASC);
        pr.addSort(sort1);
        Sort sort2 = new Sort("lastModifyTime", Sort.DESC);
        pr.addSort(sort2);
        Sort sort3 = new Sort("proposedDate", Sort.ASC);
        pr.addSort(sort3);
        PageResult<ReadingRecord> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ReadingRecordFormRequest formRequest) {
        ReadingRecord bean = new ReadingRecord();
        BeanCopy.copyProperties(formRequest, bean);
        BookCategory bookCategory = this.getUserEntity(BookCategory.class, formRequest.getBookCategoryId(), formRequest.getUserId());
        bean.setBookCategory(bookCategory);
        Country country = baseService.getObject(Country.class,formRequest.getCountryId());
        bean.setCountry(country);
        checkAndSetReadingRecord(bean);
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
        ReadingRecord bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ReadingRecordFormRequest formRequest) {
        ReadingRecord bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        ReadingStatus bs = bean.getStatus();
        BeanCopy.copyProperties(formRequest, bean, true);
        BookCategory bookCategory = this.getUserEntity(BookCategory.class, formRequest.getBookCategoryId(), formRequest.getUserId());
        bean.setBookCategory(bookCategory);
        Country country = baseService.getObject(Country.class,formRequest.getCountryId());
        bean.setCountry(country);
        checkAndSetReadingRecord(bean);
        if (bs != ReadingStatus.READED && formRequest.getStatus() == ReadingStatus.READED) {
            //如果状态改变为已完成，则自动算出开始、结束日期
            ReadingRecordTimeStat rrts = readingRecordService.selectReadTimeStat(bean.getId());
            if (bean.getBeginDate() == null) {
                bean.setBeginDate(rrts.getMinDate());
            }
            if (bean.getFinishedDate() == null) {
                bean.setFinishedDate(rrts.getMaxDate());
            }
        }
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 检查完成情况
     *
     * @param bean
     */
    private void checkAndSetReadingRecord(ReadingRecord bean) {
        //检查isbn是否存在
        ReadingRecord rr = readingRecordService.selectReadingRecord(bean.getIsbn(), bean.getUserId(), bean.getId());
        if (rr != null) {
            throw new ApplicationException(PmsErrorCode.READING_RECORD_ISBN_EXIT);
        }
        if (bean.getFinishedDate() != null && bean.getBeginDate() != null) {
            if (bean.getStatus() == null || bean.getStatus() != ReadingStatus.READED) {
                //状态不对
                throw new ApplicationException(PmsErrorCode.READING_RECORD_STATUS_ERROR);
            }
            if (bean.getScore() == null) {
                throw new ApplicationException(PmsErrorCode.READING_RECORD_SCORE_NULL);
            }
            //需要加1，因为同天的是一天
            int costDays = DateUtil.getIntervalDays(bean.getBeginDate(), bean.getFinishedDate()) + 1;
            bean.setCostDays(costDays);
        }
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        Long[] ids = NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(","));
        for (Long id : ids) {
            ReadingRecord bean = this.getUserEntity(beanClass, id, deleteRequest.getUserId());
            readingRecordService.deleteReadingRecord(bean);
        }
        return callback(null);
    }

    /**
     * 总的概要统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(ReadingRecordStatSearch sf) {
        sf.setDateQueryType("finished_date");
        ReadingRecordAnalyseStatSearch statSearch = new ReadingRecordAnalyseStatSearch();
        BeanCopy.copyProperties(sf, statSearch);
        List<ReadingRecordAnalyseStat> list = readingRecordService.statReadingRecordAnalyse(statSearch);
        ChartPieData chartPieData = this.createAnalyseStatPieData(statSearch, list);
        ReadingRecordReadedStat result = readingRecordService.statReadedReadingRecord(sf);
        result.setPieData(chartPieData);
        return callback(result);
    }

    /**
     * 完整的统计（对应页面图形为旭日图）
     *
     * @return
     */
    @RequestMapping(value = "/fullStat", method = RequestMethod.GET)
    public ResultBean fullStat(ReadingRecordFullStatSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        List<ReadingRecord> list = baseService.getBeanList(pr);
        ChartSunburstData chartData = new ChartSunburstData();
        List<ChartSunburstDetailData> voList = new ArrayList<>();
        for(ReadingRecord rr : list){
            //第一层：书籍类型
            ChartSunburstDetailData vo = this.getFullStatVo(rr.getBookType().getName(),voList);
            if(vo ==null){
                vo = new ChartSunburstDetailData();
                vo.setName(rr.getBookType().getName());
                voList.add(vo);
            }
            //第二层：书籍分类
            String category = rr.getBookCategory().getName();
            ChartSunburstDetailData categoryVo = this.getFullStatVo(category,vo.getChildren());
            if(categoryVo==null){
                categoryVo = new ChartSunburstDetailData();
                categoryVo.setName(category);
                vo.addChild(categoryVo);
            }
            //第三层：书籍评分
            String score = PriceUtil.changeToString(0,rr.getScore());
            ChartSunburstDetailData scoreVo = this.getFullStatVo(score,categoryVo.getChildren());
            if(scoreVo==null){
                scoreVo = new ChartSunburstDetailData();
                scoreVo.setName(score);
                categoryVo.addChild(scoreVo);
            }
            //第四层：书籍名称
            String bookName = rr.getBookName();
            ChartSunburstDetailData bookNameVo = new ChartSunburstDetailData();
            bookNameVo.setName(bookName);
            scoreVo.addChild(bookNameVo);
        }
        chartData.setDataList(voList);
        return callback(chartData);
    }

    /**
     * 查找
     * @param name
     * @param list
     * @return
     */
    private ChartSunburstDetailData getFullStatVo(String name, List<ChartSunburstDetailData> list){
        for(ChartSunburstDetailData vo : list){
            if(vo.getName().equals(name)){
                return vo;
            }
        }
        return null;
    }

    /**
     * 总阅读时间（分钟）
     *
     * @return
     */
    @RequestMapping(value = "/getCostTimes", method = RequestMethod.GET)
    public ResultBean getCostTimes(@Valid CommonBeanGetRequest getRequest) {
        Long minutes = readingRecordService.getCostTimes(getRequest.getId());
        return callback(minutes);
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(ReadingRecordDateStatSearch sf) {
        switch (sf.getDateGroupType()){
            case HOURMINUTE :
                //散点图
                List<Date> dateList = this.getReadTimeList(sf);
                return callback(this.createHMChartData(dateList,"阅读分析","阅读时间点"));
            default:
                break;
        }
        List<ReadingRecordDateStat> list = readingRecordService.statDateReadingRecord(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("阅读统计");
        //chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setUnit("本");
        chartData.setLegendData(new String[]{"本数"});
        ChartYData yData1 = new ChartYData("本数","本");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (ReadingRecordDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount()));
        }
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.longValue()) + "本");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    /**
     * 获取阅读时间列表
     * @param sf
     * @return
     */
    private List<Date> getReadTimeList(ReadingRecordDateStatSearch sf){
        ReadingRecordDetailSearch rrds = new ReadingRecordDetailSearch();
        BeanCopy.copyProperties(sf,rrds);
        PageRequest pr = rrds.buildQuery();
        //阅读散点图统计的是阅读明细的数据
        pr.setBeanClass(ReadingRecordDetail.class);
        List<Date> dateList = dataService.getDateList(pr,"readTime");
        return dateList;
    }

    /**
     * 统计分析
     *
     * @return
     */
    @RequestMapping(value = "/analyseStat")
    public ResultBean analyseStat(ReadingRecordAnalyseStatSearch sf) {
        List<ReadingRecordAnalyseStat> list;
        if (sf.getGroupType() == ReadingRecordAnalyseStatSearch.GroupType.PERIOD) {
            List<BigInteger> pl = readingRecordService.statReadingRecordAnalyseByPeriod(sf);
            Map<String, Integer> map = new HashMap<>();
            for (BigInteger b : pl) {
                int m = b.intValue();
                String key;
                if (m <= 3) {
                    key = "3天内";
                } else if (m <= 7) {
                    key = "3-7天";
                } else if (m <= 15) {
                    key = "7-15天";
                } else if (m <= 30) {
                    key = "15-30天";
                } else {
                    key = "超过1个月";
                }
                Integer v = map.get(key);
                if (v == null) {
                    map.put(key, 1);
                } else {
                    map.put(key, v + 1);
                }
            }
            list = new ArrayList<>();
            for (String kk : map.keySet()) {
                ReadingRecordAnalyseStat as = new ReadingRecordAnalyseStat();
                as.setName(kk);
                as.setValue(map.get(kk));
                list.add(as);
            }
        } else if (sf.getGroupType() == ReadingRecordAnalyseStatSearch.GroupType.TIME) {
            List<BigDecimal> pl = readingRecordService.statReadingRecordAnalyseByTime(sf);
            Map<String, Integer> map = new HashMap<>();
            for (BigDecimal b : pl) {
                int m = b.intValue();
                String key;
                if (m <= 60) {
                    key = "1小时内";
                } else if (m <= 3 * 60) {
                    key = "1-3小时";
                } else if (m <= 24 * 60) {
                    key = "4小时-1天";
                } else if (m <= 3 * 24 * 60) {
                    key = "1-3天";
                } else if (m <= 7 * 24 * 60) {
                    key = "3-7天";
                } else if (m <= 30 * 24 * 60) {
                    key = "7天-1个月";
                } else {
                    key = "超过1个月";
                }
                Integer v = map.get(key);
                if (v == null) {
                    map.put(key, 1);
                } else {
                    map.put(key, v + 1);
                }
            }
            list = new ArrayList<>();
            for (String kk : map.keySet()) {
                ReadingRecordAnalyseStat as = new ReadingRecordAnalyseStat();
                as.setName(kk);
                as.setValue(map.get(kk));
                list.add(as);
            }
        } else {
            list = readingRecordService.statReadingRecordAnalyse(sf);
        }
        ChartPieData chartPieData = this.createAnalyseStatPieData(sf, list);
        return callback(chartPieData);
    }

    private ChartPieData createAnalyseStatPieData(ReadingRecordAnalyseStatSearch sf, List<ReadingRecordAnalyseStat> list) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("阅读记录分析");
        chartPieData.setUnit("本");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("本数");
        serieData.setUnit("本");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (ReadingRecordAnalyseStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getValue()));
        }
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.intValue()) + "本");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 总体统计
     *
     * @return
     */
    @RequestMapping(value = "/overallStat")
    public ResultBean overallStat(@Valid ReadingRecordOverallStatSearch sf) {
        GroupType groupType = sf.getValueType();
        if(groupType==GroupType.COUNT){
            //阅读本数
            return this.overallStatReadingRecord(sf);
        }else{
            //阅读时间
            ReadingRecordDetailOverallStatSearch df = new ReadingRecordDetailOverallStatSearch();
            BeanCopy.copyProperties(sf,df);
            return this.overallStatReadingRecordDetail(df);
        }
    }

     /**
     * @param sf
     * @return
     */
    public ResultBean overallStatReadingRecord(ReadingRecordOverallStatSearch sf) {
        ChartHeatmapData chartData = new ChartHeatmapData();
        chartData.setTitle("阅读统计");
        DateGroupType dateGroupType = sf.getDateGroupType();
        int[] minMax = ChartUtil.getMinMax(dateGroupType,sf.getStartDate(),sf.getEndDate());
        int min = minMax[0];
        int max = minMax[1];
        List<String> xdata = ChartUtil.getStringXdataList(dateGroupType,min, max);
        chartData.setXdata(xdata);
        //Y轴
        List<BookCategory> bookCategoryList = readingRecordService.getAchieveBookCategory(sf.getUserId());
        Map<String,OverallYIndex> yMap = new HashMap<>();
        int stn = bookCategoryList.size();
        for(int i=0;i<stn;i++){
            BookCategory st = bookCategoryList.get(i);
            yMap.put(st.getId().toString(),new OverallYIndex(st.getId().toString(),st.getName(),"本",i));
            chartData.addYData(st.getName());
        }
        List<ReadingRecordOverallStat> list = readingRecordService.statOverallReadingRecord(sf);
        GroupType valueType = sf.getValueType();
        ChartHeatmapSerieData serieData = new ChartHeatmapSerieData(valueType.getName());
        int vn = list.size();
        for (int i=0;i<vn;i++) {
            ReadingRecordOverallStat seos = list.get(i);
            int indexValue = seos.getIndexValue();
            if(dateGroupType==DateGroupType.DAY){
                indexValue = DateUtil.getDayOfYear(DateUtil.getDate(indexValue+"","yyyyMMdd"));
            }
            int xIndex = ChartUtil.getXIndex(dateGroupType,indexValue,min,xdata) ;
            OverallYIndex yi = yMap.get(seos.getBookCategoryId().toString());
            int yIndex = yi.getIndex();
            double value = seos.getTotalCount().doubleValue();
            String unit ="本";
            chartData.updateMinMaxValue(value);
            serieData.addData(new Object[]{xIndex,yIndex,value,unit});
        }
        chartData.addSerieData(serieData);
        return callback(chartData);
    }

    /**
     * @param sf
     * @return
     */
    public ResultBean overallStatReadingRecordDetail(ReadingRecordDetailOverallStatSearch sf) {
        ChartHeatmapData chartData = new ChartHeatmapData();
        chartData.setTitle("阅读统计");
        DateGroupType dateGroupType = sf.getDateGroupType();
        int[] minMax = ChartUtil.getMinMax(dateGroupType,sf.getStartDate(),sf.getEndDate());
        int min = minMax[0];
        int max = minMax[1];
        List<String> xdata = ChartUtil.getStringXdataList(dateGroupType,min, max);
        chartData.setXdata(xdata);
        //Y轴
        List<BookCategory> bookCategoryList = readingRecordService.getAchieveBookCategory(sf.getUserId());
        Map<String,OverallYIndex> yMap = new HashMap<>();
        int stn = bookCategoryList.size();
        for(int i=0;i<stn;i++){
            BookCategory st = bookCategoryList.get(i);
            yMap.put(st.getId().toString(),new OverallYIndex(st.getId().toString(),st.getName(),"本",i));
            chartData.addYData(st.getName());
        }
        List<ReadingRecordDetailOverallStat> list = readingRecordService.statOverallReadingRecordDetail(sf);
        GroupType valueType = sf.getValueType();
        ChartHeatmapSerieData serieData = new ChartHeatmapSerieData(valueType.getName());
        int vn = list.size();
        for (int i=0;i<vn;i++) {
            ReadingRecordDetailOverallStat seos = list.get(i);
            int indexValue = seos.getIndexValue();
            if(dateGroupType==DateGroupType.DAY){
                indexValue = DateUtil.getDayOfYear(DateUtil.getDate(indexValue+"","yyyyMMdd"));
            }
            int xIndex = ChartUtil.getXIndex(dateGroupType,indexValue,min,xdata) ;
            OverallYIndex yi = yMap.get(seos.getBookCategoryId().toString());
            int yIndex = yi.getIndex();
            double value = NumberUtil.getDoubleValue(seos.getTotalMinutes().doubleValue()/60,1);
            String unit ="小时";
            chartData.updateMinMaxValue(value);
            serieData.addData(new Object[]{xIndex,yIndex,value,unit});
        }
        chartData.addSerieData(serieData);
        return callback(chartData);
    }


}
