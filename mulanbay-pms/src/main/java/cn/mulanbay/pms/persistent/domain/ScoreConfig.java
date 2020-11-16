package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.CompareType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 评分配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "score_config")
public class ScoreConfig implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    //名称
    private String name;
    private Long groupId;
    //sql语句
    private String sqlContent;
    /**
     * 极限值
     * （1）compareType=MORE时，比如音乐练习得分类型
     * limitValue=LV（例如：0.8），表示每天频率超过0.8时得到满分MS（假如为10分），0为0分
     * 那么sqlContent统计出来的值VV在0-0.8之间时按照比例乘以10取整，比如0.2时的得分为0.2/0.8*10=4
     * 公式为score = VV/LV*MS
     * （2）compareType=LESS时，比如消费得分类型
     * limitValue=LV（例如：0.8），表示每天频率超过0.8时得到0分，0为满分MS（假如为10分）
     * 那么sqlContent统计出来的值VV在0-0.8之间时，分值为（0.8-统计值）按照比例乘以10取整，比如0.2时的得分为（0.8-0.2）/0.8*10=7.5约等于8
     * 公式为score = （LV-VV）/VV*MS
     */
    private Double limitValue;

    private CompareType compareType;

    private Integer maxScore;
    //账户状态
    private CommonStatus status;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "sql_content")
    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    @Basic
    @Column(name = "limit_value")
    public Double getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Double limitValue) {
        this.limitValue = limitValue;
    }

    @Basic
    @Column(name = "compare_type")
    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    @Basic
    @Column(name = "max_score")
    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    @Basic
    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
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
    public String getStatusName() {
        return status.getName();
    }

    @Transient
    public String getCompareTypeName() {
        return compareType.getName();
    }
}
