package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.BestType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 锻炼记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "sport_exercise")
public class SportExercise implements java.io.Serializable {
    private static final long serialVersionUID = 6821542686263268133L;
    private Long id;
    private Long userId;
    private SportType sportType;
    private Date exerciseDate;
    private Double kilometres;
    private Integer minutes;
    private Double speed;
    private Double maxSpeed;
    private Double pace;
    private Double maxPace;
    private Integer maxHeartRate;
    private Integer averageHeartRate;
    private String remark;
    //里程最佳状态
    private BestType mileageBest;
    //速度最佳状态
    private BestType fastBest;
    private Date createdTime;
    private Date lastModifyTime;
    //最大安全心率，需要和exerciseDate一起计算
    private int safeMaxHeartRate;

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
    @JoinColumn(name = "sport_type_id")
    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    //后期修改带时分秒，因为需要详细统计
    //@Temporal(TemporalType.DATE)
    @Column(name = "exercise_date")
    public Date getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    @Basic
    @Column(name = "kilometres")
    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    @Basic
    @Column(name = "minutes")
    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Basic
    @Column(name = "speed")
    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "max_speed")
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Basic
    @Column(name = "pace")
    public Double getPace() {
        return pace;
    }

    public void setPace(Double pace) {
        this.pace = pace;
    }

    @Basic
    @Column(name = "max_pace")
    public Double getMaxPace() {
        return maxPace;
    }

    public void setMaxPace(Double maxPace) {
        this.maxPace = maxPace;
    }

    @Basic
    @Column(name = "max_heart_rate")
    public Integer getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(Integer maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    @Basic
    @Column(name = "average_heart_rate")
    public Integer getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(Integer averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
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
    @Column(name = "mileage_best")
    public BestType getMileageBest() {
        return mileageBest;
    }

    public void setMileageBest(BestType mileageBest) {
        this.mileageBest = mileageBest;
    }

    @Basic
    @Column(name = "fast_best")
    public BestType getFastBest() {
        return fastBest;
    }

    public void setFastBest(BestType fastBest) {
        this.fastBest = fastBest;
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

    public void setSafeMaxHeartRate(int safeMaxHeartRate) {
        this.safeMaxHeartRate = safeMaxHeartRate;
    }

    @Transient
    public int getSafeMaxHeartRate() {
        return safeMaxHeartRate;
    }
}
