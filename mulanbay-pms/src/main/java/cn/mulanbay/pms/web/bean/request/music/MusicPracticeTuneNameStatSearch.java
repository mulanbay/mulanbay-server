package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.QueryBuilder;

import javax.validation.constraints.NotNull;

public class MusicPracticeTuneNameStatSearch extends QueryBuilder implements BindUser {

    @NotNull(message = "{validate.musicPracticeTune.id.NotNull}")
    private Long id;

    private Long userId;

    /**
     * 如果是全部乐器，则统计曲子时不区分乐器类别
     */
    private Boolean allMi;

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

    public Boolean getAllMi() {
        return allMi;
    }

    public void setAllMi(Boolean allMi) {
        this.allMi = allMi;
    }
}
