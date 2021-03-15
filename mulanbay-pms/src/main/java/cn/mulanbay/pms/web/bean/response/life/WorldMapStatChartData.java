package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.web.bean.response.chart.BaseChartData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/12
 */
public class WorldMapStatChartData extends BaseChartData {

    private String mapName;

    private int maxValue;

    private Map<String, double[]> geoCoordMapData;

    private List<MapStatChartDetail> dataList = new ArrayList<>();

    public void addDetail(MapStatChartDetail detail){
        this.dataList.add(detail);
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public Map<String, double[]> getGeoCoordMapData() {
        return geoCoordMapData;
    }

    public void setGeoCoordMapData(Map<String, double[]> geoCoordMapData) {
        this.geoCoordMapData = geoCoordMapData;
    }

    public List<MapStatChartDetail> getDataList() {
        return dataList;
    }

    public void setDataList(List<MapStatChartDetail> dataList) {
        this.dataList = dataList;
    }

}
