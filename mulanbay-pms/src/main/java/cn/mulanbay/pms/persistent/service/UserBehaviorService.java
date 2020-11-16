package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.UserBehavior;
import cn.mulanbay.pms.persistent.domain.UserBehaviorConfig;
import cn.mulanbay.pms.persistent.domain.UserOperationConfig;
import cn.mulanbay.pms.persistent.dto.UserBehaviorDataStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserBehaviorService extends BaseHibernateDao {

    /**
     * 获取用户操作配置模式列表
     *
     * @param configIds
     * @return
     */
    public List<UserOperationConfig> getUserOperationConfigList(String configIds, UserBehaviorType behaviorType) {
        try {
            String hql = "from UserOperationConfig where status =?0 ";
            if (StringUtil.isNotEmpty(configIds)) {
                hql += " and id in (" + configIds + ")";
            }
            if (behaviorType != null) {
                hql += " and behaviorType=" + behaviorType.ordinal();
            }
            hql += " order by orderIndex";
            List<UserOperationConfig> list = this.getEntityListNoPageHQL(hql, CommonStatus.ENABLE);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户操作配置模式列表异常", e);
        }
    }

    /**
     * 获取用户操作列表
     *
     * @param uoc
     * @return
     */
    public List<Object[]> getUserOperationList(UserOperationConfig uoc, Date startTime, Date endTime, Long userId, int page, int pageSize) {
        try {
            String sql = uoc.getSqlContent();
            return this.getEntityListSQL(sql, page, pageSize, userId, startTime, endTime);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户操作列表异常", e);
        }
    }

    /**
     * 获取用户行为配置模式列表
     *
     * @param minLevel
     * @return
     */
    public List<UserBehaviorConfig> getUserBehaviorConfigList(Integer minLevel) {
        try {
            String hql = "from UserBehaviorConfig where status =?0 and level<=?1 order by orderIndex ";
            List<UserBehaviorConfig> list = this.getEntityListNoPageHQL(hql, CommonStatus.ENABLE, minLevel);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户行为配置模式列表异常", e);
        }
    }

    /**
     * 获取用户行为配置模板
     *
     * @param id
     * @param minLevel
     * @return
     */
    public UserBehaviorConfig getUserBehaviorConfig(Long id, Integer minLevel) {
        try {
            String hql = "from UserBehaviorConfig where id =?0 and level<=?1 order by orderIndex ";
            UserBehaviorConfig bean = (UserBehaviorConfig) this.getEntityForOne(hql, id, minLevel);
            return bean;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户行为配置模式列表异常", e);
        }
    }

    /**
     * 按小时点条件统计用户的行为
     * 比如统计用户在14-15点都做了什么
     *
     * @param startDate
     * @param endDate
     * @param userId
     * @param minHour
     * @param maxHour
     * @return
     */
    public List<UserBehaviorDataStat> statHourUserBehaviorData(Date startDate, Date endDate, Long userId, int minHour, int maxHour) {
        try {
            String hql = "from UserBehavior where status =1 and hourStat=1 ";
            List<UserBehavior> userBehaviors = this.getEntityListNoPageHQL(hql);
            if (userBehaviors.isEmpty()) {
                return new ArrayList<>();
            }
            List<UserBehaviorDataStat> resultList = new ArrayList<>();
            for (UserBehavior userBehavior : userBehaviors) {
                List queryArgs = new ArrayList();
                queryArgs.add(startDate);
                queryArgs.add(endDate);
                queryArgs.add(userId);
                StringBuffer sb = new StringBuffer();
                UserBehaviorConfig config = userBehavior.getUserBehaviorConfig();
                String sqlContent = config.getSqlContent();
                sb.append(MysqlUtil.replaceBindValues(sqlContent, userBehavior.getBindValues()));
                if (!sqlContent.contains("where")) {
                    sb.append(" where 1=1");
                }
                sb.append(" and " + config.getDateField() + ">=?0 and " + config.getDateField() + "<=?1 ");
                sb.append(" and " + config.getUserField() + "=?2 ");
                String hourCondition = MysqlUtil.dateTypeMethod(config.getDateField(), DateGroupType.HOUR);
                sb.append(" and " + hourCondition + ">=?3 ");
                sb.append(" and " + hourCondition + "<=?4 ");
                queryArgs.add(minHour);
                queryArgs.add(maxHour);
                List<UserBehaviorDataStat> result = this.getEntityListWithClassSQL(sb.toString(), -1, 0, UserBehaviorDataStat.class, queryArgs.toArray());
                if (!result.isEmpty()) {
                    resultList.addAll(result);
                }
            }
            return resultList;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "按小时点条件统计用户的行为异常", e);
        }
    }

    /**
     * 获取个人行为分析数据
     *
     * @param behaviorType
     * @param startDate
     * @param endDate
     * @param userId
     * @param name
     * @return
     */
    public List<UserBehaviorDataStat> statUserBehaviorData(UserBehaviorType behaviorType, Date startDate, Date endDate, Long userId, String name,
                                                           Boolean monthStat, Boolean dayStat, Long userBehaviorId) {
        try {
            String hql = "from UserBehavior where status = ?0 ";
            List args = new ArrayList();
            args.add(CommonStatus.ENABLE);
            int index = 1;
            if (behaviorType != null) {
                hql += "and userBehaviorConfig.behaviorType=?" + (index++) + " ";
                args.add(behaviorType);
            }
            if (monthStat != null) {
                hql += "and monthStat=?" + (index++) + " ";
                args.add(monthStat);
            }
            if (dayStat != null) {
                hql += "and dayStat=?" + (index++) + " ";
                args.add(dayStat);
            }
            if (userBehaviorId != null) {
                hql += "and id=?" + (index++) + " ";
                args.add(userBehaviorId);
            }
            if (userId != null) {
                hql += "and userId=?" + (index++) + " ";
                args.add(userId);
            }
            List<UserBehavior> userBehaviors = this.getEntityListNoPageHQL(hql, args.toArray());
            if (userBehaviors.isEmpty()) {
                return new ArrayList<>();
            }
            List<UserBehaviorDataStat> resultList = new ArrayList<>();
            for (UserBehavior userBehavior : userBehaviors) {
                List<UserBehaviorDataStat> result = this.statUserBehaviorData(userBehavior, startDate, endDate, userId, name);
                if (!result.isEmpty()) {
                    resultList.addAll(result);
                }
            }
            return resultList;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取个人行为分析数据异常", e);
        }
    }

    /**
     * 获取用户行为分析
     *
     * @param userBehavior
     * @param startDate
     * @param endDate
     * @param userId
     * @param name
     * @return
     */
    public List<UserBehaviorDataStat> statUserBehaviorData(UserBehavior userBehavior, Date startDate, Date endDate, Long userId, String name) {
        try {
            List queryArgs = new ArrayList();
            queryArgs.add(startDate);
            queryArgs.add(endDate);
            queryArgs.add(userId);
            StringBuffer sb = new StringBuffer();
            UserBehaviorConfig config = userBehavior.getUserBehaviorConfig();
            String sqlContent = config.getSqlContent();
            sb.append(MysqlUtil.replaceBindValues(sqlContent, userBehavior.getBindValues()));
            if (!sqlContent.contains("where")) {
                sb.append(" where 1=1");
            }
            sb.append(" and " + config.getDateField() + ">=?0 and " + config.getDateField() + "<=?1 ");
            sb.append(" and " + config.getUserField() + "=?2 ");
            if (!StringUtil.isEmpty(config.getKeywords()) && !StringUtil.isEmpty(name)) {
                //关键字查询
                String[] ss = config.getKeywords().split(",");
                String arg = "%" + name + "%";
                int l = ss.length;
                sb.append(" and (");
                int index = 3;
                for (int i = 0; i < l; i++) {
                    if (l > 1 && i > 0) {
                        sb.append(" or ");
                    }
                    sb.append(ss[i] + " like ?" + (index++) + " ");
                    queryArgs.add(arg);
                }
                sb.append(")");
            }
            List<UserBehaviorDataStat> result = this.getEntityListWithClassSQL(sb.toString(), -1, 0, UserBehaviorDataStat.class, queryArgs.toArray());
            for (UserBehaviorDataStat stat : result) {
                stat.setBehaviorType(userBehavior.getUserBehaviorConfig().getBehaviorType());
                stat.setDateRegion(config.getDateRegion());
            }
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取个人行为分析数据异常", e);
        }
    }

    /**
     * 获取用户行为配置的关键字列表
     *
     * @return
     */
    public List<String> getUserBehaviorKeywordsList(Long id) {
        try {
            UserBehavior userBehavior = (UserBehavior) this.getEntityById(UserBehavior.class, id);
            String sql = userBehavior.getUserBehaviorConfig().getKeywordsSearchSql();
            if (StringUtil.isEmpty(sql)) {
                return new ArrayList<>();
            } else {
                return this.getEntityListSQL(sql, 0, 0);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户行为配置的关键字列表异常", e);
        }
    }

    /**
     * 获取用户操作记录
     *
     * @param c
     * @param page
     * @param pageSize
     * @param sortField
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getUserOperationList(Class<T> c, int page, int pageSize, Date startTime, Date endTime, Long userId, String dateField, String sortField) {
        try {
            String hql = "from " + c.getSimpleName() + " where " + dateField + ">=?0 and " + dateField + "<=?1 and userId=?2 order by " + sortField + " desc";
            List<T> list = this.getEntityListHQL(hql, page, pageSize, startTime, endTime, userId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取列表异常", e);
        }
    }

}
