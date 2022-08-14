package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.Constant;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.MusicInstrument;
import cn.mulanbay.pms.persistent.domain.MusicPractice;
import cn.mulanbay.pms.persistent.domain.MusicPracticeTune;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.MusicPracticeService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.music.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 音乐练习记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/musicPractice")
public class MusicPracticeController extends BaseController {

    private static Class<MusicPractice> beanClass = MusicPractice.class;

    @Value("${reward.createByTemplate.perPoints}")
    int ctPerPPoints;

    @Autowired
    RewardPointsHandler rewardPointsHandler;

    @Autowired
    MusicPracticeService musicPracticeService;

    @Autowired
    DataService dataService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(MusicPracticeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("practiceDate", Sort.DESC);
        pr.addSort(s);
        PageResult<MusicPractice> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid MusicPracticeFormRequest formRequest) {
        MusicPractice bean = new MusicPractice();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        bean.setPracticeDate(DateUtil.getDate(bean.getPracticeStartTime(), DateUtil.FormatDay1));
        MusicInstrument musicInstrument = this.getUserEntity(MusicInstrument.class, formRequest.getMusicInstrumentId(), formRequest.getUserId());
        bean.setMusicInstrument(musicInstrument);
        baseService.saveObject(bean);
        return callback(bean);
    }

    /**
     * 以某天的模板创建
     *
     * @return
     */
    @RequestMapping(value = "/createByTemplate", method = RequestMethod.POST)
    public ResultBean createByTemplate(@RequestBody @Valid MusicExerciseCTFormRequest formRequest) {
        Date temEndTime = DateUtil.getTodayTillMiddleNightDate(formRequest.getTemplateDate());
        MusicPracticeSearch sf = new MusicPracticeSearch();
        sf.setStartDate(formRequest.getTemplateDate());
        sf.setEndDate(temEndTime);
        sf.setUserId(formRequest.getUserId());
        sf.setMusicInstrumentId(formRequest.getMusicInstrumentId());
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("practiceDate", Sort.ASC);
        pr.addSort(s);
        List<MusicPractice> list = baseService.getBeanList(pr);
        if (list.isEmpty()) {
            return callbackErrorInfo("无法找到[" + DateUtil.getFormatDate(formRequest.getTemplateDate(), DateUtil.FormatDay1) + "]的音乐练习记录");
        } else {
            Date ed = formRequest.getBeginTime();
            for (MusicPractice se : list) {
                MusicPractice nn = new MusicPractice();
                BeanCopy.copyProperties(se, nn);
                nn.setId(null);
                nn.setCreatedTime(new Date());
                nn.setLastModifyTime(null);
                nn.setPracticeDate(ed);
                nn.setRemark("以模板新增");
                nn.setPracticeStartTime(ed);
                ed = new Date(ed.getTime() + nn.getMinutes() * 60 * 1000);
                nn.setPracticeEndTime(ed);
                //查找曲子练习记录
                List<MusicPracticeTune> tuneList = musicPracticeService.getMusicPracticeTuneList(se.getId());
                List<MusicPracticeTune> newTuneList = new ArrayList<>();
                for (MusicPracticeTune mpt : tuneList) {
                    MusicPracticeTune ntn = new MusicPracticeTune();
                    BeanCopy.copyProperties(mpt, ntn);
                    ntn.setId(null);
                    ntn.setRemark("以模板新增");
                    newTuneList.add(ntn);
                }
                musicPracticeService.addMusicPractice(nn, newTuneList);
            }
            //增加积分
            int n = list.size();
            rewardPointsHandler.rewardPoints(formRequest.getUserId(), n * ctPerPPoints, null,
                    RewardSource.OPERATION, "以模板新增" + n + "条音乐练习记录", null);
        }
        return callback(null);
    }


    /**
     * 查询详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        MusicPractice bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid MusicPracticeFormRequest formRequest) {
        MusicPractice bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        bean.setPracticeDate(DateUtil.getDate(bean.getPracticeStartTime(), DateUtil.FormatDay1));
        MusicInstrument musicInstrument = this.getUserEntity(MusicInstrument.class, formRequest.getMusicInstrumentId(), formRequest.getUserId());
        bean.setMusicInstrument(musicInstrument);
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
        Long[] ids = NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(","));
        for (Long id : ids) {
            MusicPractice bean = this.getUserEntity(beanClass, id, deleteRequest.getUserId());
            musicPracticeService.deleteMusicPractice(bean);
        }
        return callback(null);
    }

    /**
     * 到目前为止的统计
     *
     * @return
     */
    @RequestMapping(value = "/fromThisStat", method = RequestMethod.GET)
    public ResultBean fromThisStat(@Valid CommonBeanGetRequest getRequest) {
        MusicPractice bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        MusicPracticeSummaryStat stat = musicPracticeService.musicPracticeFromThisStat(bean.getPracticeDate(), bean.getUserId(), bean.getMusicInstrument().getId());
        return callback(stat);
    }

