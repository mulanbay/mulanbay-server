package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.util.*;

/**
 * Created by fenghong on 2017/1/31.
 * 日历饼图数据
 *
 * @link http://echarts.baidu.com/gallery/editor.html?c=calendar-pie
 */
public class ChartCalendarPieData extends BaseChartData {

    private UserBehaviorType behaviorType;

    private Date startDate;

    private String unit;

    private Map<String, Integer> legendMap = new HashMap<>();

    private Map<String, List<ChartCalendarPieDetailData>> seriesData = new HashMap<>();

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ChartCalendarPieData(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }

    /**
     * 针对饼图来说，后面的值已经不重要了，所以全部设置为ChartCalandarPieData1
     *
     * @return
     */
    public List<Object[]> getScatterData() {
        List<Object[]> result = new ArrayList<>();
        for (String key : seriesData.keySet()) {
            Object[] oo = new Object[]{key, 1};
            result.add(oo);
        }
        return result;
    }

    public Map<String, List<ChartCalendarPieDetailData>> getSeriesData() {
        return seriesData;
    }

    /**
     * 如果是时间区域，说明需要根据天数来一直增加，
     * 比如旅行，开始日期：2017-01-01，一共三天，那么需要保存：2017-01-01,2017-01-02,2017-01-03
     *
     * @param date
     * @param name
     * @param value
     * @param dateRegion
     * @param days
     * @param appendValue 是否叠加值
     */
    public void addData(String date, String name, Object value, boolean dateRegion, int days, boolean appendValue) {
        if (!dateRegion) {
            this.addData(date, name, value, appendValue);
        } else {
            Date startDate = DateUtil.getDate(date, DateUtil.FormatDay1);
            for (int i = 0; i < days; i++) {
                String newDateString = DateUtil.getFormatDate(DateUtil.getDate(i, startDate), DateUtil.FormatDay1);
                //平均下去了，所以每个只是1
                this.addData(newDateString, name, 1, appendValue);
            }
        }
    }

    /**
     * 添加数据
     *
     * @param date
     * @param name
     * @param value
     */
    public void addData(String date, String name, Object value, boolean appendValue) {
        List<ChartCalendarPieDetailData> list = seriesData.get(date);
        if (list == null) {
            list = new ArrayList<>();
        }
        ChartCalendarPieDetailData pdd = null;
        for (ChartCalendarPieDetailData dd : list) {
            if (dd.getName().equals(name)) {
                if (appendValue) {
                    //往上叠加
                    if (behaviorType == null) {
                        //不同类型的值比较没有意义，因此全部设置为1
                        dd.setValue(dd.getValue() + 1L);
                    } else {
                        dd.setValue(dd.getValue() + Long.valueOf(value.toString()));
                    }
                } else {
                    dd.setValue(1L);
                }

                pdd = dd;
            }
        }
        if (pdd == null) {
            pdd = new ChartCalendarPieDetailData();
            pdd.setName(name);
            if (behaviorType == null) {
                //不同类型的值比较没有意义，因此全部设置为1
                pdd.setValue(1L);
            } else {
                pdd.setValue(Long.valueOf(value.toString()));
            }

            list.add(pdd);
        }
        seriesData.put(date, list);
        Integer nn = legendMap.get(name);
        if (nn == null) {
            legendMap.put(name, 1);
        } else {
            legendMap.put(name, nn + 1);
        }
    }

    /**
     * 自动计算
     *
     * @return
     */
    public String[] getLegendData() {
        Set<String> keySet = legendMap.keySet();
        String[] legendData = new String[keySet.size()];
        int i = 0;
        for (String key : keySet) {
            legendData[i] = key;
            i++;
        }
        return legendData;
    }

    public String[] getRange() {
        String month = DateUtil.getFormatDate(this.getStartDate(), "yyyy-MM");
        return new String[]{month};
    }

}
