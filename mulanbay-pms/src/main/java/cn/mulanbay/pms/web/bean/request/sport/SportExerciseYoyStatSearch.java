package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.web.bean.request.BaseYoyStatSearch;

import javax.validation.constraints.NotNull;

public class SportExerciseYoyStatSearch extends BaseYoyStatSearch implements BindUser {

    private Long userId;

    @NotNull(message = "{validate.sportExercise.sportTypeId.NotNull}")
    private Integer sportTypeId;

    //是否统计值
    private Boolean sumValue;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(Integer sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public Boolean getSumValue() {
        return sumValue;
    }

    public void setSumValue(Boolean sumValue) {
        this.sumValue = sumValue;
    }
}
