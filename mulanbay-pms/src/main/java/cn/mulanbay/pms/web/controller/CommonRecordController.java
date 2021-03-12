package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.CommonRecord;
import cn.mulanbay.pms.persistent.domain.CommonRecordType;
import cn.mulanbay.pms.persistent.dto.CommonRecordDateStat;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordDateStatSearch;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordFormRequest;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartCalendarData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/commonRecord")
public class CommonRecordController extends BaseController {

    private static Class<CommonRecord> beanClass = CommonRecord.class;

    @Autowired
    TreatService treatService;

    @Autowired
    DataService dataService;

    @Autowired
    RewardPointsHandler rewardPointsHandler;

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(CommonRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("occurTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<CommonRecord> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid CommonRecordFormRequest formRequest) {
        CommonRecordType commonRecordType = this.getUserEntity(CommonRecordType.class, formRequest.getCommonRecordTypeId(), formRequest.getUserId());
        CommonRecord bean = new CommonRecord();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCommonRecordType(commonRecordType);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        //增加积分
        int rp = commonRecordType.getRewardPoint();
        if (rp != 0) {
            //这里修改为通用类型ID
            rewardPointsHandler.rewardPoints(formRequest.getUserId(), commonRecordType.getRewardPoint(), commonRecordType.getId().longValue(),
                    RewardSource.COMMON_RECORD, "通用记录操作奖励", bean.getId());
        }
        return callback(bean);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        CommonRecord bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid CommonRecordFormRequest formRequest) {
        CommonRecordType commonRecordType = this.getUserEntity(CommonRecordType.class, formRequest.getCommonRecordTypeId(), formRequest.getUserId());
        CommonRecord bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCommonRecordType(commonRecordType);
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
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(CommonRecordDateStatSearch sf) {
        switch (sf.getDateGroupType()){
            case DAYCALENDAR :
                //日历
                return callback(createChartCalandarDataDateStat(sf));
            case MINUTE :
                //值的单位
                return callback(creatBarDataDateStatByValue(sf));
            case HOURMINUTE :
                //散点图
                PageRequest pr = sf.buildQuery();
                pr.setBeanClass(beanClass);
                List<Date> dateList = dataService.getDateList(pr,"occurTime");
                return callback(this.createHMChartData(dateList,"通用记录分析","时间点"));
            default:
                ChartData chartData = creatBarDataDateStat(sf);
                return callback(chartData);
        }
    }

    /**
     * 按值来区分
     *
     * @param sf
     * @return
     */
    private ChartData creatBarDataDateStatByValue(CommonRecordDateStatSearch sf) {
        CommonRecordSearch crs = new CommonRecordSearch();
        BeanCopy.copyProperties(sf, crs);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        //不分页
        pr.setPage(0);
        List<CommonRecord> list = baseService.getBeanList(pr);
        Map<String, Integer> map = new HashMap<>();
        for (CommonRecord cr : list) {
            String kv = cr.getValue().toString();
            Integer v = map.get(kv);
            if (v == null) {
                map.put(kv, 1);
            } else {
                map.put(kv, v + 1);
            }
        }
        ChartData chartData = new ChartData();
        CommonRecordType crt = baseService.getObject(CommonRecordType.class, sf.getCommonRecordTypeId());
        chartData.setTitle(crt.getName() + "统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (String key : map.keySet()) {
            chartData.getIntXData().add(Integer.valueOf(key));
            chartData.getXdata().add(key + crt.getUnit());
            yData1.getData().add(map.get(key));
            totalCount = totalCount.add(new BigDecimal(map.get(key)));
        }
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + crt.getUnit());
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return chartData;
    }

    /**
     * 柱状图、折线图数据统计
     *
     * @param sf
     * @return
     */
    private ChartData creatBarDataDateStat(CommonRecordDateStatSearch sf) {
        List<CommonRecordDateStat> list = dataService.statDateCommonRecord(sf);
        CommonRecordType crt = baseService.getObject(CommonRecordType.class, sf.getCommonRecordTypeId());
        ChartData chartData = new ChartData();
        chartData.setTitle(crt.getName() + "统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{crt.getUnit(),"次数"});
        //混合图形下使用
        chartData.addYAxis("值",crt.getUnit());
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName(crt.getUnit());
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (CommonRecordDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(bean.getTotalValue());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return chartData;
    }

    /**
     * 日历类型
     *
     * @param sf
     * @return
     */
    private ChartCalendarData createChartCalandarDataDateStat(CommonRecordDateStatSearch sf) {
        CommonRecordType crt = baseService.getObject(CommonRecordType.class, sf.getCommonRecordTypeId());
        List<CommonRecordDateStat> list = dataService.statDateCommonRecord(sf);
        ChartCalendarData calandarData = ChartUtil.createChartCalendarData(crt.getName() + "统计", "次数", crt.getUnit(), sf, list);
        calandarData.setTop(3);
        return calandarData;
    }

}
