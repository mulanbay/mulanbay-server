package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.http.HttpResult;
import cn.mulanbay.common.http.HttpUtil;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.common.XMLParser;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.WxpayHandler;
import cn.mulanbay.pms.persistent.domain.UserWxpayInfo;
import cn.mulanbay.pms.persistent.service.WechatService;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.wechat.WxAuthAccessUrlRequest;
import cn.mulanbay.pms.web.bean.request.wechat.WxOpenIdRequest;
import cn.mulanbay.pms.web.bean.response.wechat.JsApiTicketAuthVo;
import cn.mulanbay.pms.web.bean.response.wechat.WxAccessTokenPageAuthVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信相关处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    WxpayHandler wxpayHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    WechatService wechatService;

    /**
     * 微信授权地址
     *
     * @return
     * @link https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
     */
    @RequestMapping(value = "/wx_auth_access_url", method = RequestMethod.GET)
    public ResultBean wx_auth_access_url(WxAuthAccessUrlRequest war) {
        String scope = war.getScope();
        if (StringUtil.isEmpty(scope)) {
            scope = "snsapi_base";
        }
        String redirectUri = war.getRedirectUri();
        if (StringUtil.isEmpty(redirectUri)) {
            redirectUri = wxpayHandler.getAccessAuthRedirectUrl();
        }
        if (!redirectUri.startsWith("http")) {
            redirectUri = systemConfigHandler.getServerDomain() + redirectUri;
        }
        String appId = wxpayHandler.getAppId();
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId +
                "&redirect_uri=" + redirectUri + "&response_type=code&scope=" + scope +
                "&state=" + war.getState() + "#wechat_redirect ";
        logger.info("wx_auth_access_url:" + url);
        return callback(url);
    }

    /**
     * 获取微信openId
     *
     * @param wor
     * @return
     * @link https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
     */
    @RequestMapping(value = "/getWxOpenId", method = RequestMethod.GET)
    public ResultBean getWxOpenId(WxOpenIdRequest wor) {
        String appId = wxpayHandler.getAppId();
        String secret = wxpayHandler.getSecret();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="
                + secret + "&code=" + wor.getCode() + "&grant_type=authorization_code";
        HttpResult hr = HttpUtil.doHttpGet(url);
        logger.debug("getWxOpenId result:" + hr.getResponseData());
        WxAccessTokenPageAuthVo wt = (WxAccessTokenPageAuthVo) JsonUtil.jsonToBean(hr.getResponseData(), WxAccessTokenPageAuthVo.class);
        if (wt.getErrcode() != null) {
            return callbackErrorInfo(wt.getErrmsg());
        }
        try {
            UserWxpayInfo wxpayInfo = wechatService.getUserWxpayInfo(wor.getUserId(), appId);
            if (wxpayInfo == null) {
                wxpayInfo = new UserWxpayInfo();
                wxpayInfo.setAppId(appId);
                wxpayInfo.setUserId(wor.getUserId());
                wxpayInfo.setCreatedTime(new Date());
            } else {
                wxpayInfo.setLastModifyTime(new Date());
            }
            wxpayInfo.setOpenId(wt.getOpenid());
            wechatService.saveOrUpdateUserWxpayInfo(wxpayInfo);
        } catch (Exception e) {
            logger.error("绑定用户微信OpenId异常", e);
        }
        return callback(wt.getOpenid());
    }

    @RequestMapping(value = "/getWxUserInfo", method = RequestMethod.GET)
    public ResultBean getWxUserInfo(String openId) {
        String accessToken = wxpayHandler.getAccessToken();
        //获取用户信息
        String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        HttpResult uiHr = HttpUtil.doHttpGet(userInfoUrl);
        return callback(uiHr);
    }

    @RequestMapping(value = "/getWxJsapiAuth", method = RequestMethod.GET)
    public ResultBean getWxJsapiAuth(String url) {
        JsApiTicketAuthVo res = wxpayHandler.getJsapiTicketAuthWithCache(url);
        return callback(res);
    }

    /**
     * 系统的微信信息
     *
     * @return
     */
    @RequestMapping(value = "/getWxAppInfo", method = RequestMethod.GET)
    public ResultBean getWxAppInfo() {
        Map res = new HashMap<>();
        res.put("appId", wxpayHandler.getAppId());
        //公众号地址
        res.put("oaUrl", wxpayHandler.getOaUrl());
        //公众号二维码地址
        res.put("qrUrl", wxpayHandler.getQrUrl());
        return callback(res);
    }

    /**
     * 获取微信账号
     *
     * @return
     */
    @RequestMapping(value = "/getUserWxpayInfo", method = RequestMethod.GET)
    public ResultBean getUserWxpayInfo(UserCommonRequest uc) {
        UserWxpayInfo uw = wechatService.getUserWxpayInfo(uc.getUserId(), wxpayHandler.getAppId());
        return callback(uw);
    }

    /**
     * 微信公众号回调接口
     *
     * @return
     * @link https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319
     */
    @RequestMapping(value = "/wx_mp_notify")
    public void wx_mp_notify(HttpServletResponse response) {
        logger.info("收到微信公众号回调");
        PrintWriter out = null;
        try {
            if ("GET".equals(request.getMethod())) {
                //TOKEN验证事件
                logger.info("得到微信的公众号回调信息:" + JsonUtil.beanToJson(request.getParameterMap()));
                String signature = request.getParameter("signature");
                String timestamp = request.getParameter("timestamp");
                String nonce = request.getParameter("nonce");
                String echostr = request.getParameter("echostr");
                logger.info("echostr" + echostr);
                if (wxpayHandler.checkMpSignature(signature, timestamp, nonce)) {
                    out = response.getWriter();
                    out.write(echostr);
                }
            } else {
                //request xml格式，存在请求流里面
                String resultStr = IOUtils.toString(request.getInputStream(), "utf-8");
                logger.debug("微信公众号回调接口通知内容:\n" + resultStr);
                Map<String, Object> resultMap = XMLParser.getMapFromXML(resultStr);
                String s = wxpayHandler.handlerWxMessage(resultMap);
                request.setCharacterEncoding("UTF-8");
                //response.setCharacterEncoding("UTF-8");
                if (!StringUtil.isEmpty(s)) {
                    logger.debug("微信公众号自动回复:\n" + s);
                    out = response.getWriter();
                    out.write(s);
                } else {
                    logger.debug("微信公众号不自动回复");
                }
            }
        } catch (Exception e) {
            logger.error("wx_mp_notify error", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("关闭流异常", e);
                }
            }
        }
    }

}
