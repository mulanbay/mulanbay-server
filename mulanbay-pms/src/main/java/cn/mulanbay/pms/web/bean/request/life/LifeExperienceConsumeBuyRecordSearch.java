package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class LifeExperienceConsumeBuyRecordSearch extends PageSearch implements BindUser {

    private String name;

    private Long lifeExperienceDetailId;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    //查询几天内的数据
    private int roundDays;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLifeExperienceDetailId() {
        return lifeExperienceDetailId;
    }

    public void setLifeExperienceDetailId(Long lifeExperienceDetailId) {
        this.lifeExperienceDetailId = lifeExperienceDetailId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRoundDays() {
        return roundDays;
    }

    public void setRoundDays(int roundDays) {
        this.roundDays = roundDays;
    }
}
