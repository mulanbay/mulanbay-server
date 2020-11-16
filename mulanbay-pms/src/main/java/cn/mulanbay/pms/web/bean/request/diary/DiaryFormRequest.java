package cn.mulanbay.pms.web.bean.request.diary;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DiaryFormRequest implements BindUser {

    private Long id;

    private Long userId;

    //该年份第一天，方便以后统计使用
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.diary.firstDay.NotNull}")
    private Date firstDay;

    //年份
    @NotNull(message = "{validate.diary.year.NotNull}")
    private Integer year;

    //字数
    @NotNull(message = "{validate.diary.words.NotNull}")
    private Integer words;

    //篇数
    @NotNull(message = "{validate.diary.pieces.NotNull}")
    private Integer pieces;

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

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWords() {
        return words;
    }

    public void setWords(Integer words) {
        this.words = words;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
