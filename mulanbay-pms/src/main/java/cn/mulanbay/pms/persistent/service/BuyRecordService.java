package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.common.Constant;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.buy.*;
import cn.mulanbay.pms.web.bean.request.life.BackHomeDateStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消费记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Service
@Transactional
public class BuyRecordService extends BaseHibernateDao {

    /**
     * 基本的统计
     *
     * @param sf
     * @return
     */
    public BuyRecordStat getBuyRecordStat(BuyRecordSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select count(*) as totalCount,sum(shipment) as totalShipment,");
            MoneyFlow moneyFlow = sf.getMoneyFlow();
            if (moneyFlow != null) {
                if (moneyFlow == MoneyFlow.BUY) {
                    sb.append("sum(totalPrice) as totalPrice from BuyRecord ");
                } else {
                    sb.append("sum(soldPrice) as totalPrice from BuyRecord ");
                }
            }
            sb.append(pr.getParameterString());
            List<BuyRecordStat> list = this.getEntityListWithClassHQL(sb.toString(), pr.getPage(), pr.getPageSize(), BuyRecordStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取购买记录统计异常", e);
        }
    }

    /**
     * 获取雷达统计分组中的最大值
     *
     * @param sf
     * @return
     */
    public Long getMaxValueOfBuyRecord(BuyRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            if (sf.getType() == GroupType.COUNT) {
                sb.append("select max(vv) from (");
                sb.append("select " + sf.getGroupField() + ",count(*) vv from buy_record ");
                sb.append(pr.getParameterString());
                sb.append(" group by " + sf.getGroupField());
                sb.append(") as aa ");
            } else if (sf.getType() == GroupType.TOTALPRICE) {
                sb.append("select max(totalPrice) as vv from buy_record ");
                sb.append(pr.getParameterString());
            } else {
                sb.append("select max(shipment) as vv from buy_record ");
                sb.append(pr.getParameterString());
            }
            List list = this.getEntityListNoPageSQL(sb.toString(), pr.getParameterValue());
            if (list.isEmpty()) {
                return 0L;
            }
            Object oo = list.get(0);
            if (sf.getType() == GroupType.COUNT) {
                BigInteger vv = (BigInteger) oo;
                return vv.longValue();
            } else {
                BigDecimal vv = (BigDecimal) oo;
                return vv.longValue();
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取雷达统计分组中的最大值异常", e);
        }
    }

