package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.pms.persistent.enums.PlanReportReStatCompareType;

public class PlanReportReStat {

    private String ids;
    private PlanReportReStatCompareType reStatCompareType;
    private Integer year;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public PlanReportReStatCompareType getReStatCompareType() {
        return reStatCompareType;
    }

    public void setReStatCompareType(PlanReportReStatCompareType reStatCompareType) {
        this.reStatCompareType = reStatCompareType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
