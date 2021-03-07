package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.domain.BudgetSnapshot;
import cn.mulanbay.pms.persistent.dto.BuyRecordBudgetStat;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.web.bean.request.fund.BudgetSnapshotListSearch;
import cn.mulanbay.pms.web.bean.request.fund.BudgetSnapshotSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieDetailData;
import cn.mulanbay.pms.web.bean.response.fund.BudgetDetailVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<BudgetSnapshot> snapshotList = budgetService.getBudgetSnapshotList(sf.getUserId(),sf.getBudgetLogId());
        List<BudgetDetailVo> res = new ArrayList<>();
        for(BudgetSnapshot bg : snapshotList){
            BudgetDetailVo vo=null;
            switch (bg.getPeriod()){
                case ONCE:
                    if(bg.getFeeType()!=null){
                        //实时统计
                        vo = this.getDetail(bg,bussDay);
                    }else{
                        vo = new BudgetDetailVo();
                        BeanCopy.copyProperties(bg,vo);
                        //查手动日志
                        BudgetLog bl = budgetService.selectBudgetLog(budgetLog.getBussKey(), bg.getUserId(), null, bg.getFromId());
                        if(bl!=null){
                            //有记录
                            vo.setCpPaidTime(bl.getOccurDate());
                            BigDecimal paidAmount = new BigDecimal(bl.getTrAmount());
                            paidAmount.add(new BigDecimal(bl.getNcAmount()));
                            paidAmount.add(new BigDecimal(bl.getBcAmount()));
                            vo.setCpPaidAmount(paidAmount.doubleValue());
                        }
                    }
                    break;
                case MONTHLY:
                    vo = this.getDetail(bg,bussDay);
                    if(budgetLog.getPeriod()==PeriodType.YEARLY){
                        //设置子类
                        this.setChildren(vo,budgetLog.getBussKey());
                        vo.setBussKey(budgetLog.getBussKey());
                    }
                    break;
                case YEARLY:
                    vo = this.getDetail(bg,bussDay);
                    break;
                default:
                    break;
            }
            res.add(vo);
        }
        return callback(res);
    }

    /**
     * 设置子类
     * @param vo
     * @param bussKey
     */
    private void setChildren(BudgetDetailVo vo,String bussKey){
        List<BudgetLog> logList = budgetService.getBudgetLogList(vo.getUserId(),vo.getId(),bussKey);
        BigDecimal total  = new BigDecimal(0);
        for(BudgetLog log :logList){
            BudgetDetailVo child = new BudgetDetailVo();
            BeanCopy.copyProperties(log.getBudget(),child);
            child.setId(log.getBudget().getId());
            child.setBussKey(log.getBussKey());
            //有记录
            child.setCpPaidTime(log.getOccurDate());
            BigDecimal paidAmount = new BigDecimal(log.getTrAmount());
            paidAmount = paidAmount.add(new BigDecimal(log.getNcAmount()));
            paidAmount = paidAmount.add(new BigDecimal(log.getBcAmount()));
            child.setCpPaidAmount(paidAmount.doubleValue());
            total = total.add(paidAmount);
            vo.addChild(child);
        }
        //查询设置金额
        vo.setCpPaidAmount(total.doubleValue());
    }
    /**
     * 获取详情
     * @param bg
     * @param bussDay
     * @return
     */
    private BudgetDetailVo getDetail(BudgetSnapshot bg,Date bussDay){
        BudgetDetailVo bdb = new BudgetDetailVo();
        BeanCopy.copyProperties(bg, bdb);
        if (bg.getFeeType()!=null) {
            //查询预算实际支付
            Budget budget = new Budget();
            BeanCopy.copyProperties(bg,budget);
            BuyRecordBudgetStat bs= budgetHandler.getActualAmount(budget,bussDay);
            if (bs.getTotalPrice() != null) {
                bdb.setCpPaidTime(bs.getMaxBuyDate());
                bdb.setCpPaidAmount(bs.getTotalPrice().doubleValue());
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
        for (BudgetSnapshot bg : qr.getBeanList()) {
            BudgetDetailVo bdb = this.getDetail(bg,bussDay);
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
}
