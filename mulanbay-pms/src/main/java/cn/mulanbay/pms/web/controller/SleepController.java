package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.Sleep;
import cn.mulanbay.pms.persistent.dto.SleepAnalyseStat;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.SleepStatType;
import cn.mulanbay.pms.persistent.service.SleepService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.sleep.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.health.SleepAnalyseStatVo;
import cn.mulanbay.pms.web.bean.response.health.SleepPieChartStatVo;
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
 * 睡眠
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/sleep")
public class SleepController extends BaseController {

    private static Class<Sleep> beanClass = Sleep.class;

    @Autowired
    SleepService sleepService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SleepSearch sf) {
        return callbackDataGrid(getSleepResult(sf));
    }

    private PageResult<Sleep> getSleepResult(SleepSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("sleepDate", Sort.DESC);
        pr.addSort(sort);
        PageResult<Sleep> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SleepFormRequest formRequest) {
        Sleep bean = new Sleep();
        BeanCopy.copyProperties(formRequest, bean);
        Date sleepDate = calSleepDate(bean.getSleepTime());
        checkSleepDate(sleepDate);
        bean.setSleepDate(sleepDate);
        if (bean.getSleepTime() != null && bean.getGetUpTime() != null) {
            long n = bean.getGetUpTime().getTime() - bean.getSleepTime().getTime();
            bean.setTotalMinutes((int) (n / (1000 * 60)));
        }
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }

    private void checkSleepDate(Date sleepDate) {
        Sleep sleep = sleepService.getSleep(sleepDate);
        if (sleep != null) {
            throw new ApplicationException(ErrorCode.DO_BUSS_ERROR,
                    "睡眠日[" + DateUtil.getFormatDate(sleepDate, DateUtil.FormatDay1) + "]已经存在");
        }
    }

    /**
     * 快速增加睡眠
     *
     * @return
     */
    @RequestMapping(value = "/sleep", method = RequestMethod.POST)
    public ResultBean sleep(@RequestBody @Valid SleepRequest formRequest) {
        Sleep bean = new Sleep();
        BeanCopy.copyProperties(formRequest, bean);
        Date sleepDate = calSleepDate(bean.getSleepTime());
        checkSleepDate(sleepDate);
        bean.setSleepDate(sleepDate);
        bean.setCreatedTime(new Date());
        bean.setRemark("快速新增");
        baseService.saveObject(bean);
        return callback(bean);
    }

    /**
     * 快速增加睡眠
     *
     * @return
     */
    @RequestMapping(value = "/getUp", method = RequestMethod.POST)
    public ResultBean getUp(@RequestBody @Valid SleepGetUpRequest formRequest) {
        //12个小时内的没有起床信息的睡觉记录
        int hours = 12;
        Date fromTime = new Date(System.currentTimeMillis()-hours*3600*1000);
        Sleep bean = sleepService.getNearUnGetUp(fromTime,formRequest.getUserId());
        if(bean==null){
            return callbackErrorInfo("最近"+hours+"小时内没有未起床记录，请手动完整新增");
        }
        bean.setGetUpTime(formRequest.getGetUpTime());
        long n = bean.getGetUpTime().getTime() - bean.getSleepTime().getTime();
        bean.setTotalMinutes((int) (n / (1000 * 60)));
        bean.setQuality(3);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 计算睡眠日
     *
     * @param date
     */
    private Date calSleepDate(Date date) {
        if (date == null) {
            return null;
        } else {
            String hour = DateUtil.getFormatDate(date, "HH");
            int n = Integer.valueOf(hour);
            if (n >= 12) {
                //当天
                return DateUtil.getDate(0, date);
            } else {
                //昨天
                return DateUtil.getDate(-1, date);
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
        Sleep bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SleepFormRequest formRequest) {
        Sleep bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        Date sleepDate = calSleepDate(bean.getSleepTime());
        bean.setSleepDate(sleepDate);
        if (bean.getSleepTime() != null && bean.getGetUpTime() != null) {
            long n = bean.getGetUpTime().getTime() - bean.getSleepTime().getTime();
            bean.setTotalMinutes((int) (n / (1000 * 60)));
        }
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
     * 比对，采用散点图
     *
     * @return
     */
    @RequestMapping(value = "/analyseStat")
    public ResultBean analyseStat(@Valid SleepAnalyseStatSearch sf) {
        List<SleepAnalyseStatVo> statVoList = new ArrayList<>();
        List<SleepAnalyseStat> list = sleepService.statSleepAnalyse(sf);
        DateGroupType dateGroupType = sf.getXgroupType();
        for (SleepAnalyseStat stat : list) {
            if(stat.getyValue()==null){
                continue;
            }
            double x = stat.getxDoubleValue();
            SleepAnalyseStatVo vo = new SleepAnalyseStatVo();
            if(dateGroupType == DateGroupType.DAYCALENDAR){
                Date dd = DateUtil.getDate(stat.getxValue().toString(),"yyyyMMdd");
                x = DateUtil.getDayOfYear(dd);
            }
            vo.setX(x);
            if (sf.getYgroupType() == SleepStatType.DURATION) {
                double hours = stat.getyDoubleValue() / 60;
                BigDecimal b = new BigDecimal(hours);
                double v = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                vo.setV(v);
            } else {
                vo.setV(stat.getyDoubleValue());
            }
            statVoList.add(vo);
        }
        if(sf.getChartType()== ChartType.SCATTER){
            return callback(this.createAnalyseStatScatterData(statVoList,sf));
        }else{
            return callback(this.createAnalyseStatPieData(statVoList,sf));
        }
    }

    /**
     * 散点图
     * @param statVoList
     * @param sf
     * @return
     */
    private ScatterChartData createAnalyseStatScatterData(List<SleepAnalyseStatVo> statVoList,SleepAnalyseStatSearch sf){
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle("睡眠分析");
        chartData.setxUnit(sf.getXgroupType().getName());
        chartData.setyUnit(sf.getYgroupType().getUnit());
        chartData.addLegent(sf.getYgroupType().getName());
        ScatterChartDetailData detailData = new ScatterChartDetailData();
        detailData.setName(sf.getYgroupType().getName());
        double totalX = 0;
        int n = 0;
        for (SleepAnalyseStatVo stat : statVoList) {
            detailData.addData(new Object[]{stat.getX(), stat.getV()});
            totalX += stat.getX();
            n++;
        }
        detailData.setxAxisAverage(totalX / n);
        chartData.addSeriesData(detailData);
        return chartData;
    }

    /**
     * 封装饼状图数据
     *
     * @param statVoList
     * @param sf
     * @return
     */
    private ChartPieData createAnalyseStatPieData(List<SleepAnalyseStatVo> statVoList,SleepAnalyseStatSearch sf) {
        SleepStatType statType = sf.getYgroupType();
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("睡眠分析");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName(statType.getName());
        //Step 1:统计数据
        Map<String,SleepPieChartStatVo> statMap =new HashMap<>();
        for(SleepAnalyseStatVo vo : statVoList){
            int v = (int) Math.round(vo.getV());
            String key = v+"-"+(v+1);
            SleepPieChartStatVo pv = statMap.get(key);
            if(pv==null){
                if(statType==SleepStatType.SLEEP_TIME||statType==SleepStatType.GETUP_TIME){
                    pv = new SleepPieChartStatVo();
                    if(v<10){
                        pv.setName("0"+v+":00~"+"0"+v+":59");
                    }else{
                        pv.setName(v+":00~"+v+":59");
                    }
                    pv.setCount(1);
                }else if(statType == SleepStatType.DURATION){
                    pv = new SleepPieChartStatVo();
                    pv.setName("["+v+"~"+(v+1)+")小时");
                    pv.setCount(1);
                }else if(statType == SleepStatType.QUALITY){
                    pv = new SleepPieChartStatVo();
                    pv.setName("["+v+"~"+(v+1)+")分");
                    pv.setCount(1);
                }else if(statType == SleepStatType.WAKEUP_COUNT){
                    pv = new SleepPieChartStatVo();
                    pv.setName("["+v+"~"+(v+1)+")次");
                    pv.setCount(1);
                }
                statMap.put(key,pv);
            }else{
                pv.setCount(pv.getCount()+1);
            }
        }
        //Step 2:封装图表对象
        for (SleepPieChartStatVo bean : statMap.values()) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getCount());
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

}
