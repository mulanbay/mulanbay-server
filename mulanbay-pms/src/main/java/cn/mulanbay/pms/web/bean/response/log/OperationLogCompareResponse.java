package cn.mulanbay.pms.web.bean.response.log;

import cn.mulanbay.pms.persistent.domain.OperationLog;

public class OperationLogCompareResponse {

    //业务的ID
    private String bussId;

    //最新的数据，业务数据表中的实时数据(json格式)（页面左边的数据）
    private Object latestData;

    //当前操作日记所记录的数据(json格式)，来源于paras字段，只针对edit类型有效，因为能找到业务ID(页面中间的数据)
    private OperationLog currentData;

    //参与比对的数据，比如比当前操作记录早或者迟的数据(json格式)，来源于paras字段，只针对edit类型有效，因为能找到业务ID（页面右边的数据）
    private OperationLog compareData;

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public Object getLatestData() {
        return latestData;
    }

    public void setLatestData(Object latestData) {
        this.latestData = latestData;
    }

    public OperationLog getCurrentData() {
        return currentData;
    }

    public void setCurrentData(OperationLog currentData) {
        this.currentData = currentData;
    }

    public OperationLog getCompareData() {
        return compareData;
    }

    public void setCompareData(OperationLog compareData) {
        this.compareData = compareData;
    }
}
