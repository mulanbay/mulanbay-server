package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.web.bean.response.chart.BaseChartData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:中国迁徙地图
 * @Author: fenghong
 * @Create : 2021/3/12
 */
public class ChinaTransferChartData extends BaseChartData {

    private Map<String, double[]> geoCoordMapData;

    private List<ChinaTransferChartDetailData> detailDataList = new ArrayList<>();

    public void addDetail(String name,String start,String end,int value){
        ChinaTransferChartDetailData dd = this.getDetailData(name);
        if(dd==null){
            dd = new ChinaTransferChartDetailData();
            dd.setName(name);
            detailDataList.add(dd);
        }
        dd.addDetail(start,end,value);
    }

    private ChinaTransferChartDetailData getDetailData(String name){
        for(ChinaTransferChartDetailData dd : detailDataList){
            if(dd.getName().equals(name)){
                return dd;
            }
        }
        return null;
    }

    public List<String> getLegendData() {
        List<String> legendData = new ArrayList<>();
        for(ChinaTransferChartDetailData dd : detailDataList){
            legendData.add(dd.getName());
        }
        return legendData;
    }

    public Map<String, double[]> getGeoCoordMapData() {
        return geoCoordMapData;
    }

    public void setGeoCoordMapData(Map<String, double[]> geoCoordMapData) {
        this.geoCoordMapData = geoCoordMapData;
    }

    public List<ChinaTransferChartDetailData> getDetailDataList() {
        return detailDataList;
    }

    public void setDetailDataList(List<ChinaTransferChartDetailData> detailDataList) {
        this.detailDataList = detailDataList;
    }
}
