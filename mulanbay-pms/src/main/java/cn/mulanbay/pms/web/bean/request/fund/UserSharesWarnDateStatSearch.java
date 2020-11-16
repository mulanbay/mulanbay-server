package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class UserSharesWarnDateStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {


    @Query(fieldName = "created_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "created_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "user_shares_id", op = Parameter.Operator.EQ)
    private Long userSharesId;

    @Query(fieldName = "warn_type", op = Parameter.Operator.EQ)
    private Integer warnType;

    private DateGroupType dateGroupType;

    private DataGroupType dataGroupType;
    // 是否补全日期
    private Boolean compliteDate;

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

    @Override
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

    public DataGroupType getDataGroupType() {
        return dataGroupType;
    }

    public void setDataGroupType(DataGroupType dataGroupType) {
        this.dataGroupType = dataGroupType;
    }

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

    public Long getUserSharesId() {
        return userSharesId;
    }

    public void setUserSharesId(Long userSharesId) {
        this.userSharesId = userSharesId;
    }

    public Integer getWarnType() {
        return warnType;
    }

    public void setWarnType(Integer warnType) {
        this.warnType = warnType;
    }

    public Boolean getCompliteDate() {
        return compliteDate;
    }

    public enum DataGroupType {
        COUNT("按次数"), SHARES("按股票名称"), WARN_TYPE("按警告类型");

        private String name;

        DataGroupType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
