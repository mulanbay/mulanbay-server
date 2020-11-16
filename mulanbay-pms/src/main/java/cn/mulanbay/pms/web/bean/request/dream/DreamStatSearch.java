package cn.mulanbay.pms.web.bean.request.dream;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DreamStatus;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;
import java.util.List;

public class DreamStatSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "start_date", op = Parameter.Operator.GTE, referFieldName = "dateQueryType")
    private Date startDate;

    @Query(fieldName = "start_date", op = Parameter.Operator.LTE, referFieldName = "dateQueryType")
    private Date endDate;

    @Query(fieldName = "rate", op = Parameter.Operator.GTE)
    private Integer minRate;

    @Query(fieldName = "rate", op = Parameter.Operator.LTE)
    private Integer maxRate;

    @Query(fieldName = "important_level", op = Parameter.Operator.GTE)
    private Integer minImportantLevel;

    @Query(fieldName = "important_level", op = Parameter.Operator.LTE)
    private Integer maxImportantLevel;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.IN)
    private String inStatuses;

    private String dateQueryType;

    private String sortField;

    private String sortType;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    public void setInStatuses(List<DreamStatus> statuses) {
        if (statuses != null && !statuses.isEmpty()) {
            int n = statuses.size();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                DreamStatus ds = statuses.get(i);
                if (ds == null) {
                    //需要判断为空，否则界面上会下拉框选择器里面只选后面几个时，会传["","CREATED"]这种模式的数据
                    continue;
                }
                sb.append(statuses.get(i).getValue());
                if (i < n - 1) {
                    sb.append(",");
                }
            }
            this.inStatuses = sb.toString();
        }
    }

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

    public Integer getMinRate() {
        return minRate;
    }

    public void setMinRate(Integer minRate) {
        this.minRate = minRate;
    }

    public Integer getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(Integer maxRate) {
        this.maxRate = maxRate;
    }

    public Integer getMinImportantLevel() {
        return minImportantLevel;
    }

    public void setMinImportantLevel(Integer minImportantLevel) {
        this.minImportantLevel = minImportantLevel;
    }

    public Integer getMaxImportantLevel() {
        return maxImportantLevel;
    }

    public void setMaxImportantLevel(Integer maxImportantLevel) {
        this.maxImportantLevel = maxImportantLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInStatuses() {
        return inStatuses;
    }

    public void setInStatuses(String inStatuses) {
        this.inStatuses = inStatuses;
    }

    public String getDateQueryType() {
        return dateQueryType;
    }

    public void setDateQueryType(String dateQueryType) {
        this.dateQueryType = dateQueryType;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
