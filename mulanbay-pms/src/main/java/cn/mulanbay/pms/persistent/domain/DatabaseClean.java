package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CleanType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 数据库清理配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "database_clean")
public class DatabaseClean implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 961922489014144054L;
    private Long id;
    private String name;
    private String tableName;
    private String dateField;
    private Integer days;
    private CleanType cleanType;
    //额外条件
    private String extraCondition;
    private Date lastCleanTime;
    private Integer lastCleanCounts;
    private CommonStatus status;
    private Short orderIndex;
    private Date createdTime;
    private Date lastModifyTime;

    // Constructors

    /**
     * default constructor
     */
    public DatabaseClean() {
    }

    public DatabaseClean(String name, CommonStatus status, Short orderIndex) {
        super();
        this.name = name;
        this.status = status;
        this.orderIndex = orderIndex;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Column(name = "date_field")
    public String getDateField() {
        return dateField;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    @Column(name = "days")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Column(name = "clean_type")
    public CleanType getCleanType() {
        return cleanType;
    }

    public void setCleanType(CleanType cleanType) {
        this.cleanType = cleanType;
    }

    @Column(name = "extra_condition")
    public String getExtraCondition() {
        return extraCondition;
    }

    public void setExtraCondition(String extraCondition) {
        this.extraCondition = extraCondition;
    }

    @Column(name = "last_clean_time")
    public Date getLastCleanTime() {
        return lastCleanTime;
    }

    public void setLastCleanTime(Date lastCleanTime) {
        this.lastCleanTime = lastCleanTime;
    }

    @Column(name = "last_clean_counts")
    public Integer getLastCleanCounts() {
        return lastCleanCounts;
    }

    public void setLastCleanCounts(Integer lastCleanCounts) {
        this.lastCleanCounts = lastCleanCounts;
    }

    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Column(name = "order_index")
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "created_time", length = 19)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "last_modify_time", length = 19)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    @Transient
    public String getCleanTypeName() {
        if (cleanType != null) {
            return cleanType.getName();
        } else {
            return null;
        }
    }
}