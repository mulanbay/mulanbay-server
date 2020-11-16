package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.PlanStatResultType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 计划报告
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "plan_report")
public class PlanReport implements java.io.Serializable {

    private static final long serialVersionUID = -2499080978810285317L;

    private Long id;
    private Long userId;
    private String name;
    //业务日期索引:如201701（2017一月份），2017，201728（第28周）
    private Integer bussDay;
    //业务日期
    private Date bussStatDate;
    private UserPlan userPlan;
    //次数值
    private Long reportCountValue;
    //值，比如购买金额，锻炼时间
    private Long reportValue;
    //计算出来的实际值
    private PlanStatResultType countValueStatResult;
    private PlanStatResultType valueStatResult;
    //下面两个冗余，因为不同时期的值会不一样（计划的值）
    //次数值
    private Long planCountValue;
    //值，比如购买金额，锻炼时间
    private Long planValue;
    //数据参考值的年份
    private Integer planConfigYear;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "buss_day")
    public Integer getBussDay() {
        return bussDay;
    }

    public void setBussDay(Integer bussDay) {
        this.bussDay = bussDay;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "buss_stat_date", length = 10)
    public Date getBussStatDate() {
        return bussStatDate;
    }

    public void setBussStatDate(Date bussStatDate) {
        this.bussStatDate = bussStatDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_plan_id")
    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    @Basic
    @Column(name = "report_count_value")
    public Long getReportCountValue() {
        return reportCountValue;
    }

    public void setReportCountValue(Long reportCountValue) {
        this.reportCountValue = reportCountValue;
    }

    @Basic
    @Column(name = "report_value")
    public Long getReportValue() {
        return reportValue;
    }

    public void setReportValue(Long reportValue) {
        this.reportValue = reportValue;
    }

    @Basic
    @Column(name = "count_value_stat_result")
    public PlanStatResultType getCountValueStatResult() {
        return countValueStatResult;
    }

    public void setCountValueStatResult(PlanStatResultType countValueStatResult) {
        this.countValueStatResult = countValueStatResult;
    }

    @Basic
    @Column(name = "value_stat_result")
    public PlanStatResultType getValueStatResult() {
        return valueStatResult;
    }

    public void setValueStatResult(PlanStatResultType valueStatResult) {
        this.valueStatResult = valueStatResult;
    }

    @Basic
    @Column(name = "plan_count_value")
    public Long getPlanCountValue() {
        return planCountValue;
    }

    public void setPlanCountValue(Long planCountValue) {
        this.planCountValue = planCountValue;
    }

    @Basic
    @Column(name = "plan_value")
    public Long getPlanValue() {
        return planValue;
    }

    public void setPlanValue(Long planValue) {
        this.planValue = planValue;
    }

    @Basic
    @Column(name = "plan_config_year")
    public Integer getPlanConfigYear() {
        return planConfigYear;
    }

    public void setPlanConfigYear(Integer planConfigYear) {
        this.planConfigYear = planConfigYear;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 判断是个没有计划配置值
     *
     * @return
     */
    public boolean checkPlanValuesEmpty() {
        if (planConfigYear == null || planCountValue == null || planValue == null) {
            return true;
        } else {
            return false;
        }
    }

}
