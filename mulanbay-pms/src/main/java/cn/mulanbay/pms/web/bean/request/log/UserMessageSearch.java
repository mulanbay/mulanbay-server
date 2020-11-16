package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.MessageSendStatus;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class UserMessageSearch extends PageSearch implements FullEndDateTime {

    @Query(fieldName = "title,content", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "createdTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "createdTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "sendStatus", op = Parameter.Operator.EQ)
    private MessageSendStatus sendStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public MessageSendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(MessageSendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }
}
