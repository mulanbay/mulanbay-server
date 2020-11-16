package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.SharesHandler;
import cn.mulanbay.pms.handler.bean.SharesIndexBean;
import cn.mulanbay.pms.handler.bean.SharesPriceBean;
import cn.mulanbay.pms.persistent.domain.UserShares;
import cn.mulanbay.pms.persistent.enums.SharesWarnType;
import cn.mulanbay.pms.persistent.service.SharesService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 股票统计
 */
public class SharesStatJob extends BaseAbstractSharesJob {

    private static final Logger logger = LoggerFactory.getLogger(SharesStatJob.class);

    SharesHandler sharesHandler;

    @Override
    public TaskResult doTask() {
        super.doTask();
        TaskResult tr = new TaskResult();
        SharesService sharesService = BeanFactoryUtil.getBean(SharesService.class);
        //手动执行的用户编号从额外参数获取
        Object extraPara = this.getExtraPara();
        Long userId = extraPara == null ? null : Long.valueOf(extraPara.toString());
        List<UserShares> list = sharesService.getActiveUserShares(userId);
        sharesHandler = BeanFactoryUtil.getBean(SharesHandler.class);

        int n = list.size();
        if (n > 0) {
            long cuserId = list.get(0).getUserId();
            //原来的总资产
            double op = 0;
            //当前的总资产
            double cp = 0;
            for (int i = 0; i < n; i++) {
                UserShares us = list.get(i);
                if (us.getUserId() == 0) {
                    //大盘
                    handIndexShares(us.getCode());
                } else {
                    SharesPriceBean spb = sharesHandler.getSharesPrice(us.getCode());
                    if (checkSharesPrice(spb) == false) {
                        continue;
                    }
                    if (us.getUserId() != cuserId || i == n - 1) {
                        if (i == n - 1) {
                            notifySharesInfo(SharesWarnType.TIMELY_STAT, us, spb);
                            op += us.getBuyPrice() * us.getShares();
                            cp += spb.getCurrentPrice() * us.getShares();
                        }
                        //当前用户结束，发送
                        StringBuffer sb = new StringBuffer();
                        sb.append("购买时总资产:" + PriceUtil.changeToString(2, op) + "元,");
                        sb.append("当前总资产:" + PriceUtil.changeToString(2, cp) + "元,");
                        if (op <= cp) {
                            sb.append("盈利:" + PriceUtil.changeToString(2, cp - op) + "元.");
                        } else {
                            sb.append("亏损:" + PriceUtil.changeToString(2, op - cp) + "元.");
                        }
                        op = 0;
                        cp = 0;
                        pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_SHARES_ASSET_STAT, "股票资产总统计", sb.toString(),
                                us.getUserId(), null);
                    }
                    cuserId = us.getUserId();
                    notifySharesInfo(SharesWarnType.TIMELY_STAT, us, spb);
                    op += us.getBuyPrice() * us.getShares();
                    cp += spb.getCurrentPrice() * us.getShares();
                }
            }
        }

        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        tr.setComment("一共统计了" + n + "个股票");
        return tr;
    }

    private void handIndexShares(String code) {
        try {
            SharesIndexBean sib = sharesHandler.getIndexPrice(code);
            if (sib.getPoint() <= 0) {
                logger.warn("无法获取该大盘的指数，code=" + code);
            } else {
                notifyIndexSharesInfo(SharesWarnType.TIMELY_STAT, code, sib);
            }
        } catch (Exception e) {
            logger.error("处理大盘数据异常", e);
        }
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return null;
    }
}
