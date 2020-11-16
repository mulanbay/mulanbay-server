package cn.mulanbay.schedule;

import java.util.Date;

/**
 * 手动任务的配置包装类, 用于在初始化和调用过程中统一配置, 这样就不需要在方法中写太多的参数
 * @author fenghong
 * @create 2018-01-03 21:45
 **/
public class ManualScheduleConfig {

    private Type type = Type.MANUAL;

    private int repeatCount;

    private Date startTime;

    private Date bussDay; //businessDay, 执行日期

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getBussDay() {
        return bussDay;
    }

    public void setBussDay(Date bussDay) {
        this.bussDay = bussDay;
    }

    public enum Type {

        MANUAL("人工触发","manual"),
        MANUAL_REDO("人工重做","manual_redo"),
        AUTO_REDO("自动重做","auto_redo");

        private String displayName;
        private String value;

        private Type(String displayName, String value) {
            this.displayName = displayName;
            this.value = value;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.displayName;
        }
    }

}
