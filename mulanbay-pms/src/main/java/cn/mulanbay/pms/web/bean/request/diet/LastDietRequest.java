package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DietSource;
import cn.mulanbay.pms.persistent.enums.DietType;

import javax.validation.constraints.NotNull;

public class LastDietRequest implements BindUser {

    private Long userId;

    @NotNull(message = "{validate.diet.dietType.NotNull}")
    private DietType dietType;

    @NotNull(message = "{validate.diet.dietSource.NotNull}")
    private DietSource dietSource;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public DietSource getDietSource() {
        return dietSource;
    }

    public void setDietSource(DietSource dietSource) {
        this.dietSource = dietSource;
    }
}
