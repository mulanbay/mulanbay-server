package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-02-14 15:55
 */
public class RoleFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.role.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.role.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.role.orderIndex.NotNull}")
    private Short orderIndex;

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

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
