package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 国家
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "country")
public class Country implements java.io.Serializable {

    private static final long serialVersionUID = -7957057082541835L;
    private Integer id;
    /**
     * 中文名称
     */
    private String cnName;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 两位英文代码
     */
    private String enCode2;
    /**
     * 三位英文代码
     */
    private String enCode3;
    /**
     * 数字编号
     */
    private String code;

    /**
     * 地理坐标，经纬度
     */
    private String location;

    private Short orderIndex;

    private CommonStatus status;

    private String remark;

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

    @Column(name = "cn_name", nullable = false, length = 32)
    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Column(name = "en_name")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "en_code2")
    public String getEnCode2() {
        return enCode2;
    }

    public void setEnCode2(String enCode2) {
        this.enCode2 = enCode2;
    }

    @Column(name = "en_code3")
    public String getEnCode3() {
        return enCode3;
    }

    public void setEnCode3(String enCode3) {
        this.enCode3 = enCode3;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "status", nullable = false)
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 默认名称
     * @return
     */
    @Transient
    public String getText() {
        return cnName;
    }
}
