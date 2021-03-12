package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PriceRegionFormRequest implements BindUser {

    private Integer id;

    @NotEmpty(message = "{validate.priceRegion.name.notEmpty}")
    private String name;

    private Long userId;

    @NotNull(message = "{validate.priceRegion.minPrice.NotNull}")
    private Double minPrice;

    @NotNull(message = "{validate.priceRegion.maxPrice.NotNull}")
    private Double maxPrice;

    @NotNull(message = "{validate.priceRegion.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.priceRegion.orderIndex.NotNull}")
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
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
