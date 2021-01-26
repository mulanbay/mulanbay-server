package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.MessageSendStatus;
import cn.mulanbay.pms.persistent.enums.MessageType;
import cn.mulanbay.pms.persistent.enums.MonitorBussType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户消息
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_message")
public class UserMessage implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 961922489014144054L;

    private Long id;
    private Long userId;
    private MessageType messageType;
    private MonitorBussType bussType;
    private MessageSendStatus sendStatus;
    private LogLevel logLevel;
    private String title;
    private String content;
    private Integer failCount;
    //期望发送时间，用户可以设置提醒时间，让自己可以选择什么时候发送
    private Date expectSendTime;
    private Date lastSendTime;
    //哪台服务器节点
    private String nodeId;
    //代码（错误代码）
    private Integer code;
    //微信的跳转地址
    private String url;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;


    // Constructors

    /**
     * default constructor
     */
    public UserMessage() {
    }


    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
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
    @Column(name = "message_type")
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Basic
    @Column(name = "buss_type")
    public MonitorBussType getBussType() {
        return bussType;
    }

    public void setBussType(MonitorBussType bussType) {
        this.bussType = bussType;
    }

    @Basic
    @Column(name = "send_status")
    public MessageSendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(MessageSendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Basic
    @Column(name = "log_level")
    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "fail_count")
    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    @Basic
    @Column(name = "expect_send_time")
    public Date getExpectSendTime() {
        return expectSendTime;
    }

    public void setExpectSendTime(Date expectSendTime) {
        this.expectSendTime = expectSendTime;
    }

    @Basic
    @Column(name = "last_send_time")
    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    @Basic
    @Column(name = "node_id")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public String getSendStatusName() {
        return sendStatus == null ? null : sendStatus.getName();
    }

    @Transient
    public String getMessageTypeName() {
        return messageType == null ? null : messageType.getName();
    }

    @Transient
    public String getBussTypeName() {
        return bussType == null ? null : bussType.getName();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }

        if(obj == null){
            return false;
        }

        if(obj instanceof UserMessage){
            UserMessage other = (UserMessage) obj;
            if(this.id.equals(other.id)){
                return true;
            }
        }

        return false;
    }
}
