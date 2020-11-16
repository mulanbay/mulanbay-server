package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

public class ChartSunburstData  extends BaseChartData{

    private List<ChartSunburstDetailData> dataList = new ArrayList<>();

    public List<ChartSunburstDetailData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartSunburstDetailData> dataList) {
        this.dataList = dataList;
    }
}
