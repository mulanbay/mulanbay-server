package cn.mulanbay.pms.web.bean.request.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RegisterRequest {

    @NotBlank(message = "{validate.user.username.notNull}")
    private String username;

    @NotBlank(message = "{validate.user.password.notNull}")
    private String password;

    @NotEmpty(message = "{validate.user.nickname.notEmpty}")
    private String nickname;

    //@NotEmpty(message = "{validate.user.phone.notEmpty}")
    private String phone;

    //邮件发送
    //@NotEmpty(message = "{validate.user.email.notEmpty}")
    private String email;

    //生日（计算最大心率使用到）
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.user.birthday.NotNull}")
    private Date birthday;

    @NotNull(message = "{validate.user.sendEmail.NotNull}")
    private Boolean sendEmail;

    @NotNull(message = "{validate.user.sendWxMessage.NotNull}")
    private Boolean sendWxMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
