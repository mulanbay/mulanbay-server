package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:词云
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartWorldCloudData extends BaseChartData{

    List<ChartNameValueVo> dataList = new ArrayList<>();

    public void addData(ChartNameValueVo data){
        this.dataList.add(data);
    }

    public List<ChartNameValueVo> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartNameValueVo> dataList) {
        this.dataList = dataList;
    }

}
