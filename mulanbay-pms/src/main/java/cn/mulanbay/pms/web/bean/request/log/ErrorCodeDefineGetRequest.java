package cn.mulanbay.pms.web.bean.request.log;

import javax.validation.constraints.NotNull;

public class ErrorCodeDefineGetRequest {

    @NotNull(message = "{validate.errorCodeDefine.code.notNull}")
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
