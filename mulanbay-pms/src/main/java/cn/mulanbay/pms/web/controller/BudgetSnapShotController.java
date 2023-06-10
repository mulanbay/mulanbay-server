package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.BuyRecordBudgetStat;
import cn.mulanbay.pms.persistent.enums.BudgetLogSource;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordSearch;
import cn.mulanbay.pms.web.bean.request.fund.*;
import cn.mulanbay.pms.web.bean.request.health.TreatRecordSearch;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.fund.BudgetChildrenVo;
import cn.mulanbay.pms.web.bean.response.fund.BudgetDetailVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * 预算快照
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/budgetSnapshot")
public class BudgetSnapShotController extends BaseController {

    private static Class<BudgetSnapshot> beanClass = BudgetSnapshot.class;

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetHandler budgetHandler;

    @Autowired
    DietService dietService;

    /**
     * 获取列表，普通模式
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BudgetSnapshotSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        PageResult<BudgetSnapshot> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 获取列表,查询所有相关联的
     *
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResultBean getList(BudgetSnapshotListSearch sf) {
        BudgetLog budgetLog = this.getUserEntity(BudgetLog.class, sf.getBudgetLogId(), sf.getUserId());
        Date bussDay = budgetLog.getOccurDate();
        String bussKey = budgetLog.getBussKey();
        List<BudgetSnapshot> snapshotList = budgetService.getBudgetSnapshotList(sf.getUserId(),sf.getBudgetLogId());
        List<BudgetDetailVo> res = new ArrayList<>();
        for(BudgetSnapshot bg : snapshotList){
            BudgetDetailVo vo=null;
            switch (bg.getPeriod()){
                case ONCE:
                    if(bg.getGoodsTypeId()!=null){
                        //实时统计
                        vo = this.getDetail(bg,bussDay,bussKey);
                    }else{
                        vo = new BudgetDetailVo();
                        BeanCopy.copyProperties(bg,vo);
                        //查手动日志
                        BudgetLog bl = budgetService.selectBudgetLog(budgetLog.getBussKey(), bg.getUserId(), null, bg.getFromId());
                        if(bl!=null){
                            //有记录
                            vo.setCpPaidTime(bl.getOccurDate());
                            double total = PriceUtil.sum(bl.getTrAmount(),bl.getNcAmount(),bl.getBcAmount());
                            vo.setCpPaidAmount(total);
                            vo.setSource(bl.getSource());
                        }
                    }
                    break;
                case MONTHLY:
                    if(budgetLog.getPeriod()==PeriodType.YEARLY){
                        //月报，直接空数据,前端以子列表显示
                        vo = this.createDefault(bg);
                    }else{
                        vo = this.getDetail(bg,bussDay,bussKey);
                        vo.setBussKey(budgetLog.getBussKey());
                    }
                    break;
                case YEARLY:
                    vo = this.getDetail(bg,bussDay,bussKey);
                    break;
                default:
                    break;
            }
            //ID号是原来复制过来的Budget编号
            vo.setId(bg.getFromId());
            //设置快照ID，查询子列表有用
            vo.setSnapshotId(bg.getId());
            res.add(vo);
        }
        return callback(res);
    }

    /**
     * 生成默认
     * @param bg
     * @return
     */
    private BudgetDetailVo createDefault(BudgetSnapshot bg){
        BudgetDetailVo vo = new BudgetDetailVo();
        BeanCopy.copyProperties(bg,vo);
        vo.setId(bg.getFromId());
        vo.setHasChild(true);
        return vo;
    }
    /**
     * 获取子列表
     *
     * @return
     */
    @RequestMapping(value = "/getChildren", method = RequestMethod.GET)
    public ResultBean getChildren(BudgetSnapshotChildrenSearch sf) {
        BudgetSnapshot snapshot = this.getUserEntity(beanClass,sf.getSnapshotId(),sf.getUserId());
        BudgetLog bl = baseService.getObject(BudgetLog.class,snapshot.getBudgetLogId());
        /**
         * 1.父类是年度快照，子类是每月预算，那么查询当期年度所有该月度预算的列表（正常情况下有12条）
         * 2.父类是年度快照，子类是每天预算，那么查询当期年度所有该天预算的列表（正常情况下有365-366条）
         * 3.父类是月度快照，子类是每天预算，那么查询当期月份所有该天预算的列表（正常情况下有28-31条）
         * 目前只实现第一种
         */
        PeriodType parentPeriod = bl.getPeriod();
        if(parentPeriod==PeriodType.YEARLY){
            List<BudgetSnapshot> snapshotList = budgetService.getMonthBudgetSnapshotList(sf.getUserId(),snapshot.getFromId(),bl.getBussKey());
            BudgetChildrenVo res = new BudgetChildrenVo();
            BigDecimal budgetAmount = new BigDecimal(0);
            BigDecimal paidAmount = new BigDecimal(0);
            for(BudgetSnapshot ss : snapshotList){
                Date bussDay = budgetHandler.getBussDay(ss.getBussKey());
                BudgetDetailVo child = this.getDetail(ss,bussDay,ss.getBussKey());
                double b = child.getCpPaidAmount()==null ? 0:child.getCpPaidAmount();
                budgetAmount=budgetAmount.add(new BigDecimal(child.getAmount()));
                paidAmount=paidAmount.add(new BigDecimal(b));
                res.addChild(child);
            }
            res.setBudgetAmount(budgetAmount.doubleValue());
            res.setCpPaidAmount(paidAmount.doubleValue());
            res.setBussKey(bl.getBussKey());
            res.setName(snapshot.getName());
            if(sf.isNeedChart()){
                String title = "["+res.getName()+"]"+res.getBussKey()+"执行统计";
                ChartData chartData = this.createChartData(title,res.getChildren());
                res.setChartData(chartData);
            }
            return callback(res);
        }
        return callback(null);
    }