    /**
     * 实时的分析雷达统计
     *
     * @param sf
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BuyRecordRadarStat> getRadarStat(BuyRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String groupField = sf.getGroupField();
            GroupType type = sf.getType();
            StringBuffer sql = new StringBuffer();
            sql.append("select groupId,indexValue,count(*) totalCount,sum(pp) totalPrice from ( ");
            if ("price_region".equals(groupField)) {
                sql.append("select getPriceRegionId(total_price," + sf.getUserId() + ") as groupId,");
            } else {
                sql.append("select " + groupField + " as groupId,");
            }
            sql.append(MysqlUtil.dateTypeMethod("buy_date", sf.getDateGroupType()) + " as indexValue");
            if (type == GroupType.COUNT || type == GroupType.TOTALPRICE) {
                //统计次数
                sql.append(" ,total_price as pp from buy_record ");
            } else if (type == GroupType.SHIPMENT) {
                //运费
                sql.append(" ,shipment as pp from buy_record ");
            }
            sql.append(pr.getParameterString());
            if (sf.isUseStatable()) {
                sql.append(getStatableCondition());
            }
            sql.append(") as aa ");
            sql.append("group by groupId,indexValue ");
            sql.append("order by indexValue,groupId ");
            List<BuyRecordRadarStat> list = this.getEntityListWithClassSQL(sql.toString(), pr.getPage(), pr.getPageSize(), BuyRecordRadarStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取实时的分析雷达统计异常", e);
        }
    }

    /**
     * 实时的分析统计
     *
     * @param sf
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BuyRecordRealTimeStat> getAnalyseStat(BuyRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String groupField = sf.getGroupField();
            GroupType type = sf.getType();
            StringBuffer sql = new StringBuffer();
            if ("price_region".equals(groupField)) {
                sql.append("select getPriceRegionId(total_price," + sf.getUserId() + ") as priceRegion");
            } else {
                sql.append("select " + groupField);
            }
            if (type == GroupType.COUNT) {
                //统计次数
                sql.append(" ,count(*) as cc from buy_record ");
            } else if (type == GroupType.TOTALPRICE) {
                //价格
                sql.append(" ,sum(total_price) as cc from buy_record ");
            } else if (type == GroupType.SHIPMENT) {
                //运费
                sql.append(" ,sum(shipment) as cc from buy_record ");
            }
            sql.append(pr.getParameterString());
            if (sf.isUseStatable()) {
                sql.append(getStatableCondition());
            }
            if ("price_region".equals(groupField)) {
                sql.append(" group by priceRegion");
            } else {
                sql.append(" group by " + groupField);
            }
            if (sf.getChartType() == ChartType.BAR) {
                sql.append(" order by cc desc");
            }
            List<Object[]> list = this.getEntityListNoPageSQL(sql.toString(), pr.getParameterValue());
            List<BuyRecordRealTimeStat> result = new ArrayList<BuyRecordRealTimeStat>();
            for (Object[] oo : list) {
                BuyRecordRealTimeStat bb = new BuyRecordRealTimeStat();
                Object nameFiled = oo[0];
                if (nameFiled == null) {
                    bb.setName("未知");
                } else {
                    Object serierIdObj = oo[0];
                    if (serierIdObj == null) {
                        //防止为NULL
                        serierIdObj = "0";
                    }
                    String name = getSerierName(serierIdObj.toString(), groupField);
                    bb.setName(name);
                    // ID值大部分情况下不需要
                    //bb.setId(Integer.valueOf(serierIdObj.toString()));
                }
                double value = Double.valueOf(oo[1].toString());
                bb.setValue(value);
                result.add(bb);
            }
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取实时统计异常", e);
        }
    }

    /**
     * 获取使用时间的最大记录数
     *
     * @param sf
     * @return
     */
    public long getMaxRowOfUseTime(BuyRecordUseTimeStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            String sql = "select count(0) FROM buy_record where delete_date is not null ";
            sql += pr.getParameterString();
            long n = this.getCountSQL(sql, pr.getParameterValue());
            return n;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取使用时间的最大记录数异常", e);
        }
    }

    /**
     * 使用时间统计
     *
     * @param sf
     * @return
     */
    public List<BuyRecordUseTimeStat> getUseTimeStat(BuyRecordUseTimeStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            String groupField = sf.getGroupField();
            StringBuffer sql = new StringBuffer();
            if ("goods_name".equals(groupField)) {
                sql.append("select goods_name,datediff(delete_date,buy_date) as days,1,id FROM buy_record where delete_date is not null ");
                sql.append(pr.getParameterString());
                sql.append("order by days desc ");
            } else {
                sql.append("select " + groupField + ",sum(days) as days,count(0) as c from ( ");
                sql.append("select " + groupField + ",datediff(delete_date,buy_date) as days FROM buy_record where delete_date is not null ");
                sql.append(pr.getParameterString());
                sql.append(") as aa ");
                sql.append("group by " + groupField + " order by days desc ");
            }
            List<Object[]> list = this.getEntityListSQL(sql.toString(), sf.getPage(), sf.getPageSize(), pr.getParameterValue());
            List<BuyRecordUseTimeStat> result = new ArrayList();
            for (Object[] oo : list) {
                BuyRecordUseTimeStat bb = new BuyRecordUseTimeStat();
                Object nameFiled = oo[0];
                if (nameFiled == null) {
                    bb.setName("未知");
                } else {
                    if ("goods_name".equals(groupField)) {
                        bb.setName(oo[0].toString());
                    } else {
                        Object serierIdObj = oo[0];
                        if (serierIdObj == null) {
                            //防止为NULL
                            serierIdObj = "0";
                        }
                        String name = getSerierName(serierIdObj.toString(), groupField);
                        bb.setName(name);
                    }
                }
                int days = Integer.valueOf(oo[1].toString());
                bb.setDays(days);
                int counts = Integer.valueOf(oo[2].toString());
                bb.setCounts(counts);
                if ("goods_name".equals(groupField)) {
                    bb.setId(Long.valueOf(oo[3].toString()));
                }
                result.add(bb);
            }
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "使用时间统计异常", e);
        }

    }


    /**
     * 获取消费总值
     *
     * @return
     */
    public double statBuyAmount(Date startTime, Date endTime, Long userId, Short consumeType) {
        try {
            String sql = "select sum(total_price) from buy_record where buy_date>=?0 and buy_date<=?1 and user_id=?2 ";
            if (consumeType != null) {
                sql += " and consume_type=" + consumeType;
            }
            List list = this.getEntityListNoPageSQL(sql, startTime, endTime, userId);
            if (list.isEmpty() || list.get(0) == null) {
                return 0;
            } else {
                return Double.valueOf(list.get(0).toString());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取消费总值异常", e);
        }
    }

    /**
     * 获取消费总值
     * @param startTime
     * @param endTime
     * @param userId
     * @param goodsTypeId
     * @param subGoodsTypeId
     * @return
     */
    public Double statBuyAmount(Date startTime, Date endTime, Long userId, Integer goodsTypeId,Integer subGoodsTypeId,String keywords) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select sum(total_price) from buy_record where buy_date>=?0 and buy_date<=?1 and user_id=?2");
            List args = new ArrayList();
            args.add(startTime);
            args.add(endTime);
            args.add(userId);
            int index =3;
            if(goodsTypeId!=null){
                sb.append(" and goods_type_id=?"+(index++));
                args.add(goodsTypeId);
            }
            if(subGoodsTypeId!=null){
                sb.append(" and sub_goods_type_id=?"+(index++));
                args.add(subGoodsTypeId);
            }
            if(StringUtil.isNotEmpty(keywords)){
                sb.append(" and (goods_name like?"+(index++)+" or keywords like?"+(index++)+")");
                args.add("%"+keywords+"%");
                args.add("%"+keywords+"%");
            }
            List list = this.getEntityListNoPageSQL(sb.toString(), args.toArray());
            if (list.isEmpty() || list.get(0) == null) {
                return null;
            } else {
                return Double.valueOf(list.get(0).toString());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取消费总值异常", e);
        }
    }



    /**
     * 实时的分析统计(基于树形结构)
     *
     * @param sf
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BuyRecordRealTimeTreeStat> getAnalyseTreeStat(BuyRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String groupField = sf.getGroupField();
            GroupType type = sf.getType();
            StringBuffer sql = new StringBuffer();
            sql.append("select goods_type_id as goodsId,sub_goods_type_id as subGoodsId");
            if (type == GroupType.COUNT) {
                //统计次数
                sql.append(" ,count(0) as cc from buy_record ");
            } else if (type == GroupType.TOTALPRICE) {
                //价格
                sql.append(" ,sum(total_price) as cc from buy_record ");
            } else if (type == GroupType.SHIPMENT) {
                //运费
                sql.append(" ,sum(shipment) as cc from buy_record ");
            }
            sql.append(pr.getParameterString());
            if (sf.isUseStatable()) {
                sql.append(getStatableCondition());
            }
            sql.append(" group by goods_type_id,sub_goods_type_id ");
            sql.append(" order by goods_type_id ");
            List<Object[]> list = this.getEntityListNoPageSQL(sql.toString(), pr.getParameterValue());
            List<BuyRecordRealTimeTreeStat> result = new ArrayList<BuyRecordRealTimeTreeStat>();
            for (Object[] oo : list) {
                BuyRecordRealTimeTreeStat bb = new BuyRecordRealTimeTreeStat();
                String goodsName = getSerierName(oo[0].toString(), groupField);
                bb.setGoodsId(Integer.valueOf(oo[0].toString()));
                bb.setGoodsName(goodsName);
                if (oo[1] != null) {
                    String subGoodsName = getSerierName(oo[1].toString(), groupField);
                    bb.setSubGoodsId(Integer.valueOf(oo[1].toString()));
                    bb.setSubGoodsName(subGoodsName);
                }
                double value = Double.valueOf(oo[2].toString());
                bb.setValue(value);
                result.add(bb);
            }
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "实时的分析统计(基于树形结构)异常", e);
        }
    }


    /**
     * 获取统计图表中的系列名称
     *
     * @param serierIdObj
     * @param groupField
     * @return
     */
    private String getSerierName(String serierIdObj, String groupField) {
        try {
            if ("shop_name".equals(groupField) || "brand".equals(groupField)) {
                return serierIdObj;
            }
            int serierId = Integer.valueOf(serierIdObj);
            if ("buy_type_id".equals(groupField)) {
                BuyType bt = (BuyType) this.getEntityById(BuyType.class, serierId);
                if (bt == null) {
                    return "未知";
                } else {
                    return bt.getName();
                }
            } else if ("goods_type_id".equals(groupField) || "sub_goods_type_id".equals(groupField)) {
                GoodsType gt = (GoodsType) this.getEntityById(GoodsType.class, serierId);
                if (gt == null) {
                    return "未知";
                } else {
                    return gt.getName();
                }
            } else if ("price_region".equals(groupField)) {
                PriceRegion gt = (PriceRegion) this.getEntityById(PriceRegion.class, serierId);
                if (gt == null) {
                    return "未知";
                } else {
                    return gt.getName() + "(" + Constant.MONEY_SYMBOL + gt.getMinPrice() + "~" + Constant.MONEY_SYMBOL + gt.getMaxPrice() + ")";
                }
            } else if ("payment".equals(groupField)) {
                Payment payment = Payment.getPayment(serierId);
                if (payment == null) {
                    return "未知";
                } else {
                    return payment.getName();
                }
            }
            return "未知";
        } catch (BaseException e) {

            return "未知";
        }
    }

    /**
     * 按时间来统计
     *
     * @param sf
     * @return
     */
    public List<BuyRecordDateStat> statBuyRecordByDate(BuyRecordDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            BuyRecordPriceType priceType = sf.getPriceType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,round(sum(price)) as totalPrice ,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select " + MysqlUtil.dateTypeMethod("buy_date", dateGroupType) + " as indexValue,");
            if (priceType == BuyRecordPriceType.SHIPMENT) {
                sb.append("shipment as price");
            } else if (priceType == BuyRecordPriceType.TOTALPRICE) {
                sb.append("total_price as price");
            }
            sb.append(" from buy_record ");
            sb.append(pr.getParameterString());
            if (sf.isUseStatable()) {
                sb.append(getStatableCondition());
            }
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<BuyRecordDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BuyRecordDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "按时间来统计异常", e);
        }
    }

    /**
     * 获取购买记录关键字
     *
     * @return
     */
    public List<String> getKeywordsList(BuyRecordKeywordsSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select distinct keywords from buy_record ";
            sql += pr.getParameterString();
            sql += " and keywords is not null ";
            return this.getEntityListNoPageSQL(sql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取购买记录关键字异常", e);
        }
    }

    /**
     * 按时间来统计回家记录
     *
     * @param sf
     * @return
     */
    public List<BackHomeDateStat> statBackHomeByDate(BackHomeDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,round(sum(price)) as totalPrice ,count(0) as totalCount ");
            sb.append("from (");
            DateGroupType dateGroupType = sf.getDateGroupType();
            sb.append("select " + MysqlUtil.dateTypeMethod("buy_date", dateGroupType) + " as indexValue,");
            sb.append("total_price as price");
            sb.append(" from buy_record ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<BackHomeDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BackHomeDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "按时间来统计回家记录异常", e);
        }
    }

    /**
     * 按关键字来统计购买记录
     *
     * @param sf 查询条件
     * @return
     */
    public List<BuyRecordKeywordsStat> statBuyRecordByKeywords(BuyRecordKeywordsStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select keywords,round(sum(price)) as totalPrice ,count(0) as totalCount from buy_record ");
            pr.setNeedWhere(true);
            sb.append(pr.getParameterString());
            if (sf.isUseStatable()) {
                sb.append(getStatableCondition());
            }
            //必须要有关键字
            sb.append("and keywords is not null ");
            sb.append("group by keywords ");
            sb.append("order by totalPrice desc");
            List<BuyRecordKeywordsStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BuyRecordKeywordsStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "按关键字来统计购买记录异常", e);
        }
    }

    /**
     * 获取需要统计的条件
     *
     * @return
     */
    private String getStatableCondition() {
        StringBuffer sb = new StringBuffer();
        //排除不需要统计的记录
        sb.append(" and goods_type_id in (select id from goods_type where statable =1 and pid=0 ) ");
        //有可能子商品类型为空
        sb.append(" and (sub_goods_type_id in (select id from goods_type where statable =1 and pid>0 ) or sub_goods_type_id is null )");
        sb.append(" and statable != 0 ");
        return sb.toString();
    }

    /**
     * 获取购买记录
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public List<BuyRecord> getBuyRecord(Date startTime, Date endTime, Long userId, int page, int pageSize) {
        try {
            String hql = "from BuyRecord where buyDate>=?0 and buyDate<=?1 and userId=?2 ";
            return this.getEntityListHQL(hql, page, pageSize, startTime, endTime, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取购买记录异常", e);
        }
    }

    /**
     * 获取根据消费类型分组的消费总额
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    public List<BuyRecordConsumeTypeStat> getConsumeTypeAmountStat(Date startTime, Date endTime, Long userId) {
        try {
            String sql = "select consume_type as ConsumeType,sum(total_price) as totalPrice,count(0) as totalCount from buy_record where buy_date>=?0 and buy_date<=?1 and user_id=?2 group by consume_type";
            List args = new ArrayList();
            args.add(startTime);
            args.add(endTime);
            args.add(userId);
            List<BuyRecordConsumeTypeStat> list = this.getEntityListWithClassSQL(sql, 0, 0, BuyRecordConsumeTypeStat.class, startTime, endTime, userId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取根据消费类型分组的消费总额异常", e);
        }
    }

    /**
     * 新增消费记录
     *
     * @param br
     */
    public void saveBuyRecord(BuyRecord br) {
        try {
            this.saveEntity(br);
            if (br.getSoldPrice() != null) {
                addNewIncome(br);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "保存消费记录异常", e);
        }
    }

    /**
     * 更新消费记录
     *
     * @param br
     */
    public void updateBuyRecord(BuyRecord br) {
        try {
            this.updateEntity(br);
            if (br.getSoldPrice() != null) {
                //查询收入
                String hql = "from Income where buyRecordId=?0";
                Income income = (Income) this.getEntityForOne(hql, br.getId());
                if (income == null) {
                    addNewIncome(br);
                } else {
                    if (!PriceUtil.priceEquals(br.getSoldPrice(), income.getAmount())) {
                        //价格有改变更新
                        income.setAmount(br.getSoldPrice());
                        income.setLastModifyTime(new Date());
                        this.updateEntity(income);
                    }
                }
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "保存消费记录异常", e);
        }
    }

    /**
     * 更新消费记录
     *
     * @param br
     */
    private void addNewIncome(BuyRecord br) {
        try {
            //自动增加一条收入
            Income income = new Income();
            income.setCreatedTime(new Date());
            income.setAmount(br.getSoldPrice());
            income.setName(br.getGoodsName() + " 出售");
            income.setOccurTime(br.getDeleteDate());
            income.setRemark("保存消费记录时自动增加");
            income.setStatus(CommonStatus.ENABLE);
            income.setType(IncomeType.SECONDHAND_SOLD);
            income.setUserId(br.getUserId());
            income.setBuyRecordId(br.getId());
            this.saveEntity(income);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "保存收入异常", e);
        }
    }

    /**
     * 获取商品名称列表，相识度比对使用
     *
     * @return
     */
    public List<String> getGoodsNameList(BuyRecordVarietyStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select goods_name from buy_record ";
            sql += pr.getParameterString();
            sql += " order by " + sf.getOrderByField();
            return this.getEntityListSQL(sql, sf.getPage(), sf.getPageSize(), pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取商品名称列表异常", e);
        }
    }

    /**
     * 获取商品类型
     *
     * @param userId
     * @return
     */
    public List<GoodsType> getGoodsTypeList(Long userId) {
        try {
            String hql = "from GoodsType where userId=?0 and id>0 order by parent.id ";
            return this.getEntityListNoPageHQL(hql, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取商品类型异常", e);
        }
    }

    /**
     * 词云统计
     * @param sf
     * @return
     */
    public List<BuyRecord> getBuyRecordWordCloudStat(BuyRecordWordCloudSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setPage(PageRequest.NO_PAGE);
            StringBuffer sb = new StringBuffer();
            sb.append("select new BuyRecord(goodsName,skuInfo ,shopName,brand) from BuyRecord ");
            sb.append(pr.getParameterString());
            List<BuyRecord> list = this.getEntityListNoPageHQL(sb.toString(), pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "词云统计异常", e);
        }
    }

}
