package cn.mulanbay.pms.web.bean.request;

import cn.mulanbay.web.bean.request.PageSearch;

/**
 * 运动里程碑刷新
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class SportMilestoneRefresh extends PageSearch {

    private Integer sportTypeId;

    private boolean reInit;

    public Integer getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(Integer sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public boolean isReInit() {
        return reInit;
    }

    public void setReInit(boolean reInit) {
        this.reInit = reInit;
    }
}
