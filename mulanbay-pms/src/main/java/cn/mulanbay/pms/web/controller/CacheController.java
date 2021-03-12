package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieDetailData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 缓存
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultBean info() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("命令统计");
        chartPieData.setUnit("个");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("命令");
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            String name = StringUtils.removeStart(key, "cmdstat_");
            String value = StringUtils.substringBetween(property, "calls=", ",usec");
            chartPieData.getXdata().add(name);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(name);
            dataDetail.setValue(value);
            serieData.getData().add(dataDetail);
        });
        chartPieData.getDetailData().add(serieData);
        result.put("commandStats", chartPieData);
        return callback(result);
    }

    /**
     * 删除缓存
     *
     * @return
     */
    @RequestMapping(value = "/deleteCache", method = RequestMethod.GET)
    public ResultBean deleteCache(String key) {
        cacheHandler.delete(key);
        return callback(null);
    }

    /**
     * 获取缓存
     *
     * @return
     */
    @RequestMapping(value = "/getCache", method = RequestMethod.GET)
    public ResultBean getCache(String key) {
        return callback(cacheHandler.get(key));
    }

}