    private ChartData createChartData(String title,List<BudgetDetailVo> list){
        ChartData chartData = new ChartData();
        chartData.setTitle(title);
        chartData.setLegendData(new String[]{"预算(元)","花费(元)","比例(%)"});
        //混合图形下使用
        chartData.addYAxis("金额","元");
        chartData.addYAxis("比例","%");
        ChartYData yData1 = new ChartYData();
        yData1.setName("预算(元)");
        ChartYData yData2 = new ChartYData();
        yData2.setName("花费(元)");
        ChartYData yData3 = new ChartYData();
        yData3.setName("比例(%)");
        for (BudgetDetailVo bean : list) {
            chartData.addXData(bean.getBussKey());
            yData1.getData().add(PriceUtil.changeToString(2,bean.getAmount()));
            if(bean.getCpPaidTime()==null){
                yData2.getData().add("0");
            }else{
                yData2.getData().add(PriceUtil.changeToString(2,bean.getCpPaidAmount()));
            }
            yData3.getData().add(Math.round(bean.getRate()));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData3);
        return chartData;
    }

    /**
     * 获取子列表
     *
     * @return
     */
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResultBean history(BudgetSnapshotHistorySearch sf) {
        Budget budget = this.getUserEntity(Budget.class,sf.getBudgetId(),sf.getUserId());
        sf.setStartBussKey(budgetHandler.createBussKey(budget.getPeriod(),sf.getStartDate()));
        sf.setEndBussKey(budgetHandler.createBussKey(budget.getPeriod(),sf.getEndDate()));
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("bussKey", Sort.DESC);
        pr.addSort(sort);
        PageResult<BudgetSnapshot> qr = baseService.getBeanResult(pr);
        Map res = new HashMap<>();
        List<BudgetDetailVo> list = new ArrayList<>();
        for (BudgetSnapshot bg : qr.getBeanList()) {
            if(bg.getBussKey().length()==4){
                //在年度里面不需要统计
                BudgetDetailVo bdb = this.createDefault(bg);
                list.add(bdb);
            }else{
                Date bussDay = budgetHandler.getBussDay(bg.getBussKey());
                BudgetDetailVo bdb = this.getDetail(bg,bussDay,bg.getBussKey());
                list.add(bdb);
            }
        }
        res.put("beanList",list);
        res.put("maxRow",qr.getMaxRow());
        res.put("page",qr.getPage());
        res.put("pageSize",qr.getPageSize());
        if(sf.isNeedChart()){
            String title = "["+budget.getName()+"]历史执行统计";
            ChartData chartData = this.createChartData(title,list);
            res.put("chartData",chartData);
        }
        return callback(res);
    }

