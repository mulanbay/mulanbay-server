package cn.mulanbay.ai.ml.processor.bean;

/**
 * 预算和消费结果
 *
 * @author fenghong
 * @create 2023-09-02
 */
public class BudgetConsumeER {

    /**
     * 只包含普通消费的比例
     * 值范围[0-1]
     */
    private Double ncRate;

    /**
     * 同时包含突发消费的比例
     * 值范围[0-1]
     */
    private Double bcRate;

    public Double getNcRate() {
        return ncRate;
    }

    public void setNcRate(Double ncRate) {
        this.ncRate = ncRate;
    }

    public Double getBcRate() {
        return bcRate;
    }

    public void setBcRate(Double bcRate) {
        this.bcRate = bcRate;
    }
}
