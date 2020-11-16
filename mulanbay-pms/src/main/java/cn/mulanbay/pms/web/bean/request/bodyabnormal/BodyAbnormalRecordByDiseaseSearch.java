package cn.mulanbay.pms.web.bean.request.bodyabnormal;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

import java.util.Date;

public class BodyAbnormalRecordByDiseaseSearch extends QueryBuilder implements BindUser {

    @Query(fieldName = "occurDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    //做统计绘图跟踪使用
    @Query(fieldName = "disease", op = Parameter.Operator.EQ)
    private String disease;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
