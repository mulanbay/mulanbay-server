package cn.mulanbay.pms.web.bean.request.handler;

import javax.validation.constraints.NotEmpty;

public class HandClassRequest {

    @NotEmpty(message = "{validate.handler.className.NotEmpty}")
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