    /**
     * 获取详情
     * @param bg
     * @param bussDay
     * @return
     */
    private BudgetDetailVo getDetail(BudgetSnapshot bg,Date bussDay,String bussKey ){
        BudgetDetailVo bdb = new BudgetDetailVo();
        BeanCopy.copyProperties(bg, bdb);
        if (bg.getGoodsTypeId()!=null) {
            //查询预算实际支付
            Budget budget = new Budget();
            BeanCopy.copyProperties(bg,budget);
            BuyRecordBudgetStat bs= budgetHandler.getActualAmount(budget,bussDay);
            if (bs.getTotalPrice() != null) {
                bdb.setCpPaidTime(bs.getMaxBuyDate());
                bdb.setCpPaidAmount(bs.getTotalPrice().doubleValue());
            }
            bdb.setSource(BudgetLogSource.REAL_TIME);
        }else{
            //查询日志
            BudgetLog bl = budgetService.selectBudgetLog(bussKey,bg.getUserId(),null,bg.getFromId());
            if (bl != null) {
                bdb.setCpPaidTime(bl.getOccurDate());
                double total = PriceUtil.sum(bl.getTrAmount(),bl.getNcAmount(),bl.getBcAmount());
                bdb.setCpPaidAmount(total);
                bdb.setSource(bl.getSource());
            }
        }
        bdb.setId(bg.getFromId());
        return bdb;
    }

    /**
     * 获取详情列表
     * @param sf
     * @return
     */
    private PageResult<BudgetDetailVo> getBudgetDetail(BudgetSnapshotSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        PageResult<BudgetSnapshot> qr = baseService.getBeanResult(pr);
        BudgetLog budgetLog = this.getUserEntity(BudgetLog.class, sf.getBudgetLogId(), sf.getUserId());
        PageResult<BudgetDetailVo> res = new PageResult<>(qr);
        List<BudgetDetailVo> list = new ArrayList<>();
        Date bussDay = budgetLog.getOccurDate();
        String bussKey = budgetLog.getBussKey();
        for (BudgetSnapshot bg : qr.getBeanList()) {
            BudgetDetailVo bdb = this.getDetail(bg,bussDay,bussKey);
            list.add(bdb);
        }
        res.setBeanList(list);
        return res;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(BudgetSnapshotSearch sf) {
        PageResult<BudgetDetailVo> res = this.getBudgetDetail(sf);
        BudgetLog budgetLog = this.getUserEntity(BudgetLog.class, sf.getBudgetLogId(), sf.getUserId());
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle(budgetLog.getBussKey() + "预算实际消费分析");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("花费");
        //总的值
        BigDecimal total = (new BigDecimal(budgetLog.getBcAmount())).add(new BigDecimal(budgetLog.getNcAmount())).add(new BigDecimal(budgetLog.getTrAmount()));
        BigDecimal other = total.subtract(new BigDecimal(0));
        for (BudgetDetailVo bg : res.getBeanList()) {
            Double cpPaidAmount = bg.getCpPaidAmount();
            if (cpPaidAmount != null) {
                chartPieData.getXdata().add(bg.getName());
                ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
                dataDetail.setName(bg.getName());
                dataDetail.setValue(PriceUtil.changeToString(2, cpPaidAmount));
                serieData.getData().add(dataDetail);
                other = other.subtract(new BigDecimal(cpPaidAmount));
            }
        }
        //看病
        chartPieData.getXdata().add("看病花费");
        ChartPieSerieDetailData trDetail = new ChartPieSerieDetailData();
        trDetail.setName("看病花费");
        trDetail.setValue(PriceUtil.changeToString(2, budgetLog.getTrAmount()));
        serieData.getData().add(trDetail);
        other = other.subtract(new BigDecimal(budgetLog.getTrAmount()));

        //饮食成本
        Date[] ds = budgetHandler.getDateRange(budgetLog.getPeriod(), budgetLog.getOccurDate(), true);
        BigDecimal dietPrice = dietService.getTotalPrice(sf.getUserId(), ds[0], ds[1]);
        chartPieData.getXdata().add("饮食花费");
        ChartPieSerieDetailData dietDetail = new ChartPieSerieDetailData();
        dietDetail.setName("饮食花费");
        dietDetail.setValue(PriceUtil.changeToString(2, dietPrice));
        serieData.getData().add(dietDetail);
        other = other.subtract(dietPrice);

        //其他
        chartPieData.getXdata().add("其他花费");
        ChartPieSerieDetailData otherDetail = new ChartPieSerieDetailData();
        otherDetail.setName("其他花费");
        otherDetail.setValue(PriceUtil.changeToString(2, other));
        serieData.getData().add(otherDetail);

        String subTitle = "预算金额:" + PriceUtil.changeToString(2, budgetLog.getBudgetAmount()) + "元,实际总消费:" + PriceUtil.changeToString(2, total) + "元";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);

        return callback(chartPieData);
    }

