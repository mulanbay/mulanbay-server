package cn.mulanbay.pms.web.bean.response.chart;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/23
 */
public class ChartGraphLinkData {

    private String source;

    private String target;

    /**
     * 链接线上的名称
     */
    private String name;

    /**
     * 类型
     */
    private int type;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
