package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.pms.persistent.dto.DateStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.util.ChartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartData extends BaseChartData {

    //统计分类
    private String[] legendData;

    private List<Integer> intXData = new ArrayList<>();

    //x轴数据
    private List<String> xdata = new ArrayList<>();

    //y轴数据ChartData
    private List<ChartYData> ydata = new ArrayList<>();

    //适合柱状图、折线混合图形(有且只有两条数据)
    private List<ChartYAxis> yaxis = new ArrayList<>();

    public String[] getLegendData() {
        return legendData;
    }

    public void setLegendData(String[] legendData) {
        this.legendData = legendData;
    }

    public List<Integer> getIntXData() {
        return intXData;
    }

    public void setIntXData(List<Integer> intXData) {
        this.intXData = intXData;
    }

    public List<String> getXdata() {
        return xdata;
    }

    public void setXdata(List<String> xdata) {
        this.xdata = xdata;
    }

    public List<ChartYData> getYdata() {
        return ydata;
    }

    public void setYdata(List<ChartYData> ydata) {
        this.ydata = ydata;
    }

    public List<ChartYAxis> getYaxis() {
        return yaxis;
    }

    public void setYaxis(List<ChartYAxis> yaxis) {
        this.yaxis = yaxis;
    }

    public void addYAxis(String name,String unit){
        ChartYAxis ya = new ChartYAxis();
        ya.setName(name);
        ya.setUnit(unit);
        yaxis.add(ya);
    }

    /**
     * 添加X轴的数据
     *
     * @param dateStat
     * @param dateGroupType
     */
    public void addXData(DateStat dateStat, DateGroupType dateGroupType) {
        this.getIntXData().add(dateStat.getIndexValue());
        this.getXdata().add(ChartUtil.getStringXdata(dateGroupType, dateStat.getIndexValue()));
    }

    /**
     * 添加X轴的数据
     *
     * @param s
     */
    public void addXData(String s) {
        this.getXdata().add(s);
    }

    public ChartYData findYData(String name) {
        for (ChartYData y : ydata) {
            if (y.getName().equals(name)) {
                return y;
            }
        }
        return null;
    }
}
