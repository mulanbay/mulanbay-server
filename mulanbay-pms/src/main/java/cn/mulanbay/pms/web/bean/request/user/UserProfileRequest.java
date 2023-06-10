package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.AuthType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserProfileRequest implements BindUser {

    private Long userId;
    private String nickname;
    private String phone;
    //邮件发送
    private String email;

    //生日（计算最大心率使用到）
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private AuthType secAuthType;

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

    public AuthType getSecAuthType() {
        return secAuthType;
    }

    public void setSecAuthType(AuthType secAuthType) {
        this.secAuthType = secAuthType;
    }

}
