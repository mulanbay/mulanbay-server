package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LifeExperienceDetailFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.businessTrip.lifeExperienceId.NotNull}")
    private Long lifeExperienceId;

    @NotEmpty(message = "{validate.lifeExperienceDetail.country.NotEmpty}")
    private String country;
    private String countryLocation;

    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.businessTrip.occurDate.NotNull}")
    private Date occurDate;

    @NotNull(message = "{validate.businessTrip.startCity.NotNull}")
    private String startCity;

    //出发城市地理位置
    @NotNull(message = "{validate.businessTrip.scLocation.NotNull}")
    private String scLocation;

    @NotNull(message = "{validate.businessTrip.arriveCity.NotNull}")
    private String arriveCity;

    //抵达城市地理位置
    @NotNull(message = "{validate.businessTrip.acLocation.NotNull}")
    private String acLocation;

    private Double cost;
    //是否加入到地图的绘制
    @NotNull(message = "{validate.businessTrip.mapStat.NotNull}")
    private Boolean mapStat;

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

    public Long getLifeExperienceId() {
        return lifeExperienceId;
    }

    public void setLifeExperienceId(Long lifeExperienceId) {
        this.lifeExperienceId = lifeExperienceId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryLocation() {
        return countryLocation;
    }

    public void setCountryLocation(String countryLocation) {
        this.countryLocation = countryLocation;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getScLocation() {
        return scLocation;
    }

    public void setScLocation(String scLocation) {
        this.scLocation = scLocation;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public String getAcLocation() {
        return acLocation;
    }

    public void setAcLocation(String acLocation) {
        this.acLocation = acLocation;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getMapStat() {
        return mapStat;
    }

    public void setMapStat(Boolean mapStat) {
        this.mapStat = mapStat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
