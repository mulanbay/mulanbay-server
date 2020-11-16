package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * 用户级别配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "level_config")
public class LevelConfig implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Integer level;
    private String name;
    //达到该等级的最低积分数要求
    private Integer points;
    //达到该等级的最低积分数的至少连续天数
    private Integer pointsDays;
    //达到该等级的最低评分要求
    private Integer score;
    //达到该等级的最低评分的至少连续天数
    private Integer scoreDays;
    //该等级对应的角色
    private Long roleId;
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
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
    @Column(name = "points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Basic
    @Column(name = "points_days")
    public Integer getPointsDays() {
        return pointsDays;
    }

    public void setPointsDays(Integer pointsDays) {
        this.pointsDays = pointsDays;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "score_days")
    public Integer getScoreDays() {
        return scoreDays;
    }

    public void setScoreDays(Integer scoreDays) {
        this.scoreDays = scoreDays;
    }

    @Basic
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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


}
