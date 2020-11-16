package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 雷达图的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartRadarSerieData {

    private String name;

    private List<Long> data = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }

    public void addData(Long v){
        this.data.add(v);
    }
}
