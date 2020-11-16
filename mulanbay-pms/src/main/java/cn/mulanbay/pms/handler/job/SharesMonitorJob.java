package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.handler.SharesHandler;
import cn.mulanbay.pms.handler.bean.SharesIndexBean;
import cn.mulanbay.pms.handler.bean.SharesMonitorBean;
import cn.mulanbay.pms.handler.bean.SharesPriceBean;
import cn.mulanbay.pms.persistent.domain.SharesIndexPrice;
import cn.mulanbay.pms.persistent.domain.SharesPrice;
import cn.mulanbay.pms.persistent.domain.UserShares;
import cn.mulanbay.pms.persistent.domain.UserSharesScore;
import cn.mulanbay.pms.persistent.enums.SharesWarnType;
import cn.mulanbay.pms.persistent.service.SharesService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 股票监控
 */
public class SharesMonitorJob extends BaseAbstractSharesJob {

    private static final Logger logger = LoggerFactory.getLogger(SharesMonitorJob.class);

    private BaseService baseService;

    private SharesService sharesService;

    private SharesHandler sharesHandler;

    private SharesMonitorJobPara para;

    @Override
    public TaskResult doTask() {
        super.doTask();
        TaskResult tr = new TaskResult();
        sharesService = BeanFactoryUtil.getBean(SharesService.class);
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        sharesHandler = BeanFactoryUtil.getBean(SharesHandler.class);
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        //监控用户股票
        int un = this.handUserShares();
        tr.setComment("一共监控了" + un + "个用户股票。");
        return tr;
    }

    /**
     * 监控用户股票
     *
     * @return
     */
    private int handUserShares() {
        try {
            List<UserShares> list = sharesService.getRemindUserShares();
            for (UserShares us : list) {
                try {
                    if (us.getUserId() == 0) {
                        //大盘的数据
                        this.handIndexShares(us);
                        continue;
                    }
                    SharesPriceBean spb = sharesHandler.getSharesPrice(us.getCode());
                    handleSharesPrice(spb, us);
                    if (checkSharesPrice(spb) == false) {
                        logger.warn("查询不到股票代码:" + us.getCode() + "相关数据");
                    } else {
                        if (spb.getCurrentPrice() <= us.getMinPrice()) {
                            //达到止损价格
                            notifySharesInfo(SharesWarnType.RC_MIN_PRICE, us, spb);
                        }
                        if (spb.getCurrentPrice() >= us.getMaxPrice()) {
                            //达到抛售价格
                            notifySharesInfo(SharesWarnType.RC_MAX_PRICE, us, spb);
                        }
                        Double tn = spb.getTurnOver();
                        if (tn != null && tn.doubleValue() > 0.001) {
                            if (tn.doubleValue() <= para.getMinTurnOver()) {
                                //换手率过低
                                notifySharesInfo(SharesWarnType.LOW_TURN_OVER, us, spb);
                            }
                            if (tn.doubleValue() >= para.getMaxTurnOver()) {
                                //换手率过高
                                notifySharesInfo(SharesWarnType.HIGH_TURN_OVER, us, spb);
                            }
                        }

                    }
                } catch (Exception e) {
                    logger.error("处理股票代码:" + us.getCode() + "异常", e);
                }
            }
            return list.size();
        } catch (Exception e) {
            logger.error("监控用户股票异常", e);
            return 0;
        }
    }

    /**
     * 监控大盘
     *
     * @return
     */
    private void handIndexShares(UserShares us) {
        try {
            SharesIndexBean sib = sharesHandler.getIndexPrice(us.getCode());
            if (sib.getPoint() <= 0 || sib.getCurrentPrice() <= 0) {
                logger.warn("无法获取该大盘的指数");
                return;
            } else {
                SharesIndexPrice sip = new SharesIndexPrice();
                BeanCopy.copyProperties(sib, sip);
                sip.setOccurTime(new Date());
                sip.setCreatedTime(new Date());
                baseService.saveObject(sip);
            }
            //监控
            if (sib.getPoint() > 0 && sib.getPoint() <= us.getMinPrice()) {
                notifyIndexSharesInfo(SharesWarnType.INDEX_POINT_LOW, us.getCode(), sib);
                us.setMinPrice(us.getMinPrice() - para.getIndexPointRange());
                baseService.updateObject(us);
            }
            if (sib.getPoint() > 0 && sib.getPoint() >= us.getMaxPrice()) {
                notifyIndexSharesInfo(SharesWarnType.INDEX_POINT_HIGH, us.getCode(), sib);
                us.setMaxPrice(us.getMaxPrice() + para.getIndexPointRange());
                baseService.updateObject(us);
            }
        } catch (Exception e) {
            logger.error("监控大盘异常", e);
        }
    }

