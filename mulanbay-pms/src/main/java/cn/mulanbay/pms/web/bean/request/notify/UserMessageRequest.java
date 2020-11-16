package cn.mulanbay.pms.web.bean.request.notify;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserMessageRequest {

    @NotEmpty(message = "{validate.userMessage.username.notEmpty}")
    private String username;

    private Integer code;

    @NotEmpty(message = "{validate.userMessage.title.notEmpty}")
    private String title;

    @NotEmpty(message = "{validate.userMessage.content.notEmpty}")
    private String content;

    @NotNull(message = "{validate.userMessage.notifyTime.NotNull}")
    private Date notifyTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

}
