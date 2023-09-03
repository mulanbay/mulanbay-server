package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.persistent.dto.AfterEightHourStat;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.data.AfterEightHourAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * 数据分析
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */

@RestController
@RequestMapping("/dataAnalyse")
public class DataAnalyseController extends BaseController {

    @Autowired
    DataService dataService;

    /**
     * @return
     */
    @RequestMapping(value = "/afterEightHourAnalyseStat", method = RequestMethod.GET)
    public ResultBean afterEightHourAnalyseStat(AfterEightHourAnalyseStatSearch sf) {
        List<AfterEightHourStat> list = dataService.statAfterEightHour(sf);
        if (sf.getChartType() == ChartType.PIE) {
            return callback(this.createAfterEightHourAnalyseStatPieData(list, sf));
        } else if (sf.getChartType() == ChartType.MIX_LINE_BAR) {
            return callback(this.createAfterEightHourAnalyseStatBarData(list, sf));
        } else {
            return callback(this.createAfterEightHourAnalyseStatScatterData(list, sf));
        }

    }

    /**
     * 柱状图统计数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartData createAfterEightHourAnalyseStatBarData(List<AfterEightHourStat> list, AfterEightHourAnalyseStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("八小时之外统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"时长","次数"});
        //混合图形下使用
        chartData.addYAxis("时长","小时");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("时长","小时");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        Map<String, Long> countMap = new TreeMap<>();
        Map<String, BigDecimal> minutesMap = new HashMap<>();
        for (AfterEightHourStat bean : list) {
            //默认按照明细
            String key = bean.getName();
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.GROUP) {
                key = bean.getGroupName();
            }
            if (key == null) {
                key = "未分组";
            }
            Long n = countMap.get(key);
            if (n == null) {
                countMap.put(key, bean.getTotalCountValue());
                minutesMap.put(key, bean.getTotalTime());
            } else {
                countMap.put(key, n + bean.getTotalCountValue());
                minutesMap.put(key, minutesMap.get(key).add(bean.getTotalTime()));
            }
        }
        // 将map.entrySet()转换成list
        List<Map.Entry<String, BigDecimal>> minutesList = new ArrayList<Map.Entry<String, BigDecimal>>(minutesMap.entrySet());
        // 通过比较器来实现排序
        Collections.sort(minutesList, new Comparator<Map.Entry<String, BigDecimal>>() {
            @Override
            public int compare(Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2) {
                // 降序排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (Map.Entry<String, BigDecimal> mapping : minutesList) {
            String key = mapping.getKey();
            chartData.getXdata().add(key);
            yData1.getData().add(countMap.get(key));
            yData2.getData().add(PriceUtil.changeToString(0,minutesMap.get(key).doubleValue()/60));
            totalCount = totalCount.add(new BigDecimal(countMap.get(key)));
            totalValue = totalValue.add(minutesMap.get(key));
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次，" + totalValue.doubleValue() + "分钟");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return chartData;
    }


    /**
     * 散点图统计数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ScatterChartData createAfterEightHourAnalyseStatScatterData(List<AfterEightHourStat> list, AfterEightHourAnalyseStatSearch sf) {
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle("八小时之外统计");
        for (AfterEightHourStat stat : list) {
            ScatterChartDetailData detailData = chartData.findDetailDataByName(stat.getName());
            if (detailData == null) {
                detailData = new ScatterChartDetailData();
                if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.DETAIL) {
                    detailData.setName(stat.getName());
                } else {
                    detailData.setName(stat.getGroupName());
                }
                chartData.addSeriesData(detailData);
                chartData.addLegent(stat.getName());
            }
            detailData.addData(new Object[]{stat.getDateIndexValue(), DateUtil.minutesToHours(stat.getTotalTime().doubleValue()), stat.getTotalCountValue()});
            chartData.valueCompare(stat.getTotalCountValue());
        }
        return chartData;
    }

    /**
     * 饼图统计数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartPieData createAfterEightHourAnalyseStatPieData(List<AfterEightHourStat> list, AfterEightHourAnalyseStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("八小时外统计");
        chartPieData.setSubTitle(this.getDateTitle(sf));
        ChartPieSerieData serieData = new ChartPieSerieData();
        if (sf.getType() == GroupType.COUNT) {
            serieData.setName("次数(次)");
            chartPieData.setUnit("次");
        } else {
            serieData.setName("花费时间(小时)");
            chartPieData.setUnit("小时");
        }
        for (AfterEightHourStat mp : list) {
            chartPieData.getXdata().add(mp.getName());
            ChartPieSerieDetailData cp = new ChartPieSerieDetailData();
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.DETAIL) {
                cp.setName(mp.getName());
            } else {
                cp.setName(mp.getGroupName());
            }
            if (sf.getType() == GroupType.COUNT) {
                cp.setValue(mp.getTotalCountValue());
            } else {
                cp.setValue(mp.getTotalTime()==null ? 0: DateUtil.minutesToHours(mp.getTotalTime().doubleValue()));
            }
            serieData.getData().add(cp);
        }
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

}
