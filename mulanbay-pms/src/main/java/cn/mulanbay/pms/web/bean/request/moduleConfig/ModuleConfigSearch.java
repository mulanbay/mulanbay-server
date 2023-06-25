package cn.mulanbay.pms.web.bean.request.moduleConfig;

import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.web.bean.request.PageSearch;

public class ModuleConfigSearch extends PageSearch  {

    @Query(fieldName = "name,code,fileName", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    @Query(fieldName = "du", op = Parameter.Operator.EQ)
    private Boolean du;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Boolean getDu() {
        return du;
    }

    public void setDu(Boolean du) {
        this.du = du;
    }
}