    /**
     * @param spb
     * @param us
     */
    private void handleSharesPrice(SharesPriceBean spb, UserShares us) {

        try {
            if (checkSharesPrice(spb) == false) {
                logger.error("股票[" + spb.getName() + "]代码:" + spb.getCode() + "无法查询到价格信息，价格为:" + spb.getCurrentPrice());
                return;
            }
            String key = CacheKey.getKey(CacheKey.SHARES_PRICE_MONITOR, spb.getCode());
            SharesMonitorBean smb = cacheHandler.get(key, SharesMonitorBean.class);
            if (smb != null && smb.getLastGetTime() != null) {
                long comp = Math.abs(smb.getLastGetTime().getTime() - spb.getOccurTime().getTime());
                if (comp < 3000 && spb.getCurrentPrice() - smb.getLastPrice() < 0.01) {
                    //如果时间一样且价格在一分钱以内，不处理
                    logger.warn("股票[" + spb.getName() + "]代码:" + spb.getCode() + "价格获取时间重复，不处理");
                    return;
                }
            }
            //Step1:监控价格变化
            //计算价格变化
            smb = sharesHandler.calculatePriceChange(smb, spb.getCurrentPrice(), spb.getOccurTime(), spb.getCode(),
                    para.getFailPercent(), para.getGainPercent());
            //计算评分(主要是发送通知需要使用评分)
            UserSharesScore ssb = sharesHandler.calculateScore(spb, us, smb);
            us.setScore(ssb.getTotalScore());

            if (smb.getType() == SharesMonitorBean.Type.FAIL && smb.getCount() >= para.getFailCounts()) {
                //通知
                notifySharesInfo(SharesWarnType.CT_FAIL, us, spb);
                //重设累计值
                if (para.isResetGf()) {
                    smb.resetGF();
                }
            }
            if (smb.getType() == SharesMonitorBean.Type.GAIN && smb.getCount() >= para.getGainCounts()) {
                //通知
                notifySharesInfo(SharesWarnType.CT_GAIN, us, spb);
                //重设累计值
                if (para.isResetGf()) {
                    smb.resetGF();
                }
            }
            cacheHandler.set(key, smb, para.getCacheHours() * 3600);
            //保存最近一次的价格记录，共比对使用
            String sharesLastPK = CacheKey.getKey(CacheKey.SHARES_LAST_PRICE, spb.getCode());
            cacheHandler.set(sharesLastPK, spb, para.getSharesLastPriceCacheHours() * 3600);
            //step2：更新股票评分(涉及到购买价格、总资产，因此评分是以人为单位，不是以股票为单位)
            updateScore(spb, us);
            //Step3:保存股票价格
            SharesPrice sp = new SharesPrice();
            BeanCopy.copyProperties(spb, sp);
            sp.setUserId(us.getUserId());
            sp.setCreatedTime(new Date());
            baseService.saveObject(sp);
            //Step4：保存评分记录
            ssb.setCreatedTime(new Date());
            ssb.setSharesPriceId(sp.getId());
            baseService.saveObject(ssb);
        } catch (Exception e) {
            logger.error("监控股票价格信息异常", e);
        }

    }

    /**
     * 更新股票评分
     *
     * @param spb
     * @param us
     */
    private void updateScore(SharesPriceBean spb, UserShares us) {
        try {
            if (checkSharesPrice(spb) == false) {
                return;
            }
            int score = us.getScore();
            sharesService.updateUserSharesScore(us.getId(), score);
            if (para.isNotifyScoreWarn()) {
                if (score <= para.getMinWarnScore()) {
                    notifySharesInfo(SharesWarnType.LOW_SCORE, us, spb);
                }
                if (score >= para.getMaxWarnScore()) {
                    notifySharesInfo(SharesWarnType.HIGH_SCORE, us, spb);
                }
            }
        } catch (Exception e) {
            logger.error("更新股票评分异常", e);
        }
    }

    @Override
    public boolean isNotify() {
        return para.isNotify();
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new SharesMonitorJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return SharesMonitorJobPara.class;
    }
}
