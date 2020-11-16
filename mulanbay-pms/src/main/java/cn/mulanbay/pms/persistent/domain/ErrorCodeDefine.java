package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.MonitorBussType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 错误代码定义
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "error_code_define")
public class ErrorCodeDefine implements Serializable {

    private Integer code;

    private String name;

    private LogLevel level;

    private Boolean notifiable;

    private Boolean realtimeNotify;

    private Boolean loggable;

    private String causes;

    private String solutions;

    private MonitorBussType bussType;

    private Integer count;

    //周期(秒数)，默认是0，如果大于0表示一段时间内只会对一个用户只发送一次通知
    private Integer limitPeriod;

    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    // Property accessors
    @Id
    @Column(name = "code", unique = true, nullable = false)
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "level", nullable = false)
    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    @Column(name = "notifiable", nullable = false)
    public Boolean getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(Boolean notifiable) {
        this.notifiable = notifiable;
    }

    @Column(name = "realtime_notify", nullable = false)
    public Boolean getRealtimeNotify() {
        return realtimeNotify;
    }

    public void setRealtimeNotify(Boolean realtimeNotify) {
        this.realtimeNotify = realtimeNotify;
    }

    @Column(name = "loggable", nullable = false)
    public Boolean getLoggable() {
        return loggable;
    }

    public void setLoggable(Boolean loggable) {
        this.loggable = loggable;
    }

    @Column(name = "causes")
    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }

    @Column(name = "solutions")
    public String getSolutions() {
        return solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    @Column(name = "buss_type", nullable = false)
    public MonitorBussType getBussType() {
        return bussType;
    }

    public void setBussType(MonitorBussType bussType) {
        this.bussType = bussType;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "limit_period")
    public Integer getLimitPeriod() {
        return limitPeriod;
    }

    public void setLimitPeriod(Integer limitPeriod) {
        this.limitPeriod = limitPeriod;
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
    public String getLevelName() {
        return level == null ? null : level.getName();
    }

    @Transient
    public String getBussTypeName() {
        return bussType == null ? null : bussType.getName();
    }


}
