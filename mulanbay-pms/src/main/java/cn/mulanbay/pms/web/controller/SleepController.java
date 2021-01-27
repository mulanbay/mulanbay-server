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
import cn.mulanbay.pms.persistent.enums.SleepStatType;
import cn.mulanbay.pms.persistent.service.SleepService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.sleep.*;
import cn.mulanbay.pms.web.bean.response.chart.ScatterChartData;
import cn.mulanbay.pms.web.bean.response.chart.ScatterChartDetailData;
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
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle("睡眠分析");
        chartData.setxUnit(sf.getXgroupType().getName());
        chartData.setyUnit(sf.getYgroupType().getUnit());
        List<SleepAnalyseStat> list = sleepService.statSleepAnalyse(sf);
        chartData.addLegent(sf.getYgroupType().getName());
        ScatterChartDetailData detailData = new ScatterChartDetailData();
        detailData.setName(sf.getYgroupType().getName());
        double totalX = 0;
        int n = 0;
        for (SleepAnalyseStat stat : list) {
            if (sf.getYgroupType() == SleepStatType.DURATION) {
                double hours = stat.getyDoubleValue() / 60;
                BigDecimal b = new BigDecimal(hours);
                double v = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                detailData.addData(new Object[]{stat.getxDoubleValue(), v});
            } else {
                detailData.addData(new Object[]{stat.getxDoubleValue(), stat.getyDoubleValue()});

            }
            totalX += stat.getxDoubleValue();
            n++;
        }
        detailData.setxAxisAverage(totalX / n);
        chartData.addSeriesData(detailData);
        return callback(chartData);
    }

}
