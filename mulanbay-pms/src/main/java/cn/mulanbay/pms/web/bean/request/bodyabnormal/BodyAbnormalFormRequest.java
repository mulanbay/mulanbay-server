package cn.mulanbay.pms.web.bean.request.bodyabnormal;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class BodyAbnormalFormRequest implements BindUser {

    private Long id;

    private Long userId;

    //器官
    @NotEmpty(message = "{validate.bodyAbnormal.organ.notEmpty}")
    private String organ;

    // 疾病
    @NotEmpty(message = "{validate.bodyAbnormal.disease.notEmpty}")
    private String disease;

    // 疼痛级别(1-10)
    @NotNull(message = "{validate.bodyAbnormal.painLevel.notNull}")
    private Integer painLevel;

    // 重要等级(0-5)
    @NotNull(message = "{validate.bodyAbnormal.importantLevel.notNull}")
    private Double importantLevel;

    // 发生日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.bodyAbnormal.occurDate.notNull}")
    private Date occurDate;

    // 结束日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.bodyAbnormal.finishDate.notNull}")
    private Date finishDate;

    //持续天数
    @NotNull(message = "{validate.bodyAbnormal.lastDays.notNull}")
    private Integer lastDays;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getLastDays() {
        return lastDays;
    }

    public void setLastDays(Integer lastDays) {
        this.lastDays = lastDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
