package cn.mulanbay.pms.web.bean.response.life;

import cn.mulanbay.pms.persistent.dto.TransferMapStat;

import java.util.ArrayList;
import java.util.List;

/**
 * 迁徙地图双向数据封装
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class TransferMapDoubleStatChartData extends TransferMapStatChartData {

    private List<String> legendData = new ArrayList<>();

    private List<TransferMapStat> statData = new ArrayList<>();

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<TransferMapStat> getStatData() {
        return statData;
    }

    public void setStatData(List<TransferMapStat> statData) {
        this.statData = statData;
    }

}
