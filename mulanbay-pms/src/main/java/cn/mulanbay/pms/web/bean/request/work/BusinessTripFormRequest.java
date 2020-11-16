package cn.mulanbay.pms.web.bean.request.work;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;


public class BusinessTripFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.businessTrip.companyId.NotNull}")
    private Long companyId;

    @NotEmpty(message = "{validate.businessTrip.country.notEmpty}")
    private String country;

    @NotEmpty(message = "{validate.businessTrip.province.notEmpty}")
    private String province;

    @NotEmpty(message = "{validate.businessTrip.city.notEmpty}")
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.businessTrip.tripDate.NotNull}")
    private Date tripDate;

    @NotNull(message = "{validate.businessTrip.days.NotNull}")
    private Integer days;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
