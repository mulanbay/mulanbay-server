package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class ErrorCodeDefineSearch extends PageSearch {

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "code", op = Parameter.Operator.EQ)
    private Integer code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
