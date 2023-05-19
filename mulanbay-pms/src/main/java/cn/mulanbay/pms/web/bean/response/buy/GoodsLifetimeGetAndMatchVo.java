package cn.mulanbay.pms.web.bean.response.buy;

import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.handler.bean.GoodsLifetimeMatchBean;
import cn.mulanbay.pms.persistent.domain.GoodsLifetime;

/**
 * @author fenghong
 * @title: GoodsLifetimeGetAndMatchVo
 * @description: TODO
 * @date 2023/5/19 10:51
 */
public class GoodsLifetimeGetAndMatchVo {

    private PageResult<GoodsLifetime> configs;

    private GoodsLifetimeMatchBean match;

    public PageResult<GoodsLifetime> getConfigs() {
        return configs;
    }

    public void setConfigs(PageResult<GoodsLifetime> configs) {
        this.configs = configs;
    }

    public GoodsLifetimeMatchBean getMatch() {
        return match;
    }

    public void setMatch(GoodsLifetimeMatchBean match) {
        this.match = match;
    }
}
