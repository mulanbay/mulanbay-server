package cn.mulanbay.pms.web.bean.response.user;

import cn.mulanbay.pms.persistent.enums.RewardSource;

public class UserRpRMessageContentResponse {

    private String url;

    private String para;

    private RewardSource rewardSource;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public RewardSource getRewardSource() {
        return rewardSource;
    }

    public void setRewardSource(RewardSource rewardSource) {
        this.rewardSource = rewardSource;
    }
}
