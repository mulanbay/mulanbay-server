package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsTypeFormRequest implements BindUser {

    private Integer id;

    @NotNull(message = "{validate.goodsType.parent.notNull}")
    private Integer parentId;

    @NotEmpty(message = "{validate.goodsType.name.notEmpty}")
    private String name;

    //@NotEmpty(message = "{validate.goodsType.behaviorName.notEmpty}")
    private String behaviorName;

    private Long userId;

    @NotNull(message = "{validate.goodsType.status.notNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.goodsType.orderIndex.notNull}")
    @Min(value = 0, message = "{validate.goodsType.orderIndex.notNull}")
    private Short orderIndex;

    // 是否加入统计
    @NotNull(message = "{validate.goodsType.statable.notNull}")
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

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }
}