    /**
     * 总的概要统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(MusicPracticeStatSearch sf) {
        MusicPracticeSummaryStat data = musicPracticeService.musicPracticeSummaryStat(sf);
        if (sf.getStartDate() != null && sf.getEndDate() != null) {
            int days = DateUtil.getIntervalDays(sf.getStartDate(), sf.getEndDate());
            data.setAverageMinutes(NumberUtil.getAverageValue(data.getTotalMinutes().doubleValue(), days, 2));
        }
        // 获取乐器的分析信息
        List<MusicPracticeInstrumentStat> list = musicPracticeService.musicPracticeInstrumentStat(sf);
        //统计医保、个人的支付比例
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("音乐练习统计");
        chartPieData.setSubTitle(this.getDateTitle(sf));
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("音乐练习统计(小时)");
        ChartPieSerieData serieDataCount = new ChartPieSerieData();
        serieDataCount.setName("音乐练习统计(次)");
        for (MusicPracticeInstrumentStat mp : list) {
            chartPieData.getXdata().add(mp.getName());
            ChartPieSerieDetailData cp = new ChartPieSerieDetailData();
            cp.setName(mp.getName());
            cp.setValue(minutesToHours(mp.getTotalMinutes().doubleValue()));
            serieData.getData().add(cp);

            ChartPieSerieDetailData cpCount = new ChartPieSerieDetailData();
            cpCount.setName(mp.getName());
            cpCount.setValue(mp.getTotalCount().longValue());
            serieDataCount.getData().add(cpCount);
        }
        chartPieData.getDetailData().add(serieDataCount);
        chartPieData.getDetailData().add(serieData);
        data.setPieData(chartPieData);
        return callback(data);
    }

    private double minutesToHours(double minutes) {
        return DateUtil.minutesToHours(minutes);
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(MusicPracticeDateStatSearch sf) {
        switch (sf.getDateGroupType()){
            case DAYCALENDAR :
                //日历
                return callback(createChartCalendarData(sf));
            case HOURMINUTE :
                //散点图
                PageRequest pr = sf.buildQuery();
                pr.setBeanClass(beanClass);
                List<Date> dateList = dataService.getDateList(pr,"practiceStartTime");
                return callback(this.createHMChartData(dateList,"音乐练习分析","练习时间点"));
            default:
                break;
        }
        List<MusicPracticeDateStat> list = musicPracticeService.statDateMusicPractice(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle(getChartTitle(sf.getMusicInstrumentId()));
        chartData.setLegendData(new String[]{ "总时长(小时)","次数"});
        //混合图形下使用
        chartData.addYAxis("时长","小时");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("总时长(小时)");
        ChartYData yData3 = new ChartYData();
        yData3.setName("平均每天(小时)");
        ChartYData yData4 = new ChartYData();
        yData4.setName("平均每次(小时)");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalValue = new BigDecimal(0);
        int year = DateUtil.getYear(sf.getEndDate() == null ? new Date() : sf.getEndDate());
        for (MusicPracticeDateStat bean : list) {
            chartData.getIntXData().add(bean.getIndexValue());
            if (sf.getDateGroupType() == DateGroupType.MONTH) {
                chartData.getXdata().add(bean.getIndexValue() + "月份");
                int days = DateUtil.getDayOfMonth(year, bean.getIndexValue() - 1);
                yData3.getData().add(minutesToHours(bean.getTotalMinutes().longValue() / days));
            } else if (sf.getDateGroupType() == DateGroupType.YEAR) {
                chartData.getXdata().add(bean.getIndexValue() + "年");
            } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
                chartData.getXdata().add("第" + bean.getIndexValue() + "周");
                yData3.getData().add(minutesToHours(bean.getTotalMinutes().longValue() / Constant.DAYS_WEEK));
            } else {
                chartData.getXdata().add(bean.getIndexValue().toString());
            }
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(minutesToHours(bean.getTotalMinutes().doubleValue()));
            yData4.getData().add(minutesToHours(bean.getTotalMinutes().doubleValue() / bean.getTotalCount().intValue()));
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            totalValue = totalValue.add(bean.getTotalMinutes());
        }
        chartData.getYdata().add(yData2);
        if (sf.getDateGroupType() == DateGroupType.WEEK || sf.getDateGroupType() == DateGroupType.MONTH || sf.getDateGroupType() == DateGroupType.YEAR) {
            //如果是周，计算每天锻炼值
            chartData.setLegendData(new String[]{ "总时长(小时)", "平均每天(小时)", "平均每次(小时)","次数"});
            chartData.getYdata().add(yData3);
            chartData.getYdata().add(yData4);
        }
        //次数放最后
        chartData.getYdata().add(yData1);
        String totalString = totalCount.longValue() + "(次)," + minutesToHours(totalValue.doubleValue()) + "(小时)";
        chartData.setSubTitle(this.getDateTitle(sf, totalString));
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    private ChartCalendarData createChartCalendarData(MusicPracticeDateStatSearch sf) {
        List<MusicPracticeDateStat> list = musicPracticeService.statDateMusicPractice(sf);
        ChartCalendarData calandarData = ChartUtil.createChartCalendarData("音乐练习统计", "练习时间", "分钟", sf, list);
        if (!StringUtil.isEmpty(sf.getTune())) {
            MusicPracticeTuneByTunenameSearch mpts = new MusicPracticeTuneByTunenameSearch();
            BeanCopy.copyProperties(sf, mpts);
            PageRequest pr = mpts.buildQuery();
            pr.setBeanClass(MusicPracticeTune.class);
            List<MusicPracticeTune> dd = baseService.getBeanList(pr);
            //添加监控走势数据
            for (MusicPracticeTune tt : dd) {
                calandarData.addGraph(tt.getMusicPractice().getPracticeDate(), tt.getTimes());
            }
        } else {
            calandarData.setTop(3);
        }
        return calandarData;
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid MusicPracticeYoyStatSearch sf) {
        ChartData chartData = initYoyCharData(sf, musicPracticeService.getMusicInstrumentName(sf.getMusicInstrumentId()) + "练习统计同期对比", null);
        chartData.setUnit(sf.getGroupType().getUnit());
        String[] legendData = new String[sf.getYears().size()];
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            //数据,为了代码复用及统一，统计还是按照日期的统计
            MusicPracticeDateStatSearch dateSearch = new MusicPracticeDateStatSearch();
            dateSearch.setDateGroupType(sf.getDateGroupType());
            dateSearch.setStartDate(DateUtil.getDate(sf.getYears().get(i) + "-01-01", DateUtil.FormatDay1));
            dateSearch.setEndDate(DateUtil.getDate(sf.getYears().get(i) + "-12-31", DateUtil.FormatDay1));
            dateSearch.setUserId(sf.getUserId());
            dateSearch.setMusicInstrumentId(sf.getMusicInstrumentId());
            ChartYData yData = new ChartYData();
            yData.setName(sf.getYears().get(i).toString());
            List<MusicPracticeDateStat> list = musicPracticeService.statDateMusicPractice(dateSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (MusicPracticeDateStat bean : list) {
                temp.addXData(bean, sf.getDateGroupType());
                if (sf.getGroupType() == GroupType.COUNT) {
                    yData.getData().add(bean.getTotalCount());
                } else {
                    yData.getData().add(minutesToHours(bean.getTotalMinutes().doubleValue()));
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

        return callback(chartData);
    }

    /**
     * 按照时间统计（查看主要在哪个小时内练习，或者练习分钟数）
     *
     * @return
     */
    @RequestMapping(value = "/timeStat")
    public ResultBean timeStat(MusicPracticeTimeStatSearch sf) {
        List<MusicPracticeTimeStat> list = musicPracticeService.statTimeMusicPractice(sf);
        if (sf.getChartType() == ChartType.PIE) {
            return createTimeStatPieData(list, sf);
        } else {
            return createTimeStatBarData(list, sf);
        }
    }

