package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.MLAlgorithm;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 机器学习模型配置
 *
 * @author fenghong
 * @create 2020-08-27 18:44
 */
@Entity
@Table(name = "model_config")
public class ModelConfig implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 唯一标识代码
     */
    private String code;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 是否区分用户
     */
    private Boolean du;
    /**
     * 算法
     */
    private MLAlgorithm algorithm;

    private CommonStatus status;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "du")
    public Boolean getDu() {
        return du;
    }

    public void setDu(Boolean du) {
        this.du = du;
    }


    @Basic
    @Column(name = "algorithm")
    public MLAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(MLAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Basic
    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
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
    public String getStatusName() {
        return status.getName();
    }


    @Transient
    public String getAlgorithmName() {
        return algorithm.getName();
    }
}
