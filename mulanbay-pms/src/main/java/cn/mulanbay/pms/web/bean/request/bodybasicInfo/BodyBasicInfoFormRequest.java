package cn.mulanbay.pms.web.bean.request.bodybasicInfo;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BodyBasicInfoFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.bodyBasicInfo.recordDate.notNull}")
    private Date recordDate;

    @NotNull(message = "{validate.bodyBasicInfo.weight.notNull}")
    private Double weight;

    @NotNull(message = "{validate.bodyBasicInfo.height.notNull}")
    private Integer height;

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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