    /**
     * 获取时间统计的饼图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ResultBean createTimeStatPieData(List<MusicPracticeTimeStat> list, MusicPracticeTimeStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle(this.getChartTitle(sf.getMusicInstrumentId()));
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        if (sf.getDateGroupType() == DateGroupType.MINUTE) {
            serieData.setName("练习时长");
        } else if (sf.getDateGroupType() == DateGroupType.HOUR) {
            serieData.setName("时间点");
        } else if (sf.getDateGroupType() == DateGroupType.DAY) {
            serieData.setName("天");
        } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
            serieData.setName("周");
        }
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (MusicPracticeTimeStat bean : list) {
            chartPieData.getXdata().add(bean.getIndexValue().toString());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(getTimeStatName(bean.getIndexValue(), sf.getDateGroupType()));
            dataDetail.setValue(bean.getTotalCount());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount()));
        }
        String subTitle = this.getDateTitle(sf, totalValue.longValue() + "次");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 获取时间统计的柱状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ResultBean createTimeStatBarData(List<MusicPracticeTimeStat> list, MusicPracticeTimeStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle(this.getChartTitle(sf.getMusicInstrumentId()));
        chartData.setUnit("次");
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        int year = DateUtil.getYear(sf.getEndDate() == null ? new Date() : sf.getEndDate());
        for (MusicPracticeTimeStat bean : list) {
            chartData.getIntXData().add(bean.getIndexValue());
            chartData.getXdata().add(getTimeStatName(bean.getIndexValue(), sf.getDateGroupType()));
            yData1.getData().add(bean.getTotalCount());
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount()));
        }
        chartData.getYdata().add(yData1);
        chartData = ChartUtil.completeDate(chartData, sf);
        String subTitle = this.getDateTitle(sf, totalValue.longValue()+ "次");
        chartData.setSubTitle(subTitle);
        return callback(chartData);
    }

    /**
     * 获取时间统计的名称表示
     *
     * @param dateGroupType
     * @return
     */
    private String getTimeStatName(Integer indexVaule, DateGroupType dateGroupType) {
        return ChartUtil.getStringXdata(dateGroupType, indexVaule);
    }

