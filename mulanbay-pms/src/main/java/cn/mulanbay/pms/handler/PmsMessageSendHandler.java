package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import cn.mulanbay.pms.persistent.domain.UserSetting;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.MessageSendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 消息发送处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class PmsMessageSendHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(PmsMessageSendHandler.class);

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    /**
     * 发送邮件的服务器的IP和端口
     */
    @Value("${mail.mailServerHost}")
    private String mailServerHost;

    @Value("${mail.mailServerPort}")
    private String mailServerPort = "587";

    @Value("${notify.message.send.maxFail}")
    int messageSendMaxFail;

    @Value("${notify.message.send.lock}")
    boolean sendLock;

    @Value("${system.nodeId}")
    String nodeId;

    @Autowired
    WxpayHandler wxpayHandler;

    @Autowired
    RedisDistributedLock redisDistributedLock;

    @Autowired
    LogHandler logHandler;

    @Autowired
    BaseService baseService;

    static Properties props = null;

    public PmsMessageSendHandler() {
        super("消息发送");
    }

    @Override
    public void init() {
        super.init();
        props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", mailServerHost);
        //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
        props.put("mail.smtp.port", mailServerPort);
        // 此处填写你的账号
        props.put("mail.user", username);
        // 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", password);
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    public boolean sendMessage(UserMessage message) {
        boolean needLock;
        if (message.getId() == null) {
            needLock = false;
        } else {
            needLock = true;
        }
        //这个message有可能在其他地方被设置了id
        String key = "messageSendLock:" + message.getId();
        try {
            if (needLock) {
                boolean b = redisDistributedLock.lock(key, 0);
                if (!b) {
                    logger.warn("消息ID=" + message.getId() + "正在被发送，无法重复发送");
                    logHandler.addSystemLog(LogLevel.WARNING, "消息重复发送", "消息ID=" + message.getId() + "正在被发送，无法重复发送",
                            PmsErrorCode.MESSAGE_DUPLICATE_SEND);
                    return true;
                }
            }
            UserSetting userSetting = baseService.getObject(UserSetting.class, message.getUserId());
            if (userSetting == null) {
                logger.warn("无法获取到userId=" + message.getUserId() + "用户信息,无法发送消息");
                message.setSendStatus(MessageSendStatus.SEND_SUCCESS);
                message.setFailCount(message.getFailCount() + 1);
                message.setLastSendTime(new Date());
                message.setNodeId(nodeId);
                baseService.saveOrUpdateObject(message);
                return true;
            }
            boolean res;
            if (message.getFailCount() < messageSendMaxFail) {
                res = this.sendUserMessage(userSetting, message);
                if (res) {
                    message.setSendStatus(MessageSendStatus.SEND_SUCCESS);
                } else {
                    message.setSendStatus(MessageSendStatus.SEND_FAIL);
                    message.setFailCount(message.getFailCount() + 1);
                }
            } else {
                message.setSendStatus(MessageSendStatus.SEND_FAIL);
                message.setFailCount(message.getFailCount() + 1);
                logger.info("消息ID=" + message.getId() + "达到最大发送失败次数:" + messageSendMaxFail + ",本消息已经发送失败次数:" + message.getFailCount());
                res = true;
            }
            message.setLastSendTime(new Date());
            message.setNodeId(nodeId);
            this.saveMessage(message);
            return res;
        } catch (Exception e) {
            logger.error("发送消息失败，id=" + message.getId(), e);
            return false;
        } finally {
            if (needLock) {
                boolean b = redisDistributedLock.releaseLock(key);
                if (!b) {
                    logger.warn("释放消息发送锁key=" + key + "失败");
                }
            }
        }
    }

    /**
     * 直接扔掉
     *
     * @param message
     */
    private void saveMessage(UserMessage message) {
        try {
            baseService.saveOrUpdateObject(message);
        } catch (Exception e) {
            logger.error("保持用户消息异常", e);
        }
    }

    /**
     * 发送消息(后期比如可以增加短信的发送)
     *
     * @param userSetting
     * @param message
     * @return
     */
    private boolean sendUserMessage(UserSetting userSetting, UserMessage message) {
        User user = baseService.getObject(User.class, message.getUserId());
        boolean b1 = true;
        if (userSetting.getSendEmail() && StringUtil.isNotEmpty(user.getEmail())) {
            // 发送邮件
            b1 = this.sendMail(message.getTitle(), message.getContent(), user.getEmail());
        }
        boolean b2 = true;
        if (userSetting.getSendWxMessage()) {
            b2 = this.sendWxMessage(message.getUserId(), message.getTitle(), message.getContent(), message.getCreatedTime(), message.getLogLevel(), message.getUrl());
        }
        //只要有一个发送成功算成功
        return b1 || b2;
    }

    /**
     * 发送邮件
     *
     * @param title
     * @param content
     * @param toAddress
     * @return
     */
    public boolean sendMail(String title, String content, String toAddress) {
        try {
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(toAddress);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 最后当然就是发送邮件啦
            Transport.send(message);
            logger.debug("向" + toAddress + "发送了一封邮件");
            return true;
        } catch (Exception e) {
            logger.error("向[" + toAddress + "]发送邮件异常", e);
            return false;
        }
    }

    /**
     * 发送微信消息
     *
     * @param userId
     * @param title
     * @param content
     * @param time
     * @param level
     * @return
     */
    public boolean sendWxMessage(Long userId, String title, String content, Date time, LogLevel level, String url) {
        return wxpayHandler.sendTemplateMessage(userId, title, content, time, level, url);

    }

}
