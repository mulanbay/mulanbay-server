package cn.mulanbay.pms.web.bean.request.system;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SystemConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.systemConfig.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.systemConfig.code.NotEmpty}")
    private String code;

    @NotEmpty(message = "{validate.systemConfig.configValue.NotEmpty}")
    private String configValue;

    @NotNull(message = "{validate.systemConfig.status.NotNull}")
    private CommonStatus status;

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

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
