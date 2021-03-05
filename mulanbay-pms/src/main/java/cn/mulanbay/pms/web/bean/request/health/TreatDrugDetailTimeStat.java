package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class TreatDrugDetailTimeStat implements BindUser {

    private Long treatDrugId;

    private Date startDate;

    private Date endDate;

    private Long userId;

    /**
     * 是否合并相同的药名
     */
    private boolean mergeSameName;

    public Long getTreatDrugId() {
        return treatDrugId;
    }

    public void setTreatDrugId(Long treatDrugId) {
        this.treatDrugId = treatDrugId;
    }

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

    public boolean isMergeSameName() {
        return mergeSameName;
    }

    public void setMergeSameName(boolean mergeSameName) {
        this.mergeSameName = mergeSameName;
    }
}
