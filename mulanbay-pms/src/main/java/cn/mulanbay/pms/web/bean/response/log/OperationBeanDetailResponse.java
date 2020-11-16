package cn.mulanbay.pms.web.bean.response.log;

public class OperationBeanDetailResponse {

    private String beanName;

    private String idValue;

    private Object beanData;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public Object getBeanData() {
        return beanData;
    }

    public void setBeanData(Object beanData) {
        this.beanData = beanData;
    }
}
