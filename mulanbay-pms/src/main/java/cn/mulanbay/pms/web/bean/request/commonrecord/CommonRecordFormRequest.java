package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommonRecordFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.commonRecord.commonRecordTypeId.NotNull}")
    private Integer commonRecordTypeId;

    @NotEmpty(message = "{validate.commonRecord.name.NotEmpty}")
    private String name;

    @NotNull(message = "{validate.commonRecord.value.NotNull}")
    private Integer value;

    @NotNull(message = "{validate.commonRecord.occurTime.NotNull}")
    private Date occurTime;

    private String location;
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

    public Integer getCommonRecordTypeId() {
        return commonRecordTypeId;
    }

    public void setCommonRecordTypeId(Integer commonRecordTypeId) {
        this.commonRecordTypeId = commonRecordTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
