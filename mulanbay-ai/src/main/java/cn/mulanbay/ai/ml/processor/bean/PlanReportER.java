package cn.mulanbay.ai.ml.processor.bean;

/**
 * 用户计划结果
 *
 * @author fenghong
 * @create 2023-09-02
 */
public class PlanReportER {

    /**
     * 次数的比例
     * 值范围[0-无穷]
     */
    private Double countRate;

    /**
     * 值的比例
     * 值范围[0-无穷]
     */
    private Double valueRate;

    public Double getCountRate() {
        return countRate;
    }

    public void setCountRate(Double countRate) {
        this.countRate = countRate;
    }

    public Double getValueRate() {
        return valueRate;
    }

    public void setValueRate(Double valueRate) {
        this.valueRate = valueRate;
    }
}
