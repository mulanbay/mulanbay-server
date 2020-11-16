package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.TreatTestResult;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 检测报告
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "treat_test")
public class TreatTest implements java.io.Serializable {
    private static final long serialVersionUID = 1598040961232804958L;
    private Long id;
    private TreatOperation treatOperation;
    private Long userId;
    private String name;
    private String unit;
    //检查结果
    private String value;
    //如果检查结果是数字类的minValue，maxValue为参考范围的最小最大值
    private Double minValue;
    private Double maxValue;
    //如果检查结果是普通的汉字类，则referScope为参考范围
    private String referScope;
    private Date testDate;
    //结果:-1 过低，0正常 1 过高
    private TreatTestResult result;
    //分类/试验方法
    private String typeName;
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

    @ManyToOne
    @JoinColumn(name = "treat_operation_id")
    public TreatOperation getTreatOperation() {
        return treatOperation;
    }

    public void setTreatOperation(TreatOperation treatOperation) {
        this.treatOperation = treatOperation;
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
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "min_value")
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @Basic
    @Column(name = "max_value")
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "refer_scope")
    public String getReferScope() {
        return referScope;
    }

    public void setReferScope(String referScope) {
        this.referScope = referScope;
    }

    @Basic
    @Column(name = "test_date")
    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Basic
    @Column(name = "result")
    public TreatTestResult getResult() {
        return result;
    }

    public void setResult(TreatTestResult result) {
        this.result = result;
    }

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    @Transient
    public String getResultName() {
        return result == null ? null : result.getName();
    }
}
