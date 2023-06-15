package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.BuyRecord;
import cn.mulanbay.pms.persistent.domain.BuyRecordMatchLog;
import cn.mulanbay.pms.persistent.domain.GoodsType;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordMatchLogCreateRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordMatchLogSearch;
import cn.mulanbay.pms.web.bean.response.buy.BuyRecordMatchVo;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费记录匹配日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/buyRecordMatchLog")
public class BuyRecordMatchLogController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BuyRecordMatchLogController.class);

    private static Class<BuyRecordMatchLog> beanClass = BuyRecordMatchLog.class;

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BuyRecordMatchLogSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<BuyRecordMatchLog> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BuyRecordMatchLogCreateRequest formRequest) {
        try {
            BuyRecordMatchLog bean = new BuyRecordMatchLog();
            BeanCopy.copyProperties(formRequest, bean);
            bean.setAiMatch(formRequest.getMatch());
            //计算实际匹配度
            BuyRecord buyRecord = this.getUserEntity(BuyRecord.class, formRequest.getBuyRecordId(), formRequest.getUserId());
            Integer goodsTypeId = buyRecord.getGoodsType().getId();
            Integer subGoodsTypeId = buyRecord.getSubGoodsType()==null ? null : buyRecord.getSubGoodsType().getId();
            String shopName = buyRecord.getShopName();
            String brand = buyRecord.getBrand();
            int m=0;
            int total=0;
            total++;
            if(goodsTypeId.intValue()==bean.getGoodsTypeId().intValue()){
                m++;
            }
            if(subGoodsTypeId!=null&&bean.getSubGoodsTypeId()!=null){
                total++;
                if(subGoodsTypeId.intValue()==bean.getSubGoodsTypeId().intValue()){
                    m++;
                }
            }
            //店铺和品牌部应该属于匹配对比里面
//            if(StringUtil.isNotEmpty(bean.getShopName())){
//                total++;
//                if(bean.getShopName().equals(shopName)){
//                    m++;
//                }
//            }
//            if(StringUtil.isNotEmpty(bean.getBrand())){
//                total++;
//                if(bean.getBrand().equals(brand)){
//                    m++;
//                }
//            }
            float acMatch = (float) NumberUtil.getPercentValue(m,total,4)/100;
            bean.setAcMatch(acMatch);
            bean.setRemark("用户操作新增");
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        } catch (BeansException e) {
            logger.error("增加消费记录匹配日志异常",e);
        }
        return callback(null);
    }

    /**
     * 获取消费记录
     *
     * @return
     */
    @RequestMapping(value = "/getBuyRecord", method = RequestMethod.GET)
    public ResultBean getBuyRecord(Long buyRecordId) {
        BuyRecord buyRecord = baseService.getObject(BuyRecord.class,buyRecordId);
        return callback(buyRecord);
    }

    /**
     * 获取比较数据
     *
     * @return
     */
    @RequestMapping(value = "/getCompareData", method = RequestMethod.GET)
    public ResultBean getCompareData(@Valid CommonBeanGetRequest getRequest) {
        BuyRecordMatchLog ml = baseService.getObject(beanClass,getRequest.getId());
        BuyRecord buyRecord = baseService.getObject(BuyRecord.class,ml.getBuyRecordId());
        //实际的消费数据
        BuyRecordMatchVo acData = new BuyRecordMatchVo();
        acData.setGoodsTypeId(buyRecord.getGoodsType().getId());
        acData.setGoodsTypeName(buyRecord.getGoodsType().getName());
        if(buyRecord.getSubGoodsType()!=null){
            acData.setSubGoodsTypeId(buyRecord.getSubGoodsType().getId());
            acData.setSubGoodsTypeName(buyRecord.getSubGoodsType().getName());
        }
        acData.setShopName(buyRecord.getShopName());
        acData.setBrand(buyRecord.getBrand());
        acData.setMatch(ml.getAcMatch());
        //AI匹数据
        BuyRecordMatchVo aiData = new BuyRecordMatchVo();
        //直接取匹配数据
        aiData.setGoodsTypeId(ml.getGoodsTypeId());
        GoodsType goodsType = baseService.getObject(GoodsType.class,ml.getGoodsTypeId());
        aiData.setGoodsTypeName(goodsType.getName());
        if(ml.getSubGoodsTypeId()!=null){
            aiData.setSubGoodsTypeId(ml.getSubGoodsTypeId());
            GoodsType subGoodsType = baseService.getObject(GoodsType.class,ml.getSubGoodsTypeId());
            aiData.setSubGoodsTypeName(subGoodsType.getName());
        }
        aiData.setMatch(ml.getAiMatch());
        Map res = new HashMap<>();
        res.put("acData",acData);
        res.put("aiData",aiData);
        res.put("goodsName",buyRecord.getGoodsName());
        if(ml.getCompareId()!=null){
            BuyRecord compare = baseService.getObject(BuyRecord.class,ml.getCompareId());
            res.put("compareGoodsName",compare.getGoodsName());
        }
        return callback(res);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(BuyRecordMatchLogSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        pr.setNeedTotal(false);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        PageResult<BuyRecordMatchLog> qr = baseService.getBeanResult(pr);
        List<BuyRecordMatchLog> list = qr.getBeanList();
        ChartData chartData = new ChartData();
        chartData.setTitle("商品自动匹配分析");
        chartData.setLegendData(new String[]{"AI匹配度", "实际匹配度"});
        ChartYData yData = new ChartYData();
        yData.setName("AI匹配度");
        ChartYData y2Data = new ChartYData();
        y2Data.setName("实际匹配度");
        for (BuyRecordMatchLog bean : list) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getCreatedTime(),DateUtil.Format24Datetime));
            yData.getData().add(NumberUtil.getDoubleValue(bean.getAiMatch(),2));
            y2Data.getData().add(NumberUtil.getDoubleValue(bean.getAcMatch(),2));
        }
        chartData.getYdata().add(yData);
        chartData.getYdata().add(y2Data);
        return callback(chartData);
    }

}
