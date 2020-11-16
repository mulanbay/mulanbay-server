package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 数据字典项
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "dict_item")
public class DictItem implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 961922489014144054L;
    private Long id;
    private DictGroup group;
    private String name;
    //子分类使用，可为空
    private String code;
    private CommonStatus status;
    private Short orderIndex;

    // Constructors

    /**
     * default constructor
     */
    public DictItem() {
    }

    public DictItem(String name, CommonStatus status, Short orderIndex) {
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

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    public DictGroup getGroup() {
        return group;
    }

    public void setGroup(DictGroup group) {
        this.group = group;
    }

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", nullable = false, length = 45)
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