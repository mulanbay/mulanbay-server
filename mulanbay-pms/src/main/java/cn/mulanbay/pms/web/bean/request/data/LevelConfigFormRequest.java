package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class LevelConfigFormRequest {

    private Long id;

    @NotNull(message = "{validate.levelConfig.name.NotNull}")
    private Integer level;

    @NotEmpty(message = "{validate.levelConfig.name.NotEmpty}")
    private String name;

    //达到该等级的最低积分数要求
    @NotNull(message = "{validate.levelConfig.points.NotNull}")
    private Integer points;

    //达到该等级的最低积分数的至少连续天数
    @NotNull(message = "{validate.levelConfig.pointsDays.NotNull}")
    private Integer pointsDays;

    //达到该等级的最低评分要求
    @NotNull(message = "{validate.levelConfig.score.NotNull}")
    private Integer score;

    //达到该等级的最低评分的至少连续天数
    @NotNull(message = "{validate.levelConfig.scoreDays.NotNull}")
    private Integer scoreDays;

    //该等级对应的角色
    private Long roleId;

    @NotNull(message = "{validate.levelConfig.status.NotNull}")
    private CommonStatus status;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPointsDays() {
        return pointsDays;
    }

    public void setPointsDays(Integer pointsDays) {
        this.pointsDays = pointsDays;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScoreDays() {
        return scoreDays;
    }

    public void setScoreDays(Integer scoreDays) {
        this.scoreDays = scoreDays;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
