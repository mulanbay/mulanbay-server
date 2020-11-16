package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 数据字典组
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "dict_group")
public class DictGroup implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 961922489014144054L;
    private Long id;
    private String name;
    private String code;
    private CommonStatus status;
    private Short orderIndex;

    // Constructors

    /**
     * default constructor
     */
    public DictGroup() {
    }

    public DictGroup(String name, CommonStatus status, Short orderIndex) {
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

    @Column(name = "code", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

}