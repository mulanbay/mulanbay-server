package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 锻炼里程碑
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "sport_milestone")
public class SportMilestone implements Serializable {
    private static final long serialVersionUID = 8614289867949669013L;
    private Long id;
    private String name;
    private String alais;
    private Long userId;
    private SportType sportType;
    private Double kilometres;
    private Integer minutes;
    private SportExercise sportExercise;
    private Date fromExerciseDate;
    private Date toExerciseDate;
    private Integer costDays;
    private Short orderIndex;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "alais")
    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
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

    @ManyToOne
    @JoinColumn(name = "sport_exercise_id")
    public SportExercise getSportExercise() {
        return sportExercise;
    }

    public void setSportExercise(SportExercise sportExercise) {
        this.sportExercise = sportExercise;
    }

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "from_exercise_date")
    public Date getFromExerciseDate() {
        return fromExerciseDate;
    }

    public void setFromExerciseDate(Date fromExerciseDate) {
        this.fromExerciseDate = fromExerciseDate;
    }

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "to_exercise_date")
    public Date getToExerciseDate() {
        return toExerciseDate;
    }

    public void setToExerciseDate(Date toExerciseDate) {
        this.toExerciseDate = toExerciseDate;
    }

    @Basic
    @Column(name = "cost_days")
    public Integer getCostDays() {
        return costDays;
    }

    public void setCostDays(Integer costDays) {
        this.costDays = costDays;
    }

    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
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
