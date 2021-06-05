package cn.mulanbay.pms.thread;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.MapUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.handler.CommonCacheHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.ErrorCodeDefine;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 日志记录基础线程
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class BaseLogThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BaseLogThread.class);

    public BaseLogThread(String name) {
        super(name);
    }

    /**
     * request默认拿到的是数组类型，这里转换为通用的原始参数
     *
     * @param paraMap
     * @return
     */
    protected Map changeToNormalMap(Map paraMap) {
        return MapUtil.changeRequestMapToNormalMap(paraMap);
    }

    /**
     * 获取参数ID值
     *
     * @param sf
     * @param paraMap
     * @return
     */
    protected String getParaIdValue(SystemFunction sf, Map paraMap) {
        if (!StringUtil.isEmpty(sf.getIdField())) {
            if (paraMap != null && !paraMap.isEmpty()) {
                //设置key的值，方便后期查找比对使用,目前只对修改类有效
                Object oo = paraMap.get(sf.getIdField());
                if (oo != null) {
                    return oo.toString();
                }
            }
        }
        return null;
    }

    /**
     * 消息提醒
     * @param userId
     * @param code
     * @param message
     */
    protected void notifyError(Long userId, int code, String message) {
        if (code == 0) {
            return;
        }
        SystemConfigHandler systemConfigHandler = BeanFactoryUtil.getBean(SystemConfigHandler.class);
        ErrorCodeDefine ec = systemConfigHandler.getErrorCodeDefine(code);
        notifyError(userId, ec, message);
    }

    /**
     * 消息提醒
     * @param userId
     * @param ec
     * @param message
     */
    protected void notifyError(Long userId, ErrorCodeDefine ec, String message) {
        try {
            if (ec == null) {
                return;
            }
            String title = "错误代码通知" + ec.getName();
            //通知
            PmsNotifyHandler pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
            pmsNotifyHandler.addMessageToNotifier(ec.getCode(), title, message + "," + getUserInfo(userId),
                    null, null);
        } catch (Exception e) {
            logger.error("处理错误代码异常", e);
        }
    }

    private String getUserInfo(Long userId) {
        if (userId == null) {
            return "";
        } else {
            String s = "操作人UserId:" + userId;
            CommonCacheHandler commonCacheHandler = BeanFactoryUtil.getBean(CommonCacheHandler.class);
            User user = commonCacheHandler.getBean(User.class, userId);
            if (user != null) {
                s += ",手机号:" + user.getPhone();
            }
            return s;
        }
    }

}
