package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.http.HttpResult;
import cn.mulanbay.common.http.HttpUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.bean.SharesIndexBean;
import cn.mulanbay.pms.handler.bean.SharesMonitorBean;
import cn.mulanbay.pms.handler.bean.SharesPriceBean;
import cn.mulanbay.pms.handler.bean.SharesScoreConfig;
import cn.mulanbay.pms.persistent.domain.UserShares;
import cn.mulanbay.pms.persistent.domain.UserSharesScore;
import cn.mulanbay.pms.persistent.domain.UserSharesScoreConfig;
import cn.mulanbay.pms.persistent.service.SharesService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 股票，目前基于新浪的股票接口
 *
 * @link https://blog.csdn.net/fangquan1980/article/details/80006762
 * @link https://blog.csdn.net/lgddb00000/article/details/78688420
 */
@Component
public class SharesHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(SharesHandler.class);

    //是否启用缓存
    @Value("${shares.withCache}")
    private boolean withCache = false;

    @Value("${shares.cacheSeconds}")
    private int cacheSeconds = 1800;

    //沪市大盘代码
    public static final String SH_INDEX_SHARES_CODE = "s_sh000001";

    //深市大盘代码
    public static final String SZ_INDEX_SHARES_CODE = "s_sz399001";

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    PmsNotifyHandler pmsNotifyHandler;

    @Autowired
    SharesService sharesService;

    //新浪股票接口前缀
    private String apiSinaUrlPre = "http://hq.sinajs.cn/list=";

    //腾讯股票接口前缀
    private String apiTxUrlPre = "http://qt.gtimg.cn/q=";

    public SharesHandler() {
        super("股票");
    }

    /**
     * todo 加入分布式机制，避免同时查询
     *
     * @param code
     * @return
     */
    public SharesPriceBean getSharesPrice(String code) {
        if (!withCache) {
            return this.getSharesPriceRt(code);
        } else {
            SharesPriceBean spb = cacheHandler.get("shares:" + code, SharesPriceBean.class);
            if (spb == null) {
                spb = this.getSharesPriceRt(code);
                cacheHandler.set("shares:" + code, spb, cacheSeconds);
            }
            return spb;
        }
    }

    public SharesPriceBean getSharesPriceRt(String code) {
        SharesPriceBean spb = new SharesPriceBean();
        spb.setCode(code);
        String url = apiSinaUrlPre + code;
        HttpResult hr = HttpUtil.doHttpGet(url);
        String s = hr.getResponseData();
        if (hr.getStatusCode() != HttpStatus.SC_OK) {
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_ERROR, "查询股票价格异常", "无法查询code=[" + code + "]的股票价格,返回StatusCode=" + hr.getStatusCode(),
                    null, null);
        }
        logger.debug("股票api请求地址：" + url);
        logger.debug("股票api返回结果:" + s);
        int a1 = s.indexOf("\"");
        int a2 = s.lastIndexOf("\"");
        if (a2 - a1 == 1) {
            //代码没有数据
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_FAIL, "查询股票价格失败", "无法查询code=[" + code + "]的股票价格,未获取到数据,返回结果" + s,
                    null, null);
            return spb;
        }
        String vv = s.substring(a1 + 1, a2);
        String[] data = vv.split(",");
        spb.setName(data[0]);
        spb.setOpeningPrice(Double.valueOf(data[1]));
        spb.setClosingPrice(Double.valueOf(data[2]));
        spb.setCurrentPrice(Double.valueOf(data[3]));
        spb.setMaxPrice(Double.valueOf(data[4]));
        spb.setMinPrice(Double.valueOf(data[5]));
        spb.setDealShares(Integer.valueOf(data[8]));
        spb.setDealAmount(Double.valueOf(data[9]));
        spb.setBuy1Shares(Integer.valueOf(data[10]));
        spb.setBuy1Price(Double.valueOf(data[11]));
        spb.setBuy2Shares(Integer.valueOf(data[12]));
        spb.setBuy2Price(Double.valueOf(data[13]));
        spb.setBuy3Shares(Integer.valueOf(data[14]));
        spb.setBuy3Price(Double.valueOf(data[15]));
        spb.setBuy4Shares(Integer.valueOf(data[16]));
        spb.setBuy4Price(Double.valueOf(data[17]));
        spb.setBuy5Shares(Integer.valueOf(data[18]));
        spb.setBuy5Price(Double.valueOf(data[19]));
        spb.setSale1Shares(Integer.valueOf(data[20]));
        spb.setSale1Price(Double.valueOf(data[21]));
        spb.setSale2Shares(Integer.valueOf(data[22]));
        spb.setSale2Price(Double.valueOf(data[23]));
        spb.setSale3Shares(Integer.valueOf(data[24]));
        spb.setSale3Price(Double.valueOf(data[25]));
        spb.setSale4Shares(Integer.valueOf(data[26]));
        spb.setSale4Price(Double.valueOf(data[27]));
        spb.setSale5Shares(Integer.valueOf(data[28]));
        spb.setSale5Price(Double.valueOf(data[29]));
        spb.setOccurTime(DateUtil.getDate(data[30] + " " + data[31], DateUtil.Format24Datetime));
        spb.setTurnOver(this.getTurnOver(code));
        return spb;
    }

    /**
     * 获取大盘指数
     *
     * @param scode
     * @return
     */
    public SharesIndexBean getIndexPrice(String scode) {
        String code;
        if (scode.equals(SH_INDEX_SHARES_CODE) || scode.startsWith("sh")) {
            code = SH_INDEX_SHARES_CODE;
        } else {
            code = SZ_INDEX_SHARES_CODE;
        }
        if (!withCache) {
            return this.getIndexPriceRt(code);
        } else {
            SharesIndexBean spb = cacheHandler.get("shares:" + code, SharesIndexBean.class);
            if (spb == null) {
                spb = this.getIndexPriceRt(code);
                cacheHandler.set("shares:" + code, spb, cacheSeconds);
            }
            return spb;
        }
    }

    /**
     * 查询大盘数据
     *
     * @param code
     * @return
     */
    public SharesIndexBean getIndexPriceRt(String code) {
        SharesIndexBean spb = new SharesIndexBean();
        spb.setCode(code);
        String url = apiSinaUrlPre + code;
        HttpResult hr = HttpUtil.doHttpGet(url);
        String s = hr.getResponseData();
        if (hr.getStatusCode() != HttpStatus.SC_OK) {
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_ERROR, "查询大盘数据异常", "无法查询code=[" + code + "]的股票价格,返回StatusCode=" + hr.getStatusCode(),
                    null, null);
        }
        logger.debug("股票api请求地址：" + url);
        logger.debug("股票api返回结果:" + s);
        int a1 = s.indexOf("\"");
        int a2 = s.lastIndexOf("\"");
        if (a2 - a1 == 1) {
            //代码没有数据
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_FAIL, "查询大盘数据失败", "无法查询code=[" + code + "]的股票价格,未获取到数据,返回结果" + s,
                    null, null);
            return spb;
        }
        String vv = s.substring(a1 + 1, a2);
        String[] data = vv.split(",");
        spb.setName(data[0]);
        spb.setPoint(Double.valueOf(data[1]));
        spb.setCurrentPrice(Double.valueOf(data[2]));
        spb.setFgRate(Double.valueOf(data[3]));
        spb.setDeals(Long.valueOf(data[4]));
        spb.setDealAmount(Double.valueOf(data[5]));
        return spb;
    }

    /**
     * 计算股票评分
     *
     * @param spb
     * @param us
     * @param smb
     * @return
     */
    public UserSharesScore calculateScore(SharesPriceBean spb, UserShares us, SharesMonitorBean smb) {
        try {
            UserSharesScore ssb = new UserSharesScore();
            ssb.setUserShares(us);
            ssb.setUserId(us.getUserId());
            SharesScoreConfig ssc = this.getScoreConfig(us.getUserId());
            //价格分
            ssb.setPriceScore(ssc.getPriceScore(us.getBuyPrice()));
            //资产分
            ssb.setAssetScore(ssc.getAssetScore(us.getBuyPrice() * us.getShares()));
            //当前价格与原始价格相差
            double cbp = spb.getCurrentPrice() - us.getBuyPrice();
            ssb.setPriceScore(ssb.getPriceScore() + ssc.getCbPriceScore(cbp));
            int c = smb.getCount();
            //连续涨跌分
            if (smb.getType() == SharesMonitorBean.Type.FAIL) {
                c = 0 - c;
            }
            ssb.setFgScore(c + smb.getGains() - smb.getFails());
            //售卖
            String sharesLastPK = CacheKey.getKey(CacheKey.SHARES_LAST_PRICE, spb.getCode());
            //获取上一次价格
            SharesPriceBean lastSpb = cacheHandler.get(sharesLastPK, SharesPriceBean.class);
            if (lastSpb != null) {
                double rate = (spb.getDealShares() - lastSpb.getDealShares()) / lastSpb.getDealShares() * 100;
                ssb.setSsScore(ssc.getSsScore(rate));
            }
            //换手率
            int toScore = ssc.getTurnOverScore(spb.getTurnOver());
            ssb.setSsScore(ssb.getSsScore() + toScore);
            //风险：大盘的涨跌
            SharesIndexBean sib = this.getIndexPrice(us.getCode());
            if (StringUtil.isNotEmpty(sib.getName())) {
                ssb.setRiskScore(ssc.getRiskScore(sib.getFgRate()));
            }
            return ssb;
        } catch (Exception e) {
            logger.error("计算股票评分异常", e);
            UserSharesScore ssb = new UserSharesScore();
            ssb.setUserShares(us);
            ssb.setUserId(us.getUserId());
            return ssb;
        }
    }

    /**
     * 计算股票价格变化
     *
     * @param smb
     * @param currentPrice
     * @param occurTime
     * @param code
     * @param failPercent
     * @param gainPercent
     * @return
     */
    public SharesMonitorBean calculatePriceChange(SharesMonitorBean smb, double currentPrice, Date occurTime,
                                                  String code, double failPercent, double gainPercent) {
        if (smb == null) {
            smb = new SharesMonitorBean();
            smb.updateCurrentPrice(currentPrice);
            smb.setCode(code);
            smb.setLastGetTime(occurTime);
            return smb;
        }
        smb.setLastGetTime(occurTime);
        if ((smb.getLastPrice() - currentPrice) / smb.getLastPrice() >= failPercent / 100.0) {
            smb.updateType(SharesMonitorBean.Type.FAIL);
        } else if ((currentPrice - smb.getLastPrice()) / smb.getLastPrice() >= gainPercent / 100.0) {
            smb.updateType(SharesMonitorBean.Type.GAIN);
        } else {
            logger.debug("股票代码:" + code + "价格无浮动");
        }
        smb.updateCurrentPrice(currentPrice);
        return smb;
    }

    /**
     * 获取评分配置
     *
     * @param userId
     * @return
     */
    private SharesScoreConfig getScoreConfig(Long userId) {
        String key = CacheKey.getKey(CacheKey.SHARES_SCORE_CONFIG, userId.toString());
        SharesScoreConfig ssc = cacheHandler.get(key, SharesScoreConfig.class);
        if (ssc == null) {
            UserSharesScoreConfig ussc = sharesService.getUserSharesScoreConfig(userId);
            if (ussc == null) {
                ussc = new UserSharesScoreConfig();
            }
            ssc = SharesScoreConfig.change(ussc);
            cacheHandler.set(key, ssc, CacheHandler.NO_EXPIRE);
        }
        return ssc;
    }

    /**
     * 重新设置缓存
     *
     * @param ussc
     */
    public void resetScoreConfigCache(UserSharesScoreConfig ussc) {
        SharesScoreConfig ssc = SharesScoreConfig.change(ussc);
        String key = CacheKey.getKey(CacheKey.SHARES_SCORE_CONFIG, ussc.getUserId().toString());
        cacheHandler.set(key, ssc, CacheHandler.NO_EXPIRE);

    }

    /**
     * 获取换手率
     * 调用腾讯的接口
     *
     * @param code
     * @return
     */
    private Double getTurnOver(String code) {
        String url = apiTxUrlPre + code;
        HttpResult hr = HttpUtil.doHttpGet(url);
        String s = hr.getResponseData();
        if (hr.getStatusCode() != HttpStatus.SC_OK) {
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_ERROR, "查询股票价格异常", "无法查询code=[" + code + "]的股票价格,返回StatusCode=" + hr.getStatusCode(),
                    null, null);
            return 0.0;
        }
        logger.debug("股票api请求地址：" + url);
        logger.debug("股票api返回结果:" + s);
        int a1 = s.indexOf("\"");
        int a2 = s.lastIndexOf("\"");
        if (a2 - a1 <= 2) {
            //代码没有数据
            //发送警报
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SHARES_GET_PRICE_FAIL, "查询股票价格失败", "无法查询code=[" + code + "]的股票价格,未获取到数据,返回结果" + s,
                    null, null);
            return 0.0;
        }
        String vv = s.substring(a1 + 1, a2);
        String[] data = vv.split("~");
        return Double.valueOf(data[38]);
    }
}
