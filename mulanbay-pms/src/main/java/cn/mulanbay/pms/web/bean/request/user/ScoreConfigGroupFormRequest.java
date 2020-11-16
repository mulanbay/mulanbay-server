package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ScoreConfigGroupFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.scoreConfigGroup.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.scoreConfigGroup.code.NotEmpty}")
    private String code;

    @NotNull(message = "{validate.scoreConfigGroup.status.NotNull}")
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
