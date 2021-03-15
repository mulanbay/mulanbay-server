package cn.mulanbay.pms.web.bean.response.chart;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/15
 */
public class ChartNameValueVo {

    private String name;

    //默认显示值
    private int value;

    public ChartNameValueVo() {
    }

    public ChartNameValueVo(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
