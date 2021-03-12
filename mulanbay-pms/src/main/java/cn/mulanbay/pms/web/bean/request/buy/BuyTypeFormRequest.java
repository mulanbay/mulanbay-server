package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BuyTypeFormRequest implements BindUser {

    private Integer id;

    @NotEmpty(message = "{validate.buyType.name.notEmpty}")
    private String name;

    private Long userId;

    @NotNull(message = "{validate.buyType.status.notNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.buyType.orderIndex.notNull}")
    @Min(value = 0, message = "{validate.buyType.orderIndex.notNull}")
    private Short orderIndex;

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
}
