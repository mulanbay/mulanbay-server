package cn.mulanbay.pms.persistent.enums;

import cn.mulanbay.pms.persistent.domain.*;

/**
 * 日历来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UserCalendarSource {

    MANUAL(0, "手动",UserCalendar.class),
    NOTIFY(1, "提醒", UserNotify.class),
    PLAN(2, "计划", UserPlan.class),
    COMMON_RECORD(3, "通用", CommonRecord.class),
    BUDGET(4, "预算", Budget.class),
    TREAT_OPERATION(5, "手术", TreatOperation.class),
    TREAT_DRUG(6, "用药",TreatDrug.class),
    BUY_RECORD(7, "消费",BuyRecord.class);

    private int value;

    private String name;

    private Class beanClass;

    UserCalendarSource(int value, String name) {
        this.value = value;
        this.name = name;
    }

    UserCalendarSource(int value, String name, Class beanClass) {
        this.value = value;
        this.name = name;
        this.beanClass = beanClass;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public static UserCalendarSource getUserCalendarSource(int value) {
        for (UserCalendarSource rs : UserCalendarSource.values()) {
            if (rs.getValue() == value) {
                return rs;
            }
        }
        return null;
    }
}
