package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TreatOperationFormRequest implements BindUser {

    private Long id;

    @NotNull(message = "{validate.treatOperation.treatRecordId.NotNull}")
    private Long treatRecordId;
    private Long userId;

    @NotEmpty(message = "{validate.treatOperation.name.notEmpty}")
    private String name;

    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.treatOperation.treatDate.NotNull}")
    private Date treatDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewDate;

    @NotNull(message = "{validate.treatOperation.fee.NotNull}")
    private Double fee;
    //药是否有效

    @NotNull(message = "{validate.treatOperation.available.NotNull}")
    private Boolean available;
    // 是否有病
    @NotNull(message = "{validate.treatOperation.isSick.NotNull}")
    private Boolean isSick;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreatRecordId() {
        return treatRecordId;
    }

    public void setTreatRecordId(Long treatRecordId) {
        this.treatRecordId = treatRecordId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


    public Boolean getIsSick() {
        return isSick;
    }

    public void setIsSick(Boolean isSick) {
        this.isSick = isSick;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
