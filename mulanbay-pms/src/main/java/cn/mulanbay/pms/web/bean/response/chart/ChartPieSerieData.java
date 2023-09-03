package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼图的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartPieSerieData {

    private String name;

    private String unit;

    private List<ChartPieSerieDetailData> data = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     */
    public void findAndAppendDetailData(double value, String name) {
        ChartPieSerieDetailData ct = this.findDetailData(name);
        if (ct == null) {
            ct = new ChartPieSerieDetailData(name, value);
            data.add(ct);
        } else {
            ct.setValue(Double.valueOf(ct.getValue().toString()) + value);
        }
    }

    private ChartPieSerieDetailData findDetailData(String name) {
        for (ChartPieSerieDetailData cp : data) {
            if (cp.getName().equals(name)) {
                return cp;
            }
        }
        return null;
    }

    public List<ChartPieSerieDetailData> getData() {
        return data;
    }

    public void setData(List<ChartPieSerieDetailData> data) {
        this.data = data;
    }
}
