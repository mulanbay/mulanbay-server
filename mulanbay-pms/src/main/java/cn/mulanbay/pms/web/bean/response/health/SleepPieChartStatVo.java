package cn.mulanbay.pms.web.bean.response.health;

/**
 * @author fenghong
 * @title: SleepStatVo
 * @description: TODO
 * @date 2023/4/3 09:35
 */
public class SleepPieChartStatVo {

    private String name;

    private int min;

    private int max;

    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