    /**
     * 获取统计图表的表头
     *
     * @param musicInstrumentId
     * @return
     */
    private String getChartTitle(Long musicInstrumentId) {
        return musicPracticeService.getMusicInstrumentName(musicInstrumentId) + "练习统计";
    }

    /**
     * 比对，采用散点图
     *
     * @return
     */
    @RequestMapping(value = "/compareStat")
    public ResultBean compareStat(@Valid MusicPracticeCompareStatSearch sf) {
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle("乐器练习比对");
        chartData.setxUnit(sf.getXgroupType().getName());
        chartData.setyUnit(sf.getYgroupType().getName());
        for (Long id : sf.getMusicInstrumentIds()) {
            MusicInstrument mi = baseService.getObject(MusicInstrument.class, id);
            chartData.addLegent(mi.getName());
            List<MusicPracticeCompareStat> list = musicPracticeService.statCompareMusicPractice(sf, id);
            ScatterChartDetailData detailData = new ScatterChartDetailData();
            detailData.setName(mi.getName());
            double totalX = 0;
            int n = 0;
            for (MusicPracticeCompareStat stat : list) {
                detailData.addData(new Object[]{stat.getxDoubleValue(), stat.getyDoubleValue()});
                totalX += stat.getxDoubleValue();
                n++;
            }
            detailData.setxAxisAverage(totalX / n);
            chartData.addSeriesData(detailData);
        }
        return callback(chartData);
    }

    /**
     * 总体统计
     *
     * @return
     */
    @RequestMapping(value = "/overallStat")
    public ResultBean overallStat(@Valid MusicPracticeOverallStatSearch sf) {
        ChartHeatmapData chartData = new ChartHeatmapData();
        chartData.setTitle("音乐练习统计");
        DateGroupType dateGroupType = sf.getDateGroupType();
        int[] minMax = ChartUtil.getMinMax(dateGroupType,sf.getStartDate(),sf.getEndDate());
        int min = minMax[0];
        int max = minMax[1];
        List<String> xdata = ChartUtil.getStringXdataList(dateGroupType,min, max);
        chartData.setXdata(xdata);
        //Y轴
        List<MusicInstrument> miLIst = musicPracticeService.getActiveMusicInstrument(sf.getUserId());
        Map<String,OverallYIndex> yMap = new HashMap<>();
        int stn = miLIst.size();
        for(int i=0;i<stn;i++){
            MusicInstrument st = miLIst.get(i);
            yMap.put(st.getId().toString(),new OverallYIndex(st.getId().toString(),st.getName(),"分钟",i));
            chartData.addYData(st.getName());
        }
        List<MusicPracticeOverallStat> list = musicPracticeService.statOverallMusicPractice(sf);
        GroupType valueType = sf.getValueType();
        ChartHeatmapSerieData serieData = new ChartHeatmapSerieData(valueType.getName());
        int vn = list.size();
        for (int i=0;i<vn;i++) {
            MusicPracticeOverallStat seos = list.get(i);
            int indexValue = seos.getIndexValue();
            if(dateGroupType==DateGroupType.DAY){
                indexValue = DateUtil.getDayOfYear(DateUtil.getDate(indexValue+"","yyyyMMdd"));
            }
            int xIndex = ChartUtil.getXIndex(dateGroupType,indexValue,min,xdata) ;
            OverallYIndex yi = yMap.get(seos.getMusicInstrumentId().toString());
            int yIndex = yi.getIndex();
            double value =0;
            String unit ="次";
            if(valueType==GroupType.COUNT){
                value = seos.getTotalCount().doubleValue();
            }else if(valueType==GroupType.MINUTES){
                value = NumberUtil.getDoubleValue(seos.getTotalMinutes().doubleValue()/60.0,1);
                unit ="小时";
            }
            chartData.updateMinMaxValue(value);
            serieData.addData(new Object[]{xIndex,yIndex,value,unit});
        }
        chartData.addSerieData(serieData);
        return callback(chartData);
    }


}
