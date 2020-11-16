package cn.mulanbay.pms.web.bean.request;

import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 同期对比查询基类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class BaseYoyStatSearch extends QueryBuilder {

    private DateGroupType dateGroupType;

    @NotNull(message = "{validate.stat.yoy.years.size.notValid}")
    @Size(min = 2, message = "{validate.stat.yoy.years.size.notValid}")
    private List<Integer> years;

    private GroupType groupType;

    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public List<Integer> getYears() {
        if (years != null) {
            //界面中可能传入空的year
            years.remove(null);
        }
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }
}
