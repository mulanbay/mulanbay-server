package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.ReadingRecord;
import cn.mulanbay.pms.persistent.domain.ReadingRecordDetail;
import cn.mulanbay.pms.persistent.dto.ReadingRecordDetailDateStat;
import cn.mulanbay.pms.persistent.service.ReadingRecordService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordDetailDateStatSearch;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordDetailFormRequest;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordDetailSearch;
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
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 阅读明细
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/readingRecordDetail")
public class ReadingRecordDetailController extends BaseController {

    private static Class<ReadingRecordDetail> beanClass = ReadingRecordDetail.class;

    @Autowired
    ReadingRecordService readingRecordService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ReadingRecordDetailSearch sf) {
        return callbackDataGrid(getReadingRecordDetailResult(sf));
    }

    private PageResult<ReadingRecordDetail> getReadingRecordDetailResult(ReadingRecordDetailSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort2 = new Sort("readTime", Sort.DESC);
        pr.addSort(sort2);
        PageResult<ReadingRecordDetail> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ReadingRecordDetailFormRequest formRequest) {
        ReadingRecordDetail bean = new ReadingRecordDetail();
        BeanCopy.copyProperties(formRequest, bean);
        ReadingRecord readingRecord = this.getUserEntity(ReadingRecord.class, formRequest.getReadingRecordId(), formRequest.getUserId());
        bean.setReadingRecord(readingRecord);
        bean.setCreatedTime(new Date());
        readingRecordService.saveOrUpdateReadingRecordDetail(bean, false);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        ReadingRecordDetail bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ReadingRecordDetailFormRequest formRequest) {
        ReadingRecordDetail bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        ReadingRecord readingRecord = this.getUserEntity(ReadingRecord.class, formRequest.getReadingRecordId(), formRequest.getUserId());
        bean.setReadingRecord(readingRecord);
        bean.setLastModifyTime(new Date());
        readingRecordService.saveOrUpdateReadingRecordDetail(bean, true);
        return callback(null);
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
    public ResultBean dateStat(ReadingRecordDetailDateStatSearch sf) {
        List<ReadingRecordDetailDateStat> list = readingRecordService.statDateReadingRecordDetail(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("阅读统计");
        //chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"小时","次数"});
        //混合图形下使用
        chartData.addYAxis("时长","小时");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("小时");
        //总的值
        BigDecimal totalCounts = new BigDecimal(0);
        BigDecimal totalMinutes = new BigDecimal(0);
        for (ReadingRecordDetailDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            double hours = NumberUtil.getAverageValue(bean.getTotalMinutes(), BigInteger.valueOf(60L), 1);
            yData2.getData().add(hours);
            totalCounts = totalCounts.add(new BigDecimal(bean.getTotalCount()));
            totalMinutes = totalMinutes.add(bean.getTotalMinutes());
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String total = totalCounts.longValue() + "次," + NumberUtil.getAverageValue(totalMinutes, BigInteger.valueOf(60L), 1) + "小时";
        String subTitle = this.getDateTitle(sf, total);
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }
}
