package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.ExperienceType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 人生经历
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "life_experience")
public class LifeExperience implements java.io.Serializable {
    private static final long serialVersionUID = 94792540067094963L;
    private Long id;
    private Long userId;
    private String name;
    private ExperienceType type;
    private Integer days;
    private Date startDate;
    private Date endDate;
    private Double cost;
    private String tags;
    private String lcName;
    private String location;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public ExperienceType getType() {
        return type;
    }

    public void setType(ExperienceType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "days")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", length = 10)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", length = 10)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "cost")
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "lc_name")
    public String getLcName() {
        return lcName;
    }

    public void setLcName(String lcName) {
        this.lcName = lcName;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    public String getTypeName() {
        if (type != null) {
            return type.getName();
        }
        return null;
    }
}
