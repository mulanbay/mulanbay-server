package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.pms.web.bean.request.BaseYoyStatSearch;

public class SportExerciseYoyStatSearch extends BaseYoyStatSearch {

    private Long userId;

    private Integer sportTypeId;

    //是否统计值
    private Boolean sumValue;

    public Long getUserId() {
        return userId;
    }

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
