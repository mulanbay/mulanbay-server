package cn.mulanbay.pms.persistent.dto;

import java.io.Serializable;
import java.util.Date;

public class CalendarLogDto implements Serializable {

    private static final long serialVersionUID = 2561383920772533581L;

    private Date date;

    private String value;

    private String unit;

    private String name;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
