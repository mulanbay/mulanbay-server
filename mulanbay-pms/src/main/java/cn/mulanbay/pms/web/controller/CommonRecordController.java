package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.CommonRecord;
import cn.mulanbay.pms.persistent.domain.CommonRecordType;
import cn.mulanbay.pms.persistent.dto.CommonRecordAnalyseStat;
import cn.mulanbay.pms.persistent.dto.CommonRecordDateStat;
import cn.mulanbay.pms.persistent.dto.CommonRecordStat;
import cn.mulanbay.pms.persistent.enums.LogCompareType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.enums.ValueType;
import cn.mulanbay.pms.persistent.service.CommonRecordService;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.commonrecord.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.common.CommonRecordStatVo;
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
    CommonRecordService commonRecordService;

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
     * 获取类型树
     *
     * @return
     */
    @RequestMapping(value = "/getNameTree")
    public ResultBean getNameTree(CommonRecordNameTreeSearch ts) {
        try {
            ts.setPage(PageRequest.NO_PAGE);
            ts.setEndDate(new Date());
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<String> gtList = commonRecordService.getNameList(ts);
            for (String s : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(s);
                tb.setText(s);
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, ts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "通用记录类型异常",
                    e);
        }
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
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        CommonRecord bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 查询最新
     *
     * @return
     */
    @RequestMapping(value = "/getNearest", method = RequestMethod.GET)
    public ResultBean getNearest(@Valid CommonRecordNearestSearch ls) {
        CommonRecord bean = commonRecordService.getNearest(ls);
        return callback(bean);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid CommonRecordStatSearch ls) {
        CommonRecordStat stat = commonRecordService.statCommonRecord(ls);
        CommonRecordStatVo vo = new CommonRecordStatVo();
        vo.setMinDate(stat.getMinDate());
        vo.setMaxDate(stat.getMaxDate());
        vo.setTotalCount(stat.getTotalCount().longValue());
        vo.setTotalValue(stat.getTotalValue().longValue());
        CommonRecordType bean = this.getUserEntity(CommonRecordType.class, ls.getCommonRecordTypeId(), ls.getUserId());
        vo.setUnit(bean.getUnit());
        return callback(vo);
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
     * 分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse")
    public ResultBean analyse(@Valid CommonRecordAnalyseSearch sf) {
        CommonRecordType crt = this.getUserEntity(CommonRecordType.class, sf.getCommonRecordTypeId(), sf.getUserId());
        List<CommonRecordAnalyseStat> list = commonRecordService.analyse(sf);
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("["+crt.getName()+"]记录分析");
        chartPieData.setUnit(sf.getValueType().getName());
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("类型");
        ValueType valueType = sf.getValueType();
        if(valueType==ValueType.TIMES){
            serieData.setUnit("次");
        }else if(valueType==ValueType.MINUTE){
            serieData.setUnit(crt.getUnit());
        }
        for (CommonRecordAnalyseStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            if(valueType==ValueType.TIMES){
                dataDetail.setValue(bean.getTotalCount());
            }else if(valueType==ValueType.MINUTE){
                dataDetail.setValue(bean.getTotalValue());
            }
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 时间线
     *
     * @return
     */
    @RequestMapping(value = "/timeline")
    public ResultBean timeline(@Valid CommonRecordTimelineSearch sf) {
        CommonRecordType crt = this.getUserEntity(CommonRecordType.class, sf.getCommonRecordTypeId(), sf.getUserId());
        ChartData chartData = new ChartData();
        chartData.setTitle("["+crt.getName()+"]时间线分析");
        chartData.setUnit("分钟");
        //混合图形下使用
        chartData.addYAxis("时长","分钟");
        chartData.addYAxis("天数","天");
        chartData.setLegendData(new String[]{"时长","距离上次"});
        ChartYData yData1 = new ChartYData("时长","分钟");
        ChartYData yData2 = new ChartYData("距离上次","天");
        CommonRecordSearch crs = new CommonRecordSearch();
        crs.setCommonRecordTypeId(sf.getCommonRecordTypeId());
        crs.setUserId(sf.getUserId());
        crs.setName(sf.getName());
        crs.setStartDate(sf.getStartDate());
        crs.setEndDate(sf.getEndDate());
        PageRequest pr = crs.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("occurTime", Sort.ASC);
        pr.addSort(sort);
        pr.setPage(PageRequest.NO_PAGE);
        List<CommonRecord> list = baseService.getBeanList(pr);
        Date lastDate = null;
        for (CommonRecord bean : list) {
            Date dt = bean.getOccurTime();
            chartData.getXdata().add(DateUtil.getFormatDate(dt, DateUtil.FormatDay1));
            yData1.getData().add(bean.getValue());
            int days =0;
            if(lastDate!=null){
                days = DateUtil.getIntervalDays(lastDate,dt);
            }
            yData2.getData().add(days);
            lastDate = dt;
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return callback(chartData);
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
        ChartYData yData1 = new ChartYData("次数","次");
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
        chartData.setLegendData(new String[]{"时长","次数"});
        //混合图形下使用
        chartData.addYAxis("时长",crt.getUnit());
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("时长",crt.getUnit());
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
