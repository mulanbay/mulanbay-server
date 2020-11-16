package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.web.bean.request.PageSearch;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserScorePointsCompareSearch extends PageSearch implements BindUser {

    @NotNull(message = "{validate.user.startDate.NotNull}")
    private Date startDate;

    @NotNull(message = "{validate.user.endDate.NotNull}")
    private Date endDate;

    private Long userId;

    private DataType dataType;

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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public enum DataType {
        //相对和绝对
        OPPOSITE, ABSOLUTE
    }
}
