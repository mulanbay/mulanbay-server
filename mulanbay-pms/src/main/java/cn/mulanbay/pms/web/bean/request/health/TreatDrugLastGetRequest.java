package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class TreatDrugLastGetRequest implements BindUser {

    @NotNull(message = "{validate.treatDrug.name.NotEmpty}")
    private String name;

    private Long userId;

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

}
