package cn.mulanbay.pms.web.bean.request.data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DatabaseManualCleanRequest {

    @NotNull(message = "{validate.database.clean.id.NotNull}")
    private Long id;

    @Min(value = 1, message = "{validate.database.clean.days.Min}")
    private Integer days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
