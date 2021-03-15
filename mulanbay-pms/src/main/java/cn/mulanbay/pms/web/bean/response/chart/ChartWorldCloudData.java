package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartWorldCloudData extends BaseChartData{

    List<ChartWorldCloudDetailData> dataList = new ArrayList<>();

    public void addData(ChartWorldCloudDetailData data){
        this.dataList.add(data);
    }

    public List<ChartWorldCloudDetailData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartWorldCloudDetailData> dataList) {
        this.dataList = dataList;
    }

}
