package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.bean.SharesIndexBean;
import cn.mulanbay.pms.handler.bean.SharesMonitorBean;
import cn.mulanbay.pms.handler.bean.SharesPriceBean;
import cn.mulanbay.pms.persistent.domain.UserShares;
import cn.mulanbay.pms.persistent.domain.UserSharesWarn;
import cn.mulanbay.pms.persistent.enums.SharesWarnType;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 股票基类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public abstract class BaseAbstractSharesJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(BaseAbstractSharesJob.class);

    protected CacheHandler cacheHandler;

    protected PmsNotifyHandler pmsNotifyHandler;

    @Override
    public TaskResult doTask() {
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
        return new TaskResult();
    }

    /**
     * 是否通知
     *
     * @return
     */
    public boolean isNotify() {
        return true;
    }

    protected void notifySharesInfo(SharesWarnType warnType, UserShares us, SharesPriceBean spb) {
        try {
            if (!isNotify()) {
                return;
            }
            String lastSendKey = CacheKey.getKey(CacheKey.SHARES_MSG_MONITOR, us.getUserId().toString(), spb.getCode(), warnType.toString());
            String rr = cacheHandler.getForString(lastSendKey);
            if (rr != null) {
                logger.warn("用户[" + us.getUserId() + "]对于股票[" + us.getCode() + "]的消息类型[" + warnType.toString() + "]已经通知过，不再重复通知.");
                return;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("股票[" + us.getName() + "],代码:" + us.getCode() + ".\n");
            sb.append("<*>买入价:" + us.getBuyPrice() + ",当前价:" + spb.getCurrentPrice() + ",购买股数:" + us.getShares());
            if (warnType == SharesWarnType.LOW_TURN_OVER || (spb.getTurnOver() != null && spb.getTurnOver() > 0)) {
                sb.append(",换手率:" + PriceUtil.changeToString(2, spb.getTurnOver()) + "%.\n");
            } else {
                sb.append(",购入日期:" + DateUtil.getFormatDate(us.getCreatedTime(), DateUtil.FormatDay1) + ".\n");
            }
            double bb = spb.getCurrentPrice() - us.getBuyPrice();
            if (bb >= 0) {
                sb.append("<*>价格上涨:" + PriceUtil.changeToString(2, bb) + "元,盈利:" + PriceUtil.changeToString(2, bb * us.getShares()) + "元.\n");
            } else {
                sb.append("<*>价格下跌:" + PriceUtil.changeToString(2, (0 - bb)) + "元,亏损:" + PriceUtil.changeToString(2, (0 - bb) * us.getShares()) + "元.\n");
            }
            sb.append("<*>止损价格:" + PriceUtil.changeToString(2, us.getMinPrice()) + ",最高抛售价格:" + PriceUtil.changeToString(2, us.getMaxPrice()) + ".\n");
            String key = CacheKey.getKey(CacheKey.SHARES_PRICE_MONITOR, spb.getCode());
            SharesMonitorBean smb = cacheHandler.get(key, SharesMonitorBean.class);
            if (smb != null) {
                sb.append("<*>历史最低价:" + smb.getMinPrice() + ",历史最高价:" + smb.getMaxPrice() + ",当前变化趋势:" + smb.getType().getName() +
                        "(" + smb.getCount() + "次).\n");
            }
            sb.append("<*>股票评分:" + us.getScore());
            Long messageId = pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_SHARES_STAT, warnType.getName(), sb.toString(),
                    us.getUserId(), null);
            //todo 半个小时会通知一次
            cacheHandler.set(lastSendKey, "rr", 1800);
            //保存记录
            if (warnType.isSave()) {
                this.addWarnRecord(warnType, us, messageId);
            }
        } catch (Exception e) {
            logger.error("处理股票[" + us.getName() + "]异常", e);
        }
    }

    protected void notifyIndexSharesInfo(SharesWarnType warnType, String code, SharesIndexBean sib) {
        try {
            if (!isNotify()) {
                return;
            }
            String lastSendKey = CacheKey.getKey(CacheKey.SHARES_MSG_MONITOR, "0", code, warnType.toString());
            String rr = cacheHandler.getForString(lastSendKey);
            if (rr != null) {
                logger.warn("大盘[" + sib.getName() + "]的消息类型[" + warnType.toString() + "]已经通知过，不再重复通知.");
                return;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("大盘[" + sib.getName() + "],代码:" + sib.getCode() + ".\n");
            sb.append("<*>当前点数:" + PriceUtil.changeToString(2, sib.getPoint()) + ",价格:" + PriceUtil.changeToString(2, sib.getCurrentPrice()) + ",涨跌率:" + sib.getFgRate() + ".\n");
            sb.append("<*>成交量:" + sib.getDeals() + "(手),成交额:" + PriceUtil.changeToString(2, sib.getDealAmount()) + "(万元).\n");
            //通知系统管理员
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.USER_SHARES_STAT, warnType.getName(), sb.toString(),
                    null, null);
            //todo 半个小时会通知一次
            cacheHandler.set(lastSendKey, "rr", 1800);

        } catch (Exception e) {
            logger.error("处理大盘数据异常", e);
        }
    }

    /**
     * 检验股票价格
     *
     * @param spb
     * @return
     */
    protected boolean checkSharesPrice(SharesPriceBean spb) {
        if (spb == null) {
            return false;
        } else if (spb.getCurrentPrice() <= 0.01) {
            return false;
        } else {
            return true;
        }
    }

    private void addWarnRecord(SharesWarnType warnType, UserShares us, Long messageId) {
        try {
            UserSharesWarn usw = new UserSharesWarn();
            usw.setUserId(us.getUserId());
            usw.setUserShares(us);
            usw.setWarnType(warnType);
            usw.setMessageId(messageId);
            usw.setCreatedTime(new Date());
            BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
            baseService.saveObject(usw);
        } catch (Exception e) {
            logger.error("保存股票报警记录异常", e);
        }
    }

}
