package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.web.bean.response.chart.BaseChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartNameValueVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/12
 */
public class WorldTransferChartData extends BaseChartData {

    private String mapName;

    private int maxValue;

    private String centerCity;

    private Map<String, double[]> geoCoordMapData;

    private List<ChartNameValueVo[]> dataList = new ArrayList<>();

    public void addDetail(String start,String end,int value){
        ChartNameValueVo v1 = new ChartNameValueVo(start,value);
        ChartNameValueVo v2 = new ChartNameValueVo(end,1);
        this.dataList.add(new ChartNameValueVo[]{v1,v2});
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

    public String getCenterCity() {
        return centerCity;
    }

    public void setCenterCity(String centerCity) {
        this.centerCity = centerCity;
    }

    public Map<String, double[]> getGeoCoordMapData() {
        return geoCoordMapData;
    }

    public void setGeoCoordMapData(Map<String, double[]> geoCoordMapData) {
        this.geoCoordMapData = geoCoordMapData;
    }

    public List<ChartNameValueVo[]> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartNameValueVo[]> dataList) {
        this.dataList = dataList;
    }
}
