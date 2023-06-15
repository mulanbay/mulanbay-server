package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 商品类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "goods_type")
public class GoodsType implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 8575214853538973501L;
    private Integer id;
    private Long userId;
    private GoodsType parent;
    private String name;
    private String behaviorName;
    private CommonStatus status;
    private Short orderIndex;
    // 是否加入统计
    private Boolean statable;
    //标签，目前用于AI自动匹配
    private String tags;
    // Constructors

    /**
     * default constructor
     */
    public GoodsType() {
    }

    public GoodsType(String name, CommonStatus status, Short orderIndex) {
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

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pid", nullable = true)
    public GoodsType getParent() {
        return parent;
    }

    public void setParent(GoodsType parent) {
        this.parent = parent;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "behavior_name", nullable = false, length = 32)
    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
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

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        } else {
            return null;
        }
    }

    @Transient
    public Integer getParentId() {
        if (parent != null) {
            return parent.getId();
        } else {
            return null;
        }
    }

}