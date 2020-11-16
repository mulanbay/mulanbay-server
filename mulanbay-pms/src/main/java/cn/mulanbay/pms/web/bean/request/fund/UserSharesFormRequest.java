package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserSharesFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.userShares.name.notEmpty}")
    private String name;

    private Long userId;

    //股票代码
    @NotEmpty(message = "{validate.userShares.code.notEmpty}")
    private String code;

    //买入股数
    @NotNull(message = "{validate.userShares.shares.notNull}")
    private Integer shares;

    //买入价格
    @NotNull(message = "{validate.userShares.buyPrice.notNull}")
    private Double buyPrice;

    //最低止损价格
    @NotNull(message = "{validate.userShares.minPrice.notNull}")
    private Double minPrice;

    //最高可卖出价格
    @NotNull(message = "{validate.userShares.maxPrice.notNull}")
    private Double maxPrice;

    private String remark;

    @NotNull(message = "{validate.budget.remind.notNull}")
    private Boolean remind;

    @NotNull(message = "{validate.userShares.status.notNull}")
    private CommonStatus status;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
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

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
