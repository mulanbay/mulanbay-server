package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 阅读详情
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "reading_record_detail")
public class ReadingRecordDetail implements java.io.Serializable {
    private static final long serialVersionUID = -178426399345959964L;
    private Long id;
    private Long userId;
    private ReadingRecord readingRecord;
    //阅读日期
    private Date readTime;
    private Integer minutes;
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
    @JoinColumn(name = "reading_record_id")
    public ReadingRecord getReadingRecord() {
        return readingRecord;
    }

    public void setReadingRecord(ReadingRecord readingRecord) {
        this.readingRecord = readingRecord;
    }

    @Basic
    @Column(name = "read_time")
    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
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
