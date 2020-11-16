package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class LifeExperienceConsumeSearch extends PageSearch implements BindUser {

    @Query(fieldName = "lifeExperienceDetail.lifeExperience.id", op = Parameter.Operator.EQ)
    private Long lifeExperienceId;

    @Query(fieldName = "lifeExperienceDetail.id", op = Parameter.Operator.EQ)
    private Long lifeExperienceDetailId;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    public Long getLifeExperienceId() {
        return lifeExperienceId;
    }

    public void setLifeExperienceId(Long lifeExperienceId) {
        this.lifeExperienceId = lifeExperienceId;
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

}
