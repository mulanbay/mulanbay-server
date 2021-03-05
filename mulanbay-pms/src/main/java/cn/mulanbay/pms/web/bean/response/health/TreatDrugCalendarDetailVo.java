package cn.mulanbay.pms.web.bean.response.health;

import java.util.Date;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/5
 */
public class TreatDrugCalendarDetailVo {

    private Long treatDrugId;
    private String name;
    private String disease;
    //每多少天一次
    private Integer perDay;
    private Integer perTimes;
    //每次食用的单位
    private String eu;
    //每次食用的量
    private Integer ec;
    private String useWay;
    private Long treatDrugDetailId;
    private Date occurTime;
    //用药时间和预期是否匹配
    private boolean match=true;
    public Long getTreatDrugId() {
        return treatDrugId;
    }

    public void setTreatDrugId(Long treatDrugId) {
        this.treatDrugId = treatDrugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getPerDay() {
        return perDay;
    }

    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

    public Integer getPerTimes() {
        return perTimes;
    }

    public void setPerTimes(Integer perTimes) {
        this.perTimes = perTimes;
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public Integer getEc() {
        return ec;
    }

    public void setEc(Integer ec) {
        this.ec = ec;
    }

    public String getUseWay() {
        return useWay;
    }

    public void setUseWay(String useWay) {
        this.useWay = useWay;
    }

    public Long getTreatDrugDetailId() {
        return treatDrugDetailId;
    }

    public void setTreatDrugDetailId(Long treatDrugDetailId) {
        this.treatDrugDetailId = treatDrugDetailId;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}
