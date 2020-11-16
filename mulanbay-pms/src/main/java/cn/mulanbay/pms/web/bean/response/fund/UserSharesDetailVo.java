package cn.mulanbay.pms.web.bean.response.fund;

import cn.mulanbay.pms.handler.bean.SharesMonitorBean;
import cn.mulanbay.pms.persistent.domain.UserShares;

public class UserSharesDetailVo extends UserShares {

    private double currentPrice = -1.0;

    //价格获取距离现在的秒数
    private long priceGetFromNow;

    //价格监控
    private SharesMonitorBean smb;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public long getPriceGetFromNow() {
        return priceGetFromNow;
    }

    public void setPriceGetFromNow(long priceGetFromNow) {
        this.priceGetFromNow = priceGetFromNow;
    }

    public SharesMonitorBean getSmb() {
        return smb;
    }

    public void setSmb(SharesMonitorBean smb) {
        this.smb = smb;
    }
}
