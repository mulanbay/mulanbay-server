package cn.mulanbay.pms.web.bean.response.user;

import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.util.Date;

public class UserOperationVo {

    private Date occurTime;

    private String content;

    //主题,目前词云统计使用
    private String title;

    private UserBehaviorType behaviorType;

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }

    public int getBehaviorTypeIndex() {
        return behaviorType == null ? 0 : behaviorType.getValue();
    }

    public String getBehaviorTypeName() {
        return behaviorType == null ? null : behaviorType.getName();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
