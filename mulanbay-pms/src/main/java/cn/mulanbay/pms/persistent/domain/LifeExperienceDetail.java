package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 人生经历每天的明细
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "life_experience_detail")
public class LifeExperienceDetail implements java.io.Serializable {
    private static final long serialVersionUID = 1618796530128502244L;
    private Long id;
    private Long userId;
    private LifeExperience lifeExperience;
    private Integer countryId;
    private String countryLocation;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private Date occurDate;
    private String startCity;
    //出发城市地理位置
    private String scLocation;
    private String arriveCity;
    //抵达城市地理位置
    private String acLocation;
    private Double cost;
    //是否加入到地图的绘制
    private Boolean mapStat;
    //国际线路
    private Boolean international;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "life_experience_id", nullable = true)
    public LifeExperience getLifeExperience() {
        return lifeExperience;
    }

    public void setLifeExperience(LifeExperience lifeExperience) {
        this.lifeExperience = lifeExperience;
    }

    @Basic
    @Column(name = "country_id")
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "country_location")
    public String getCountryLocation() {
        return countryLocation;
    }

    public void setCountryLocation(String countryLocation) {
        this.countryLocation = countryLocation;
    }

    @Basic
    @Column(name = "province_id")
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Basic
    @Column(name = "city_id")
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "district_id")
    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "occur_date", length = 10)
    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    @Basic
    @Column(name = "start_city")
    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    @Basic
    @Column(name = "sc_location")
    public String getScLocation() {
        return scLocation;
    }

    public void setScLocation(String scLocation) {
        this.scLocation = scLocation;
    }

    @Basic
    @Column(name = "arrive_city")
    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    @Basic
    @Column(name = "ac_location")
    public String getAcLocation() {
        return acLocation;
    }

    public void setAcLocation(String acLocation) {
        this.acLocation = acLocation;
    }

    @Basic
    @Column(name = "cost")
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "map_stat")
    public Boolean getMapStat() {
        return mapStat;
    }

    public void setMapStat(Boolean mapStat) {
        this.mapStat = mapStat;
    }

    @Basic
    @Column(name = "international")
    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
