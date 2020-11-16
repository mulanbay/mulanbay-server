package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.AuthType;

import java.util.Date;

public class UserProfileRequest implements BindUser {

    private Long userId;
    private String nickname;
    private String phone;
    //邮件发送
    private String email;
    //生日（计算最大心率使用到）
    private Date birthday;
    private Boolean sendEmail;
    private Boolean sendWxMessage;
    private AuthType secAuthType;
    private String residentCity;
    //评分的配置组，对应的是ScoreConfig中的key
    private String scoreGroup;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getSendWxMessage() {
        return sendWxMessage;
    }

    public void setSendWxMessage(Boolean sendWxMessage) {
        this.sendWxMessage = sendWxMessage;
    }

    public AuthType getSecAuthType() {
        return secAuthType;
    }

    public void setSecAuthType(AuthType secAuthType) {
        this.secAuthType = secAuthType;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public String getScoreGroup() {
        return scoreGroup;
    }

    public void setScoreGroup(String scoreGroup) {
        this.scoreGroup = scoreGroup;
    }
}
