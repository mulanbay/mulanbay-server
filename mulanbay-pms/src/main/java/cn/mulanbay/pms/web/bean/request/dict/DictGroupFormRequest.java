package cn.mulanbay.pms.web.bean.request.dict;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DictGroupFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.dietGroup.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.dietGroup.code.NotEmpty}")
    private String code;

    @NotNull(message = "{validate.dietGroup.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.dietGroup.orderIndex.NotNull}")
    private Short orderIndex;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
