package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 消费类型（人生经历管理模块）
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "consume_type")
public class ConsumeType implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 8575214853538973501L;
    private Integer id;
    private String name;
    private Long userId;
    private CommonStatus status;
    private Short orderIndex;
    // 是否加入统计
    private Boolean statable;

    // Constructors

    /**
     * default constructor
     */
    public ConsumeType() {
    }

    public ConsumeType(String name, CommonStatus status, Short orderIndex) {
        super();
        this.name = name;
        this.status = status;
        this.orderIndex = orderIndex;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "status", nullable = false)
    public CommonStatus getStatus() {
        return this.status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }


    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "statable")
    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

}