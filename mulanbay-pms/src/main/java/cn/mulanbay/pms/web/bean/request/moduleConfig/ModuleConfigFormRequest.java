package cn.mulanbay.pms.web.bean.request.moduleConfig;

import cn.mulanbay.pms.persistent.enums.MLAlgorithm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ModuleConfigFormRequest  {

    private Long id;
    /**
     * 名称
     */
    @NotEmpty(message = "{validate.moduleConfig.name.notEmpty}")
    private String name;
    /**
     * 唯一标识代码
     */
    @NotEmpty(message = "{validate.moduleConfig.code.notEmpty}")
    private String code;

    /**
     * 文件名
     */
    @NotEmpty(message = "{validate.moduleConfig.fileName.notEmpty}")
    private String fileName;

    /**
     * 是否区分用户
     */
    @NotNull(message = "{validate.moduleConfig.du.NotNull}")
    private Boolean du;

    /**
     * 算法
     */
    @NotNull(message = "{validate.moduleConfig.algorithm.NotNull}")
    private MLAlgorithm algorithm;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getDu() {
        return du;
    }

    public void setDu(Boolean du) {
        this.du = du;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MLAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(MLAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
