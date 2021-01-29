package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.UserScoreHandler;
import cn.mulanbay.pms.persistent.domain.UserScore;
import cn.mulanbay.pms.persistent.domain.UserScoreDetail;
import cn.mulanbay.pms.persistent.dto.UserScorePointsCompareDto;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.service.UserScoreService;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.user.*;
import cn.mulanbay.pms.web.bean.response.auth.UseScoreVo;
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
 * 用户评分
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userScore")
public class UserScoreController extends BaseController {

    private static Class<UserScore> beanClass = UserScore.class;

    @Autowired
    UserScoreHandler userScoreHandler;

    @Autowired
    UserScoreService userScoreService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserScoreSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("endTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<UserScore> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserScore bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 积分详情
     *
     * @return
     */
    @RequestMapping(value = "/getScoreDetail", method = RequestMethod.GET)
    public ResultBean getScoreDetail(@Valid CommonBeanGetRequest getRequest) {
        UserScore userScore = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        List<UserScoreDetail> list = userScoreService.selectUserScoreDetailList(userScore.getId());
        return callback(createUserScoreResult(list));
    }

    private List<UseScoreVo> createUserScoreResult(List<UserScoreDetail> list) {
        List<UseScoreVo> res = new ArrayList<>();
        for (UserScoreDetail dd : list) {
            UseScoreVo bean = new UseScoreVo();
            BeanCopy.copyProperties(dd, bean);
            BeanCopy.copyProperties(dd.getScoreConfig(), bean);
            res.add(bean);
        }
        return res;
    }

    /**
     * 用户自评
     *
     * @return
     */
    @RequestMapping(value = "/selfJudge", method = RequestMethod.POST)
    public ResultBean selfJudge(@RequestBody @Valid SelfJudgeScoreRequest jlr) {
        List<UserScoreDetail> list = userScoreHandler.getUseScore(jlr.getUserId(), jlr.getGroupCode(), new Date());
        return callback(createUserScoreResult(list));
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid UserScoreSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("endTime", Sort.ASC);
        pr.addSort(sort);
        pr.setPage(-1);
        List<UserScore> list = baseService.getBeanList(pr);
        if (sf.getChartType() == ChartType.LINE) {
            return callback(this.createLineStatChartData(list));
        } else {
            return callback(this.createPieStatChartData(list));
        }
    }

    /**
     * 饼图模式
     *
     * @param list
     * @return
     */
    private ChartPieData createPieStatChartData(List<UserScore> list) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("用户评分统计分析");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("次数");
        Map<String, Integer> map = new HashMap<>();
        int totalScore = 0;
        int total = 0;
        for (UserScore bean : list) {
            int x = bean.getScore() / 10;
            String key = x * 10 + "-" + (x + 1) * 10 + "分";
            Integer n = map.get(key);
            if (n == null) {
                map.put(key, 1);
            } else {
                map.put(key, n + 1);
            }
            totalScore += bean.getScore();
            total++;
        }
        for (String key : map.keySet()) {
            chartPieData.getXdata().add(key);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(key);
            dataDetail.setValue(map.get(key));
            serieData.getData().add(dataDetail);
        }
        String subTitle = "平均分：" + NumberUtil.getAverageValue(totalScore, total, 1);
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 折现图模式
     *
     * @param list
     * @return
     */
    private ChartData createLineStatChartData(List<UserScore> list) {
        ChartData chartData = new ChartData();
        chartData.setTitle("用户评分统计分析");
        chartData.setUnit("分");
        chartData.setLegendData(new String[]{"分数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("分数");
        int totalScore = 0;
        int total = 0;
        for (UserScore bean : list) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getEndTime(), DateUtil.FormatDay1));
            yData1.getData().add(bean.getScore());
            totalScore += bean.getScore();
            total++;
        }
        chartData.getYdata().add(yData1);
        String subTitle = "平均分：" + NumberUtil.getAverageValue(totalScore, total, 1);
        chartData.setSubTitle(subTitle);
        return chartData;
    }

    /**
     * 评分分析
     * 雷达图
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid CommonBeanGetRequest getRequest) {
        UserScore userScore = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        List<UserScoreDetail> list = userScoreService.selectUserScoreDetailList(userScore.getId());
        ChartRadarData chartRadarData = new ChartRadarData();
        String date = DateUtil.getFormatDate(userScore.getEndTime(),DateUtil.FormatDay1);
        chartRadarData.setTitle("["+date+"]评分分析");
        chartRadarData.addLegend("评分");
        ChartRadarSerieData serieData = new ChartRadarSerieData();
        serieData.setName("评分");
        long total = 0L;
        long scTotal = 0L;
        for(UserScoreDetail usd : list){
            chartRadarData.addIndicatorData(usd.getScoreConfig().getName(),usd.getScoreConfig().getMaxScore().longValue());
            serieData.addData(usd.getScore().longValue());
            total+=usd.getScore().longValue();
            scTotal+=usd.getScoreConfig().getMaxScore().longValue();
        }
        chartRadarData.setSubTitle("总得分:"+total+" (满分:"+scTotal+")");
        chartRadarData.addSerie(serieData);
        return callback(chartRadarData);
    }

    /**
     * 获取下一个ID
     * @param usg
     * @return
     */
    @RequestMapping(value = "/getNextId", method = RequestMethod.GET)
    public ResultBean getNextId(UserScoreGetNextIdRequest usg) {
        Long id = userScoreService.getNearestId(usg.getCurrentId(),usg.getUserId(),usg.getCompareType());
        return callback(id);
    }

    /**
     * 重新保存
     *
     * @return
     */
    @RequestMapping(value = "/reSave", method = RequestMethod.POST)
    public ResultBean reSave(@RequestBody @Valid UserScoreReSaveRequest sf) {
        Date d = sf.getStartDate();
        while (!d.after(sf.getEndDate())) {
            Date[] dates = userScoreHandler.getDays(d);
            userScoreHandler.saveUseScoreAsync(sf.getUserId(), dates[0], dates[1], true);
            d = DateUtil.getDate(1, d);
        }
        return callback(null);
    }

    /**
     * 积分和评分比对
     *
     * @return
     */
    @RequestMapping(value = "/scorePointsCompare", method = RequestMethod.GET)
    public ResultBean scorePointsCompare(@Valid UserScorePointsCompareSearch sf) {
        List<UserScorePointsCompareDto> list = userScoreService.scorePointsCompare(sf.getUserId(), sf.getStartDate(), sf.getEndDate());
        ChartData chartData = new ChartData();
        chartData.setTitle("用户评分与积分比较");
        chartData.setLegendData(new String[]{"评分", "积分"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("评分");
        ChartYData yData2 = new ChartYData();
        yData2.setName("积分");
        int maxPoints = this.getMaxPoints(list);
        for (UserScorePointsCompareDto bean : list) {
            chartData.getXdata().add(bean.getDate().toString());
            yData1.getData().add(bean.getScore().intValue());
            if (sf.getDataType() == UserScorePointsCompareSearch.DataType.ABSOLUTE) {
                yData2.getData().add(bean.getPoints().intValue());
            } else {
                //实际上是求与最大值的百分比值
                int v = (int) (bean.getPoints().doubleValue() / maxPoints * 100);
                yData2.getData().add(v);
            }
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return callback(chartData);
    }

    private int getMaxPoints(List<UserScorePointsCompareDto> list) {
        int max = 0;
        for (UserScorePointsCompareDto bean : list) {
            if (bean.getPoints().intValue() > max) {
                max = bean.getPoints().intValue();
            }
        }
        return max;
    }
}
