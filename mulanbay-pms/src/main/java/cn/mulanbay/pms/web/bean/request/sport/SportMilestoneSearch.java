package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class SportMilestoneSearch extends PageSearch implements BindUser {

    @Query(fieldName = "sportExercise.id", op = Parameter.Operator.EQ)
    private Long sportExerciseId;

    @Query(fieldName = "sportType.id", op = Parameter.Operator.EQ)
    private Integer sportTypeId;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    public Long getSportExerciseId() {
        return sportExerciseId;
    }

    public void setSportExerciseId(Long sportExerciseId) {
        this.sportExerciseId = sportExerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(Integer sportTypeId) {
        this.sportTypeId = sportTypeId;
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
