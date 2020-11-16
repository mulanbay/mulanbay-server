package cn.mulanbay.pms.web.bean.response.auth;

import cn.mulanbay.pms.persistent.enums.AuthType;

public class SecAuthInfoResponse {

    private AuthType secAuthType;

    private String address;

    public AuthType getSecAuthType() {
        return secAuthType;
    }

    public void setSecAuthType(AuthType secAuthType) {
        this.secAuthType = secAuthType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecAuthTypeName() {
        if (secAuthType == null) {
            return null;
        } else {
            return secAuthType.getName();
        }
    }
}
