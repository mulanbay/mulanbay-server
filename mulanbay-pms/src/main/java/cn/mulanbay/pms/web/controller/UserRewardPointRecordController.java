package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonOrderType;
import cn.mulanbay.pms.web.bean.request.user.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.user.UserRewardPointRecordResponse;
import cn.mulanbay.pms.web.bean.response.user.UserRpRMessageContentResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 积分记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userRewardPointRecord")
public class UserRewardPointRecordController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserRewardPointRecordController.class);

    private static Class<UserRewardPointRecord> beanClass = UserRewardPointRecord.class;

    @Autowired
    AuthService authService;

    @Autowired
    RedisDistributedLock redisDistributedLock;

    /**
     * 获取任列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserRewardPointRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("createdTime", Sort.DESC);
        pr.addSort(s1);
        PageResult<UserRewardPointRecord> qr = baseService.getBeanResult(pr);
        PageResult<UserRewardPointRecordResponse> result = new PageResult<UserRewardPointRecordResponse>(qr);
        List<UserRewardPointRecordResponse> list = new ArrayList<>();
        for (UserRewardPointRecord urp : qr.getBeanList()) {
            UserRewardPointRecordResponse bean = new UserRewardPointRecordResponse();
            BeanCopy.copyProperties(urp, bean);
            String sourceName = this.getSourceName(urp.getSourceId(), urp.getRewardSource());
            if (sourceName != null) {
                bean.setSourceName(sourceName);
            } else {
                bean.setSourceName(urp.getRemark());
            }
            list.add(bean);
        }
        result.setBeanList(list);
        return callbackDataGrid(result);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        String[] ss = deleteRequest.getIds().split(",");
        Long userId = deleteRequest.getUserId();
        for (String s : ss) {
            UserRewardPointRecord bean = this.getUserEntity(beanClass, Long.valueOf(s), deleteRequest.getUserId());
            String key = CacheKey.getKey(CacheKey.REWARD_POINT_LOCK, userId.toString());
            try {
                boolean b = redisDistributedLock.lock(key, 5000L, 3, 20);
                if (!b) {
                    return callbackErrorCode(PmsErrorCode.USER_REWARD_UPDATE_LOCK_FAIL);
                }
                authService.revertUserPoint(bean, "还原原来的积分记录");
            } catch (Exception e) {
                logger.error("更新用户ID=" + userId + "积分异常", e);
            } finally {
                redisDistributedLock.releaseLock(key);
            }
        }
        return callback(null);
    }


    /**
     * 日终积分统计
     *
     * @return
     */
    @RequestMapping(value = "/dailyStat", method = RequestMethod.GET)
    public ResultBean dailyStat(@Valid UserPointsDailyStatSearch ss) {
        Date startTime = ss.getDate();
        if (startTime == null) {
            startTime = DateUtil.getFromMiddleNightDate(new Date());
        }
        Date endTime = DateUtil.getTodayTillMiddleNightDate(startTime);
        UserRewardPointsStat ps = null;
        List<UserRewardPointsStat> list = authService.statUserRewardPoints(startTime, endTime, ss.getUserId());
        if (list.isEmpty()) {
            ps = new UserRewardPointsStat();
            ps.setTotalCount(BigInteger.ZERO);
            ps.setTotalPoints(BigDecimal.ZERO);
        } else {
            ps = list.get(0);
        }
        Integer currentPoints = authService.getUserPoint(ss.getUserId().longValue());
        ps.setCurrentPoints(new BigDecimal(currentPoints));
        String dateStr = DateUtil.getFormatDate(startTime, DateUtil.FormatDay1);
        ps.setDateStr(dateStr);
        return callback(ps);
    }

    /**
     * 积分时间线
     *
     * @return
     */
    @RequestMapping(value = "/pointsTimelineStat", method = RequestMethod.GET)
    public ResultBean pointsTimelineStat(UserPointsTimelineStatSearch sf) {
        if (sf.getDateGroupType() == null) {
            return callback(createTimelineUserPoint(sf));
        } else if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
            return callback(createCalandarTimelineUserPoint(sf));
        } else {
            return callback(createUserPointsDateStat(sf));
        }
    }

    /**
     * 日历类型
     *
     * @param sf
     * @return
     */
    private ChartCalendarCompareData createCalandarTimelineUserPoint(UserPointsTimelineStatSearch sf) {
        if (sf.getStartDate() == null || sf.getEndDate() == null) {
            throw new ApplicationException(PmsErrorCode.START_OR_END_DATE_NULL);
        }
        List<UserPointsDateStat> list = authService.statDateUserPoints(sf);
        int year = DateUtil.getYear(sf.getStartDate());
        int endYear = DateUtil.getYear(sf.getEndDate());
        if (year != endYear) {
            throw new ApplicationException(PmsErrorCode.START_YEAR_NOT_EQUALS_END_YEAR);
        }
        ChartCalendarCompareData chartData = new ChartCalendarCompareData();
        String sourceName = this.getSourceName(sf.getSourceId(), sf.getRewardSource());
        if (sourceName != null) {
            chartData.setTitle("[" + sourceName + "]" + year + "年用户积分统计");
        } else {
            chartData.setTitle(year + "年用户积分统计");
        }
        chartData.setUnit("次");
        chartData.setYear(year);
        for (UserPointsDateStat stat : list) {
            String dd = DateUtil.getFormatDateString(String.valueOf(stat.getDateIndexValue()), "yyyyMMdd", DateUtil.FormatDay1);
            double vv = stat.getCalendarStatValue();
            if (vv >= 0) {
                chartData.addSerieData(dd, vv, false, 1, 1);
            } else {
                chartData.addSerieData(dd, 0 - vv, false, 1, 2);
            }
        }
        chartData.setLegendData(new String[]{"正分", "负分"});
        return chartData;
    }

    /**
     * 时间线
     *
     * @param sf
     * @return
     */
    private ChartData createTimelineUserPoint(@Valid UserPointsTimelineStatSearch sf) {
        UserRewardPointRecordSearch urpr = new UserRewardPointRecordSearch();
        BeanCopy.copyProperties(sf, urpr);
        //不需要分页
        urpr.setPage(-1);
        PageRequest pr = urpr.buildQuery();
        pr.setBeanClass(UserRewardPointRecord.class);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        List<UserRewardPointRecord> list = baseService.getBeanList(pr);
        ChartData chartData = new ChartData();
        String sourceName = this.getSourceName(sf.getSourceId(), sf.getRewardSource());
        if (sourceName != null) {
            chartData.setTitle("[" + sourceName + "]用户积分统计");
        } else {
            chartData.setTitle("用户积分统计");
        }
        chartData.setLegendData(new String[]{"积分"});
        ChartYData yData = new ChartYData("积分");
        String timeFormat = "yyyy-MM-dd HH:mm";
        int startYear = DateUtil.getYear(urpr.getStartDate());
        int endYear = DateUtil.getYear(urpr.getEndDate());
        if (startYear == endYear) {
            //同一年内
            timeFormat = "MM-dd HH:mm";
        }
        for (UserRewardPointRecord bean : list) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getCreatedTime(), timeFormat));
            yData.getData().add(bean.getAfterPoints());
        }
        chartData.getYdata().add(yData);
        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        return chartData;
    }

    private ChartData createUserPointsDateStat(UserPointsTimelineStatSearch tlSf) {
        List<UserPointsDateStat> list = authService.statDateUserPoints(tlSf);
        ChartData chartData = new ChartData();
        String sourceName = this.getSourceName(tlSf.getSourceId(), tlSf.getRewardSource());
        if (sourceName != null) {
            chartData.setTitle("[" + sourceName + "]用户积分统计");
        } else {
            chartData.setTitle("用户积分统计");
        }
        chartData.setLegendData(new String[]{"次数", "总积分", "平均积分"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("总积分");
        ChartYData yData3 = new ChartYData();
        yData3.setName("平均积分");
        for (UserPointsDateStat bean : list) {
            chartData.getXdata().add(bean.getIndexValue().toString());
            yData1.getData().add(bean.getTotalCount().longValue());
            yData2.getData().add(bean.getTotalRewardPoints().longValue());
            yData3.getData().add(NumberUtil.getAverageValue(bean.getTotalRewardPoints().longValue(), bean.getTotalCount().intValue(), 2));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData3);
        String subTitle = this.getDateTitle(tlSf);
        chartData.setSubTitle(subTitle);
        return chartData;
    }

    /**
     * 按照值积分值统计
     *
     * @return
     */
    @RequestMapping(value = "/pointsValueStat", method = RequestMethod.GET)
    public ResultBean pointsValueStat(UserPointsValueStatSearch sf) {
        sf.setRewardSourceIntByRewardSource(sf.getRewardSource());
        if (sf.getDateGroupType() == null) {
            return callback(createDefaultPointsValueStat(sf));
        } else {
            UserPointsTimelineStatSearch tlSf = new UserPointsTimelineStatSearch();
            BeanCopy.copyProperties(sf, tlSf);
            if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
                return callback(createCalandarTimelineUserPoint(tlSf));
            } else {
                return callback(createUserPointsDateStat(tlSf));
            }
        }

    }

    private ChartPieData createDefaultPointsValueStat(UserPointsValueStatSearch sf) {
        List<UserPointsValueStat> list = authService.statUserPointsValue(sf);
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("[" + this.getSourceName(sf.getSourceId(), sf.getRewardSource()) + "]积分值统计");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("积分(次)");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (UserPointsValueStat bean : list) {
            chartPieData.getXdata().add(bean.getRewards().toString() + "分");
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getRewards().toString() + "分");
            dataDetail.setValue(bean.getTotalCount().intValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getTotalRewardPoints().longValue()));
        }
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.intValue()) + "分");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    private String getSourceName(Long sourceId, RewardSource rewardSource) {
        if (sourceId == null || rewardSource == null) {
            return null;
        }
        if (rewardSource == RewardSource.NOTIFY) {
            UserNotify userNotify = baseService.getObject(UserNotify.class, sourceId);
            return userNotify == null ? "未知" : userNotify.getTitle();
        } else if (rewardSource == RewardSource.PLAN) {
            UserPlan userPlan = baseService.getObject(UserPlan.class, sourceId);
            return userPlan == null ? "未知" : userPlan.getTitle();
        } else if (rewardSource == RewardSource.OPERATION) {
            SystemFunction systemFunction = baseService.getObject(SystemFunction.class, sourceId);
            return systemFunction == null ? "未知" : systemFunction.getName();
        } else if (rewardSource == RewardSource.COMMON_RECORD) {
            CommonRecordType commonRecordType = baseService.getObject(CommonRecordType.class, sourceId.intValue());
            return commonRecordType == null ? "未知" : commonRecordType.getName();
        } else if (rewardSource == RewardSource.DREAM) {
            Dream dream = baseService.getObject(Dream.class, sourceId);
            return dream == null ? "未知" : dream.getName();
        } else if (rewardSource == RewardSource.BUDGET) {
            Budget bd = baseService.getObject(Budget.class, sourceId);
            return bd.getName();
        } else if (rewardSource == RewardSource.BUDGET_LOG) {
            BudgetLog bl = baseService.getObject(BudgetLog.class, sourceId);
            if (bl == null) {
                return "未知";
            } else if (bl.getBudget() == null) {
                return bl.getPeriodName() + "预算统计";
            } else {
                return bl.getBudget().getName();
            }
        }
        return null;
    }

    /**
     * 获取用户积分统计(完整统计、旭日图使用)
     *
     * @return
     */
    @RequestMapping(value = "/fullStat", method = RequestMethod.GET)
    public ResultBean fullStat(UserPointsSourceFullStatSearch sf) {
        List<UserPointsSourceFullStat> list = authService.fullStatUserPoints(sf);
        ChartSunburstData chartData = new ChartSunburstData();
        if(StringUtil.isEmpty(list)){
            return callback(chartData);
        }
        //先计算最小值最大值，正负需要分开
        //正
        long pMin = Long.MAX_VALUE;
        long pMax=0L;
        //负
        long nMin = 0L;
        long nMax=Long.MIN_VALUE;
        for(UserPointsSourceFullStat rr : list){
            long l = rr.getTotalRewardPoints().longValue();
            if(l>=0){
                if(l>pMax){
                    pMax =l;
                }
                if(l<pMin){
                    pMin =l;
                }
            }else{
                if(l>=nMax){
                    nMax =l;
                }
                if(l<nMin){
                    nMin =l;
                }
            }
        }
        //分为5份
        long pAvg = (pMax-pMin)/5;
        long nAvg = (nMin-nMax)/5;
        List<ChartSunburstDetailData> voList = new ArrayList<>();
        for(UserPointsSourceFullStat rr : list){
            //第一层：正负
            String flowName = this.getFlowName(rr.getFlow());
            ChartSunburstDetailData vo = this.getFullStatVo(flowName,voList);
            if(vo ==null){
                vo = new ChartSunburstDetailData();
                vo.setName(flowName);
                voList.add(vo);
            }
            //第二层：来源分类
            String rewardSource = RewardSource.getRewardSource(rr.getRewardSource().intValue()).getName();
            ChartSunburstDetailData rewardSourceVo = this.getFullStatVo(rewardSource,vo.getChildren());
            if(rewardSourceVo==null){
                rewardSourceVo = new ChartSunburstDetailData();
                rewardSourceVo.setName(rewardSource);
                vo.addChild(rewardSource);
            }
            //第三层：分值范围
            long pp = 5;
            long trp = rr.getTotalRewardPoints().longValue();
            if(trp>=0){
                pp = trp/pAvg;
            }else{
                pp = trp/nAvg;
            }
            String pointsRange = pp+"";
            ChartSunburstDetailData pointsVo = this.getFullStatVo(pointsRange,rewardSourceVo.getChildren());
            if(pointsVo==null){
                pointsVo = new ChartSunburstDetailData();
                pointsVo.setName(pointsRange);
                rewardSourceVo.addChild(pointsVo);
            }
            //第四层：名称
            String sourceName;
            RewardSource rs = RewardSource.getRewardSource(rr.getRewardSource().intValue());
            if (rr.getSourceId() == null) {
                sourceName = "未知";
            } else {
                sourceName = this.getSourceName(rr.getSourceId().longValue(), rs);
            }
            String ps = rr.getTotalRewardPoints().longValue()>0 ? "+"+rr.getTotalRewardPoints().longValue() : rr.getTotalRewardPoints().longValue()+"";
            sourceName+="("+rr.getTotalCount().intValue()+"次,"+ps+"分)";
            ChartSunburstDetailData sourceNameVo = new ChartSunburstDetailData();
            sourceNameVo.setName(sourceName);
            pointsVo.addChild(sourceNameVo);
        }
        chartData.setDataList(voList);
        return callback(chartData);
    }

    /**
     * 积分范围
     * @param totalRewardPoints
     * @return
     */
    private String getPointsRange(BigDecimal totalRewardPoints,long avg){
        long p = totalRewardPoints.longValue();
        //取绝对值
        long n = Math.abs(p)/avg;
        if(n>5){
            n=5;
        }
        return n+"";
    }

    /**
     * 正负名称
     * @param flow
     * @return
     */
    private String getFlowName(Integer flow){
        if(flow>0){
            return "正分";
        }else if(flow<0){
            return "负分";
        }else {
            return "无分";
        }
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
     * 按照来源的积分值统计
     *
     * @return
     */
    @RequestMapping(value = "/pointsSourceStat", method = RequestMethod.GET)
    public ResultBean pointsSourceStat(UserPointsSourceStatSearch sf) {
        sf.setRewardSourceIntByRewardSource(sf.getRewardSource());
        List<UserPointsSourceStat> list = authService.statUserPointsSource(sf);
        if (sf.getChartType() == ChartType.BAR) {
            return callback(createUserPointsSourceStatBarChartData(list, sf));
        } else {
            return callback(createUserPointsSourceStatPieChartData(list, sf));
        }
    }

    private ChartData createUserPointsSourceStatBarChartData(List<UserPointsSourceStat> list, UserPointsSourceStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("用户积分统计");
        chartData.setLegendData(new String[]{"次数", "积分"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("积分");
        for (UserPointsSourceStat bean : list) {
            String sourceName;
            RewardSource rewardSource = RewardSource.getRewardSource(bean.getRewardSource().intValue());
            if (bean.getSourceId() == null) {
                sourceName = "未知";
            } else {
                sourceName = this.getSourceName(bean.getSourceId().longValue(), rewardSource);
            }
            chartData.getXdata().add(sourceName == null ? "未知" : sourceName);
            yData1.getData().add(bean.getTotalCount().longValue());
            yData2.getData().add(bean.getTotalRewardPoints().longValue());
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        return chartData;
    }

    /**
     * 封装消费分析的饼状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartPieData createUserPointsSourceStatPieChartData(List<UserPointsSourceStat> list, UserPointsSourceStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("用户积分统计");
        ChartPieSerieData serieData = new ChartPieSerieData();
        if (sf.getOrderBy() == CommonOrderType.COUNTS) {
            serieData.setName("次数");
        } else {
            serieData.setName("积分");
        }
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (UserPointsSourceStat bean : list) {
            RewardSource rewardSource = RewardSource.getRewardSource(bean.getRewardSource().intValue());
            String sourceName = this.getSourceName(bean.getSourceId().longValue(), rewardSource);
            if (sourceName == null) {
                sourceName = "未知";
            }
            chartPieData.getXdata().add(sourceName);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(sourceName);
            if (sf.getOrderBy() == CommonOrderType.COUNTS) {
                dataDetail.setValue(bean.getTotalCount().longValue());
                totalValue = totalValue.add(new BigDecimal(bean.getTotalCount().longValue()));
            } else {
                dataDetail.setValue(bean.getTotalRewardPoints().longValue());
                totalValue = totalValue.add(new BigDecimal(bean.getTotalRewardPoints().longValue()));
            }
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/getMessageContent", method = RequestMethod.GET)
    public ResultBean getMessageContent(@Valid CommonBeanGetRequest getRequest) {
        UserRewardPointRecord bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        if (bean.getMessageId() == null) {
            return callbackErrorInfo("没有相关消息记录");
        } else {
            UserRpRMessageContentResponse res = new UserRpRMessageContentResponse();
            res.setRewardSource(bean.getRewardSource());
            if (bean.getRewardSource() == RewardSource.OPERATION) {
                OperationLog ol = baseService.getObject(OperationLog.class, bean.getMessageId());
                if (ol == null) {
                    return callbackErrorInfo("未找到相关操作记录");
                } else {
                    res.setPara(ol.getParas());
                    res.setUrl(ol.getUrlAddress());
                    return callback(res);
                }
            } else if (bean.getRewardSource() == RewardSource.COMMON_RECORD) {
                CommonRecord cr = baseService.getObject(CommonRecord.class, bean.getMessageId());
                res.setUrl(cr.getCommonRecordType().getName());
                res.setPara(JsonUtil.beanToJson(cr));
                return callback(res);
            } else {
                UserMessage userMessage = baseService.getObject(UserMessage.class, bean.getMessageId());
                if (userMessage == null) {
                    return callbackErrorInfo("未找到相关消息记录");
                } else {
                    res.setPara(userMessage.getContent());
                    return callback(res);
                }
            }
        }
    }
}
