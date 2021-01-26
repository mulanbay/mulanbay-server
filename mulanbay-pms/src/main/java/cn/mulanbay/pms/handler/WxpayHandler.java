package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.http.HttpResult;
import cn.mulanbay.common.http.HttpUtil;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.bean.MessageContent;
import cn.mulanbay.pms.handler.bean.WxAccessToken;
import cn.mulanbay.pms.handler.qa.QaHandler;
import cn.mulanbay.pms.handler.qa.bean.QaResult;
import cn.mulanbay.pms.persistent.domain.UserWxpayInfo;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.QaMessageSource;
import cn.mulanbay.pms.persistent.service.WechatService;
import cn.mulanbay.pms.web.bean.response.wechat.JsApiTicketAuthVo;
import cn.mulanbay.pms.web.bean.response.wechat.WxJsApiTicketVo;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 微信相关处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class WxpayHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(WxpayHandler.class);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();// 读写锁

    public WxpayHandler() {
        super("微信");
    }

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.token}")
    private String token;

    @Value("${wx.userMessageTemplateId}")
    private String userMessageTemplateId;

    @Value("${wx.oaUrl}")
    private String oaUrl;

    @Value("${wx.oa.qrUrl}")
    private String qrUrl;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    WechatService wechatService;

    @Autowired
    BaseService baseService;

    @Autowired
    QaHandler qaHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Value("${wx.accessAuthRedirectUrl}")
    String accessAuthRedirectUrl;

    public String getAccessAuthRedirectUrl() {
        return accessAuthRedirectUrl;
    }

    public String getOaUrl() {
        return oaUrl;
    }

    public String getQrUrl() {
        return systemConfigHandler.getPictureFullUrl(qrUrl);
    }

    /**
     * 获取微信信息
     *
     * @param userId
     * @return
     */
    public UserWxpayInfo getWxpayInfo(Long userId) {
        UserWxpayInfo uw = wechatService.getUserWxpayInfo(userId, appId);
        return uw;
    }

    public boolean sendTemplateMessage(Long userId, String title, String content, Date time, LogLevel level, String url) {
        UserWxpayInfo uw = this.getWxpayInfo(userId);
        if (uw == null) {
            logger.warn("无法获取到userId=" + userId + "的用户微信信息");
            return false;
        }
        String color = null;
        if (level == LogLevel.WARNING) {
            color = "#FF6347";
        } else if (level == LogLevel.ERROR) {
            color = "#FF1493";
        } else if (level == LogLevel.FATAL) {
            color = "#9A32CD";
        } else {
            logger.debug("不需要设置模板消息的颜色属性");
        }
        MessageContent mc = new MessageContent();
        mc.setTouser(uw.getOpenId());
        mc.setTemplate_id(userMessageTemplateId);
        mc.addMessageData("title", title, color);
        mc.addMessageData("content", "\n" + content);
        if (time != null) {
            mc.addMessageData("time", DateUtil.getFormatDate(time, DateUtil.Format24Datetime));
        }
        mc.setUrl(getFullUrl(url));
        HttpResult hr = this.sendTemplateMessage(mc);
        //logger.debug("发送模板消息,mc:"+JsonUtil.beanToJson(mc));
        //logger.debug("发送模板消息,hr:"+JsonUtil.beanToJson(hr));
        if (hr.getStatusCode() == HttpStatus.SC_OK) {
            return true;
        } else {
            logger.error("发送模板消息异常，" + hr.getResponseData());
            return false;
        }
    }

    private String getFullUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            url = systemConfigHandler.getStringConfig("system.mobile.mainUrl");
        }
        if (url.startsWith("http")) {
            return url;
        } else {
            return systemConfigHandler.getStringConfig("system.mobile.baseUrl") + url;
        }
    }

    /**
     * 发送模板消息
     *
     * @param mc
     * @return
     */
    public HttpResult sendTemplateMessage(MessageContent mc) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        String jsonData = JsonUtil.beanToJson(mc);
        HttpResult hr = HttpUtil.doPostJson(url, jsonData);
        return hr;
    }

    /**
     * 获取模板列表
     *
     * @param accessTocken
     * @return
     */
    public HttpResult getTemplateList(String accessTocken) {
        String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + accessTocken;
        HttpResult hr = HttpUtil.doHttpGet(url);
        return hr;
    }

    /**
     * 获取授权token
     *
     * @return
     */
    public String getAccessToken() {
        String accessToken = null;
        try{
            //lock.readLock().lock();
            boolean b = lock.readLock().tryLock(5, TimeUnit.SECONDS);
            if(!b){
                logger.error("无法获取到获取授权token的读锁");
                return null;
            }
            accessToken = cacheHandler.getForString("wx:accessToken:" + appId);
            if (accessToken == null) {
                /**
                 * Must release read lock before acquiring write lock
                 * 参考ReentrantReadWriteLock源码，要想拿写锁，必须先要释放读锁
                 */
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    // 获取accessToken
                    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
                    HttpResult hr = HttpUtil.doHttpGet(url);
                    if (hr.getStatusCode() == HttpStatus.SC_OK) {
                        WxAccessToken at = (WxAccessToken) JsonUtil.jsonToBean(hr.getResponseData(), WxAccessToken.class);
                        accessToken = at.getAccess_token();
                        cacheHandler.set("wx:accessToken:" + appId, accessToken, at.getExpires_in() - 10);
                    } else {
                        logger.warn("无法获取到AccessToken");
                    }
                    lock.readLock().lock();
                } catch (Exception e) {
                    logger.error("从微信获取AccessToken异常",e);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }catch (Exception e) {
            logger.error("获取AccessToken异常",e);
        } finally {
            lock.readLock().unlock();
        }
        return accessToken;
    }

    /**
     * 根据access_token获取jsapi_ticket
     *
     * @param accessToken
     * @return
     */
    public WxJsApiTicketVo getJsapiTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        HttpResult hr = HttpUtil.doHttpGet(url);
        if (hr.getStatusCode() == HttpStatus.SC_OK) {
            logger.debug("getJsapiTicket:" + hr.getResponseData());
            WxJsApiTicketVo at = (WxJsApiTicketVo) JsonUtil.jsonToBean(hr.getResponseData(), WxJsApiTicketVo.class);
            return at;
        } else {
            throw new ApplicationException(PmsErrorCode.WXPAY_JSAPITOCKEN_ERROR);
        }
    }

    /**
     * 获取授权
     *
     * @param jsapiTicket
     * @param url
     * @return
     */
    public JsApiTicketAuthVo getJsapiTicketAuth(String jsapiTicket, String url) {
        try {
            String noncestr = RandomStringGenerator.getRandomStringByLength(16);
            long timestamp = System.currentTimeMillis() / 1000;
            String toBeSign = "jsapi_ticket=" + jsapiTicket +
                    "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            logger.debug("JsapiTicketAuth to be sign:" + toBeSign);
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(toBeSign.getBytes("UTF-8"));
            String sign = Md5Util.byteArrayToHexString(crypt.digest());
            logger.debug("JsapiTicketAuth sign:" + sign);
            JsApiTicketAuthVo jta = new JsApiTicketAuthVo();
            jta.setNoncestr(noncestr);
            jta.setTimestamp(timestamp);
            jta.setAppId(appId);
            jta.setSign(sign);
            jta.setUrl(url);
            return jta;
        } catch (Exception e) {
            logger.error("getJsapiTicketAuth error", e);
            throw new ApplicationException(PmsErrorCode.WXPAY_JSAPITOCKEN_ERROR);
        }
    }

    /**
     * 获取JsapiTicketAuth
     *
     * @param url
     * @return
     */
    public JsApiTicketAuthVo getJsapiTicketAuthWithCache(String url) {
        //先取缓存
        String key = CacheKey.getKey(CacheKey.WX_JSAPI_TICKET, appId);
        String jsapiTicket = cacheHandler.getForString(key);
        if (jsapiTicket == null) {
            String accessToken = getAccessToken();
            WxJsApiTicketVo jt = this.getJsapiTicket(accessToken);
            jsapiTicket = jt.getTicket();
            if (jt.getExpires_in() <= 0) {
                throw new ApplicationException(PmsErrorCode.WXPAY_JSAPITOCKEN_ERROR);
            }
            cacheHandler.set(key, jt.getTicket(), jt.getExpires_in() - 60);
        }
        JsApiTicketAuthVo res = this.getJsapiTicketAuth(jsapiTicket, url);
        return res;
    }

    /**
     * 检查微信公众号的token
     *
     * @param signature
     * @param timestrap
     * @param nonce
     * @return
     */
    public boolean checkMpSignature(String signature, String timestrap,
                                    String nonce) {
        String tt = token;
        String[] arr = new String[]{tt, timestrap, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            buf.append(arr[i]);
        }
        String temp =
                getSha1(buf.toString());
        return temp.equals(signature);
    }

    public String getSha1(String str) {
        try {
            if (null == str || str.length() == 0) {
                return null;
            }
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byTemp = md[i];
                buf[k++] = hexDigits[byTemp >>> 4 & 0xf];
                buf[k++] = hexDigits[byTemp & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            logger.error("getSha1 异常", e);
            throw new ApplicationException(PmsErrorCode.WXPAY_TOKEN_SHA_ERROR);
        }
    }

    /**
     * 处理微信公众号来的微信消息
     *
     * @param resultMap
     * @return
     */
    public String handlerWxMessage(Map<String, Object> resultMap) {
        String s = this.createWxMessageResponse(resultMap);
        return s;
    }

    /**
     * 处理微信公众号消息
     * 后期可以换成异步模式
     *
     * @param resultMap
     */
    public String createWxMessageResponse(Map<String, Object> resultMap) {
        String msgType = resultMap.get("MsgType").toString();
        String fromUserName = resultMap.get("FromUserName").toString();
        String toUserName = resultMap.get("ToUserName").toString();
        if ("event".equals(msgType)) {
            String event = resultMap.get("Event").toString();
            if ("unsubscribe".equals(event)) {
                logger.info("取消关注:" + fromUserName);
                updateSubscribe(false, fromUserName);
            } else if ("subscribe".equals(event)) {
                logger.info("增加关注:" + fromUserName);
                updateSubscribe(true, fromUserName);
                Object eventKey = resultMap.get("EventKey");
                if (eventKey != null && StringUtil.isNotEmpty(eventKey.toString())) {
                    //带参数的二维码地址
                    String id = eventKey.toString().replace("qrscene_", "");
                    logger.info("带参数的二维码地址:" + fromUserName);
                } else {
                    logger.info("普通的关注:" + fromUserName);
                }
            } else if ("SCAN".equals(event)) {
                Object eventKey = resultMap.get("EventKey");
                if (eventKey != null) {
                    logger.info("扫码:" + fromUserName);
                }
            }
        } else if ("text".equals(msgType)) {
            String content = resultMap.get("Content").toString();
            logger.info("文本消息:" + fromUserName + ",content:" + content);
            String res = this.handleTextMessage(content, fromUserName);
            String reply = this.createTextWxReply(res, fromUserName, toUserName);
            return reply;
        } else {
            String reply = this.createTextWxReply("未能处理的消息类别:" + msgType, fromUserName, toUserName);
            return reply;
        }
        return null;
    }

    /**
     * 处理文本消息
     *
     * @param content
     * @param fromUserName
     * @return
     */
    private String handleTextMessage(String content, String fromUserName) {
        //先判断权限
        UserWxpayInfo wx = wechatService.getUserWxpayInfo(appId, fromUserName);
        if (wx == null || wx.getUserId() == null) {
            return "你的微信尚未在系统中绑定";
        }
        String sessionId = "WxOpenId_" + fromUserName;
        QaResult cr = qaHandler.handleMessage(QaMessageSource.WECHAT, content, wx.getUserId(), sessionId);
        return cr.getRes();
    }

    private void updateSubscribe(boolean subscribe, String openId) {
        UserWxpayInfo uw = wechatService.getUserWxpayInfo(appId, openId);
        if (uw == null) {
            uw = new UserWxpayInfo();
            uw.setAppId(appId);
            uw.setOpenId(openId);
            uw.setCreatedTime(new Date());
            uw.setRemark("微信消息回调加入");
            uw.setSubscribe(subscribe);
            uw.setSubscribeTime(new Date());
        } else {
            uw.setSubscribe(subscribe);
            uw.setSubscribeTime(new Date());
        }
        wechatService.saveOrUpdateUserWxpayInfo(uw);
    }

    private String createTextWxReply(String content, String ToUserName, String FromUserName) {
        if (StringUtil.isEmpty(content)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName>" + "<![CDATA[" + ToUserName + "]]></ToUserName>");
        sb.append("<FromUserName>" + "<![CDATA[" + FromUserName + "]]></FromUserName>");
        long createTime = System.currentTimeMillis() / 1000;
        sb.append("<CreateTime>" + "<![CDATA[" + createTime + "]]></CreateTime>");
        sb.append("<MsgType>" + "<![CDATA[text]]></MsgType>");
        sb.append("<Content>" + "<![CDATA[" + content + "]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }
}
