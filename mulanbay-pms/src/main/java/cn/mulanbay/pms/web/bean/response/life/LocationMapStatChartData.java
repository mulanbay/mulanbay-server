package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.web.bean.response.chart.BaseChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import com.github.abel533.echarts.data.MapData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationMapStatChartData extends BaseChartData {

    /**
     * 最小值
     */
    private int min = 0;

    /**
     * 最大值
     */
    private int max;
    /**
     * 值的单位
     */
    private String unit="次";

    /**
     * 统计的字段名称
     */
    private String name;

    /**
     * 主题数据
     */
    private List<MapData> dataList = new ArrayList<>();

    /**
     * geo坐标信息
     */
    private Map<String, double[]> geoCoordMapData;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MapData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MapData> dataList) {
        this.dataList = dataList;
    }

    public Map<String, double[]> getGeoCoordMapData() {
        return geoCoordMapData;
    }

    public void setGeoCoordMapData(Map<String, double[]> geoCoordMapData) {
        this.geoCoordMapData = geoCoordMapData;
    }
}
