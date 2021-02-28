package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.queue.LimitQueue;
import cn.mulanbay.common.server.*;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统（监控数据）
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

    /**
     * 获取系统详情
     * @return
     */
    @RequestMapping(value = "/getSystemDetail", method = RequestMethod.GET)
    public ResultBean getSystemDetail() {
        ServerDetail serverDetail = new ServerDetail();
        serverDetail.copyTo();
        return callback(serverDetail);
    }

    /**
     * 系统监控统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(SystemResourceType resourceType) {
        String key = CacheKey.getKey(CacheKey.SERVER_DETAIL_MONITOR_TIMELINE);
        LimitQueue<ServerDetail> queue = cacheHandler.get(key, LimitQueue.class);
        if (queue == null) {
            ChartData chartData = new ChartData();
            chartData.setTitle("暂无数据");
            return callback(chartData);
        } else {
            if (resourceType == SystemResourceType.DISK) {
                return callback(createDiskChartData(queue));
            } else if (resourceType == SystemResourceType.MEMORY) {
                return callback(createMemoryChartData(queue));
            } else {
                return callback(createCpuChartData(queue));
            }
        }
    }

    /**
     * 磁盘统计
     * @param queue
     * @return
     */
    private ChartData createDiskChartData(LimitQueue<ServerDetail> queue) {
        ChartData chartData = new ChartData();
        chartData.setTitle("磁盘监控");
        chartData.setLegendData(new String[]{"磁盘空闲空间(G)", "已使用比例(%)"});
        //混合图形下使用
        chartData.addYAxis("空间","G");
        chartData.addYAxis("比例","%");
        ChartYData yData1 = new ChartYData();
        yData1.setName("磁盘空闲空间(G)");
        ChartYData yData2 = new ChartYData();
        yData2.setName("已使用比例(%)");
        int n = queue.size();
        for (int i = 0; i < n; i++) {
            ServerDetail sd = queue.get(i);
            SysFile di = sd.getSysFiles().get(0);
            chartData.getXdata().add(DateUtil.getFormatDate(sd.getDate(), "MM-dd HH:mm"));
            yData1.getData().add(PriceUtil.changeToString(0, di.getFreeSpace() * 1.0 / 1024 / 1024 / 1024));
            yData2.getData().add(PriceUtil.changeToString(0, (di.getTotalSpace() - di.getFreeSpace()) * 1.0 / di.getTotalSpace() * 100));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return chartData;
    }

    /**
     * 内存统计
     * @param queue
     * @return
     */
    private ChartData createMemoryChartData(LimitQueue<ServerDetail> queue) {
        ChartData chartData = new ChartData();
        chartData.setTitle("内存监控");
        chartData.setLegendData(new String[]{"空闲空间(G)", "已使用比例(%)"});
        //混合图形下使用
        chartData.addYAxis("空间","G");
        chartData.addYAxis("比例","%");
        ChartYData yData1 = new ChartYData();
        yData1.setName("空闲空间(G)");
        ChartYData yData2 = new ChartYData();
        yData2.setName("已使用比例(%)");
        int n = queue.size();
        for (int i = 0; i < n; i++) {
            ServerDetail sd = queue.get(i);
            chartData.getXdata().add(DateUtil.getFormatDate(sd.getDate(), "MM-dd HH:mm"));
            Mem mem = sd.getMem();
            yData1.getData().add(PriceUtil.changeToString(0, mem.getFreePhysicalMemorySize() * 1.0 / 1024 / 1024 / 1024));
            yData2.getData().add(PriceUtil.changeToString(0, (mem.getTotalMemorySize() - mem.getFreePhysicalMemorySize()) * 1.0 / mem.getTotalMemorySize() * 100));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return chartData;
    }

    /**
     * CPU统计
     * @param queue
     * @return
     */
    private ChartData createCpuChartData(LimitQueue<ServerDetail> queue) {
        ChartData chartData = new ChartData();
        chartData.setTitle("CPU监控");
        chartData.setLegendData(new String[]{"系统使用率(%)", "当前空闲率(%)"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("系统使用率(%)");
        ChartYData yData2 = new ChartYData();
        yData2.setName("当前空闲率(%)");
        int n = queue.size();
        for (int i = 0; i < n; i++) {
            ServerDetail sd = queue.get(i);
            chartData.getXdata().add(DateUtil.getFormatDate(sd.getDate(), "MM-dd HH:mm"));
            Cpu cpu = sd.getCpu();
            yData1.getData().add(PriceUtil.changeToString(0, cpu.getSysCpuRate() * 100));
            yData2.getData().add(PriceUtil.changeToString(0, cpu.getIdleCpuRate() * 100));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return chartData;
    }
}
