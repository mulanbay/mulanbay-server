package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookCategoryFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotEmpty(message = "{validate.bookCategory.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.bookCategory.orderIndex.notNull}")
    private Short orderIndex;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
