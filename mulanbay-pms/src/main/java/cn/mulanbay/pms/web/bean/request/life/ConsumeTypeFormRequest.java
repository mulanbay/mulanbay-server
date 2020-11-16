package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ConsumeTypeFormRequest implements BindUser {

    private Integer id;

    @NotEmpty(message = "{validate.consumeType.name.notEmpty}")
    private String name;
    private Long userId;

    @NotNull(message = "{validate.consumeType.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.consumeType.orderIndex.NotNull}")
    private Short orderIndex;

    // 是否加入统计
    @NotNull(message = "{validate.consumeType.statable.NotNull}")
    private Boolean statable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }
}
