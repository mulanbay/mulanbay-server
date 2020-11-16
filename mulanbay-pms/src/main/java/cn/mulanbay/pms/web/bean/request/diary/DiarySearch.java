package cn.mulanbay.pms.web.bean.request.diary;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class DiarySearch extends PageSearch implements BindUser {

    @Query(fieldName = "remark", op = Parameter.Operator.LIKE)
    private String remark;

    @Query(fieldName = "words", op = Parameter.Operator.GTE)
    private Integer minWords;

    @Query(fieldName = "words", op = Parameter.Operator.LTE)
    private Integer maxWords;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMinWords() {
        return minWords;
    }

    public void setMinWords(Integer minWords) {
        this.minWords = minWords;
    }

    public Integer getMaxWords() {
        return maxWords;
    }

    public void setMaxWords(Integer maxWords) {
        this.maxWords = maxWords;
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
