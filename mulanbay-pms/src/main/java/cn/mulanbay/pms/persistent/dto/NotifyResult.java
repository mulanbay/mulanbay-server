package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.domain.NotifyConfig;
import cn.mulanbay.pms.persistent.domain.UserNotify;
import cn.mulanbay.pms.persistent.enums.NotifyType;
import cn.mulanbay.pms.persistent.enums.ResultType;
import cn.mulanbay.pms.persistent.enums.ValueType;

import java.io.Serializable;
import java.util.Date;

public class NotifyResult implements Serializable {

    private static final long serialVersionUID = 2561383920772533581L;

    private int id;

    private String name;

    private Date dateValue;

    private Long numValue;

    private UserNotify userNotify;

    private long compareValue = 0L;

    private long overWarningValue = 0L;

    private long overAlertValue = 0L;

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Long getNumValue() {
        return numValue;
    }

    public void setNumValue(Long numValue) {
        this.numValue = numValue;
    }

    public UserNotify getUserNotify() {
        return userNotify;
    }

    public void setUserNotify(UserNotify userNotify) {
        this.userNotify = userNotify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompareValue(long compareValue) {
        this.compareValue = compareValue;
    }

    public void setOverWarningValue(long overWarningValue) {
        this.overWarningValue = overWarningValue;
    }

    public void setOverAlertValue(long overAlertValue) {
        this.overAlertValue = overAlertValue;
    }

    /**
     * 计算
     */
    public void calculte() {
        NotifyConfig notifyConfig = userNotify.getNotifyConfig();
        //计算比较值
        if (notifyConfig.getResultType() == ResultType.DATE || notifyConfig.getResultType() == ResultType.NAME_DATE) {
            if (dateValue == null) {
                //没有数据
                return;
            }
            long v = (System.currentTimeMillis() - this.getDateValue().getTime()) / 1000;
            if (notifyConfig.getValueType() == ValueType.DAY) {
                this.compareValue = v / (3600 * 24);
            } else if (notifyConfig.getValueType() == ValueType.HOUR) {
                this.compareValue = v / 3600;
            } else if (notifyConfig.getValueType() == ValueType.MINUTE) {
                this.compareValue = v / 60;
            }
        } else if (notifyConfig.getResultType() == ResultType.NUMBER || notifyConfig.getResultType() == ResultType.NAME_NUMBER) {
            this.compareValue = this.getNumValue();
        } else {
            //
        }
        //超过warning的值，告警类与统计类的逻辑正好相反
        if (notifyConfig.getNotifyType() == NotifyType.LESS) {
            this.overWarningValue = getCompareValue() - userNotify.getWarningValue();
        } else {
            this.overWarningValue = userNotify.getWarningValue() - getCompareValue();
        }
        // 超过alert的值
        if (notifyConfig.getNotifyType() == NotifyType.LESS) {
            this.overAlertValue = getCompareValue() - userNotify.getAlertValue();
        } else {
            this.overAlertValue = userNotify.getAlertValue() - getCompareValue();
        }
    }

    /**
     * 超过warning的值
     * 告警类与统计类的逻辑正好相反
     *
     * @return
     */
    public long getOverWarningValue() {
        return this.overWarningValue;
    }

    /**
     * 超过alert的值
     *
     * @return
     */
    public long getOverAlertValue() {
        return this.overAlertValue;
    }

    /**
     * 比较值获取
     *
     * @return
     */
    public long getCompareValue() {
        return this.compareValue;
    }

    /**
     * 获取结果值
     *
     * @return
     */
    public String getResultValue() {
        NotifyConfig notifyConfig = userNotify.getNotifyConfig();
        if (notifyConfig.getResultType() == ResultType.DATE) {
            return DateUtil.getFormatDate(this.dateValue, DateUtil.FormatDay1);
        } else if (notifyConfig.getResultType() == ResultType.NUMBER) {
            return this.getNumValue().toString();
        } else if (notifyConfig.getResultType() == ResultType.NAME_DATE) {
            return "" + this.getName() + DateUtil.getFormatDate(this.dateValue, DateUtil.FormatDay1);
        } else if (notifyConfig.getResultType() == ResultType.NUMBER) {
            return "" + this.getName() + this.getNumValue().toString();
        } else {
            return null;
        }
    }


}
