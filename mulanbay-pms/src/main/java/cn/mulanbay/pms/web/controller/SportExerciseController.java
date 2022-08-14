package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.SportExercise;
import cn.mulanbay.pms.persistent.domain.SportMilestone;
import cn.mulanbay.pms.persistent.domain.SportType;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.dto.SportExerciseDateStat;
import cn.mulanbay.pms.persistent.dto.SportExerciseMultiStat;
import cn.mulanbay.pms.persistent.dto.SportExerciseStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.NextMilestoneType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.SportExerciseService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.sport.*;
import cn.mulanbay.pms.web.bean.response.chart.ChartCalendarData;
import cn.mulanbay.pms.web.bean.response.chart.ChartCalendarMultiData;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.pms.web.bean.response.sport.SportExerciseResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 运动锻炼管理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/sportExercise")
public class SportExerciseController extends BaseController {

    private static Class<SportExercise> beanClass = SportExercise.class;

    @Value("${reward.createByTemplate.perPoints}")
    int ctPerPPoints;

    @Autowired
    SportExerciseService sportExerciseService;

    @Autowired
    RewardPointsHandler rewardPointsHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    DataService dataService;
    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SportExerciseSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort(sf.getSortField().getField(), sf.getSortType());
        pr.addSort(s);
        PageResult<SportExercise> qr = baseService.getBeanResult(pr);
        long userId = sf.getUserId();
        User user = baseService.getObject(User.class, userId);
        Date birthday = user.getBirthday();
        int birthYear = 0;
        if (birthday != null) {
            birthYear = DateUtil.getYear(birthday);
        } else {
            birthYear = 1970;
        }
        List<SportExerciseResponse> beanList = new ArrayList<>();
        int baseMaxHeartRate = systemConfigHandler.getIntegerConfig("system.maxHeartRate.base");
        for (SportExercise se : qr.getBeanList()) {
            //根据年龄计算最大心率范围
            int myAge = DateUtil.getYear(se.getExerciseDate()) - birthYear + 1;
            se.setSafeMaxHeartRate(baseMaxHeartRate - myAge);
            SportExerciseResponse res = new SportExerciseResponse();
            BeanCopy.copyProperties(se, res);
            long cc = sportExerciseService.getMilestoneCount(se.getId());
            res.setSportMilestones(cc);
            beanList.add(res);
        }
        PageResult<SportExerciseResponse> result = new PageResult<>();
        result.setBeanList(beanList);
        result.setPageSize(qr.getPageSize());
        result.setMaxRow(qr.getMaxRow());
        return callbackDataGrid(result);
    }

    /**
     * 总的概要统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(SportExerciseStatSearch sf) {
        SportExerciseStat data = sportExerciseService.statSportExercise(sf);
        return callback(data);
    }

    /**
     * 获取锻炼的多重统计：最大、最小、平均
     *
     * @return
     */
    @RequestMapping(value = "/multiStat", method = RequestMethod.GET)
    public ResultBean multiStat(SportExerciseMultiStatSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        SportExerciseMultiStat data = sportExerciseService.statMultiSportExercise(sf);
        return callback(data);
    }

    /**
     * 获取锻炼的多重统计：最大、最小、平均
     *
     * @return
     */
    @RequestMapping(value = "/getByMultiStat", method = RequestMethod.GET)
    public ResultBean getByMultiStat(SportExerciseByMultiStatSearch sf) {
        SportExercise data = sportExerciseService.getSportExerciseByMultiStat(sf);
        return callback(data);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SportExerciseFormRequest formRequest) {
        SportExercise bean = new SportExercise();
        BeanCopy.copyProperties(formRequest, bean);
        SportType sportType = this.getUserEntity(SportType.class, formRequest.getSportTypeId(), formRequest.getUserId());
        bean.setSportType(sportType);
        bean.setCreatedTime(new Date());
        sportExerciseService.saveSportExercise(bean, true);
        return callback(bean);
    }

    /**
     * 以某天的模板创建
     *
     * @return
     */
    @RequestMapping(value = "/createByTemplate", method = RequestMethod.POST)
    public ResultBean createByTemplate(@RequestBody @Valid SportExerciseCTFormRequest formRequest) {
        Date temEndTime = DateUtil.getTodayTillMiddleNightDate(formRequest.getTemplateDate());
        SportExerciseSearch sf = new SportExerciseSearch();
        sf.setStartDate(formRequest.getTemplateDate());
        sf.setEndDate(temEndTime);
        sf.setUserId(formRequest.getUserId());
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("exerciseDate", Sort.ASC);
        pr.addSort(s);
        List<SportExercise> list = baseService.getBeanList(pr);
        if (list.isEmpty()) {
            return callbackErrorInfo("无法找到[" + DateUtil.getFormatDate(formRequest.getTemplateDate(), DateUtil.FormatDay1) + "]的锻炼记录");
        } else {
            Date ed = formRequest.getBeginTime();
            List<SportExercise> newList = new ArrayList<>();
            for (SportExercise se : list) {
                SportExercise nn = new SportExercise();
                BeanCopy.copyProperties(se, nn);
                nn.setId(null);
                nn.setCreatedTime(new Date());
                nn.setLastModifyTime(null);
                nn.setExerciseDate(ed);
                nn.setRemark("以模板新增");
                ed = new Date(ed.getTime() + nn.getMinutes() * 60 * 1000);
                newList.add(nn);
            }
            sportExerciseService.saveSportExerciseList(newList, true);
            //增加积分
            int n = newList.size();
            rewardPointsHandler.rewardPoints(formRequest.getUserId(), n * ctPerPPoints, null,
                    RewardSource.OPERATION, "以模板新增" + n + "条锻炼记录", null);

        }
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        SportExercise bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SportExerciseFormRequest formRequest) {
        SportExercise bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        SportType sportType = this.getUserEntity(SportType.class, formRequest.getSportTypeId(), formRequest.getUserId());
        bean.setSportType(sportType);
        bean.setLastModifyTime(new Date());
        sportExerciseService.saveSportExercise(bean, true);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        //需要删除对应的里程碑信息
        String[] ss = deleteRequest.getIds().split(",");
        for (String s : ss) {
            sportExerciseService.deleteSportExercise(Long.valueOf(s));
        }
        return callback(null);
    }

    /**
     * 达到的里程碑，不管以前的锻炼是否已经有实现过
     * 前端采用grid，因此这里采用grid返回格式
     *
     * @return
     */
    @RequestMapping(value = "/getAchieveMilestones", method = RequestMethod.GET)
    public ResultBean getAchieveMilestones(Long sportExerciseId) {
        List<SportMilestone> list = sportExerciseService.getAchieveMilestones(sportExerciseId);
        PageResult<SportMilestone> qr = new PageResult<>();
        qr.setMaxRow(list.size());
        qr.setPageSize(list.size());
        qr.setBeanList(list);
        return callbackDataGrid(qr);
    }

    /**
     * 获取下一个待达到的里程碑
     *
     * @return
     */
    @RequestMapping(value = "/getNextAchieveMilestone", method = RequestMethod.GET)
    public ResultBean getNextAchieveMilestone(Long sportExerciseId, NextMilestoneType type) {
        SportMilestone sm = sportExerciseService.getNextAchieveMilestone(sportExerciseId, type);
        return callback(sm);
    }

    /**
     * 刷新最佳统计
     *
     * @return
     */
    @RequestMapping(value = "/refreshMaxStat", method = RequestMethod.GET)
    public ResultBean refreshMaxStat(Integer sportTypeId) {
        sportExerciseService.updateAndRefreshSportExerciseMaxStat(sportTypeId);
        return callback(null);
    }

    /**
     * 锻炼管理的统计接口
     * 界面上使用echarts展示图表，后端返回的是核心模块的数据，不再使用Echarts的第三方jar包封装（比较麻烦）
     *
     * @return
     */
    @RequestMapping(value = "/dateStat", method = RequestMethod.GET)
    public ResultBean dateStat(@Valid SportExerciseDateStatSearch sf) {
        switch (sf.getDateGroupType()){
            case DAYCALENDAR :
                //日历
                List<SportExerciseDateStat> list = sportExerciseService.statDateSportExercise(sf);
                ChartCalendarData calandarData = ChartUtil.createChartCalendarData("锻炼统计", "公里数", "km", sf, list);
                if (!StringUtil.isEmpty(sf.getBestField())) {
                    //获取最佳记录
                    List<SportExercise> bests = sportExerciseService.getBestMilestoneSportExerciseList(sf);
                    for (SportExercise se : bests) {
                        if ("mileageBest".equals(sf.getBestField())) {
                            calandarData.addGraph(se.getExerciseDate(), se.getKilometres());
                        } else {
                            calandarData.addGraph(se.getExerciseDate(), se.getMaxSpeed());
                        }
                    }
                }
                return callback(calandarData);
            case HOURMINUTE :
                //散点图
                PageRequest pr = sf.buildQuery();
                pr.setBeanClass(beanClass);
                List<Date> dateList = dataService.getDateList(pr,"exerciseDate");
                return callback(this.createHMChartData(dateList,"锻炼分析","锻炼时间点"));
            default:
                break;
        }

        ChartData chartData = new ChartData();
        chartData.setTitle("锻炼统计");
        //混合图形下使用
        chartData.addYAxis("数值","");
        chartData.addYAxis("次数","次");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalKilometres = new BigDecimal(0);
        DateGroupType dateGroupType = sf.getDateGroupType();
        String unit="";
        if(sf.getSportTypeId()!=null){
            SportType sportType = baseService.getObject(SportType.class, sf.getSportTypeId());
            unit = sportType.getUnit();
        }
        String totalKey = "总" + unit + "数";
        String averKey = "平均" + unit + "数";
        //详细统计才加各种统计信息
        if (sf.getFullStat()) {
            if (dateGroupType == DateGroupType.DAY) {
                chartData.setLegendData(new String[]{totalKey, "锻炼时间", "平均速度", "配速", "最大心率", "平均心率", "锻炼次数"});
            } else {
                //非天计算的则要添加平均公里数
                chartData.setLegendData(new String[]{totalKey, averKey, "锻炼时间", "平均速度", "配速", "最大心率", "平均心率", "锻炼次数"});
            }
        } else {
            chartData.setLegendData(new String[]{totalKey, "锻炼次数"});
        }
        ChartYData kilometresData = new ChartYData(totalKey);
        ChartYData averageKilometresData = new ChartYData(averKey);
        ChartYData minutesData = new ChartYData("锻炼时间");
        ChartYData speedData = new ChartYData("平均速度");
        ChartYData paceData = new ChartYData("配速");
        ChartYData maxHeartRateData = new ChartYData("最大心率");
        ChartYData averageHeartRateData = new ChartYData("平均心率");
        ChartYData countData = new ChartYData("锻炼次数");
        List<SportExerciseDateStat> list = sportExerciseService.statDateSportExercise(sf);
        for (SportExerciseDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            kilometresData.getData().add(bean.getTotalKilometres());
            averageKilometresData.getData().add(NumberUtil.getAverageValue(bean.getTotalKilometres(), bean.getTotalCount(), 2));
            minutesData.getData().add(NumberUtil.getAverageValue(bean.getTotalMinutes(), bean.getTotalCount(), 0));
            speedData.getData().add(NumberUtil.getAverageValue(bean.getTotalSpeed(), bean.getTotalCount(), 1));
            paceData.getData().add(NumberUtil.getAverageValue(bean.getTotalPace(), bean.getTotalCount(), 1));
            maxHeartRateData.getData().add(NumberUtil.getAverageValue(bean.getTotalMaxHeartRate(), bean.getTotalCount(), 0));
            averageHeartRateData.getData().add(NumberUtil.getAverageValue(bean.getTotalAverageHeartRate(), bean.getTotalCount(), 0));
            countData.getData().add(bean.getTotalCount());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            totalKilometres = totalKilometres.add(bean.getTotalKilometres());
        }
        String totalString = totalCount.longValue() + "(次)," + totalKilometres.doubleValue() + "(" + unit + ")";
        chartData.setSubTitle(this.getDateTitle(sf, totalString));
        chartData.getYdata().add(kilometresData);
        if (sf.getFullStat()) {
            if (dateGroupType != DateGroupType.DAY) {
                chartData.getYdata().add(averageKilometresData);
            }
            chartData.getYdata().add(minutesData);
            chartData.getYdata().add(speedData);
            chartData.getYdata().add(paceData);
            chartData.getYdata().add(maxHeartRateData);
            chartData.getYdata().add(averageHeartRateData);
        }
        chartData.getYdata().add(countData);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    /**
     * 同期统计
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid SportExerciseYoyStatSearch sf) {
        if (sf.getDateGroupType() == DateGroupType.DAY) {
            return callback(createChartCalandarMultiData(sf));
        }
        SportType sp =this.getUserEntity(SportType.class,sf.getSportTypeId(),sf.getUserId());
        String title = "["+sp.getName()+"]运动锻炼同期对比";
        ChartData chartData = initYoyCharData(sf, title, null);
        if(sf.getGroupType()== GroupType.KILOMETRES){
            chartData.setUnit(sp.getUnit());
        }else{
            chartData.setUnit(sf.getGroupType().getUnit());
        }
        String[] legendData = new String[sf.getYears().size()];
        //是否统计值（参数只对公里数、锻炼时间有效）
        boolean isSum = sf.getSumValue() == null ? true : sf.getSumValue();
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            //数据,为了代码复用及统一，统计还是按照日期的统计
            ChartYData yData = new ChartYData();
            yData.setName(sf.getYears().get(i).toString());
            SportExerciseDateStatSearch dateSearch = generateSearch(sf.getYears().get(i), sf);
            List<SportExerciseDateStat> list = sportExerciseService.statDateSportExercise(dateSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (SportExerciseDateStat bean : list) {
                temp.addXData(bean, sf.getDateGroupType());
                BigInteger cc = BigInteger.valueOf(1);
                if (!isSum) {
                    // 如果取平均值的话要除以总条数
                    cc = bean.getTotalCount();
                }
                yData.getData().add(this.getStatValue(sf.getGroupType(), bean, cc));
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
     * 基于日历的热点图
     *
     * @param sf
     * @return
     */
    private ChartCalendarMultiData createChartCalandarMultiData(SportExerciseYoyStatSearch sf) {
        ChartCalendarMultiData data = new ChartCalendarMultiData();
        data.setTitle("运动锻炼同期对比");
        data.setUnit(sf.getGroupType().getUnit());
        for (int i = 0; i < sf.getYears().size(); i++) {
            BigInteger cc = BigInteger.valueOf(1);
            SportExerciseDateStatSearch dateSearch = generateSearch(sf.getYears().get(i), sf);
            List<SportExerciseDateStat> list = sportExerciseService.statDateSportExercise(dateSearch);
            for (SportExerciseDateStat bean : list) {
                String dateString = DateUtil.getFormatDateString(bean.getIndexValue().toString(), "yyyyMMdd", "yyyy-MM-dd");
                data.addData(sf.getYears().get(i), dateString, this.getStatValue(sf.getGroupType(), bean, cc));
            }
        }
        return data;
    }

    private SportExerciseDateStatSearch generateSearch(int year, SportExerciseYoyStatSearch sf) {
        SportExerciseDateStatSearch dateSearch = new SportExerciseDateStatSearch();
        dateSearch.setSportTypeId(sf.getSportTypeId());
        dateSearch.setDateGroupType(sf.getDateGroupType());
        dateSearch.setStartDate(DateUtil.getDate(year + "-01-01", DateUtil.FormatDay1));
        dateSearch.setEndDate(DateUtil.getDate(year + "-12-31", DateUtil.FormatDay1));
        dateSearch.setUserId(sf.getUserId());
        return dateSearch;
    }

    private Object getStatValue(GroupType groupType, SportExerciseDateStat bean, BigInteger cc) {
        if (groupType == GroupType.COUNT) {
            return bean.getTotalCount();
        } else if (groupType == GroupType.AVERAGEHEART) {
            return NumberUtil.getAverageValue(bean.getTotalAverageHeartRate(), bean.getTotalCount(), 0);
        } else if (groupType == GroupType.MAXHEARTRATE) {
            return NumberUtil.getAverageValue(bean.getTotalMaxHeartRate(), bean.getTotalCount(), 0);
        } else if (groupType == GroupType.KILOMETRES) {
            return NumberUtil.getAverageValue(bean.getTotalKilometres(), cc, 0);
        } else if (groupType == GroupType.MINUTES) {
            return NumberUtil.getAverageValue(bean.getTotalMinutes(), cc, 0);
        } else if (groupType == GroupType.PACE) {
            return NumberUtil.getAverageValue(bean.getTotalPace(), bean.getTotalCount(), 2);
        } else if (groupType == GroupType.SPEED) {
            return NumberUtil.getAverageValue(bean.getTotalSpeed(), bean.getTotalCount(), 2);
        } else {
            return 0;
        }
    }

}
