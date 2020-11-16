package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.MonitorBussType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 系统监控用户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "system_monitor_user")
public class SystemMonitorUser implements Serializable {

    private Long id;

    private Long userId;

    private MonitorBussType bussType;

    private Boolean smsNotify;

    private Boolean wxNotify;

    private Boolean sysMsgNotify;

    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

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
    @Column(name = "buss_type")
    public MonitorBussType getBussType() {
        return bussType;
    }

    public void setBussType(MonitorBussType bussType) {
        this.bussType = bussType;
    }

    @Basic
    @Column(name = "sms_notify")
    public Boolean getSmsNotify() {
        return smsNotify;
    }

    public void setSmsNotify(Boolean smsNotify) {
        this.smsNotify = smsNotify;
    }

    @Basic
    @Column(name = "wx_notify")
    public Boolean getWxNotify() {
        return wxNotify;
    }

    public void setWxNotify(Boolean wxNotify) {
        this.wxNotify = wxNotify;
    }

    @Basic
    @Column(name = "sys_msg_notify")
    public Boolean getSysMsgNotify() {
        return sysMsgNotify;
    }

    public void setSysMsgNotify(Boolean sysMsgNotify) {
        this.sysMsgNotify = sysMsgNotify;
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
}
