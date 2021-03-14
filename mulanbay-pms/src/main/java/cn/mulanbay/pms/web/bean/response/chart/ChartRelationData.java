package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartRelationData extends BaseChartData{

    List<ChartRelationDetailData> dataList = new ArrayList<>();

    public void addData(ChartRelationDetailData data){
        this.dataList.add(data);
    }

    public List<ChartRelationDetailData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartRelationDetailData> dataList) {
        this.dataList = dataList;
    }


}
