package cn.mulanbay.pms.web.bean.request.schedule;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class TaskServerSearch extends PageSearch {

    @Query(fieldName = "deployId", op = Parameter.Operator.LIKE)
    private String deployId;

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }
}
