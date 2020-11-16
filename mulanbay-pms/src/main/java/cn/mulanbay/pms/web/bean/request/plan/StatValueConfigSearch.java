package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.StatValueType;
import cn.mulanbay.web.bean.request.PageSearch;

public class StatValueConfigSearch extends PageSearch {


    @Query(fieldName = "fid", op = Parameter.Operator.EQ)
    private Long fid;

    @Query(fieldName = "type", op = Parameter.Operator.EQ)
    private StatValueType type;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public StatValueType getType() {
        return type;
    }

    public void setType(StatValueType type) {
        this.type = type;
    }
}
