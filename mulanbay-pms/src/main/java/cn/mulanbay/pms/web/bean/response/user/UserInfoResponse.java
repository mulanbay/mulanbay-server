package cn.mulanbay.pms.web.bean.response.user;

import cn.mulanbay.pms.persistent.enums.AuthType;
import cn.mulanbay.pms.persistent.enums.FamilyMode;
import cn.mulanbay.pms.web.bean.response.TreeBean;

import java.util.Date;
import java.util.List;

public class UserInfoResponse {

    private Long userId;
    private String username;
    private String nickname;
    private String phone;
    //邮件发送
    private String email;
    //生日（计算最大心率使用到）
    private Date birthday;
    private Boolean sendEmail;
    private Boolean sendWxMessage;
    private AuthType secAuthType;
    private String avatar;
    private FamilyMode familyMode;
    //家长模式下家庭组下的用户列表
    private List<TreeBean> familyUserList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public FamilyMode getFamilyMode() {
        return familyMode;
    }

    public void setFamilyMode(FamilyMode familyMode) {
        this.familyMode = familyMode;
    }

    public List<TreeBean> getFamilyUserList() {
        return familyUserList;
    }

    public void setFamilyUserList(List<TreeBean> familyUserList) {
        this.familyUserList = familyUserList;
    }
}
