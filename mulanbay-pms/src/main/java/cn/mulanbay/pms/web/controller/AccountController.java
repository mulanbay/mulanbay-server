package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.persistent.domain.Account;
import cn.mulanbay.pms.persistent.domain.AccountSnapshotInfo;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.dto.AccountStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.AccountFlowService;
import cn.mulanbay.pms.persistent.service.AccountService;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.UserPlanService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.fund.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
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
 * 账户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

    private static Class<Account> beanClass = Account.class;

    @Autowired
    AccountService accountService;

    @Autowired
    UserPlanService userPlanService;

    @Autowired
    BudgetHandler budgetHandler;

    @Autowired
    AccountFlowService accountFlowService;

    @Autowired
    BudgetService budgetService;
    /**
     * 获取账户树
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getAccountTree")
    public ResultBean getAccountTree(AccountSearch sf) {
        try {
            sf.setPage(PageRequest.NO_PAGE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort s = new Sort("createdTime", Sort.DESC);
            pr.addSort(s);
            List<Account> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (Account gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取账户树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(AccountSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", Sort.DESC);
        pr.addSort(s);
        PageResult<Account> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid AccountFormRequest bean) {
        Account account = new Account();
        BeanCopy.copyProperties(bean, account);
        account.setCreatedTime(new Date());
        accountService.saveAccount(account);
        this.setOperateBeanId(account.getId());
        return callback(null);
    }

    /**
     * 改变
     *
     * @return
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResultBean change(@RequestBody @Valid AccountChangeRequest bean) {
        Account account = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        Double beforeAmount = account.getAmount();
        account.setAmount(bean.getAfterAmount());
        account.setLastModifyTime(new Date());
        accountService.updateAccount(account, beforeAmount, bean.getRemark());
        //baseService.updateObject(account);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        Account account = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(account);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid AccountFormRequest bean) {
        Account account = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        Double beforeAmount = account.getAmount();
        BeanCopy.copyProperties(bean, account);
        account.setLastModifyTime(new Date());
        accountService.updateAccount(account, beforeAmount, "用户修改账户余额自动写入");
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        String[] ss = deleteRequest.getIds().split(",");
        for(String s : ss){
            accountService.deleteAccount(Long.valueOf(s),deleteRequest.getUserId());
        }
        return callback(null);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(AccountStatSearch as) {
        List<AccountStat> list = this.accountService.statAccount(as.getUserId(), as.getGroupType(),
                as.getType(), as.getStatus(), as.getSnapshotId());
        String title;
        String subTitleSuffix = "";
        if (as.getSnapshotId() == null) {
            title = "实时账户分析";
        } else {
            AccountSnapshotInfo asi = baseService.getObject(AccountSnapshotInfo.class, as.getSnapshotId());
            title = "账户快照分析(" + asi.getName() + ")";
            subTitleSuffix = "(" + DateUtil.getFormatDate(asi.getCreatedTime(), DateUtil.FormatDay1) + ")";
        }
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle(title);
        chartPieData.setUnit("元");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("账户");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (AccountStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(bean.getValue());
        }
        String subTitle = "总计：" + PriceUtil.changeToString(2,totalValue) + "元";
        subTitle += subTitleSuffix;
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 获取当前账号信息
     *
     * @return
     */
    @RequestMapping(value = "/getCurrentAccountInfo", method = RequestMethod.GET)
    public ResultBean getCurrentAccountInfo() {
        Long userId = this.getCurrentUserId();
        double currentAccountAmount = accountService.selectCurrentAccountAmount(userId);
        Map res = new HashMap<>();
        res.put("currentAccountAmount", currentAccountAmount);
        BudgetSearch bs = new BudgetSearch();
        bs.setStatus(CommonStatus.ENABLE);
        bs.setPeriod(PeriodType.MONTHLY);
        bs.setUserId(userId);
        PageRequest pr = bs.buildQuery();
        pr.setBeanClass(Budget.class);
        //预算默认以预算列表来统计实现
        List<Budget> budgetList = baseService.getBeanList(pr);
        double monthBudget = 0;
        for (Budget b : budgetList) {
            monthBudget += b.getAmount();
        }
        //月度消费以预算来实现
        res.put("lastMonthConsume", monthBudget);
        res.put("lastMonthSalary", 0);
        return callback(res);
    }

    /**
     * 生成快照
     *
     * @return
     */
    @RequestMapping(value = "/createSnapshot", method = RequestMethod.POST)
    public ResultBean createSnapshot(@RequestBody @Valid CreateAccountSnapshotRequest bean) {
        Date bussDay = new Date();
        String bussKey = null;
        if(bean.getPeriod()==null){
            //普通快照
            bussKey = DateUtil.getFormatDate(bussDay, DateUtil.Format24Datetime2);
        }else{
            //上个月或者去年
            Date date = null;
            if(bean.getPeriod()==PeriodType.MONTHLY){
                date = DateUtil.getDateMonth(-1,bussDay);
            }else if(bean.getPeriod()==PeriodType.YEARLY){
                date = DateUtil.getDateYear(-1,bussDay);
            }
            bussKey = budgetHandler.createBussKey(bean.getPeriod(), date);
        }
        accountService.createSnapshot(bean.getName(), bussKey,bean.getPeriod(), bean.getRemark(), bean.getUserId());
        //更新预算日志
        this.updateBudgetLogAccountChange(bussKey , bean.getUserId());
        return callback(null);
    }

    /**
     * 更新预算日志中的账户变化
     * @param bussKey
     */
    private boolean updateBudgetLogAccountChange(String bussKey,Long userId){
        BudgetLog bl = budgetService.selectBudgetLog(bussKey,userId);
        if(bl==null){
            //没有预算记录
            return false;
        }
        BigDecimal afterAmount = accountFlowService.statAccountAmount(bussKey,userId);
        if(afterAmount==null){
            return false;
        }
        //往前
        Date date =null;
        String bussKey2=null;
        if(bussKey.length()==4){
            //年
            date = DateUtil.getDate(bussKey,BudgetHandler.YEARLY_DATE_FORMAT);
            date = DateUtil.getDateYear(-1,date);
            bussKey2 = DateUtil.getFormatDate(date, BudgetHandler.YEARLY_DATE_FORMAT);
        }else if(bussKey.length()==6){
            //年
            date = DateUtil.getDate(bussKey,BudgetHandler.MONTHLY_DATE_FORMAT);
            date = DateUtil.getDateMonth(-1,date);
            bussKey2 = DateUtil.getFormatDate(date, BudgetHandler.MONTHLY_DATE_FORMAT);
        }
        BigDecimal beforeAmount = accountFlowService.statAccountAmount(bussKey2,userId);
        if(beforeAmount==null){
            return false;
        }
        //差值
        BigDecimal v = afterAmount.subtract(beforeAmount);
        //更新
        bl.setAccountChangeAmount(v.doubleValue());
        baseService.updateObject(bl);
        return true;
    }

    /**
     * 更新账户改变
     *
     * @return
     */
    @RequestMapping(value = "/updateBudgetLogAccountChange", method = RequestMethod.POST)
    public ResultBean updateBudgetLogAccountChange(@RequestBody @Valid AccountUpdateBudgetLogRequest bean) {
        //更新预算日志
        boolean b = this.updateBudgetLogAccountChange(bean.getBussKey() , bean.getUserId());
        return callback(b);
    }

    /**
     * 预测
     *
     * @return
     */
    @RequestMapping(value = "/forecast", method = RequestMethod.GET)
    public ResultBean forecast(@Valid AccountForecastRequest bean) {
        if (bean.getMonthlySalary() + bean.getMonthlyOtherIncome() - bean.getMonthlyConsume() <= 0) {
            return callbackErrorInfo("每月收入小于支出，无法计算，请先设置各个参数");
        }
        double ta = bean.getCurrentAmount() - bean.getFixExpend();
        Date date = new Date();
        String dateFormat = "yyyy-MM";
        int currentYear = DateUtil.getYear(date);
        int needMonths = 0;
        double monthlySalary = bean.getMonthlySalary();
        ChartData chartData = new ChartData();
        chartData.setTitle("账户预测");
        chartData.setLegendData(new String[]{"账户总额"});
        ChartYData yData = new ChartYData();
        yData.setName("账户总额");
        chartData.getXdata().add(DateUtil.getFormatDate(date, dateFormat));
        yData.getData().add((int) ta);
        //超过1000个月已经没有意义了
        for (int i = 1; i < 1000; i++) {
            if (bean.getForecastType() == 0) {
                if (ta - bean.getForecastValue() > 0) {
                    needMonths = i;
                    break;
                }
            } else {
                if (i > bean.getForecastValue()) {
                    needMonths = i;
                    break;
                }
            }
            date = DateUtil.getDateMonth(1, date);
            int y = DateUtil.getYear(date);
            if (y > currentYear) {
                //增加年收入
                ta += bean.getYearlyIncome();
                //工资增长
                monthlySalary = monthlySalary * (100 + bean.getYearlySalaryRate()) / 100;
                //存款理财收益
                ta += ta * (bean.getAmountInvestRate() / 100) * (bean.getYearlyAmountRate() / 100);
            }
            ta += monthlySalary;
            ta += bean.getMonthlyOtherIncome();
            ta -= bean.getMonthlyConsume();
            currentYear = y;
            chartData.getXdata().add(DateUtil.getFormatDate(date, dateFormat));
            yData.getData().add((int) ta);
        }
        String subTitle;
        if (bean.getForecastType() == 0) {
            subTitle = "预计要" + needMonths + "个月后,在[" + DateUtil.getFormatDate(date, dateFormat) + "]实现";
        } else {
            subTitle = "预期账户总额:" + (int) ta + "元";
        }
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        return callback(chartData);
    }
}
