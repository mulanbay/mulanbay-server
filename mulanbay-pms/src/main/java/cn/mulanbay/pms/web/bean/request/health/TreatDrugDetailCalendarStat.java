package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DateGroupType;

public class TreatDrugDetailCalendarStat implements BindUser {

    private Long treatDrugId;

    private Integer year;

    private String month;

    private DateGroupType dateGroupType;

    private Long userId;

    public Long getTreatDrugId() {
        return treatDrugId;
    }

    public void setTreatDrugId(Long treatDrugId) {
        this.treatDrugId = treatDrugId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
