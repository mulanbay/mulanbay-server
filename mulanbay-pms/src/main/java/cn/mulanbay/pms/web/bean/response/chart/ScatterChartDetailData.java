package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 散点图
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ScatterChartDetailData {

    private String name;

    private Object xAxisAverage;

    private List<Object[]> data = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getxAxisAverage() {
        return xAxisAverage;
    }

    public void setxAxisAverage(Object xAxisAverage) {
        this.xAxisAverage = xAxisAverage;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public void addData(Object[] os) {
        if (os.length < 3) {
            data.add(new Object[]{os[0], os[1], 1});
        } else {
            data.add(os);
        }
    }

    public void appendData(Object x, Object y, double v) {
        Object[] oo = this.getData(x, y);
        if (oo == null) {
            this.addData(new Object[]{x, y, v});
        } else {
            v += Double.valueOf(oo[2].toString());
            oo[2] = v;
        }
    }

    private Object[] getData(Object x, Object y) {
        for (Object[] oo : data) {
            if (oo[0].equals(x) && oo[1].equals(y)) {
                return oo;
            }
        }
        return null;
    }
}
