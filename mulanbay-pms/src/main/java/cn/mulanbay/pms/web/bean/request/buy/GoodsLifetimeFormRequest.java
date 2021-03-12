package cn.mulanbay.pms.web.bean.request.buy;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsLifetimeFormRequest  {

    private Long id;

    @NotEmpty(message = "{validate.goodsLifetime.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{validate.goodsLifetime.keywords.notEmpty}")
    private String keywords;

    @NotNull(message = "{validate.goodsLifetime.days.notNull}")
    private Integer days;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
