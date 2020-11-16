package cn.mulanbay.pms.web.bean.request.family;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class FamilyFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.family.name.notEmpty}")
    private String name;

    //家庭拥有者或者创建者
    private Long userId;

    @NotNull(message = "{validate.family.status.notNull}")
    private CommonStatus status;

    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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
