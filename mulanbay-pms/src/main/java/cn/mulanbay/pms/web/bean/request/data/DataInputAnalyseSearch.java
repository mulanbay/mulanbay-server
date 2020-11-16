package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.web.bean.request.PageSearch;

public class DataInputAnalyseSearch extends PageSearch {

    @Query(fieldName = "tableName", op = Parameter.Operator.LIKE)
    private String tableName;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }
}
