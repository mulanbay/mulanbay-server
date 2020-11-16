package cn.mulanbay.pms.web.bean.request.common;

import cn.mulanbay.pms.persistent.enums.EnumIdType;

public class GetEnumTreeRequest {

    private Boolean needRoot;

    private String enumClass;

    private EnumIdType idType;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public String getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(String enumClass) {
        this.enumClass = enumClass;
    }

    public EnumIdType getIdType() {
        return idType;
    }

    public void setIdType(EnumIdType idType) {
        this.idType = idType;
    }

}
