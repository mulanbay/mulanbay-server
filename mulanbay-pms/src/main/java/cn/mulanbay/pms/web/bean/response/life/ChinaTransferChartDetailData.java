package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.web.bean.response.chart.BaseChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartNameValueVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:中国迁徙地图
 * @Author: fenghong
 * @Create : 2021/3/12
 */
public class ChinaTransferChartDetailData extends BaseChartData {

    private String name;

    private List<ChartNameValueVo[]> dataList = new ArrayList<>();

    public void addDetail(String start,String end,int value){
        ChartNameValueVo v1 = new ChartNameValueVo(start,value);
        ChartNameValueVo v2 = new ChartNameValueVo(end,1);
        this.dataList.add(new ChartNameValueVo[]{v1,v2});
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChartNameValueVo[]> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChartNameValueVo[]> dataList) {
        this.dataList = dataList;
    }
}
