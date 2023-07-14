package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.BookLanguage;
import cn.mulanbay.pms.persistent.enums.BookSource;
import cn.mulanbay.pms.persistent.enums.BookType;
import cn.mulanbay.pms.persistent.enums.ReadingStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 阅读记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "reading_record")
public class ReadingRecord implements java.io.Serializable {
    private static final long serialVersionUID = -6674046076514845193L;
    private Long id;
    private Long userId;
    private BookCategory bookCategory;
    private String bookName;
    private String author;
    private String isbn;
    //出版日期
    private Integer publishedYear;
    //出版社
    private String press;
    private Country country;
    private BookType bookType;
    private BookLanguage language;
    // 评分(0-5)
    private Double score;
    private Date proposedDate;
    private Date beginDate;
    private Date finishedDate;
    //保存日期：如购入、借入
    private Date storeDate;
    private ReadingStatus status;
    private BookSource source;
    // 是否二手
    private Boolean secondhand;
    //读完花费时间
    private Integer costDays;
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
    @JoinColumn(name = "book_category_id")
    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Basic
    @Column(name = "book_name")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "published_year")
    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Basic
    @Column(name = "press")
    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    @ManyToOne
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Basic
    @Column(name = "book_type")
    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    @Basic
    @Column(name = "language")
    public BookLanguage getLanguage() {
        return language;
    }

    public void setLanguage(BookLanguage language) {
        this.language = language;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "secondhand")
    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "proposed_date")
    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "finished_date")
    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "store_date")
    public Date getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(Date storeDate) {
        this.storeDate = storeDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "status")
    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "source")
    public BookSource getSource() {
        return source;
    }

    public void setSource(BookSource source) {
        this.source = source;
    }

    @Basic
    @Column(name = "cost_days")
    public Integer getCostDays() {
        return costDays;
    }

    public void setCostDays(Integer costDays) {
        this.costDays = costDays;
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

    @Transient
    public String getStatusName() {
        if (this.status != null) {
            return this.status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getLanguageName() {
        if (this.language != null) {
            return this.language.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getBookTypeName() {
        if (this.bookType != null) {
            return this.bookType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getSourceName() {
        return this.source==null ? null:source.getName();
    }
}
