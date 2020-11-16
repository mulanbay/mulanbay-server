package cn.mulanbay.pms.web.bean.request.work;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CompanyFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotEmpty(message = "{validate.company.name.notEmpty}")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.company.companyId.NotNull}")
    private Date entryDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date quitDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
