package cn.mulanbay.pms.web.bean.response.fund;

import java.util.ArrayList;
import java.util.List;

public class UserSharesWarnVo {

    private List<UserSharesWarnDetailVo> list = new ArrayList<>();

    public List<UserSharesWarnDetailVo> getList() {
        return list;
    }

    public void setList(List<UserSharesWarnDetailVo> list) {
        this.list = list;
    }

    /**
     * 增加
     *
     * @param indexValue
     * @param warnType
     * @param vv
     */
    public void addData(String indexValue, int warnType, int vv) {
        UserSharesWarnDetailVo us = this.getDetail(indexValue);
        us.addValue(warnType, vv);
    }

    private UserSharesWarnDetailVo getDetail(String indexValue) {
        for (UserSharesWarnDetailVo us : list) {
            if (us.getIndexValue().equals(indexValue)) {
                return us;
            }
        }
        UserSharesWarnDetailVo us = new UserSharesWarnDetailVo();
        us.setIndexValue(indexValue);
        list.add(us);
        return us;
    }
}