    /**
     * 获取消费记录
     *
     * @return
     */
    @RequestMapping(value = "/buyRecord", method = RequestMethod.GET)
    public ResultBean buyRecord(BudgetSnapshotBuyRecordSearch sf) {
        BudgetSnapshot snapshot = this.getUserEntity(beanClass,sf.getSnapshotId(),sf.getUserId());
        Date[] ds = this.getDateRange(snapshot);
        BuyRecordSearch brs = new BuyRecordSearch();
        brs.setUserId(sf.getUserId());
        brs.setPage(sf.getPage());
        brs.setPageSize(sf.getPageSize());
        brs.setStartDate(ds[0]);
        brs.setEndDate(ds[1]);
        brs.setGoodsType(snapshot.getGoodsTypeId());
        brs.setSubGoodsType(snapshot.getSubGoodsTypeId());
        brs.setKeywords(snapshot.getKeywords());
        PageRequest pr = brs.buildQuery();
        Sort s = new Sort("buyDate", Sort.DESC);
        pr.addSort(s);
        pr.setBeanClass(BuyRecord.class);
        PageResult<BuyRecord> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    private Date[] getDateRange(BudgetSnapshot snapshot ){
        Date bussDay = budgetHandler.getBussDay(snapshot.getBussKey());
        BudgetLog bl = baseService.getObject(BudgetLog.class,snapshot.getBudgetLogId());
        /**
         * 周期需要使用父类的周期
         * 假如snapshot是月度类型预算，但是该记录是在年度预算统计里面，需要查询的是整个年度的数据
         */
        Date[] ds = budgetHandler.getDateRange(bl.getPeriod(), bussDay, true);
        return ds;
    }
    /**
     * 获取看病记录
     *
     * @return
     */
    @RequestMapping(value = "/treatRecord", method = RequestMethod.GET)
    public ResultBean treatRecord(BudgetSnapshotBuyRecordSearch sf) {
        BudgetSnapshot snapshot = this.getUserEntity(beanClass,sf.getSnapshotId(),sf.getUserId());
        Date[] ds = this.getDateRange(snapshot);
        TreatRecordSearch brs = new TreatRecordSearch();
        brs.setUserId(sf.getUserId());
        brs.setPage(sf.getPage());
        brs.setPageSize(sf.getPageSize());
        brs.setStartDate(ds[0]);
        brs.setEndDate(ds[1]);
        PageRequest pr = brs.buildQuery();
        Sort s = new Sort("treatDate", Sort.DESC);
        pr.addSort(s);
        pr.setBeanClass(TreatRecord.class);
        PageResult<TreatRecord> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }
}
