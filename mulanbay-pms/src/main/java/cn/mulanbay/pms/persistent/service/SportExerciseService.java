package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.SportExercise;
import cn.mulanbay.pms.persistent.domain.SportMilestone;
import cn.mulanbay.pms.persistent.domain.SportType;
import cn.mulanbay.pms.persistent.dto.SportExerciseDateStat;
import cn.mulanbay.pms.persistent.dto.SportExerciseMultiStat;
import cn.mulanbay.pms.persistent.dto.SportExerciseStat;
import cn.mulanbay.pms.persistent.enums.BestType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.NextMilestoneType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.sport.SportExerciseByMultiStatSearch;
import cn.mulanbay.pms.web.bean.request.sport.SportExerciseDateStatSearch;
import cn.mulanbay.pms.web.bean.request.sport.SportExerciseMultiStatSearch;
import cn.mulanbay.pms.web.bean.request.sport.SportExerciseStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 锻炼
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class SportExerciseService extends BaseHibernateDao {

    /**
     * 获取按时间的统计
     *
     * @param sf
     * @return
     */
    public List<SportExerciseDateStat> statDateSportExercise(SportExerciseDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,sum(kilometres) as totalKilometres,");
            sb.append("count(0) as totalCount, ");
            sb.append("sum(max_heart_rate) as totalMaxHeartRate,");
            sb.append("sum(average_heart_rate) as totalAverageHeartRate,");
            sb.append("sum(speed) as totalSpeed,");
            sb.append("sum(minutes) as totalMinutes,");
            sb.append("sum(pace) as totalPace ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("exercise_date", dateGroupType) + "as indexValue,");
            sb.append("kilometres,minutes,speed,pace,max_heart_rate,average_heart_rate from sport_exercise ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<SportExerciseDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), SportExerciseDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "锻炼统计异常", e);
        }
    }

    /**
     * 获取锻炼的总的统计
     *
     * @param sf
     * @return
     */
    public SportExerciseStat statSportExercise(SportExerciseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select count(*) as totalCount,sum(kilometres) as totalKilometres,sum(minutes) as totalMinutes from sport_exercise";
            sql += pr.getParameterString();
            List<SportExerciseStat> list = this.getEntityListWithClassSQL(sql, pr.getPage(), pr.getPageSize(), SportExerciseStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "锻炼统计异常", e);
        }
    }

    /**
     * 获取锻炼的多重统计：最大、最小、平均
     *
     * @param sf
     * @return
     */
    public SportExerciseMultiStat statMultiSportExercise(SportExerciseMultiStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            //最佳
            sb.append("select sport_type_id as sportTypeId,");
            sb.append("max(kilometres) as maxKilometres,");
            sb.append("max(minutes) as maxMinutes,");
            sb.append("max(speed) as maxSpeed,");
            sb.append("max(max_speed) as maxMaxSpeed,");
            sb.append("min(pace) as maxPace,");
            sb.append("min(max_pace) as maxMaxPace,");
            sb.append("max(max_heart_rate) as maxMaxHeartRate,");
            sb.append("max(average_heart_rate) as maxAverageHeartRate,");
            //最小
            sb.append("min(kilometres) as minKilometres,");
            sb.append("min(minutes) as minMinutes,");
            sb.append("min(speed) as minSpeed,");
            sb.append("min(max_speed) as minMaxSpeed,");
            sb.append("max(pace) as minPace,");
            sb.append("max(max_pace) as minMaxPace,");
            sb.append("min(max_heart_rate) as minMaxHeartRate,");
            sb.append("min(average_heart_rate) as minAverageHeartRate,");
            //平均
            sb.append("avg(kilometres) as avgKilometres,");
            sb.append("avg(minutes) as avgMinutes,");
            sb.append("avg(speed) as avgSpeed,");
            sb.append("avg(max_speed) as avgMaxSpeed,");
            sb.append("avg(pace) as avgPace,");
            sb.append("avg(max_pace) as avgMaxPace,");
            sb.append("avg(max_heart_rate) as avgMaxHeartRate,");
            sb.append("avg(average_heart_rate) as avgAverageHeartRate ");
            sb.append("from sport_exercise ");
            sb.append(pr.getParameterString());
            sb.append("group by sport_type_id ");
            SportExerciseMultiStat data = null;
            List<SportExerciseMultiStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), SportExerciseMultiStat.class, pr.getParameterValue());
            if (list.isEmpty()) {
                data = new SportExerciseMultiStat();
                data.setSportTypeId(BigInteger.valueOf(sf.getSportTypeId().longValue()));
                return data;
            } else {
                data = list.get(0);
                if (sf.getSportTypeId() != null) {
                    SportType sportType = (SportType) this.getEntityById(SportType.class, sf.getSportTypeId());
                    data.setUnit(sportType.getUnit());
                }
            }
            return data;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取锻炼的多重统计：最大、最小、平均异常", e);
        }
    }

    /**
     * 根据最大统计值获取锻炼信息
     *
     * @param sf
     * @return
     */
    public SportExercise getSportExerciseByMultiStat(SportExerciseByMultiStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String method = "";
            if (sf.getType() == SportExerciseByMultiStatSearch.Type.MAX) {
                method = "max";
                if (sf.getGroupType() == GroupType.MAXPACE || sf.getGroupType() == GroupType.PACE) {
                    //pace正好相反
                    method = "min";
                }
            } else {
                method = "min";
                if (sf.getGroupType() == GroupType.MAXPACE || sf.getGroupType() == GroupType.PACE) {
                    //pace正好相反
                    method = "max";
                }
            }
            String paraString = pr.getParameterString();
            StringBuffer sb = new StringBuffer();
            sb.append("from SportExercise");
            sb.append(paraString);
            sb.append(" and " + sf.getGroupType().getField() + " =");
            sb.append("(select " + method + "(" + sf.getGroupType().getField() + ") from SportExercise ");
            PageRequest pr2 = sf.buildQuery();
            //下标需要重新计算
            pr2.setFirstIndex(pr.getNextIndex());
            sb.append(pr2.getParameterString() + ") ");
            List newArgs = new ArrayList();
            //需要两遍，因为前面的条件在父查询、子查询中都有包含
            newArgs.addAll(pr.getParameterValueList());
            newArgs.addAll(pr2.getParameterValueList());
            List<SportExercise> list = this.getEntityListHQL(sb.toString(), sf.getIndex(), 1, newArgs.toArray());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取锻炼的最大值的统计异常", e);
        }
    }

    /**
     * 获取最大的里程碑ID
     *
     * @param sportTypeId
     * @param selfMilestoneId 除去自己的
     * @return
     */
    public Short getMaxOrderIndexOfSportMilestone(Integer sportTypeId, Long selfMilestoneId) {
        try {
            String sql = "select max(order_index) from sport_milestone where sport_type_id = ?0 ";
            if (selfMilestoneId != null) {
                sql += "and id !=" + selfMilestoneId;
            }
            List list = this.getEntityListNoPageSQL(sql, sportTypeId);
            if (list.isEmpty()) {
                return null;
            }
            Object o = list.get(0);
            if (o == null) {
                return null;
            }
            return Short.valueOf(list.get(0).toString());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "锻炼统计异常", e);
        }
    }

    /**
     * 保存锻炼，且更新里程碑(目前仅针对新增操作)
     *
     * @param list
     * @param updateMilestone
     */
    public void saveSportExerciseList(List<SportExercise> list, boolean updateMilestone) {
        for (SportExercise se : list) {
            this.saveSportExercise(se, updateMilestone);
        }
    }

    /**
     * 保存锻炼，且更新里程碑(目前仅针对新增操作)
     *
     * @param bean
     * @param updateMilestone
     */
    public void saveSportExercise(SportExercise bean, boolean updateMilestone) {
        try {
            boolean isCreate = true;
            if (bean.getId() == null) {
                this.saveEntity(bean);
            } else {
                isCreate = false;
                this.updateEntity(bean);
            }
            this.updateBest(bean, isCreate);
            if (isCreate && updateMilestone) {
                updateAndRefreshSportMilestone(false, bean.getSportType().getId());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "保存锻炼异常", e);
        }
    }

    /**
     * 达到的里程碑，不管以前的锻炼是否已经有实现过
     *
     * @param sportExerciseId
     */
    public List<SportMilestone> getAchieveMilestones(Long sportExerciseId) {
        try {
            SportExercise se = (SportExercise) this.getEntityById(SportExercise.class, sportExerciseId);
            String hql = "from SportMilestone where ((kilometres<=?0 and minutes=0) or (kilometres<=?1 and minutes>=?2 and minutes>0)) and sportType.id=?3 order by orderIndex desc";
            List<SportMilestone> list = this.getEntityListNoPageHQL(hql, se.getKilometres(), se.getKilometres(), se.getMinutes(), se.getSportType().getId());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "保存锻炼异常", e);
        }
    }

    /**
     * 获取下一个待达到的里程碑
     *
     * @param sportExerciseId
     * @param type
     */
    public SportMilestone getNextAchieveMilestone(Long sportExerciseId, NextMilestoneType type) {
        try {
            SportExercise se = (SportExercise) this.getEntityById(SportExercise.class, sportExerciseId);
            if (null == type || type == NextMilestoneType.CURRENT) {
                //当前该锻炼需要实现的下一个里程碑ssadad
                String hql = "select max(orderIndex) from SportMilestone where ((kilometres<=?0 and minutes=0) or (kilometres<=?1 and minutes>=?2 and minutes>0)) and sportType.id=?3 ";
                Short orderIndex = (Short) this.getEntityForOne(hql, se.getKilometres(), se.getKilometres(), se.getMinutes(), se.getSportType().getId());
                if (orderIndex == null) {
                    //没有，则取第一个
                    orderIndex = 1;
                } else {
                    orderIndex++;
                }
                String hql2 = "from SportMilestone where orderIndex=?0 and sportType.id=?1 ";
                return (SportMilestone) this.getEntityForOne(hql2, orderIndex, se.getSportType().getId());
            } else {
                //针对所有,未实现就可以了
                String hql = "select max(orderIndex) from SportMilestone where sportExercise.id !=null and sportType.id=?0 ";
                Short orderIndex = (Short) this.getEntityForOne(hql, se.getSportType().getId());
                if (orderIndex == null) {
                    //没有，则取第一个
                    orderIndex = 1;
                } else {
                    orderIndex++;
                }
                String hql2 = "from SportMilestone where orderIndex=?0 and sportType.id=?1 ";
                return (SportMilestone) this.getEntityForOne(hql2, orderIndex, se.getSportType().getId());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取下一个待达到的里程碑异常", e);
        }
    }

    /**
     * 更新最佳状态
     *
     * @param bean
     * @param isCreate
     */
    private void updateBest(SportExercise bean, boolean isCreate) {
        try {
            boolean needUpdate = false;
            //获取最佳的（采用平均速度，最大的速度意义不大）,跟以前的比，除了个人录入原因外，实际上当前录入的可能都是最新的
            String sql = "select max(kilometres) as maxKilometres,max(speed) as maxSpeed from sport_exercise where sport_type_id=?0 and exercise_date<?1 ";
            String addtion = "";
            if (!isCreate) {
                addtion = " and id != " + bean.getId();
            }
            List<SportExerciseMultiStat> list = this.getEntityListWithClassSQL(sql + addtion, 1, 1, SportExerciseMultiStat.class, bean.getSportType().getId(), bean.getExerciseDate());
            SportExerciseMultiStat data = list.get(0);
            BigDecimal kilometres = data.getMaxKilometres();
            if (kilometres == null || kilometres.doubleValue() < bean.getKilometres().doubleValue()) {
                //说明现在的是最佳,把以前的都更新为历史
                String hql = "update SportExercise set mileageBest=?0 where mileageBest=?1 and sportType.id=?2 ";
                this.updateEntities(hql + addtion, BestType.ONCE, BestType.CURRENT, bean.getSportType().getId());
                needUpdate = true;
                bean.setMileageBest(BestType.CURRENT);
            }
            BigDecimal speed = data.getMaxSpeed();
            if (speed == null || speed.doubleValue() < bean.getSpeed().doubleValue()) {
                //说明现在的是最佳,把以前的都更新为历史
                String hql = "update SportExercise set fastBest=?0 where fastBest=?1 and sportType.id=?2 ";
                this.updateEntities(hql + addtion, BestType.ONCE, BestType.CURRENT, bean.getSportType().getId());
                needUpdate = true;
                bean.setFastBest(BestType.CURRENT);
            }

            if (needUpdate) {
                this.updateEntity(bean);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新最佳状态异常", e);
        }
    }

    /**
     * 初始化里程碑(由于配置文件中事务的规则以方法名做前缀，因此这里需要特别注意)
     *
     * @param reInit
     * @param sportTypeId
     */
    public void updateAndRefreshSportMilestone(boolean reInit, Integer sportTypeId) {
        try {
            if (reInit) {
                //全部初始化
                String sql = "update sport_milestone set sportExerciseId = null,fromExerciseDate = null,toExerciseDate=null,costDays=null where sportTypeId= ?0 ";
                this.execSqlUpdate(sql, sportTypeId);
            }
            //获取没有完成的里程碑
            String hql = "from SportMilestone where sportType.id =?0 and sportExercise.id is null order by orderIndex";
            List<SportMilestone> list = this.getEntityListNoPageHQL(hql, sportTypeId);
            for (SportMilestone sm : list) {
                independUpdateAndRefreshSportMilestone(sm);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "初始化里程碑异常", e);
        }
    }

    /**
     * 重新刷新锻炼的最佳统计数据
     *
     * @param sportTypeId
     */
    public void updateAndRefreshSportExerciseMaxStat(Integer sportTypeId) {
        try {
            //获取没有完成的里程碑
            String hql = "from SportExercise where sportType.id =?0 order by exerciseDate";
            List<SportExercise> list = this.getEntityListNoPageHQL(hql, sportTypeId);
            for (SportExercise sm : list) {
                updateBest(sm, false);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "重新刷新锻炼的最佳统计数据异常", e);
        }
    }

    /**
     * 独立出来希望能做单独事务
     *
     * @param sm
     */
    public void independUpdateAndRefreshSportMilestone(SportMilestone sm) {
        try {
            String hh = "from SportExercise where sportType.id =?0 and kilometres>=?1 ";
            if (sm.getMinutes() > 0) {
                hh += " and minutes<=" + sm.getMinutes();
            }
            hh += " order by exerciseDate asc";
            SportExercise se = (SportExercise) this.getEntityForOne(hh, sm.getSportType().getId(), sm.getKilometres());
            if (se != null) {
                // 再获取花费天数
                Date fromDate = this.getPreviousMilestoneSportExercise(sm, se.getId());
                Date toDate = se.getExerciseDate();
                int costDays = DateUtil.getIntervalDays(fromDate, toDate);
                sm.setSportExercise(se);
                sm.setFromExerciseDate(fromDate);
                sm.setToExerciseDate(se.getExerciseDate());
                sm.setCostDays(costDays);
                //this.updateEntity(sm);
                //时间类型不能绑定站位符编号？
                String sql = "update sport_milestone set sport_exercise_id = ?0,from_exercise_date = ?,to_exercise_date = ?,cost_days= ?1 where id= ?2 ";
                this.execSqlUpdate(sql, se.getId(), fromDate, toDate, costDays, sm.getId());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新里程碑的锻炼异常", e);
        }
    }


    /**
     * 获取上一个实现的里程碑的锻炼(不能和当前的一样)
     *
     * @param milestone
     * @param sportExerciseId
     * @return
     */
    public Date getPreviousMilestoneSportExercise(SportMilestone milestone, Long sportExerciseId) {
        try {
            String hql = "from SportMilestone where sportType.id =?0 and sportExercise.id !=null and sportExercise.id !=?1 order by orderIndex desc";
            SportMilestone sm = (SportMilestone) this.getEntityForOne(hql, milestone.getSportType().getId(), sportExerciseId);
            if (sm == null) {
                //获取第一个
                String hh = "from SportExercise where sportType.id =?0 order by exerciseDate asc";
                SportExercise se = (SportExercise) this.getEntityForOne(hh, milestone.getSportType().getId());
                return se.getExerciseDate();
            } else {
                return sm.getToExerciseDate();
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "获取上一个实现的里程碑的锻炼异常", e);
        }
    }

    /**
     * 获取实现的里程数（如果以前已经有实现了，那就不计算在内）
     *
     * @param sportExerciseId
     * @return
     */
    public long getMilestoneCount(Long sportExerciseId) {
        try {
            String hql = "select count(*) from SportMilestone where sportExercise.id=?0 ";
            return this.getCount(hql, sportExerciseId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取实现的里程数异常", e);
        }
    }

    /**
     * 获取实现的里程数
     *
     * @param sportExerciseId
     * @return
     */
    public List<SportMilestone> getMilestoneList(Long sportExerciseId) {
        try {
            String hql = "from SportMilestone where sportExercise.id=?0 ";
            return this.getEntityListNoPageHQL(hql, sportExerciseId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取实现的里程数异常", e);
        }
    }

    /**
     * 删除锻炼
     *
     * @param sportExerciseId
     * @return
     */
    public void deleteSportExercise(Long sportExerciseId) {
        try {
            //删除绑定的里程碑
            String hql = "update SportMilestone set sportExercise.id=null,fromExerciseDate=null,toExerciseDate=null,costDays=null where sportExercise.id=?0 ";
            this.updateEntities(hql, sportExerciseId);

            String hql2 = "delete from SportExercise where id=?0 ";
            this.updateEntities(hql2, sportExerciseId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_DELETE_ERROR,
                    "删除锻炼异常", e);
        }
    }

    /**
     * 获取最佳的锻炼
     *
     * @param sf
     * @return
     */
    public List<SportExercise> getBestMilestoneSportExerciseList(SportExerciseDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("from SportExercise ");
            sb.append(pr.getParameterString());
            sb.append(" and " + sf.getBestField() + " is not null order by exerciseDate");
            return this.getEntityListNoPageHQL(sb.toString(), pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最佳的锻炼异常", e);
        }
    }
}
