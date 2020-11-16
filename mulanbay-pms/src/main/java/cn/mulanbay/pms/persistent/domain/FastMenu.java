package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 快捷菜单
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "fast_menu")
public class FastMenu implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 961922489014144054L;
    private Long id;
    private Long userId;
    private Long functionId;
    private Short orderIndex;

    // Constructors

    /**
     * default constructor
     */
    public FastMenu() {
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

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "function_id", nullable = false)
    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

}