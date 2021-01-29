package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.domain.LifeExperience;
import cn.mulanbay.pms.persistent.domain.LifeExperienceDetail;
import cn.mulanbay.pms.persistent.dto.LifeExperienceCostStat;
import cn.mulanbay.pms.persistent.dto.LifeExperienceDateStat;
import cn.mulanbay.pms.persistent.dto.LifeExperienceMapStat;
import cn.mulanbay.pms.persistent.dto.TransferMapStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ExperienceType;
import cn.mulanbay.pms.persistent.enums.LifeExperienceCostStatType;
import cn.mulanbay.pms.persistent.enums.MapType;
import cn.mulanbay.pms.persistent.service.LifeExperienceService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.life.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.life.LocationMapStatChartData;
import cn.mulanbay.pms.web.bean.response.life.TransferMapDoubleStatChartData;
import cn.mulanbay.pms.web.bean.response.life.TransferMapSingleStatChartData;
import cn.mulanbay.pms.web.bean.response.life.TransferMapStatChartData;
import cn.mulanbay.web.bean.response.ResultBean;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.VisualMap;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.MapData;
import com.github.abel533.echarts.series.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 人生经历表
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/lifeExperience")
public class LifeExperienceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LifeExperienceController.class);

    @Autowired
    LifeExperienceService lifeExperienceService;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    private static Class<LifeExperience> beanClass = LifeExperience.class;

    /**
     * 获取类型树列表
     *
     * @return
     */
    @RequestMapping(value = "/getLifeExperienceTree")
    public ResultBean getLifeExperienceTree(LifeExperienceTreeSearch ts) {
        try {
            LifeExperienceSearch sf = new LifeExperienceSearch();
            sf.setUserId(ts.userId);
            PageResult<LifeExperience> qr = this.getLifeExperience(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (LifeExperience le : qr.getBeanList()) {
                TreeBean tb = new TreeBean();
                tb.setId(le.getId().toString());
                tb.setText(le.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, ts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取类型树列表异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(LifeExperienceSearch sf) {
        return callbackDataGrid(getLifeExperience(sf));
    }

    private PageResult<LifeExperience> getLifeExperience(LifeExperienceSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setPageSize(sf.getPageSize());
        pr.setBeanClass(beanClass);
        Sort s = new Sort("startDate", Sort.DESC);
        pr.addSort(s);
        PageResult<LifeExperience> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 获取经历类型树
     *
     * @return
     */
    @RequestMapping(value = "/getTypeTree")
    public ResultBean getTypeTree(Boolean needRoot) {

        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (ExperienceType type : ExperienceType.values()) {
                TreeBean tb = new TreeBean();
                tb.setId(type.name());
                tb.setText(type.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取经历类型树异常",
                    e);
        }
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid LifeExperienceFormRequest formRequest) {
        LifeExperience bean = new LifeExperience();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCost(0.0);
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
        LifeExperience bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid LifeExperienceFormRequest formRequest) {
        LifeExperience bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        if (bean.getCost() == null) {
            bean.setCost(0.0);
        }
        baseService.updateObject(bean);
        return callback(null);
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
     * 地图统计
     * 如果是中国地图、世界地图类型的统计，则直接返回全部封装好的Option
     * 如果是按照地点统计分析（即详细的统计），后端只返回核心数据（用com.github.abel533.echarts比较麻烦，觉得以后的统计还是只返回核心数据）
     *
     * @return
     */
    @RequestMapping(value = "/mapStat", method = RequestMethod.GET)
    public ResultBean mapStat(LifeExperienceMapStatSearch sf) {
        if (sf.getMapType() == MapType.LOCATION) {
            List<LifeExperienceMapStat> list = lifeExperienceService.getLifeExperienceMapStat(sf);
            return this.callback(createLocationMapStat(list, sf.getStatType(), sf.getUserId(), sf.getStartDate(), sf.getEndDate()));
        }else if (sf.getMapType() == MapType.LC_NAME) {
            return this.callback(createLcNameMapStat(sf));
        }
        String mapType = "china";
        // 是否需要显示地图上的地点名称
        boolean showName = true;
        if (sf.getMapType() == MapType.WORLD) {
            mapType = "world";
            showName = false;
        }
        Option option = new Option();
        option.title("人生去过的地方统计", this.getDateTitle(sf));
        List<LifeExperienceMapStat> list = lifeExperienceService.getLifeExperienceMapStat(sf);
        int ps = list.size();
        String subText = "一共去过" + ps + "个省或直辖市";
        subText += "(占比:" + (int) NumberUtil.getPercentValue(ps, 34, 0) + "%)";
        if (sf.getStartDate() != null && sf.getEndDate() != null) {
            subText += "," + DateUtil.getFormatDate(sf.getStartDate(), DateUtil.FormatDay1) + "~" + DateUtil.getFormatDate(sf.getEndDate(), DateUtil.FormatDay1);
        }
        option.title().subtext(subText);
        option.title().x("center");
        option.tooltip().trigger(Trigger.item);
        option.legend().orient(Orient.vertical).left("left").data(sf.getStatType().getName());

        VisualMap visualMap = new VisualMap();
        visualMap.setMin(0);
        visualMap.left("left");
        visualMap.top("bottom");
        visualMap.text(new String[]{"高", "低"});
        visualMap.calculable(true);
        option.visualMap().add(visualMap);
        option.toolbox().show(true).orient(Orient.vertical).left("right").top("center");

        List<MapData> mapDataList = new ArrayList<>();
        int maxValue = 0;
        for (LifeExperienceMapStat dd : list) {
            if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.COUNT) {
                MapData c = new MapData(dd.getName(), dd.getTotalCount().intValue());
                mapDataList.add(c);
                if (dd.getTotalCount().intValue() > maxValue) {
                    maxValue = dd.getTotalCount().intValue();
                }
            } else if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.DAYS) {
                MapData c = new MapData(dd.getName(), dd.getTotalDays().longValue());
                mapDataList.add(c);
                if (dd.getTotalDays().intValue() > maxValue) {
                    maxValue = dd.getTotalDays().intValue();
                }
            } else {
                MapData c = new MapData(dd.getName(), PriceUtil.changeToString(2, dd.getTotalCost()));
                mapDataList.add(c);
                if (dd.getTotalCost().intValue() > maxValue) {
                    maxValue = dd.getTotalCost().intValue();
                }
            }
        }

        //最大值由计算得出
        visualMap.setMax(maxValue);
        Map map = new Map();
        map.name(sf.getStatType().getName()).mapType(mapType).roam(false).label().normal().show(showName);
        map.label().emphasis().show(true);
        map.setData(mapDataList);
        option.series().add(map);
        return callback(option);
    }

    /**
     * 基于地点的统计
     *
     * @param list
     * @return
     */
    private LocationMapStatChartData createLocationMapStat(List<LifeExperienceMapStat> list, LifeExperienceMapStatSearch.StatType statType, Long userId, Date startDate, Date endDate) {
        LocationMapStatChartData chartData = new LocationMapStatChartData();
        chartData.setTitle("人生去过的地方统计");
        if (statType == LifeExperienceMapStatSearch.StatType.COUNT){
            chartData.setName("旅行次数");
            chartData.setUnit("次");
        }else if (statType == LifeExperienceMapStatSearch.StatType.DAYS){
            chartData.setName("旅行天数");
            chartData.setUnit("天");
        }else{
            chartData.setName("旅行花费");
            chartData.setUnit("元");
        }
        List<MapData> dataList = new ArrayList<>();
        List<LifeExperienceMapStat> newList = convertMapStatLocation(list);
        int maxValue = 0;
        for (LifeExperienceMapStat dd : newList) {
            if (statType == LifeExperienceMapStatSearch.StatType.COUNT) {
                int count = dd.getTotalCount().intValue();
                MapData c = new MapData(dd.getName(), count);
                dataList.add(c);
                if (count > maxValue) {
                    maxValue = count;
                }
            }else if (statType == LifeExperienceMapStatSearch.StatType.DAYS) {
                int days = dd.getTotalDays().intValue();
                MapData c = new MapData(dd.getName(), days );
                dataList.add(c);
                if (days > maxValue) {
                    maxValue = days;
                }
            } else {
                double money = dd.getTotalCost()==null ? 0:dd.getTotalCost().doubleValue();
                MapData c = new MapData(dd.getName(), money);
                dataList.add(c);
                if (money > maxValue) {
                    maxValue = (int) money;
                }
            }
        }
        chartData.setDataList(dataList);
        chartData.setMax(maxValue);
        chartData.setGeoCoordMapData(getGeoMapData(userId, startDate, endDate));
        return chartData;

    }

    /**
     * 基于经历的统计(即采用LifeExperience表数据)
     *
     * @param sf
     * @return
     */
    private LocationMapStatChartData createLcNameMapStat(LifeExperienceMapStatSearch sf) {
        LocationMapStatChartData chartData = new LocationMapStatChartData();
        chartData.setTitle("人生去过的地方统计");
        if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.COUNT){
            chartData.setName("旅行次数");
            chartData.setUnit("次");
        }else if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.DAYS){
            chartData.setName("旅行天数");
            chartData.setUnit("天");
        }else{
            chartData.setName("旅行花费");
            chartData.setUnit("元");
        }
        List<MapData> dataList = new ArrayList<>();
        //获取经历列表
        LifeExperienceSearch les = new LifeExperienceSearch();
        BeanCopy.copyProperties(sf,les);
        les.setPage(PageRequest.NO_PAGE);
        PageRequest pr = les.buildQuery();
        pr.setBeanClass(beanClass);
        List<LifeExperience> list = baseService.getBeanList(pr);
        java.util.Map<String, double[]> geoMap = new HashMap<>();
        int maxValue = 0;
        for (LifeExperience dd : list) {
            if(StringUtil.isEmpty(dd.getLocation())){
                continue;
            }
            String name = dd.getName();
            if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.COUNT) {
                MapData c = new MapData(name, 1);
                dataList.add(c);
            }else if (sf.getStatType() == LifeExperienceMapStatSearch.StatType.DAYS) {
                int days = dd.getDays();
                MapData c = new MapData(name, days);
                dataList.add(c);
                if (days > maxValue) {
                    maxValue = days;
                }
            }else {
                double m = dd.getCost()==null ? 0 : dd.getCost();
                MapData c = new MapData(name, m);
                dataList.add(c);
                if (m > maxValue) {
                    maxValue = (int) m;
                }
            }
            String[] geo = dd.getLocation().split(",");
            geoMap.put(name,new double[]{Double.valueOf(geo[0]), Double.valueOf(geo[1])});
        }
        chartData.setDataList(dataList);
        chartData.setMax(maxValue);
        chartData.setGeoCoordMapData(geoMap);
        return chartData;

    }

    /**
     * 获取地理位置数据定义
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    private java.util.Map<String, double[]> getGeoMapData(Long userId, Date startDate, Date endDate) {
        if (startDate == null) {
            startDate = new Date(0L);
        }
        if (endDate == null) {
            endDate = new Date();
        }
        List<Object[]> list = lifeExperienceService.statCityLocation(userId, startDate, endDate);
        java.util.Map<String, double[]> geoMapData = new HashMap<>();
        for (Object[] ss : list) {
            String[] geo = ss[1].toString().split(",");
            geoMapData.put(ss[0].toString(), new double[]{Double.valueOf(geo[0]), Double.valueOf(geo[1])});

        }
        return geoMapData;
    }

    /**
     * 把location字段根据分割符重新统计
     *
     * @param list
     * @return
     */
    private List<LifeExperienceMapStat> convertMapStatLocation(List<LifeExperienceMapStat> list) {
        java.util.Map<String, LifeExperienceMapStat> map = new HashMap<>();
        for (LifeExperienceMapStat dd : list) {
            String name = dd.getName();
            LifeExperienceMapStat stat = map.get(name);
            if (stat == null) {
                LifeExperienceMapStat newStat = new LifeExperienceMapStat();
                newStat.setName(name);
                newStat.setTotalDays(dd.getTotalDays());
                newStat.setTotalCount(dd.getTotalCount());
                map.put(name, newStat);
            } else {
                stat.setTotalCount(stat.getTotalCount().add(dd.getTotalCount()));
                stat.setTotalDays(stat.getTotalDays().add(dd.getTotalDays()));
            }
        }
        List<LifeExperienceMapStat> result = new ArrayList<>();
        for (LifeExperienceMapStat stat : map.values()) {
            result.add(stat);
        }
        return result;
    }

    /**
     * 获取出发城市树
     *
     * @return
     */
    @RequestMapping(value = "/getStartCityTree")
    public ResultBean getStartCityTree(LifeExperienceGetStartCityTreeRequest sf) {
        try {
            List<String> citys = lifeExperienceService.getStartCityList(sf.getUserId());
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (String s : citys) {
                TreeBean tb = new TreeBean();
                tb.setId(s);
                tb.setText(s);
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取出发城市树异常",
                    e);
        }
    }

    /**
     * 针对某个经历迁徙地图统计
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/transferMapByLifeExpStat")
    public ResultBean transferMapByLifeExpStat(Long id) {
        LifeExperience lifeExperience = baseService.getObject(LifeExperience.class, id);
        LifeExperienceDetailSearch sf = new LifeExperienceDetailSearch();
        sf.setLifeExperienceId(id);
        sf.setMapStat(true);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("occurDate", Sort.ASC);
        pr.addSort(s);
        pr.setBeanClass(LifeExperienceDetail.class);
        List<LifeExperienceDetail> detailList = baseService.getBeanList(pr);
        TransferMapDoubleStatChartData response = new TransferMapDoubleStatChartData();
        response.setTitle(lifeExperience.getName() + "线路");
        response.setSubTitle("");
        response.setGeoCoordMapData(getGeoMapData(lifeExperience.getUserId(), lifeExperience.getStartDate(), lifeExperience.getEndDate()));
        List<TransferMapStat> list = new ArrayList<>();
        BigInteger totalCount = BigInteger.valueOf(1L);
        for (LifeExperienceDetail detail : detailList) {
            TransferMapStat tms = new TransferMapStat();
            tms.setStartCity(detail.getStartCity());
            tms.setArriveCity(detail.getArriveCity());
            tms.setTotalCount(totalCount);
            list.add(tms);
        }
        response.setStatData(list);
        return callback(response);
    }

    /**
     * 迁徙地图统计
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/transferMapStat")
    public ResultBean transferMapStat(LifeExperienceMapStatSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        if (sf.getMapType() == MapType.TRANSFER_DOUBLE) {
            return callback(this.createDoubleTransferMapStatResponse(sf));
        } else {
            return callback(this.createSingleTransferMapStatResponse(sf));
        }
    }

    /**
     * 单向迁移地图数据封装
     *
     * @param sf
     * @return
     */
    private TransferMapStatChartData createDoubleTransferMapStatResponse(LifeExperienceMapStatSearch sf) {
        TransferMapDoubleStatChartData response = new TransferMapDoubleStatChartData();
        response.setTitle("人生经历线路统计");
        response.setSubTitle(this.getDateTitle(sf));
        response.setGeoCoordMapData(getGeoMapData(sf.getUserId(), sf.getStartDate(), sf.getEndDate()));
        //sf.setUserId(this.getCurrentUserId());
        List<TransferMapStat> list = lifeExperienceService.statTransMap(sf);
        response.setStatData(list);
        return response;
    }

    /**
     * 单向迁移地图数据封装
     *
     * @param sf
     * @return
     */
    private TransferMapStatChartData createSingleTransferMapStatResponse(LifeExperienceMapStatSearch sf) {
        TransferMapSingleStatChartData response = new TransferMapSingleStatChartData();
        response.setTitle("人生经历线路统计");
        response.setSubTitle(this.getDateTitle(sf));
        response.setGeoCoordMapData(getGeoMapData(sf.getUserId(), sf.getStartDate(), sf.getEndDate()));
        //sf.setUserId(this.getCurrentUserId());
        List<String> startCitys = null;
        if (StringUtil.isEmpty(sf.getStartCity())) {
            //获取全部
            startCitys = lifeExperienceService.getStartCityList(sf.getUserId());
        } else {
            startCitys = new ArrayList<>();
            startCitys.add(sf.getStartCity());
        }
        response.setLegendData(startCitys);
        for (String s : startCitys) {
            List<TransferMapStat> list = lifeExperienceService.statTransMap(sf);
            if (StringUtil.isNotEmpty(list)) {
                sf.setStartCity(s);
                response.getStatData().add(list);
            }
        }
        return response;
    }

    /**
     * 基于日期的统计
     * 界面上使用echarts展示图表，后端返回的是核心模块的数据，不再使用Echarts的第三方jar包封装（比较麻烦）
     *
     * @return
     */
    @RequestMapping(value = "/dateStat", method = RequestMethod.GET)
    public ResultBean dateStat(LifeExperienceDateStatSearch sf) {
        sf.setIntTypes(sf.getTypes());
        List<LifeExperienceDateStat> list = lifeExperienceService.statDateLifeExperience(sf);
        if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
            return callback(ChartUtil.createChartCalendarData("人生经历统计", "次数", "次", sf, list));
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("人生经历统计");
        chartData.setLegendData(new String[]{"天数","次数"});
        //混合图形下使用
        chartData.addYAxis("天数","天");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("天数");
        for (LifeExperienceDateStat bean : list) {
            chartData.addXData(bean, sf.getDateGroupType());
            yData1.getData().add(bean.getTotalCount());
            yData2.getData().add(bean.getTotalDays());
        }
        chartData.getYdata().add(yData2);
        chartData.getYdata().add(yData1);
        chartData = ChartUtil.completeDate(chartData, sf);
        return callback(chartData);
    }

    /**
     * 同期对比
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid LifeExperienceYoyStatSearch sf) {
        ChartData chartData = initYoyCharData(sf, "人生经历同期对比", null);
        String[] legendData = new String[sf.getYears().size()];
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            //数据,为了代码复用及统一，统计还是按照日期的统计
            LifeExperienceDateStatSearch dateSearch = new LifeExperienceDateStatSearch();
            dateSearch.setDateGroupType(sf.getDateGroupType());
            dateSearch.setStartDate(DateUtil.getDate(sf.getYears().get(i) + "-01-01", DateUtil.FormatDay1));
            dateSearch.setEndDate(DateUtil.getDate(sf.getYears().get(i) + "-12-31", DateUtil.FormatDay1));
            dateSearch.setUserId(sf.getUserId());
            dateSearch.setIntTypes(sf.getTypes());
            ChartYData yData = new ChartYData();
            yData.setName(sf.getYears().get(i).toString());
            List<LifeExperienceDateStat> list = lifeExperienceService.statDateLifeExperience(dateSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (LifeExperienceDateStat bean : list) {
                temp.addXData(bean, sf.getDateGroupType());
                if (sf.getGroupType() == GroupType.COUNT) {
                    yData.getData().add(bean.getTotalCount());
                } else if (sf.getGroupType() == GroupType.DAYS) {
                    yData.getData().add(bean.getTotalDays());
                }
            }
            //临时内容，作为补全用
            temp.getYdata().add(yData);
            dateSearch.setCompliteDate(true);
            temp = ChartUtil.completeDate(temp, dateSearch);
            //设置到最终的结果集中
            chartData.getYdata().add(temp.getYdata().get(0));
        }
        chartData.setLegendData(legendData);

        return callback(chartData);
    }

    /**
     * 修正花费和天数
     *
     * @param revise
     * @return
     */
    @RequestMapping(value = "/revise")
    public ResultBean revise(LifeExperienceRevise revise) {
        lifeExperienceService.reviseLifeExperience(revise);
        return callback(null);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/costStat", method = RequestMethod.GET)
    public ResultBean costStat(LifeExperienceCostStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        String title = "人生经历花费分析";
        Long lifeExperienceId = sf.getLifeExperienceId();
        if (lifeExperienceId != null) {
            LifeExperience bean = this.getUserEntity(beanClass, lifeExperienceId, sf.getUserId());
            title = "[" + bean.getName() + "]花费分析";
        }
        chartPieData.setTitle(title);
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("费用");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        List<LifeExperienceCostStat> list;
        LifeExperienceCostStatType statType = sf.getStatType();
        if (statType == LifeExperienceCostStatType.CONSUME_TYPE) {
            list = lifeExperienceService.statCostLifeExperienceByConsumeType(sf);
        } else if (statType == LifeExperienceCostStatType.TYPE) {
            list = lifeExperienceService.statCostLifeExperienceByType(sf);
        } else {
            list = lifeExperienceService.statCostLifeExperienceById(sf);
        }
        for (LifeExperienceCostStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(PriceUtil.changeToString(2, bean.getTotalCost()));
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(bean.getTotalCost());
        }
        String subTitle = "总花费：" + PriceUtil.changeToString(2, totalValue) + "元";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 人生经历的词云
     *
     * @return
     */
    @RequestMapping(value = "/wordCloudStat", method = RequestMethod.GET)
    public ResultBean wordCloudStat(@Valid LifeExperienceWouldCloudStatSearch sf) {
        try {
            List<String> tagsList = lifeExperienceService.getTagsList(sf);
            List<String> wordList = new ArrayList<>();
            for (String s : tagsList) {
                String[] ss = s.split(",");
                for (String w : ss) {
                    wordList.add(w);
                }
            }
            String picPath = ahaNLPHandler.wordCloud(wordList, sf.getPicWidth(), sf.getPicHeight());
            String imgBase64 = FileUtil.encodeImageTOBase64(picPath);
            //删除
            new File(picPath).delete();
            return callback(imgBase64);
        } catch (Exception e) {
            logger.error("生成词云异常", e);
            return callbackErrorInfo("生成词云异常:" + e.getMessage());
        }
    }

}
