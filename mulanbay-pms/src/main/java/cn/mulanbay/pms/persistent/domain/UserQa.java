package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.QaMessageSource;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户咨询
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_qa")
public class UserQa implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Long userId;
    private String appId;
    private String openId;
    private QaMessageSource source;
    private String requestContent;
    private Long replyQaId;
    private String replyContent;
    private String sessionId;
    private String matchInfo;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    public UserQa() {
    }

    public UserQa(Long id, Long userId, String appId, String openId, QaMessageSource source, String requestContent, Long replyQaId, String sessionId, Date createdTime, Date lastModifyTime) {
        this.id = id;
        this.userId = userId;
        this.appId = appId;
        this.openId = openId;
        this.source = source;
        this.requestContent = requestContent;
        this.replyQaId = replyQaId;
        this.sessionId = sessionId;
        this.createdTime = createdTime;
        this.lastModifyTime = lastModifyTime;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "open_id")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Basic
    @Column(name = "source")
    public QaMessageSource getSource() {
        return source;
    }

    public void setSource(QaMessageSource source) {
        this.source = source;
    }

    @Basic
    @Column(name = "request_content")
    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    @Basic
    @Column(name = "reply_qa_id")
    public Long getReplyQaId() {
        return replyQaId;
    }

    public void setReplyQaId(Long replyQaId) {
        this.replyQaId = replyQaId;
    }

    @Basic
    @Column(name = "reply_content")
    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    @Basic
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "match_info")
    public String getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public String getSourceName() {
        return source.getName();
    }

}
