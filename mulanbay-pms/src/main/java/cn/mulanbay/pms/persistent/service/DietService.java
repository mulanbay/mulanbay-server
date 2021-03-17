package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.Diet;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.diet.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DietService extends BaseHibernateDao {

    /**
     * 获取最后一次饮食
     *
     * @param userId
     * @param dietSource
     * @param dietType
     * @return
     */
    public Diet getLastDiet(Long userId, DietSource dietSource, DietType dietType, FoodType foodType) {
        try {
            String hql = "from Diet where userId=?0 ";
            List args = new ArrayList();
            args.add(userId);
            int index = 1;
            if (dietSource != null) {
                hql += "and dietSource=?" + (index++) + " ";
                args.add(dietSource);
            }
            if (dietType != null) {
                hql += "and dietType=?" + (index++) + " ";
                args.add(dietType);
            }
            if (foodType != null) {
                hql += "and foodType=?" + (index++) + " ";
                args.add(foodType);
            }
            hql += "order by occurTime desc ";
            Diet diet = (Diet) this.getEntityForOne(hql, args.toArray());
            return diet;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最后一次饮食异常", e);
        }
    }

    /**
     * 获取饮食习惯列表
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @param dietSource
     * @param dietType
     * @return
     */
    public List<Diet> getDietList(Date startTime, Date endTime, Long userId, DietSource dietSource, DietType dietType, FoodType foodType) {
        try {
            String hql = "from Diet where userId=?0 and occurTime>=?1 and occurTime<=?2 ";
            List args = new ArrayList();
            args.add(userId);
            args.add(startTime);
            args.add(endTime);
            int index = 3;
            if (dietSource != null) {
                hql += "and dietSource=?" + (index++) + " ";
                args.add(dietSource);
            }
            if (dietType != null) {
                hql += "and dietType=?" + (index++) + " ";
                args.add(dietType);
            }
            if (foodType != null) {
                hql += "and foodType=?" + (index++) + " ";
                args.add(foodType);
            }
            hql += "order by occurTime ";
            List<Diet> list = this.getEntityListNoPageHQL(hql, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食习惯列表异常", e);
        }
    }

    /**
     * 饮食分析
     *
     * @param sf 查询条件
     * @return
     */
    public List<DietAnalyseStat> statDietAnalyse(DietAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String fieldName = DietAnalyseSearch.StatField.FOODS.getFieldName();
            if (sf.getField() != DietAnalyseSearch.StatField.CLASS_NAME && sf.getField() != DietAnalyseSearch.StatField.TYPE) {
                fieldName = sf.getField().getFieldName();
            }
            StringBuffer sb = new StringBuffer();
            sb.append("select * from ( ");
            sb.append("select name,count(0) as totalCount from  ");
            sb.append("( ");
            sb.append("select substring_index(substring_index(a." + fieldName + ",',',b.help_topic_id+1),',',-1)  as name ");
            sb.append("from diet a join mysql.help_topic b ");
            sb.append("on b.help_topic_id < (length(a." + fieldName + ") - length(replace(a." + fieldName + ",',',''))+1) ");
            sb.append(pr.getParameterString());
            sb.append(" and a." + fieldName + " is not null ");
            sb.append(" ) as res");
            sb.append(" group by name ");
            sb.append(" ) as aaa ");
            if (sf.getMinCount() > 0) {
                sb.append(" where totalCount >= " + sf.getMinCount());
            }
            if (sf.getChartType() == ChartType.BAR || sf.getPage() > 0) {
                sb.append(" order by totalCount desc ");
            }
            List<DietAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), DietAnalyseStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "饮食分析异常", e);
        }
    }

    /**
     * 饮食分析
     *
     * @param sf 查询条件
     * @return
     */
    public List<DietAnalyseStat> statDietAnalyseByType(DietAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String fieldName = sf.getField().getFieldName();
            StringBuffer sb = new StringBuffer();
            sb.append("select * from ( ");
            sb.append("select " + fieldName + " as type,count(0) as totalCount from diet a ");
            sb.append(pr.getParameterString());
            sb.append("group by " + fieldName + " ) as res ");
            if (sf.getMinCount() > 0) {
                sb.append(" where totalCount >= " + sf.getMinCount());
            }
            if (sf.getChartType() == ChartType.BAR || sf.getPage() > 0) {
                sb.append(" order by totalCount desc ");
            }
            List<Object[]> list = this.getEntityListSQL(sb.toString(), pr.getPage(), pr.getPageSize(), pr.getParameterValue());
            List<DietAnalyseStat> res = new ArrayList<>();
            for (Object[] oo : list) {
                DietAnalyseStat das = new DietAnalyseStat();
                das.setTotalCount(BigInteger.valueOf(Long.valueOf(oo[1].toString())));
                int type = Integer.valueOf(oo[0].toString());
                if (sf.getField() == DietAnalyseSearch.StatField.FOOD_TYPE) {
                    FoodType ft = FoodType.getFoodType(type);
                    das.setName(ft.getName());
                } else if (sf.getField() == DietAnalyseSearch.StatField.DIET_SOURCE) {
                    DietSource ft = DietSource.getDietSource(type);
                    das.setName(ft.getName());
                } else if (sf.getField() == DietAnalyseSearch.StatField.DIET_TYPE) {
                    DietType ft = DietType.getDietType(type);
                    das.setName(ft.getName());
                } else {
                    das.setName("类型" + type);
                }
                res.add(das);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "饮食分析异常", e);
        }
    }

    /**
     * 饮食比较
     *
     * @param sf 查询条件
     * @return
     */
    public List<DietCompareStat> statDietCompare(DietCompareSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select diet_type as dietType,diet_source as dietSource ,count(0) as totalCount,sum(price) as totalPrice,sum(score) as totalScore from diet ");
            sb.append(pr.getParameterString());
            sb.append(" group by diet_type,diet_source ");
            List<Object[]> list = this.getEntityListSQL(sb.toString(), pr.getPage(), pr.getPageSize(), pr.getParameterValue());
            List<DietCompareStat> res = new ArrayList<>();
            for (Object[] oo : list) {
                DietCompareStat das = new DietCompareStat();
                das.setTotalCount(BigInteger.valueOf(Long.valueOf(oo[2].toString())));
                DietType dietType = DietType.getDietType(Integer.valueOf(oo[0].toString()));
                das.setDietType(dietType);
                DietSource dietSource = DietSource.getDietSource(Integer.valueOf(oo[1].toString()));
                das.setDietSource(dietSource);
                das.setTotalPrice(BigDecimal.valueOf(Double.valueOf(oo[3].toString())));
                if (oo[4] != null) {
                    das.setTotalScore(BigInteger.valueOf(Long.valueOf(oo[4].toString())));
                } else {
                    das.setTotalScore(BigInteger.ZERO);
                }
                res.add(das);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "饮食比较异常", e);
        }
    }

    /**
     * 饮食分析
     *
     * @param sf 查询条件
     * @return
     */
    public List<DietPriceAnalyse2Stat> statDietPriceAnalyseByType(DietPriceAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String fieldName = null;
            String dg = sf.getDateGroupTypeStr();
            if ("DIET_SOURCE".equals(dg)) {
                fieldName = "diet_source";
            } else if ("FOOD_TYPE".equals(dg)) {
                fieldName = "food_type";
            } else if ("DIET_TYPE".equals(dg)) {
                fieldName = "diet_type";
            }
            StringBuffer sb = new StringBuffer();
            sb.append("select * from ( ");
            sb.append("select " + fieldName + " as type,count(0) as totalCount,sum(price) as totalPrice from diet a ");
            sb.append(pr.getParameterString());
            sb.append("group by " + fieldName + " ) as res ");

            List<Object[]> list = this.getEntityListSQL(sb.toString(), pr.getPage(), pr.getPageSize(), pr.getParameterValue());
            List<DietPriceAnalyse2Stat> res = new ArrayList<>();
            for (Object[] oo : list) {
                DietPriceAnalyse2Stat das = new DietPriceAnalyse2Stat();
                das.setTotalCount(BigInteger.valueOf(Long.valueOf(oo[1].toString())));
                das.setTotalPrice(BigDecimal.valueOf(Double.valueOf(oo[2].toString())));
                int type = Integer.valueOf(oo[0].toString());
                if ("DIET_SOURCE".equals(dg)) {
                    DietSource ft = DietSource.getDietSource(type);
                    das.setName(ft.getName());
                } else if ("FOOD_TYPE".equals(dg)) {
                    FoodType ft = FoodType.getFoodType(type);
                    das.setName(ft.getName());
                } else if ("DIET_TYPE".equals(dg)) {
                    DietType ft = DietType.getDietType(type);
                    das.setName(ft.getName());
                } else {
                    das.setName("类型" + type);
                }
                res.add(das);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "饮食分析异常", e);
        }
    }

    /**
     * 获取饮食的食物
     *
     * @return
     */
    public List<String> getFoodsList(DietKeywordsSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select distinct foods from diet ";
            sql += pr.getParameterString();
            return this.getEntityListNoPageSQL(sql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食的食物异常", e);
        }
    }

    /**
     * 获取饮食的店铺
     *
     * @return
     */
    public List<String> getShopList(DietKeywordsSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            String sql = "select distinct shop from diet where shop is not null ";
            sql += pr.getParameterString();
            return this.getEntityListNoPageSQL(sql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食的店铺异常", e);
        }
    }

    /**
     * 获取总价
     *
     * @return
     */
    public BigDecimal getTotalPrice(Long userId, Date startTime, Date endTime) {
        try {
            String sql = "select sum(price) from diet where user_id=?0 and occur_time>=?1 and occur_time<=?2 ";
            List list = this.getEntityListSQL(sql, 0, 0, userId, startTime, endTime);
            Object o = list.get(0);
            if (o == null) {
                return new BigDecimal(0);
            } else {
                return new BigDecimal(o.toString());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食的店铺异常", e);
        }
    }

    /**
     * 获取饮食的标签
     *
     * @return
     */
    public List<String> getTagsList(DietKeywordsSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            String sql = "select distinct tags from diet where tags is not null ";
            sql += pr.getParameterString();
            return this.getEntityListNoPageSQL(sql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食的标签异常", e);
        }
    }

    /**
     * 获取饮食，相识度比对使用
     *
     * @return
     */
    public List<String> getFoodsList(DietVarietySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select foods from diet ";
            sql += pr.getParameterString();
            sql += " order by " + sf.getOrderByField();
            return this.getEntityListSQL(sql, sf.getPage(), sf.getPageSize(), pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食异常", e);
        }
    }

    /**
     * 获取饮食(不区分餐次)，相识度比对使用
     *
     * @return
     */
    public List<Object[]> getFoodsListWithAllDietType(DietVarietySearch sf) {
        try {
            //sf.setDietType(null);
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ");
            sb.append("    MAX(CASE diet_type WHEN 0 THEN foods ELSE 0 END ) 早餐,");
            sb.append("    MAX(CASE diet_type WHEN 1 THEN foods ELSE 0 END ) 午餐,");
            sb.append("    MAX(CASE diet_type WHEN 2 THEN foods ELSE 0 END ) 晚餐,");
            sb.append("    MAX(CASE diet_type WHEN 3 THEN foods ELSE 0 END ) 其他");
            sb.append(" FROM ");
            sb.append(" (SELECT DATE_FORMAT(occur_time,'%Y-%m-%d') as ot,diet_type,foods FROM diet ");
            sb.append(pr.getParameterString());
            sb.append(" ) as ss ");
            sb.append("GROUP BY ot ");
            sb.append("order by ot ");
            return this.getEntityListSQL(sb.toString(), sf.getPage(), sf.getPageSize(), pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取饮食异常", e);
        }
    }

    /**
     * 获取用户ID列表
     *
     * @return
     */
    public List<Long> getUserIdList(Date startDate, Date endDate) {
        try {
            String sql = "select distinct userId from Diet where occurTime>=?0 and occurTime<=?1 ";
            return this.getEntityListHQL(sql, 0, 0, startDate, endDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户ID列表异常", e);
        }
    }


    /**
     * 按价格来统计
     *
     * @param sf
     * @return
     */
    public List<DietPriceAnalyseStat> statDietPrice(DietPriceAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,sum(price) as totalPrice ,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select " + MysqlUtil.dateTypeMethod("occur_time", dateGroupType) + " as indexValue,");
            sb.append("price");
            sb.append(" from diet ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<DietPriceAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), 0, 0, DietPriceAnalyseStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "按价格来统计异常", e);
        }
    }

    /**
     * 饮食点统计
     *
     * @param sf
     * @return
     */
    public List<DietTimeStat> timeStatDiet(DietTimeStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select " + MysqlUtil.dateTypeMethod("occur_time", sf.getXgroupType()) + " as xValue,");
            sb.append(MysqlUtil.dateTypeMethod("occur_time", DateGroupType.HOURMINUTE) + " as yValue ");
            sb.append(" from diet ");
            sb.append(pr.getParameterString());
            List args = pr.getParameterValueList();
            List<DietTimeStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), DietTimeStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "饮食点统计异常", e);
        }
    }

    /**
     * 获取标签列表
     *
     * @param sf
     * @return
     */
    public List<NameCountDto> statTags(DietWordCloudSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String field = sf.getField();
            StringBuffer sb = new StringBuffer();
            sb.append("select name,count(0) as counts from ");
            sb.append("( ");
            sb.append("select (substring_index(substring_index(a.col,',',b.help_topic_id+1),',',-1)) as name ");
            sb.append("from ");
            sb.append(" (select "+field+" as col from diet  ");
            sb.append(pr.getParameterString());
            sb.append("    ) as a ");
            sb.append("join ");
            sb.append("mysql.help_topic as b ");
            sb.append("on b.help_topic_id < (char_length(a.col) - char_length(replace(a.col,',',''))+1) ");
            sb.append(") as res group by name ");
            List<NameCountDto> list = this.getEntityListWithClassSQL(sb.toString(),0,0,NameCountDto.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取标签列表异常", e);
        }
    }
}
