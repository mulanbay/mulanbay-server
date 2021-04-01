package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.Account;
import cn.mulanbay.pms.persistent.domain.AccountFlow;
import cn.mulanbay.pms.persistent.dto.AccountFlowSnapshotStat;
import cn.mulanbay.pms.persistent.service.AccountFlowService;
import cn.mulanbay.pms.web.bean.request.fund.AccountFlowSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 账户流水
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/accountFlow")
public class AccountFlowController extends BaseController {

    private static Class<AccountFlow> beanClass = AccountFlow.class;

    @Autowired
    AccountFlowService accountFlowService;

    /**
     * 获取列表
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(AccountFlowSearch sf) {
        return callbackDataGrid(this.getAccountFlowResult(sf));
    }

    private PageResult<AccountFlow> getAccountFlowResult(AccountFlowSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", Sort.DESC);
        pr.addSort(s);
        PageResult<AccountFlow> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid AccountFlowSearch sf) {
        if (sf.getAccountId() == null) {
            return callback(this.createSnapshotAnalyseChart(sf));
        }
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", Sort.ASC);
        pr.addSort(s);
        PageResult<AccountFlow> qr = baseService.getBeanResult(pr);
        ChartData chartData = new ChartData();
        Account account = baseService.getObject(Account.class, sf.getAccountId());
        chartData.setTitle("[" + account.getName() + "]账户变化分析");
        chartData.setUnit("元");
        chartData.setLegendData(new String[]{"账户余额", "账户变化值"});
        //混合图形下使用
        chartData.addYAxis("余额","元");
        chartData.addYAxis("变化值","元");
        ChartYData yData = new ChartYData("账户余额");
        ChartYData y2Data = new ChartYData("账户变化值");
        for (AccountFlow bean : qr.getBeanList()) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getCreatedTime(), DateUtil.FormatDay1));
            yData.getData().add(PriceUtil.changeToString(0,bean.getAfterAmount()));
            y2Data.getData().add(PriceUtil.changeToString(0, bean.getAfterAmount() - bean.getBeforeAmount()));
        }
        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        chartData.getYdata().add(y2Data);
        return callback(chartData);
    }

    /**
     * 总账户变化
     * @param sf
     * @return
     */
    private ChartData createSnapshotAnalyseChart(AccountFlowSearch sf) {
        List<AccountFlowSnapshotStat> list = accountFlowService.statSnapshot(sf.getUserId(), sf.getStartDate(), sf.getEndDate());
        ChartData chartData = new ChartData();
        chartData.setTitle("总账户变化分析(有效账户)");
        chartData.setUnit("元");
        chartData.setLegendData(new String[]{"账户总额", "账户变化值"});
        //混合图形下使用
        chartData.addYAxis("余额","元");
        chartData.addYAxis("变化值","元");
        ChartYData yData = new ChartYData("账户总额");
        ChartYData y2Data = new ChartYData("账户变化值");
        int n = list.size();
        if (n > 0) {
            //由于生成快照时很难获取上一次的金额，所以其实是没有保存到账户流水中
            BigDecimal before = list.get(0).getAfterAmount();
            for (int i = 0; i < n; i++) {
                AccountFlowSnapshotStat bean = list.get(i);
                String xs=null;
                int l = bean.getBussKey().length();
                if(l<=8){
                    xs =bean.getBussKey();
                }else{
                    xs = bean.getBussKey().substring(0, 8);
                }
                chartData.getXdata().add(xs);
                yData.getData().add(bean.getAfterAmount().doubleValue());
                String s = PriceUtil.changeToString(0, bean.getAfterAmount().subtract(before));
                y2Data.getData().add(s);
                before = bean.getAfterAmount();
            }
        }

        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        chartData.getYdata().add(y2Data);
        return chartData;
    }

}
