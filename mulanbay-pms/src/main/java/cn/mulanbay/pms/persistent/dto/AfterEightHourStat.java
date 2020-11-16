package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class AfterEightHourStat implements DateStat {

    private String name;

    private String groupName;

    private Integer indexValue;

    private Object totalCount;

    //分钟为单位
    private BigDecimal totalTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public Object getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public long getTotalCountValue() {
        if (totalCount != null) {
            return Long.valueOf(totalCount.toString());
        } else {
            return 0L;
        }
    }
}
