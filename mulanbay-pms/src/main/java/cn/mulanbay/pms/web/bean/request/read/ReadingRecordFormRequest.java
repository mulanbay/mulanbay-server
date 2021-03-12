package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.BookLanguage;
import cn.mulanbay.pms.persistent.enums.BookType;
import cn.mulanbay.pms.persistent.enums.ReadingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReadingRecordFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.readingRecord.bookCategoryId.NotNull}")
    private Long bookCategoryId;

    @NotEmpty(message = "{validate.readingRecord.bookName.notEmpty}")
    private String bookName;

    @NotEmpty(message = "{validate.readingRecord.author.notEmpty}")
    private String author;

    @NotEmpty(message = "{validate.readingRecord.isbn.notEmpty}")
    private String isbn;

    //初步日期
    @NotNull(message = "{validate.readingRecord.publishedYear.NotNull}")
    private Integer publishedYear;

    //出版社
    @NotEmpty(message = "{validate.readingRecord.press.notEmpty}")
    private String press;

    //国家
    @NotEmpty(message = "{validate.readingRecord.nation.notEmpty}")
    private String nation;

    @NotNull(message = "{validate.readingRecord.bookType.NotNull}")
    private BookType bookType;

    @NotNull(message = "{validate.readingRecord.language.NotNull}")
    private BookLanguage language;
    // 重要等级(0-5)

    //@NotNull(message = "{validate.readingRecord.importantLevel.NotNull}")
    private Double score;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.readingRecord.proposedDate.NotNull}")
    private Date proposedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishedDate;

    //保存日期：如购入、借入
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date storeDate;

    @NotNull(message = "{validate.readingRecord.status.NotNull}")
    private ReadingStatus status;
    //读完花费时间
    private Integer costDays;

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

    public Long getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(Long bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public BookLanguage getLanguage() {
        return language;
    }

    public void setLanguage(BookLanguage language) {
        this.language = language;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public Date getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(Date storeDate) {
        this.storeDate = storeDate;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    public Integer getCostDays() {
        return costDays;
    }

    public void setCostDays(Integer costDays) {
        this.costDays = costDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
