package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class TreatDrugDetailCTFormRequest implements BindUser {

    private Long userId;

    private Date templateDate;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(Date templateDate) {
        this.templateDate = templateDate;
    }

}
