package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MusicInstrumentFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.musicInstrument.name.notEmpty}")
    private String name;

    private Long userId;

    @NotNull(message = "{validate.musicInstrument.orderIndex.NotNull}")
    private Short orderIndex;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
