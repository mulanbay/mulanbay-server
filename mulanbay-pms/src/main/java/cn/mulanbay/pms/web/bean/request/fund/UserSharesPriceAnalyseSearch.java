package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

public class UserSharesPriceAnalyseSearch extends QueryBuilder implements BindUser {

    private Long userSharesId;

    private Long userId;

    @Query(fieldName = "sp.code", op = Parameter.Operator.EQ)
    private String code;

    public Long getUserSharesId() {
        return userSharesId;
    }

    public void setUserSharesId(Long userSharesId) {
        this.userSharesId = userSharesId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
