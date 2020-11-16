package cn.mulanbay.pms.web.bean.request.dict;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DictItemFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.dietItem.name.NotEmpty}")
    private String name;

    @NotNull(message = "{validate.dietItem.groupId.NotNull}")
    private Long groupId;

    //子分类使用，可为空
    @NotEmpty(message = "{validate.dietItem.code.NotEmpty}")
    private String code;

    @NotNull(message = "{validate.dietItem.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.dietItem.orderIndex.NotNull}")
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
