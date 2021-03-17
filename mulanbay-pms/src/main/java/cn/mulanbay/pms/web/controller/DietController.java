package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.DietHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.domain.Diet;
import cn.mulanbay.pms.persistent.domain.DietCategory;
import cn.mulanbay.pms.persistent.domain.DietVarietyLog;
import cn.mulanbay.pms.persistent.domain.UserSetting;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.diet.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 饮食
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/diet")
public class DietController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DietController.class);

    private static Class<Diet> beanClass = Diet.class;

    @Autowired
    DietService dietService;

    @Autowired
    AuthService authService;

    @Autowired
    DietHandler dietHandler;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * 食物列表
     *
     * @return
     */
    @RequestMapping(value = "/getFoodsTree")
    public ResultBean getFoodsTree(DietKeywordsSearch sf) {
        Date[] range = this.getTagsDateRange();
        sf.setStartDate(range[0]);
        sf.setEndDate(range[1]);
        List<String> foodsList = dietService.getFoodsList(sf);
        //去重
        Set<String> foodsSet = TreeBeanUtil.deleteDuplicate(foodsList);
        return callback(createTreeBean(foodsSet, sf.getNeedRoot()));
    }

    /**
     * 店铺/品牌列表
     *
     * @return
     */
    @RequestMapping(value = "/getShopTree")
    public ResultBean getShopTree(DietKeywordsSearch sf) {
        Date[] range = this.getTagsDateRange();
        sf.setStartDate(range[0]);
        sf.setEndDate(range[1]);
        List<String> shopList = dietService.getShopList(sf);
        //去重
        Set<String> shopSet = TreeBeanUtil.deleteDuplicate(shopList);
        return callback(createTreeBean(shopSet, sf.getNeedRoot()));
    }

    /**
     * 店铺/品牌列表
     *
     * @return
     */
    @RequestMapping(value = "/getTagsTree")
    public ResultBean getTagsTree(DietKeywordsSearch sf) {
        Date[] range = this.getTagsDateRange();
        sf.setStartDate(range[0]);
        sf.setEndDate(range[1]);
        List<String> shopList = dietService.getTagsList(sf);
        //去重
        Set<String> shopSet = TreeBeanUtil.deleteDuplicate(shopList);
        return callback(createTreeBean(shopSet, sf.getNeedRoot()));
    }

    /**
     * 获取标签的时间段
     * @return
     */
    private Date[] getTagsDateRange(){
        Date[] ds = systemConfigHandler.getDateRange(null,"diet.tags.days");
        return ds;
    }

    private List<TreeBean> createTreeBean(Set<String> set, Boolean needRoot) {
        List<TreeBean> list = new ArrayList<TreeBean>();
        for (String s : set) {
            TreeBean tb = new TreeBean();
            tb.setId(s);
            tb.setText(s);
            list.add(tb);
        }
        return TreeBeanUtil.addRoot(list, needRoot);
    }

    /**
     * 获取列表数据
     * 使用RequestParam 方式是因为easyui 的datagrid使用ajax请求时对于list多值类型的参数参数名会带上中挂号
     * 且无法在js请求方式里设置使用 traditional:true参数
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(DietSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("occurTime", Sort.DESC);
        pr.addSort(s);
        PageResult<Diet> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid DietFormRequest formRequest) {
        Diet bean = new Diet();
        BeanCopy.copyProperties(formRequest, bean, true);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        Diet bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 获取最后的地点
     *
     * @return
     */
    @RequestMapping(value = "/getLastLocation", method = RequestMethod.GET)
    public ResultBean getLastLocation(UserCommonRequest uc) {
        Long userId = uc.getUserId();
        Diet diet = dietService.getLastDiet(userId, null, null, null);
        if (diet == null) {
            UserSetting us = authService.getUserSettingForCache(uc.getUserId());
            return callback(us.getResidentCity());
        } else {
            return callback(diet.getLocation());
        }
    }

    /**
     * 获取最近一次的饮食
     *
     * @return
     */
    @RequestMapping(value = "/getLastDiet", method = RequestMethod.GET)
    public ResultBean getLastDiet(@Valid LastDietRequest dietRequest) {
        Diet diet = dietService.getLastDiet(dietRequest.getUserId(), dietRequest.getDietSource(), dietRequest.getDietType(), null);
        return callback(diet);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid DietFormRequest formRequest) {
        Diet bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean, true);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 用户行为统计（带饼图分析）
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid DietStatSearch sf) {
        if (sf.getDateGroupType() == DateGroupType.MONTH) {
            return callback(this.createMonthStat(sf));
        } else {
            return callback(this.createYearStat(sf));
        }
    }

    /**
     * 月份统计
     * @param sf
     * @return
     */
    private ChartCalendarPieData createMonthStat(DietStatSearch sf) {
        Date date = DateUtil.getDate(sf.getYear() + "-" + sf.getMonth() + "-01", DateUtil.FormatDay1);
        Date startTime = DateUtil.getFirstDayOfMonth(date);
        Date endDate = DateUtil.getLastDayOfMonth(date);
        Date endTime = DateUtil.getTodayTillMiddleNightDate(endDate);
        ChartCalendarPieData pieData = new ChartCalendarPieData(UserBehaviorType.LIFE);
        String monthString = DateUtil.getFormatDate(startTime, "yyyyMM");
        pieData.setTitle(monthString + "饮食习惯分析");
        pieData.setStartDate(startTime);
        List<Diet> list = dietService.getDietList(startTime, endTime, sf.getUserId(), sf.getDietSource(), sf.getDietType(), sf.getFoodType());
        for (Diet stat : list) {
            String dd = DateUtil.getFormatDate(stat.getOccurTime(), DateUtil.FormatDay1);
            pieData.addData(dd, stat.getDietType().getName(), 1, false, 1, true);
        }
        return pieData;
    }

    /**
     * 年份统计
     * @param sf
     * @return
     */
    private ChartCalendarCompareData createYearStat(DietStatSearch sf) {
        if (sf.getDietType() == null) {
            throw new ApplicationException(PmsErrorCode.DIET_TYPE_NULL);
        }
        Date startTime = DateUtil.getDate(sf.getYear() + "-01-01 00:00:00", DateUtil.Format24Datetime);
        Date endTime = DateUtil.getDate(sf.getYear() + "-12-31 23:59:59", DateUtil.Format24Datetime);

        ChartCalendarCompareData chartData = new ChartCalendarCompareData();
        chartData.setTitle(sf.getYear() + "饮食习惯分析");
        chartData.setUnit("次");
        chartData.setYear(sf.getYear());
        List<Diet> list = dietService.getDietList(startTime, endTime, sf.getUserId(), sf.getDietSource(), sf.getDietType(), sf.getFoodType());
        Date beginFlag = list.get(0).getOccurTime();
        for (Diet stat : list) {
            String dd = DateUtil.getFormatDate(stat.getOccurTime(), DateUtil.FormatDay1);
            chartData.addSerieData(dd, 1, false, 1, 1);
            addMissDate(beginFlag, stat.getOccurTime(), chartData);
            beginFlag = stat.getOccurTime();
        }
        String s1 = sf.getDietType().getName();
        String s2 = "没吃" + sf.getDietType().getName();
        chartData.setLegendData(new String[]{s1, s2});

        return chartData;
    }

    /**
     * 统计没吃某餐的数据
     *
     * @param start
     * @param end
     * @param chartData
     */
    private void addMissDate(Date start, Date end, ChartCalendarCompareData chartData) {
        Date startDate = DateUtil.getDate(start, DateUtil.FormatDay1);
        Date endDate = DateUtil.getDate(end, DateUtil.FormatDay1);
        int n = DateUtil.getIntervalDays(startDate, endDate);
        if (n <= 1) {
            return;
        } else {
            for (int i = 2; i <= n; i++) {
                String dd = DateUtil.getFormatDate(DateUtil.getDate((i - 1), startDate), DateUtil.FormatDay1);
                chartData.addSerieData(dd, 1, false, 1, 2);
            }
        }
    }

    /**
     * 根据时间点的统计
     *
     * @return
     */
    @RequestMapping(value = "/timeStat", method = RequestMethod.GET)
    public ResultBean timeStat(@Valid DietTimeStatSearch sf) {
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle("饮食时间点分析");
        chartData.setxUnit(sf.getXgroupType().getName());
        chartData.setyUnit("点");
        List<DietTimeStat> list = dietService.timeStatDiet(sf);
        chartData.addLegent("时间点");
        ScatterChartDetailData detailData = new ScatterChartDetailData();
        detailData.setName("时间点");
        double totalX = 0;
        int n = 0;
        for (DietTimeStat stat : list) {
            detailData.addData(new Object[]{stat.getxDoubleValue(), stat.getyDoubleValue()});
            totalX += stat.getxDoubleValue();
            n++;
        }
        detailData.setxAxisAverage(totalX / n);
        chartData.addSeriesData(detailData);
        return callback(chartData);
    }

    /**
     * 分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid DietAnalyseSearch sf) {
        if (sf.getChartType() == ChartType.TREE_MAP) {
            //只有按照商品子类型的才能
            if (sf.getField() != DietAnalyseSearch.StatField.CLASS_NAME) {
                return callbackErrorInfo("只有按照小类分组的才支持该分析图型");
            }
            return callback(this.createAnalyseStatTreeMapData(sf));
        }
        if (sf.getChartType() == ChartType.PIE) {
            return callback(this.createAnalyseStatPieData(sf));
        } else {
            return callback(this.createAnalyseStatBarData(sf));
        }
    }

    /**
     * 价格分析
     *
     * @return
     */
    @RequestMapping(value = "/priceAnalyse", method = RequestMethod.GET)
    public ResultBean priceAnalyse(@Valid DietPriceAnalyseSearch sf) {
        String dg = sf.getDateGroupTypeStr();
        if ("DIET_SOURCE".equals(dg) || "FOOD_TYPE".equals(dg) || "DIET_TYPE".equals(dg)) {
            return callback(createAnalyseStatPieData2(sf));
        }
        List<DietPriceAnalyseStat> list = dietService.statDietPrice(sf);
        if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
            return callback(createAnalyseStatPieData(list, sf));
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("饮食价格统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"消费(元)","次数"});
        //混合图形下使用
        chartData.addYAxis("消费","元");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("消费(元)");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (DietPriceAnalyseStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(bean.getTotalPrice());
            totalCount = totalCount.add(new BigDecimal(bean.getTotalCount().longValue()));
            totalValue = totalValue.add(bean.getTotalPrice());
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        String subTitle = this.getDateTitle(sf, totalCount.longValue() + "次，" + totalValue.doubleValue() + "元");
        chartData.setSubTitle(subTitle);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    private ChartPieData createAnalyseStatPieData(List<DietPriceAnalyseStat> list, DietPriceAnalyseSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setUnit("元");
        chartPieData.setTitle("饮食价格区间分析");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("价格区间");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        Map<String, Integer> map = new HashMap<>();
        for (DietPriceAnalyseStat bean : list) {
            int p = bean.getTotalPrice().intValue();
            int x = p / 5;
            String key = x * 5 + "-" + (x + 1) * 5 + "元";
            Integer n = map.get(key);
            if (n == null) {
                map.put(key, 1);
            } else {
                map.put(key, n + 1);
            }
            totalValue = totalValue.add(bean.getTotalPrice());
        }
        for (String key : map.keySet()) {
            chartPieData.getXdata().add(key);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(key);
            dataDetail.setValue(map.get(key));
            serieData.getData().add(dataDetail);
        }
        String subTitle = this.getDateTitle(sf, totalValue.doubleValue() + "元");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 以饮食的分类来统计
     *
     * @param sf
     * @return
     */
    private ChartPieData createAnalyseStatPieData2(DietPriceAnalyseSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("饮食分析");
        chartPieData.setUnit("元");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("价格");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        List<DietPriceAnalyse2Stat> list = dietService.statDietPriceAnalyseByType(sf);
        for (DietPriceAnalyse2Stat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(PriceUtil.changeToString(2, bean.getTotalPrice()));
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(bean.getTotalPrice());
        }
        String subTitle = this.getDateTitle(sf, totalValue.doubleValue() + "元");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 获取有效的食品分类列表
     *
     * @return
     */
    private List<DietCategory> getActiveDietCategoryList() {
        DietCategorySearch sf = new DietCategorySearch();
        sf.setStatus(CommonStatus.ENABLE);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(DietCategory.class);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        List<DietCategory> list = baseService.getBeanList(pr);
        return list;
    }

    private DietCategory findDietCategory(List<DietCategory> list, String s) {
        for (DietCategory dc : list) {
            String[] ks = dc.getKeywords().split(",");
            for (String k : ks) {
                if (s.contains(k)) {
                    return dc;
                }
            }
        }
        logger.warn("饮食[" + s + "]找不到食品类型分析");
        return null;
    }

    /**
     * 比对
     *
     * @return
     */
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ResultBean compare(@Valid DietCompareSearch sf) {
        List<DietCompareStat> list = dietService.statDietCompare(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("饮食比对");
        chartData.setUnit(sf.getStatField().getUnit());
        List dietTypeVs = new ArrayList<>();
        for (DietType dt : DietType.values()) {
            dietTypeVs.add(0);
            chartData.getXdata().add(dt.getName());
        }
        List<String> dietSourceNames = new ArrayList<>();
        for (DietSource ds : DietSource.values()) {
            dietSourceNames.add(ds.getName());
            ChartYData yData = new ChartYData();
            yData.setName(ds.getName());
            List values = new ArrayList();
            values.addAll(dietTypeVs);
            yData.setData(values);
            chartData.getYdata().add(yData);
        }
        String[] strings = new String[dietSourceNames.size()];
        chartData.setLegendData(dietSourceNames.toArray(strings));
        for (DietCompareStat m : list) {
            ChartYData yData = chartData.findYData(m.getDietSource().getName());
            List values = yData.getData();
            Object v = 0;
            if (sf.getStatField() == DietCompareSearch.StatField.COUNTS) {
                v = m.getTotalCount().longValue();
            } else if (sf.getStatField() == DietCompareSearch.StatField.TOTAL_PRICE) {
                v = PriceUtil.changeToString(0, m.getTotalPrice());
            } else if (sf.getStatField() == DietCompareSearch.StatField.AVG_PRICE) {
                v = PriceUtil.changeToString(0, m.getTotalPrice().doubleValue() / m.getTotalCount().longValue());
            } else if (sf.getStatField() == DietCompareSearch.StatField.AVG_SCORE) {
                v = PriceUtil.changeToString(0, m.getTotalScore().doubleValue() / m.getTotalCount().longValue());
            }
            values.set(m.getDietType().getValue(), v);
        }
        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        return callback(chartData);
    }

    /**
     * 封装消费记录分析的树形图数据
     *
     * @param sf
     * @return
     */
    private ChartTreeMapData createAnalyseStatTreeMapData(DietAnalyseSearch sf) {
        ChartTreeMapData chartData = new ChartTreeMapData();
        chartData.setTitle("饮食分析");
        chartData.setName("食物");
        chartData.setUnit("次");
        Map<String, ChartTreeMapDetailData> dataMap = new HashMap<>();
        List<DietAnalyseStat> list = dietService.statDietAnalyse(sf);
        List<DietCategory> dcList = this.getActiveDietCategoryList();
        for (DietAnalyseStat bean : list) {
            DietCategory dc = this.findDietCategory(dcList, bean.getName());
            if (dc == null) {
                if (!sf.isIncludeUnknown()) {
                    continue;
                }
                dc = new DietCategory();
                dc.setClassName(bean.getName());
                dc.setType("未知");
            }
            ChartTreeMapDetailData mdd = dataMap.get(dc.getType());
            //只有两层结构
            if (mdd == null) {
                mdd = new ChartTreeMapDetailData(1, dc.getType(), dc.getType());
                dataMap.put(dc.getType(), mdd);
            }
            mdd.findAndAppendChild(bean.getTotalCount().doubleValue(), dc.getClassName(), dc.getType() + "/" + dc.getClassName());
        }
        chartData.setData(new ArrayList<>(dataMap.values()));
        return chartData;

    }

    /**
     * 封装消费分析的饼状图数据
     *
     * @param sf
     * @return
     */
    private ChartPieData createAnalyseStatPieData(DietAnalyseSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("饮食分析");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("食物");
        Map<String, DietAnalyseStat> map = this.getDietAnalyseStat(sf);
        for (String key : map.keySet()) {
            chartPieData.getXdata().add(key);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(key);
            dataDetail.setValue(map.get(key).getTotalCount().longValue());
            serieData.getData().add(dataDetail);
        }
        String subTitle = this.getDateTitle(sf);
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 封装消费记录分析的柱状图数据
     *
     * @param sf
     * @return
     */
    private ChartData createAnalyseStatBarData(DietAnalyseSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("饮食分析");
        chartData.setUnit("次");
        chartData.setLegendData(new String[]{"食物"});
        ChartYData yData = new ChartYData();
        yData.setName("食物");
        Map<String, DietAnalyseStat> map = this.getDietAnalyseStat(sf);
        // 将map.entrySet()转换成list
        List<Map.Entry<String, DietAnalyseStat>> list = new ArrayList<>(map.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, DietAnalyseStat>>() {
            @Override
            public int compare(Map.Entry<String, DietAnalyseStat> o1, Map.Entry<String, DietAnalyseStat> o2) {
                // 降序排序
                return o2.getValue().getTotalCount().compareTo(o1.getValue().getTotalCount());
            }
        });
        for (Map.Entry<String, DietAnalyseStat> m : list) {
            chartData.getXdata().add(m.getKey());
            yData.getData().add(m.getValue().getTotalCount().longValue());
        }
        String subTitle = this.getDateTitle(sf);
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        return chartData;

    }

    private Map<String, DietAnalyseStat> getDietAnalyseStat(DietAnalyseSearch sf) {
        List<DietCategory> dcList = null;
        DietAnalyseSearch.StatField statField = sf.getField();
        //是否根据类别统计
        boolean isDs = false;
        if (statField == DietAnalyseSearch.StatField.CLASS_NAME || statField == DietAnalyseSearch.StatField.TYPE) {
            isDs = true;
            dcList = this.getActiveDietCategoryList();
        }
        List<DietAnalyseStat> list = null;
        if (sf.getField() == DietAnalyseSearch.StatField.FOOD_TYPE ||
                sf.getField() == DietAnalyseSearch.StatField.DIET_SOURCE ||
                sf.getField() == DietAnalyseSearch.StatField.DIET_TYPE) {
            list = dietService.statDietAnalyseByType(sf);
        } else {
            list = dietService.statDietAnalyse(sf);
        }
        Map<String, DietAnalyseStat> map = new HashMap<>();
        for (DietAnalyseStat da : list) {
            DietCategory dc = null;
            if (isDs) {
                dc = this.findDietCategory(dcList, da.getName());
                if (dc == null) {
                    if (!sf.isIncludeUnknown()) {
                        continue;
                    }
                    dc = new DietCategory();
                    dc.setClassName("未知");
                    dc.setType("未知");
                }
                String key = null;
                if (statField == DietAnalyseSearch.StatField.CLASS_NAME) {
                    key = dc.getClassName();
                } else if (statField == DietAnalyseSearch.StatField.TYPE) {
                    key = dc.getType();
                }
                DietAnalyseStat dass = map.get(key);
                if (dass == null) {
                    dass = new DietAnalyseStat();
                    dass.setName(da.getName());
                    dass.setTotalCount(da.getTotalCount());
                    map.put(key, dass);
                } else {
                    //往里面追加值
                    dass.setTotalCount(dass.getTotalCount().add(da.getTotalCount()));
                }
            } else {
                map.put(da.getName(), da);
            }
        }
        return map;
    }

    /**
     * 食物的平均相似度
     *
     * @return
     */
    @RequestMapping(value = "/getFoodsAvgSimilarity", method = RequestMethod.GET)
    public ResultBean getFoodsAvgSimilarity(@Valid DietVarietySearch sf) {
        return callback(dietHandler.getFoodsAvgSimilarity(sf));
    }

    /**
     * 食物的词云
     *
     * @return
     */
    @RequestMapping(value = "/statWordCloud", method = RequestMethod.GET)
    public ResultBean statWordCloud(@Valid DietWordCloudSearch sf) {
        List<NameCountDto> tagsList = dietService.statTags(sf);
        ChartWorldCloudData chartData = new ChartWorldCloudData();
        for(NameCountDto s : tagsList){
            ChartNameValueVo dd = new ChartNameValueVo();
            dd.setName(s.getName());
            dd.setValue(s.getCounts().intValue());
            chartData.addData(dd);
        }
        chartData.setTitle("饮食词云统计");
        return callback(chartData);
    }

    /**
     * 食物的相似度
     *
     * @return
     */
    @RequestMapping(value = "/statFoodsAvgSimLog", method = RequestMethod.GET)
    public ResultBean statFoodsAvgSimLog(@Valid DietAvgVarietyLogSearch sf) {
        sf.setPage(PageRequest.NO_PAGE);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(DietVarietyLog.class);
        Sort s = new Sort("endDate", Sort.ASC);
        pr.addSort(s);
        List<DietVarietyLog> list = baseService.getBeanList(pr);
        ChartData chartData = new ChartData();
        DietType dietType = sf.getDietType();
        String dietTypeName = dietType == null ? "" : dietType.getName();
        chartData.setTitle("[" + dietTypeName + "]多样性");
        chartData.setUnit("%");
        chartData.setLegendData(new String[]{"重复度(%)"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("重复度(%)");
        for (DietVarietyLog bean : list) {
            chartData.getXdata().add(DateUtil.getFormatDate(bean.getEndDate(), DateUtil.FormatDay1));
            yData1.getData().add(PriceUtil.changeToString(0, bean.getVariety() * 100));
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }
}
