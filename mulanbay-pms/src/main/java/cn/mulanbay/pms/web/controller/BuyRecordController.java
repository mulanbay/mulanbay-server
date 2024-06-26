package cn.mulanbay.pms.web.controller;

import cn.mulanbay.ai.nlp.processor.NLPProcessor;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.ConfigKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.handler.ConsumeHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.bean.BuyRecordMatchBean;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.*;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.buy.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.buy.BuyRecordCostStatVo;
import cn.mulanbay.pms.web.bean.response.buy.ChartRadarGroupVo;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 购买记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/buyRecord")
public class BuyRecordController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BuyRecordController.class);

    private static Class<BuyRecord> beanClass = BuyRecord.class;

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    LifeExperienceService lifeExperienceService;

    @Autowired
    AuthService authService;

    @Autowired
    BudgetHandler budgetHandler;

    @Autowired
    IncomeService incomeService;

    @Autowired
    NLPProcessor nlpProcessor;

    @Autowired
    ConsumeHandler consumeHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    DataService dataService;

    // # 购买记录统计中是否启用商品类型里的可统计字段
    @Value("${system.buyRecord.stat.useStatable}")
    boolean useStatable;

    /**
     * 关键字列表
     *
     * @return
     */
    @RequestMapping(value = "/getKeywordsTree")
    public ResultBean getKeywordsTree(BuyRecordKeywordsSearch sf) {
        List<TreeBean> list = new ArrayList<TreeBean>();
        if(sf.getStartDate()==null&&sf.getEndDate()==null){
            Date[] range = this.getTagsDateRange();
            sf.setStartDate(range[0]);
            sf.setEndDate(range[1]);
        }
        List<String> keywordsList = buyRecordService.getKeywordsList(sf);
        //去重
        Set<String> keywordsSet = TreeBeanUtil.deleteDuplicate(keywordsList);
        for (String s : keywordsSet) {
            TreeBean tb = new TreeBean();
            tb.setId(s);
            tb.setText(s);
            list.add(tb);
        }
        return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
    }

    /**
     * 获取标签的时间段
     * @return
     */
    private Date[] getTagsDateRange(){
        Date[] ds = systemConfigHandler.getDateRange(null,"buyRecord.tags.days");
        return ds;
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BuyRecordSearch sf) {
        PageRequest pr = sf.buildQuery();
        if (StringUtil.isEmpty(sf.getSortField())) {
            Sort s = new Sort("buyDate", Sort.DESC);
            pr.addSort(s);
        } else {
            Sort s = new Sort(sf.getSortField(), sf.getSortType());
            pr.addSort(s);
        }
        pr.setBeanClass(beanClass);
        PageResult<BuyRecord> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 总的概要统计
     * 这里的出售已经不准了，因为售出的时间应该用deleteDate查询了
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(BuyRecordSearch sf) {
        sf.setMoneyFlow(MoneyFlow.BUY);
        BuyRecordStat buyData = buyRecordService.getBuyRecordStat(sf);
        sf.setMoneyFlow(MoneyFlow.SALE);
        BuyRecordStat saleData = buyRecordService.getBuyRecordStat(sf);
        List<BuyRecordStat> list = new ArrayList<>();
        list.add(buyData);
        list.add(saleData);
        return callback(list);
    }

    /**
     * 统计消费金额，包含看病费用
     *
     * @return
     */
    @RequestMapping(value = "/statWithTreat", method = RequestMethod.GET)
    public ResultBean statWithTreat(BuyRecordAnalyseStatSearch sf) {
        sf.setType(GroupType.TOTALPRICE);
        sf.setGroupField("goods_type_id");
        List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(sf);
        return callback(this.createAnalyseStatPieData(list, sf));
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BuyRecordFormRequest formRequest) {
        BuyRecord buyRecord = new BuyRecord();
        changeFormToBean(formRequest, buyRecord);
        buyRecord.setCreatedTime(new Date());
        buyRecordService.saveBuyRecord(buyRecord);
        updateIncome(buyRecord);
        consumeHandler.addToCache(buyRecord);
        return callback(buyRecord);
    }

    /**
     * 转换
     * @param formRequest
     * @param buyRecord
     */
    private void changeFormToBean(BuyRecordFormRequest formRequest, BuyRecord buyRecord) {
        BeanCopy.copyProperties(formRequest, buyRecord, true);
        buyRecord.setTotalPrice(buyRecord.getPrice() * buyRecord.getAmount()
                + buyRecord.getShipment());
        //不判断会导致保存异常。当不选择商品子类型时，spring mvc会初始化一个空的GoodsType对象（id=null）
        if (formRequest.getSubGoodsTypeId() != null) {
            GoodsType subGoodsType = this.getUserEntity(GoodsType.class, formRequest.getSubGoodsTypeId(), formRequest.getUserId());
            buyRecord.setSubGoodsType(subGoodsType);
        } else {
            buyRecord.setSubGoodsType(null);
        }
        BuyType buyType = this.getUserEntity(BuyType.class, formRequest.getBuyTypeId(), formRequest.getUserId());
        buyRecord.setBuyType(buyType);
        GoodsType goodsType = this.getUserEntity(GoodsType.class, formRequest.getGoodsTypeId(), formRequest.getUserId());
        buyRecord.setGoodsType(goodsType);
        //消费日期默认为购买日期
        if (buyRecord.getConsumeDate() == null) {
            buyRecord.setConsumeDate(buyRecord.getBuyDate());
        }
        //设置总价
        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.add(new BigDecimal(formRequest.getPrice()).multiply(new BigDecimal(formRequest.getAmount())));
        totalPrice = totalPrice.add(new BigDecimal(formRequest.getShipment()));
        buyRecord.setTotalPrice(totalPrice.doubleValue());
        //设置使用时长
        if(buyRecord.getDeleteDate()!=null){
            long usedTime = buyRecord.getDeleteDate().getTime()-buyRecord.getBuyDate().getTime();
            buyRecord.setUseTime(usedTime);
        }
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BuyRecordFormRequest formRequest) {
        BuyRecord buyRecord = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        changeFormToBean(formRequest, buyRecord);
        buyRecord.setLastModifyTime(new Date());
        buyRecordService.updateBuyRecord(buyRecord);
        lifeExperienceService.updateLifeExperienceConsumeByBuyRecord(buyRecord);
        updateIncome(buyRecord);
        return callback(buyRecord);
    }

    /**
     * 如果有出售记录，则增加收入
     *
     * @param buyRecord
     */
    private void updateIncome(BuyRecord buyRecord) {
        Double money = buyRecord.getSoldPrice();
        if (money == null || PriceUtil.priceEquals(money, 0)) {
            return;
        }
        Long id = buyRecord.getId();
        Income income = incomeService.getIncomeByBuyRecord(id);
        if (income == null) {
            income = new Income();
            income.setAmount(money);
            income.setBuyRecordId(id);
            income.setCreatedTime(new Date());
            income.setName(buyRecord.getGoodsName());
            income.setOccurTime(buyRecord.getDeleteDate());
            income.setRemark("从消费记录自动生成");
            income.setStatus(CommonStatus.ENABLE);
            income.setUserId(buyRecord.getUserId());
            baseService.saveObject(income);
        } else {
            //只有价格修改的才要更新
            if (!PriceUtil.priceEquals(money, income.getAmount())) {
                income.setAmount(money);
                income.setLastModifyTime(new Date());
                income.setRemark("从消费记录自动更新");
                baseService.updateObject(income);
            }
        }
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        BuyRecord buyRecord = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(buyRecord);
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
     * 设置上级
     *
     * @return
     */
    @RequestMapping(value = "/setParent", method = RequestMethod.POST)
    public ResultBean setParent(@RequestBody @Valid BuyRecordSetParentRequest spr) {
        buyRecordService.setParent(spr.getId(),spr.getPid());
        return callback(null);
    }

    /**
     * 取消上级
     *
     * @return
     */
    @RequestMapping(value = "/deleteParent", method = RequestMethod.POST)
    public ResultBean deleteParent(@RequestBody @Valid BuyRecordSetParentRequest spr) {
        buyRecordService.deleteParent(spr.getId());
        return callback(null);
    }

    /**
     * 取消下级
     *
     * @return
     */
    @RequestMapping(value = "/deleteChildren", method = RequestMethod.POST)
    public ResultBean deleteChildren(@RequestBody @Valid BuyRecordDeleteChildrenRequest dcr) {
        buyRecordService.deleteChildren(dcr.getPid());
        return callback(null);
    }


    /**
     * 统计分析
     *
     * @return
     */
    @RequestMapping(value = "/analyseStat")
    public ResultBean analyseStat(BuyRecordAnalyseStatSearch sf) {
        sf.setUseStatable(useStatable);
        if (sf.getChartType() == ChartType.BAR) {
            List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(sf);
            return callback(this.createAnalyseStatBarData(list, sf));
        } else if (sf.getChartType() == ChartType.PIE) {
            List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(sf);
            return callback(this.createAnalyseStatPieData(list, sf));
        } else if (sf.getChartType() == ChartType.TREE_MAP) {
            //只有按照商品子类型的才能
            if (!"sub_goods_type_id".equals(sf.getGroupField())) {
                return callbackErrorInfo("只有按照商品子类型分组的才支持该分析图型");
            }
            List<BuyRecordRealTimeTreeStat> list = buyRecordService.getAnalyseTreeStat(sf);
            return callback(this.createAnalyseStatTreeMapData(list, sf));
        } else {
            return callback(this.createAnalyseStatRadarData(sf));
        }
    }

    /**
     * 成本统计
     *
     * @return
     */
    @RequestMapping(value = "/costStat")
    public ResultBean costStat(BuyRecordStatCostRequest tcr) {
        BuyRecord buyRecord = this.getUserEntity(beanClass, tcr.getId(), tcr.getUserId());
        BuyRecordCostStatVo vo = new BuyRecordCostStatVo();
        BeanCopy.copyProperties(buyRecord,vo);
        Date expDate = vo.getDeleteDate();
        if(expDate==null){
            expDate = new Date();
        }else{
            long expMillSecs = expDate.getTime()-vo.getBuyDate().getTime();
            vo.setExpMillSecs(expMillSecs);
        }
        long usedMillSecs = expDate.getTime()-vo.getBuyDate().getTime();
        vo.setUsedMillSecs(usedMillSecs);
        long usedDays = usedMillSecs / (24*3600*1000);
        if(usedDays<=0){
            usedDays=1;
        }
        //计算下一级
        boolean deepCost = tcr.getDeepCost();
        BuyRecordChildrenCost cc = null;
        if(deepCost==true){
            cc = buyRecordService.getChildrenTotalDeepCost(tcr.getId());
        }else{
            cc = buyRecordService.getChildrenTotalCost(tcr.getId());
        }
        Long childrens = cc.getTotalCount()==null ? null: cc.getTotalCount().longValue();
        vo.setChildrens(childrens);
        if(cc.getSoldPrice()!=null){
            vo.setChildrenSoldPrice(NumberUtil.getDoubleValue(cc.getSoldPrice().doubleValue(),2));
        }
        BigDecimal ctp = cc.getTotalPrice()==null ? new BigDecimal(0) : cc.getTotalPrice();
        vo.setChildrenPrice(NumberUtil.getDoubleValue(ctp.doubleValue(),2));
        //总成本=商品价格+下一级商品成本
        BigDecimal totalCost = ctp.add(new BigDecimal(vo.getTotalPrice()));
        vo.setTotalCost(NumberUtil.getDoubleValue(totalCost.doubleValue(),2));
        //计算每天花费
        BigDecimal vs = vo.getSoldPrice()==null ? new BigDecimal(0) : new BigDecimal(vo.getSoldPrice());
        BigDecimal cts = cc.getSoldPrice()==null ? new BigDecimal(0) : cc.getSoldPrice();
        // (买入价格-出售价格)/使用天数
        Double costPerDay = ((new BigDecimal(vo.getTotalPrice()).subtract(vs)).divide(new BigDecimal(usedDays),2,BigDecimal.ROUND_HALF_UP)).doubleValue();
        // (买入价格-出售价格+下一级商品成本-下一级商品出售价格)/使用天数
        Double totalCostPerDay = ((new BigDecimal(vo.getTotalPrice()).subtract(vs).add(ctp).subtract(cts)).divide(new BigDecimal(usedDays),2,BigDecimal.ROUND_HALF_UP)).doubleValue();
        vo.setCostPerDay(costPerDay);
        vo.setTotalCostPerDay(totalCostPerDay);
        if(vo.getSoldPrice()!=null){
            //折旧率
            // 买入价格/出售价格
            Double depRate = vo.getSoldPrice()*10/vo.getTotalPrice();
            // 买入价格/(总成本-下一级商品出售价格)
            Double totalDepRate = vo.getSoldPrice()*10/(totalCost.subtract(cts).doubleValue());
            vo.setDepRate(depRate);
            vo.setTotalDepRate(totalDepRate);
        }
        return callback(vo);
    }

    /**
     * 子集树形统计
     *
     * @return
     */
    @RequestMapping(value = "/treeStat", method = RequestMethod.GET)
    public ResultBean treeStat(@Valid CommonBeanGetRequest getRequest) {
        Long rootId = getRequest.getId();
        BuyRecord buyRecord = this.getUserEntity(beanClass, rootId, getRequest.getUserId());
        List<BuyRecordCascadeDto> children = buyRecordService.getChildrenDeepList(rootId);
        // 转换为Map
        Map<Long,BuyRecordCascadeDto> map = new HashMap<>();
        // 添加根节点信息
        BuyRecordCascadeDto root = new BuyRecordCascadeDto();
        root.setGoodsName(buyRecord.getGoodsName());
        map.put(rootId,root);
        BigDecimal totalCost = new BigDecimal(buyRecord.getTotalPrice());
        for (BuyRecordCascadeDto child : children){
            map.put(child.getId().longValue(),child);
            totalCost = totalCost.add(child.getTotalPrice());
        }
        ChartTreeDetailData rootData = new ChartTreeDetailData(buyRecord.getTotalPrice(), buyRecord.getGoodsName(),false);
        ChartTreeDetailData data = this.generateTree(rootData,map,children);
        ChartTreeData treeData = new ChartTreeData();
        treeData.setData(data);
        treeData.setUnit("元");
        treeData.setTitle("商品关系图");
        treeData.setSubTitle("商品总价:"+NumberUtil.getDoubleValue(buyRecord.getTotalPrice(),2)+"元,总成本:"+NumberUtil.getDoubleValue(totalCost.doubleValue(),2)+"元");
        return callback(treeData);
    }

    /**
     * 构建树
     * @param root
     * @param map
     * @param list
     * @return
     */
    private ChartTreeDetailData generateTree(ChartTreeDetailData root,Map<Long,BuyRecordCascadeDto> map,List<BuyRecordCascadeDto> list){
        for (BuyRecordCascadeDto child : list) {
            BuyRecordCascadeDto parent = map.get(child.getPid().longValue());
            String parentName = parent.getGoodsName();
            if (root.getName().equals(parentName)) {
                root.addChild(NumberUtil.getDoubleValue(child.getTotalPrice().doubleValue(),2), child.getGoodsName(),false);
            }
        }
        if (root.getChildren() != null) {
            for (ChartTreeDetailData cc : root.getChildren()) {
                generateTree(cc,map, list);
            }
        }
        return root;
    }

    /**
     * 商品使用寿命
     *
     * @return
     */
    @RequestMapping(value = "/useTimeList")
    public ResultBean useTimeList(BuyRecordUseTimeListSearch sf) {
        sf.setDeleteDateType(NullType.NOT_NULL);
        PageRequest req = sf.buildQuery();
        req.setBeanClass(beanClass);
        if (StringUtil.isEmpty(sf.getSortField())) {
            req.addSort(new Sort("deleteDate", Sort.DESC));
        } else {
            req.addSort(new Sort(sf.getSortField(), sf.getSortType()));
        }
        PageResult<BuyRecord> res = baseService.getBeanResult(req);
        return callbackDataGrid(res);
    }

    /**
     * 商品使用寿命
     *
     * @return
     */
    @RequestMapping(value = "/useTimeStat")
    public ResultBean useTimeStat(BuyRecordUseTimeStatSearch sf) {
        sf.setDeleteDateType(NullType.NOT_NULL);
        List<BuyRecordUseTimeStat> list = buyRecordService.getUseTimeStat(sf);
        Collections.sort(list, (o1, o2) -> {
            //按照平均使用时间排序
            Long t1 = o1.getTotalUseTime().longValue()/o1.getTotalCount().longValue();
            Long t2 = o2.getTotalUseTime().longValue()/o2.getTotalCount().longValue();
            return t2.compareTo(t1);
        });

        ChartData chartData = new ChartData();
        chartData.setTitle("商品使用时间分析");
        //混合图形下使用
        chartData.addYAxis("天数","天");
        chartData.addYAxis("次数","次");
        chartData.setLegendData(new String[]{"平均寿命","次数"});
        ChartYData yData1 = new ChartYData("平均寿命","天");
        ChartYData yData2 = new ChartYData("次数","次");
        for (BuyRecordUseTimeStat bean : list) {
            chartData.getXdata().add(bean.getName().toString());
            long days = bean.getTotalUseTime().longValue()/bean.getTotalCount().longValue()/(24*3600*1000);
            yData1.getData().add(days);
            yData2.getData().add(bean.getTotalCount().longValue());
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return callback(chartData);
    }

    /**
     * 封装消费记录分析的树形图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartTreeMapData createAnalyseStatTreeMapData(List<BuyRecordRealTimeTreeStat> list, BuyRecordAnalyseStatSearch sf) {
        ChartTreeMapData chartData = new ChartTreeMapData();
        chartData.setTitle("消费分析");
        chartData.setName("消费");
        if (sf.getType() == GroupType.COUNT) {
            chartData.setUnit("次");
        } else {
            chartData.setUnit("元");
        }
        BigDecimal totalValue = new BigDecimal(0);
        Map<Integer, ChartTreeMapDetailData> dataMap = new HashMap<>();
        for (BuyRecordRealTimeTreeStat bean : list) {
            totalValue = totalValue.add(new BigDecimal(bean.getValue()));
            ChartTreeMapDetailData mdd = dataMap.get(bean.getGoodsId());
            //只有两层结构
            if (mdd == null) {
                mdd = new ChartTreeMapDetailData(bean.getValue(), bean.getGoodsName(), bean.getGoodsName());
                dataMap.put(bean.getGoodsId(), mdd);
            }
            if (bean.getSubGoodsId() != null) {
                ChartTreeMapDetailData child = new ChartTreeMapDetailData(bean.getValue(),
                        bean.getSubGoodsName(), bean.getGoodsName() + "/" + bean.getSubGoodsName());
                mdd.addChild(child);
            }
        }
        chartData.setData(new ArrayList<>(dataMap.values()));
        String subTitle = this.getDateTitle(sf, getSubTitlePostfix(sf.getType(), totalValue));
        chartData.setSubTitle(subTitle);
        return chartData;

    }

    /**
     * 封装消费分析的雷达图数据
     *
     * @param sf
     * @return
     */
    private ChartRadarData createAnalyseStatRadarData(BuyRecordAnalyseStatSearch sf) {
        ChartRadarData chartRadarData = new ChartRadarData();
        chartRadarData.setTitle("购买记录分析");
        chartRadarData.setUnit(sf.getType().getUnit());
        sf.setDateGroupType(DateGroupType.YEAR);
        List<BuyRecordRadarStat> list = buyRecordService.getRadarStat(sf);
        if (list.isEmpty()) {
            return chartRadarData;
        }
        long maxValue = 0;
        //获取分类
        List<ChartRadarGroupVo> groups = this.getChartRadarGroupData(sf);
        List<Long> initData = new ArrayList<>();

        Map<Integer, List<BuyRecordRadarStat>> map = new TreeMap();
        //转为Map
        int size = list.size();
        for (int i = 0; i < size; i++) {
            BuyRecordRadarStat stat = list.get(i);
            List<BuyRecordRadarStat> statList = map.get(stat.getDateIndexValue());
            if (statList == null) {
                statList = new ArrayList<>();
                map.put(stat.getDateIndexValue(), statList);
            }
            statList.add(stat);
            //获取最大值
            long v = 0L;
            if (sf.getType() == GroupType.COUNT) {
                v = stat.getTotalCount().longValue();
            } else {
                v = stat.getTotalPrice().longValue();
            }
            if (v > maxValue) {
                maxValue = v;
            }
        }
        //设置标签
        for (ChartRadarGroupVo gb : groups) {
            ChartRadarIndicatorData rid = new ChartRadarIndicatorData();
            rid.setText(gb.getName());
            rid.setMax(maxValue);
            chartRadarData.getIndicatorData().add(rid);
            initData.add(Long.valueOf(0 - gb.getId()));
        }
        // 设置LegendData
        for (Integer ii : map.keySet()) {
            chartRadarData.getLegendData().add(ii.toString());
            //
            ChartRadarSerieData serieData = new ChartRadarSerieData();
            List<Long> data = new ArrayList<>();
            //克隆数组
            data.addAll(initData);
            int dataSize = data.size();
            List<BuyRecordRadarStat> statList = map.get(ii);
            for (BuyRecordRadarStat ss : statList) {
                for (int i = 0; i < dataSize; i++) {
                    long v = data.get(i);
                    if (v + ss.getIntGroupIdValue() == 0) {
                        //取代位置
                        if (sf.getType() == GroupType.COUNT) {
                            v = ss.getTotalCount().longValue();
                        } else {
                            v = ss.getTotalPrice().longValue();
                        }
                        data.set(i, v);
                    }
                }
            }
            // 把没有取代位置全部设置为0
            for (int i = 0; i < dataSize; i++) {
                long v = data.get(i);
                if (v < 0) {
                    data.set(i, 0L);
                }
            }
            //不能加中文，否则echarts无法显示lab
            serieData.setName(ii.toString());
            serieData.setData(data);
            chartRadarData.getSeries().add(serieData);
        }

        return chartRadarData;
    }

    /**
     * 获取分组数据
     *
     * @param sf
     * @return
     */
    private List<ChartRadarGroupVo> getChartRadarGroupData(BuyRecordAnalyseStatSearch sf) {
        List<ChartRadarGroupVo> result = new ArrayList<>();
        //获取分类
        if ("goods_type_id".equals(sf.getGroupField())) {
            // 获取商品类型
            GoodsTypeSearch search = new GoodsTypeSearch();
            search.setUserId(sf.getUserId());
            search.setPid(0);
            PageRequest pr = search.buildQuery();
            pr.setBeanClass(GoodsType.class);
            Sort sort = new Sort("id", Sort.ASC);
            pr.addSort(sort);
            List<GoodsType> qr = baseService.getBeanList(pr);
            for (GoodsType bb : qr) {
                ChartRadarGroupVo bean = new ChartRadarGroupVo();
                bean.setId(bb.getId());
                bean.setName(bb.getName());
                result.add(bean);
            }
        } else if ("sub_goods_type_id".equals(sf.getGroupField())) {
            Integer goodsTypeId = sf.getGoodsType();
            if (goodsTypeId == null) {
                //商品子类统计需要父类商品类型
                throw new ApplicationException(PmsErrorCode.BUY_RECORD_GOODS_TYPE_NULL);
            }
            // 获取商品类型
            GoodsTypeSearch search = new GoodsTypeSearch();
            search.setUserId(sf.getUserId());
            search.setPid(goodsTypeId);
            PageRequest pr = search.buildQuery();
            pr.setBeanClass(GoodsType.class);
            Sort sort = new Sort("id", Sort.ASC);
            pr.addSort(sort);
            List<GoodsType> qr = baseService.getBeanList(pr);
            for (GoodsType bb : qr) {
                ChartRadarGroupVo bean = new ChartRadarGroupVo();
                bean.setId(bb.getId());
                bean.setName(bb.getName());
                result.add(bean);
            }
        } else if ("buy_type_id".equals(sf.getGroupField())) {
            BuyTypeSearch search = new BuyTypeSearch();
            search.setUserId(sf.getUserId());
            PageRequest pr = search.buildQuery();
            pr.setBeanClass(BuyType.class);
            Sort sort = new Sort("id", Sort.ASC);
            pr.addSort(sort);
            List<BuyType> qr = baseService.getBeanList(pr);
            for (BuyType bb : qr) {
                ChartRadarGroupVo bean = new ChartRadarGroupVo();
                bean.setId(bb.getId());
                bean.setName(bb.getName());
                result.add(bean);
            }
        } else if ("payment".equals(sf.getGroupField())) {
            // 未知
            ChartRadarGroupVo unKnown = new ChartRadarGroupVo();
            unKnown.setId(-1);
            unKnown.setName("未知");
            result.add(unKnown);

            for (Payment p : Payment.values()) {
                ChartRadarGroupVo bean = new ChartRadarGroupVo();
                bean.setId(p.ordinal());
                bean.setName(p.getName());
                result.add(bean);
            }
        } else if ("price_region".equals(sf.getGroupField())) {
            PriceRegionSearch search = new PriceRegionSearch();
            search.setUserId(sf.getUserId());
            PageRequest pr = search.buildQuery();
            pr.setBeanClass(PriceRegion.class);
            Sort sort = new Sort("id", Sort.ASC);
            pr.addSort(sort);
            List<PriceRegion> qr = baseService.getBeanList(pr);
            for (PriceRegion bb : qr) {
                ChartRadarGroupVo bean = new ChartRadarGroupVo();
                bean.setId(bb.getId());
                bean.setName(bb.getName() + "(" + bb.getMinPrice().longValue() + "-" + bb.getMaxPrice().longValue() + "元)");
                result.add(bean);
            }
        } else {
            throw new ApplicationException(PmsErrorCode.UNSUPPORT_BUY_RECORD_GROUP_TYPE);
        }
        //todo 其他
        return result;
    }

    /**
     * 封装消费分析的饼状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartPieData createAnalyseStatPieData(List<BuyRecordRealTimeStat> list, BuyRecordAnalyseStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("消费分析");
        chartPieData.setUnit(sf.getType().getUnit());
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName(sf.getType().getName());
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (BuyRecordRealTimeStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getValue()));
        }
        String subTitle = this.getDateTitle(sf, getSubTitlePostfix(sf.getType(), totalValue));
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 封装消费记录分析的柱状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartData createAnalyseStatBarData(List<BuyRecordRealTimeStat> list, BuyRecordAnalyseStatSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("消费分析");
        chartData.setUnit(sf.getType().getUnit());
        chartData.setLegendData(new String[]{sf.getType().getName()});
        ChartYData yData = new ChartYData();
        yData.setName(sf.getType().getName());
        BigDecimal totalValue = new BigDecimal(0);
        for (BuyRecordRealTimeStat bean : list) {
            chartData.getXdata().add(bean.getName());
            yData.getData().add(bean.getValue());
            totalValue = totalValue.add(new BigDecimal(bean.getValue()));
        }
        String subTitle = this.getDateTitle(sf, getSubTitlePostfix(sf.getType(), totalValue));
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        return chartData;

    }

    /**
     * 获取子标题后缀
     *
     * @param groupType
     * @param totalValue
     * @return
     */
    private String getSubTitlePostfix(GroupType groupType, BigDecimal totalValue) {
        if (groupType == GroupType.COUNT) {
            return PriceUtil.changeToString(0, totalValue) + "次";
        } else {
            return PriceUtil.changeToString(2, totalValue) + "元";
        }
    }


    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(BuyRecordDateStatSearch sf) {
        sf.setUseStatable(useStatable);
        switch (sf.getDateGroupType()){
            case DAYCALENDAR :
                //日历
                List<BuyRecordDateStat> list = buyRecordService.statBuyRecordByDate(sf);
                return callback(ChartUtil.createChartCalendarData("消费统计", "次数", "次", sf, list));
            case HOURMINUTE :
                //散点图
                PageRequest pr = sf.buildQuery();
                pr.setBeanClass(beanClass);
                List<Date> dateList = dataService.getDateList(pr,"buyDate");
                return callback(this.createHMChartData(dateList,"消费分析","消费时间点"));
            default:
                break;
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("消费统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"消费","次数"});
        //混合图形下使用(最后一组数据默认为次数)
        chartData.addYAxis("消费","元");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("消费","元");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        List<BuyRecordDateStat> list = buyRecordService.statBuyRecordByDate(sf);
        for (BuyRecordDateStat bean : list) {
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

    /**
     * 同期比对统计
     *
     * @return
     */
    @RequestMapping(value = "/yoyStat")
    public ResultBean yoyStat(@Valid BuyRecordYoyStatSearch sf) {
        if (sf.getDateGroupType() == DateGroupType.DAY) {
            return callback(createChartCalendarMultiData(sf));
        }
        String unit = sf.getGroupType().getUnit();
        ChartData chartData = initYoyCharData(sf, "消费统计同期对比", null);
        chartData.setUnit(unit);
        String[] legendData = new String[sf.getYears().size()];
        for (int i = 0; i < sf.getYears().size(); i++) {
            legendData[i] = sf.getYears().get(i).toString();
            //数据,为了代码复用及统一，统计还是按照日期的统计
            BuyRecordDateStatSearch dateSearch = generateSearch(sf.getYears().get(i), sf);
            ChartYData yData = new ChartYData();
            yData.setName(sf.getYears().get(i).toString());
            yData.setUnit(unit);
            List<BuyRecordDateStat> list = buyRecordService.statBuyRecordByDate(dateSearch);
            //临时内容，作为补全用
            ChartData temp = new ChartData();
            for (BuyRecordDateStat bean : list) {
                temp.addXData(bean, sf.getDateGroupType());
                if (sf.getGroupType() == GroupType.COUNT) {
                    yData.getData().add(bean.getTotalCount());
                } else {
                    yData.getData().add(bean.getTotalPrice());
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
     * 基于日历的热点图
     *
     * @param sf
     * @return
     */
    private ChartCalendarMultiData createChartCalendarMultiData(BuyRecordYoyStatSearch sf) {
        ChartCalendarMultiData data = new ChartCalendarMultiData();
        data.setTitle("消费统计同期对比");
        if (sf.getGroupType() == GroupType.COUNT) {
            data.setUnit("次");
        } else {
            data.setUnit("元");
        }
        for (int i = 0; i < sf.getYears().size(); i++) {
            BuyRecordDateStatSearch dateSearch = generateSearch(sf.getYears().get(i), sf);
            List<BuyRecordDateStat> list = buyRecordService.statBuyRecordByDate(dateSearch);
            for (BuyRecordDateStat bean : list) {
                String dateString = DateUtil.getFormatDateString(bean.getDateIndexValue().toString(), "yyyyMMdd", "yyyy-MM-dd");
                if (sf.getGroupType() == GroupType.COUNT) {
                    data.addData(sf.getYears().get(i), dateString, bean.getTotalCount());
                } else {
                    data.addData(sf.getYears().get(i), dateString, bean.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        }
        return data;
    }

    private BuyRecordDateStatSearch generateSearch(int year, BuyRecordYoyStatSearch sf) {
        BuyRecordDateStatSearch dateSearch = new BuyRecordDateStatSearch();
        dateSearch.setDateGroupType(sf.getDateGroupType());
        dateSearch.setStartDate(DateUtil.getDate(year + "-01-01", DateUtil.FormatDay1));
        dateSearch.setEndDate(DateUtil.getDate(year + "-12-31", DateUtil.FormatDay1));
        dateSearch.setUserId(sf.getUserId());
        dateSearch.setStartTotalPrice(sf.getStartTotalPrice());
        dateSearch.setEndTotalPrice(sf.getEndTotalPrice());
        dateSearch.setPriceType(BuyRecordPriceType.TOTALPRICE);
        dateSearch.setBuyType(sf.getBuyType());
        dateSearch.setGoodsType(sf.getGoodsType());
        dateSearch.setConsumeType(sf.getConsumeType());
        dateSearch.setSubGoodsType(sf.getSubGoodsType());
        dateSearch.setUseStatable(useStatable);
        dateSearch.setSecondhand(sf.getSecondhand());
        return dateSearch;
    }

    /**
     * 根据关键字统计
     *
     * @return
     */
    @RequestMapping(value = "/keywordsStat")
    public ResultBean keywordsStat(@Valid BuyRecordKeywordsStatSearch sf) {
        sf.setUseStatable(useStatable);
        List<BuyRecordKeywordsStat> list = buyRecordService.statBuyRecordByKeywords(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle("关键字统计");
        chartData.setSubTitle(this.getDateTitle(sf));
        chartData.setLegendData(new String[]{"消费","次数"});
        //混合图形下使用
        chartData.addYAxis("消费","元");
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData("次数","次");
        ChartYData yData2 = new ChartYData("消费","元");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        for (BuyRecordKeywordsStat bean : list) {
            chartData.getXdata().add(bean.getKeywords());
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

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/keywordsDetailStat", method = RequestMethod.GET)
    public ResultBean keywordsDetailStat(BuyRecordAnalyseStatSearch basf) {
        basf.setType(GroupType.TOTALPRICE);
        List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(basf);
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("[" + basf.getKeywords() + "]的消费分析");
        chartPieData.setUnit("元");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("费用");
        //总的值
        double totalValue = 0;
        for (BuyRecordRealTimeStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue += bean.getValue();
        }
        String subTitle = "花费总金额:" + PriceUtil.changeToString(2, totalValue) + "元";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 商品相似度
     *
     * @return
     */
    @RequestMapping(value = "/getGoodsNameAvgSimilarity", method = RequestMethod.GET)
    public ResultBean getGoodsNameAvgSimilarity(@Valid BuyRecordVarietyStatSearch sf) {
        List<String> list = buyRecordService.getGoodsNameList(sf);
        return callback(nlpProcessor.avgSentenceSimilarity(list));
    }

    /**
     * 商品词云统计
     *
     * @return
     */
    @RequestMapping(value = "/statWordCloud", method = RequestMethod.GET)
    public ResultBean statWordCloud(@Valid BuyRecordWordCloudSearch sf) {
        List<String> list = buyRecordService.getBuyRecordWordCloudStat(sf);
        Map<String,Integer> statData = new HashMap<>();
        Integer num = systemConfigHandler.getIntegerConfig(ConfigKey.NLP_BUYRECORD_GOODSNAME_EKNUM);
        String field = sf.getField();
        for (String d : list) {
            if("goodsName".equals(field)||"skuInfo".equals(field)){
                //先分词
                List<String> keywords = nlpProcessor.extractKeyword(d,num);
                for(String s : keywords){
                    //忽略分词后为词长度为1的
                    if(sf.getIgnoreShort()!=null&&sf.getIgnoreShort()){
                        if(s.length()<2){
                            continue;
                        }
                    }
                    Integer n = statData.get(s);
                    if(n==null){
                        statData.put(s,1);
                    }else{
                        statData.put(s,n+1);
                    }
                }
            }else if("shopName".equals(field)||"brand".equals(field)){
                Integer n = statData.get(d);
                if(n==null){
                    statData.put(d,1);
                }else{
                    statData.put(d,n+1);
                }
            }else if("keywords".equals(field)){
                String[] keywords = d.split(",");
                for(String s : keywords){
                    Integer n = statData.get(s);
                    if(n==null){
                        statData.put(s,1);
                    }else{
                        statData.put(s,n+1);
                    }
                }
            }

        }

        ChartWorldCloudData chartData = new ChartWorldCloudData();
        for(String key : statData.keySet()){
            ChartNameValueVo dd = new ChartNameValueVo();
            dd.setName(key);
            dd.setValue(statData.get(key));
            chartData.addData(dd);
        }
        chartData.setTitle("消费词云");
        return callback(chartData);
    }


    /**
     * 根据商品名智能分析出其分类及品牌等
     * 如果没有配置NLP直接返回空对象
     * @return
     */
    @RequestMapping(value = "/aiMatch", method = RequestMethod.POST)
    public ResultBean aiMatch(@RequestBody @Valid GoodsNameAiMatchRequest mr) {
        BuyRecordMatchBean bean = null;
        try {
            bean = consumeHandler.match(mr.getUserId(),mr.getGoodsName());
            if(bean==null){
                bean = new BuyRecordMatchBean();
            }
            Integer num = systemConfigHandler.getIntegerConfig(ConfigKey.NLP_BUYRECORD_GOODSNAME_EKNUM);
            List<String> keywords = nlpProcessor.extractKeyword(mr.getGoodsName(),num);
            bean.setKeywords(keywords);
            if(bean.getCompareId()==null){
                //说明没有匹配,设置默认的配置
                UserSetting us = authService.getUserSetting(mr.getUserId());
                bean.setBuyTypeId(us.getBuyTypeId());
                bean.setPayment(us.getPayment());
            }
        } catch (Exception e) {
            logger.error("根据商品名智能分析出其分类及品牌等异常",e);
            return callback(null);
        }
        return callback(bean);
    }
}
