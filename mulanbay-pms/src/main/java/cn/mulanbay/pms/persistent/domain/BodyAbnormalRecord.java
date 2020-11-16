package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 身体不适Bean定义
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "body_abnormal_record")
public class BodyAbnormalRecord implements java.io.Serializable {

    private static final long serialVersionUID = 6781130949887224965L;

    private Long id;

    private Long userId;
    //器官
    private String organ;
    // 疾病
    private String disease;
    // 疼痛级别(1-10)
    private Integer painLevel;
    // 重要等级(0-5)
    private Double importantLevel;
    // 发生日期
    private Date occurDate;
    // 结束日期
    private Date finishDate;
    //持续天数
    private Integer lastDays;
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
    @Column(name = "organ")
    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    @Basic
    @Column(name = "disease")
    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Basic
    @Column(name = "pain_level")
    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    @Basic
    @Column(name = "important_level")
    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "occur_date", length = 10)
    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "finish_date", length = 10)
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Basic
    @Column(name = "last_days")
    public Integer getLastDays() {
        return lastDays;
    }

    public void setLastDays(Integer lastDays) {
        this.lastDays = lastDays;
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
