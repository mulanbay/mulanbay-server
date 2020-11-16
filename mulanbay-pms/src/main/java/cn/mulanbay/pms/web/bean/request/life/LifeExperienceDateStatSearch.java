package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ExperienceType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;
import java.util.List;

public class LifeExperienceDateStatSearch extends QueryBuilder implements DateStatSearch, BindUser {

    @Query(fieldName = "start_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "start_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "type", op = Parameter.Operator.IN)
    private String inTypes;

    private List<ExperienceType> types;

    private DateGroupType dateGroupType;

    // 是否补全日期
    private Boolean compliteDate;

    public Boolean getCompliteDate() {
        return compliteDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
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

    @Override
    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

    public String getInTypes() {
        return inTypes;
    }

    public void setInTypes(String inTypes) {
        this.inTypes = inTypes;
    }

    public List<ExperienceType> getTypes() {
        return types;
    }

    public void setTypes(List<ExperienceType> types) {
        this.types = types;
    }

    public void setIntTypes(List<ExperienceType> types) {
        if (types != null && !types.isEmpty()) {
            int n = types.size();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                sb.append(types.get(i).getValue());
                if (i < n - 1) {
                    sb.append(",");
                }
            }
            this.inTypes = sb.toString();
        }
    }

}
