package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class SharesMonitorJobPara extends AbstractTriggerPara {

    //跌的比较幅度（百分比）
    @JobParameter(name = "跌的比较幅度", dataType = Double.class, desc = "%", editType = EditType.NUMBER, scale = 1)
    private double failPercent = 5;

    //跌的监控次数
    @JobParameter(name = "跌的监控次数", dataType = Integer.class, desc = "次", editType = EditType.NUMBER)
    private int failCounts = 5;

    //涨的比较幅度（百分比）
    @JobParameter(name = "涨的比较幅度", dataType = Double.class, desc = "%", editType = EditType.NUMBER, scale = 1)
    private double gainPercent = 5;

    //涨的监控次数
    @JobParameter(name = "涨的监控次数", dataType = Integer.class, desc = "次", editType = EditType.NUMBER)
    private int gainCounts = 5;

    //缓存小时数
    @JobParameter(name = "缓存小时数", dataType = Integer.class, desc = "小时", editType = EditType.NUMBER)
    private int cacheHours = 24;

    //是否提醒评分警告
    @JobParameter(name = "是否提醒评分警告", editType = EditType.BOOLEAN,
            dataType = Boolean.class)
    private boolean notifyScoreWarn = false;

    //评分的最低值警告值
    @JobParameter(name = "评分的最低值警告值", dataType = Integer.class, desc = "分", editType = EditType.NUMBER)
    private int minWarnScore = 0;

    //评分的最高值警告值
    @JobParameter(name = "评分的最高值警告值", dataType = Integer.class, desc = "分", editType = EditType.NUMBER)
    private int maxWarnScore = 100;

    //股票上一次价格的缓存时间
    @JobParameter(name = "股票最近价格缓存时间", dataType = Integer.class, desc = "小时", editType = EditType.NUMBER)
    private int sharesLastPriceCacheHours = 48;

    //股票上一次价格的缓存时间
    @JobParameter(name = "大盘点数波动幅度", dataType = Integer.class, desc = "点", editType = EditType.NUMBER)
    private int indexPointRange = 150;

    //股票上一次价格的缓存时间
    @JobParameter(name = "最低换手率警告值", dataType = Integer.class, desc = "%", editType = EditType.NUMBER, scale = 1)
    private double minTurnOver = 2;

    //股票上一次价格的缓存时间
    @JobParameter(name = "最高换手率警告值", dataType = Integer.class, desc = "%", editType = EditType.NUMBER, scale = 1)
    private double maxTurnOver = 10;

    //是否提醒评分警告
    @JobParameter(name = "是否重置涨跌累计次数", editType = EditType.BOOLEAN,
            dataType = Boolean.class, desc = "提醒后是否重置累计涨跌次数")
    private boolean resetGf = false;

    //是否提醒评分警告
    @JobParameter(name = "是否需要提醒", editType = EditType.BOOLEAN,
            dataType = Boolean.class)
    private boolean notify = true;


    public double getFailPercent() {
        return failPercent;
    }

    public void setFailPercent(double failPercent) {
        this.failPercent = failPercent;
    }

    public int getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(int failCounts) {
        this.failCounts = failCounts;
    }

    public double getGainPercent() {
        return gainPercent;
    }

    public void setGainPercent(double gainPercent) {
        this.gainPercent = gainPercent;
    }

    public int getGainCounts() {
        return gainCounts;
    }

    public void setGainCounts(int gainCounts) {
        this.gainCounts = gainCounts;
    }

    public int getCacheHours() {
        return cacheHours;
    }

    public void setCacheHours(int cacheHours) {
        this.cacheHours = cacheHours;
    }

    public boolean isNotifyScoreWarn() {
        return notifyScoreWarn;
    }

    public void setNotifyScoreWarn(boolean notifyScoreWarn) {
        this.notifyScoreWarn = notifyScoreWarn;
    }

    public int getMinWarnScore() {
        return minWarnScore;
    }

    public void setMinWarnScore(int minWarnScore) {
        this.minWarnScore = minWarnScore;
    }

    public int getMaxWarnScore() {
        return maxWarnScore;
    }

    public void setMaxWarnScore(int maxWarnScore) {
        this.maxWarnScore = maxWarnScore;
    }

    public int getSharesLastPriceCacheHours() {
        return sharesLastPriceCacheHours;
    }

    public void setSharesLastPriceCacheHours(int sharesLastPriceCacheHours) {
        this.sharesLastPriceCacheHours = sharesLastPriceCacheHours;
    }

    public int getIndexPointRange() {
        return indexPointRange;
    }

    public void setIndexPointRange(int indexPointRange) {
        this.indexPointRange = indexPointRange;
    }

    public double getMinTurnOver() {
        return minTurnOver;
    }

    public void setMinTurnOver(double minTurnOver) {
        this.minTurnOver = minTurnOver;
    }

    public double getMaxTurnOver() {
        return maxTurnOver;
    }

    public void setMaxTurnOver(double maxTurnOver) {
        this.maxTurnOver = maxTurnOver;
    }

    public boolean isResetGf() {
        return resetGf;
    }

    public void setResetGf(boolean resetGf) {
        this.resetGf = resetGf;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
