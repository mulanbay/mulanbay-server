package cn.mulanbay.pms.handler.bean;

import cn.mulanbay.pms.persistent.domain.GoodsLifetime;

import java.io.Serializable;

/**
 * 商品寿命匹配
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class GoodsLifetimeMatchBean extends GoodsLifetime implements Serializable {

    private static final long serialVersionUID = 5066451654516299863L;

    //匹配度
    private float match=0;

    public float getMatch() {
        return match;
    }

    public void setMatch(float match) {
        this.match = match;
    }

}
