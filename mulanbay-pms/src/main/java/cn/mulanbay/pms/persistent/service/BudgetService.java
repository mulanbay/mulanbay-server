package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.domain.BudgetSnapshot;
import cn.mulanbay.pms.persistent.domain.BudgetTimeline;
import cn.mulanbay.pms.persistent.dto.BudgetStat;
import cn.mulanbay.pms.persistent.dto.UserBudgetAndIncomeStat;
import cn.mulanbay.pms.persistent.dto.UserBudgetStat;
import cn.mulanbay.pms.persistent.enums.BudgetStatType;
import cn.mulanbay.pms.persistent.enums.BudgetType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预算
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Service
@Transactional
public class BudgetService extends BaseHibernateDao {

    /**
     * 获取预算分析
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BudgetStat> statBudget(Long userId, BudgetType type, PeriodType period, BudgetStatType statType) {
        try {
            StringBuffer sql = new StringBuffer();
            List args = new ArrayList();
            if (statType == BudgetStatType.NAME) {
                sql.append("select name as name,sum(amount) as value from ");
            } else if (statType == BudgetStatType.TYPE) {
                sql.append("select type as id,sum(amount) as value from ");
            } else if (statType == BudgetStatType.PERIOD) {
                sql.append("select period as id,sum(amount) as value from ");
            }
            sql.append("(");
            sql.append("SELECT name,type,period,");
            sql.append("case when period=0 then amount ");
            sql.append("when period=1 then amount*365 ");
            sql.append("when period=2 then amount*52 ");
            sql.append("when period=3 then amount*12 ");
            sql.append("when period=4 then amount*4 ");
            sql.append("when period=5 then amount ");
            sql.append("end as amount ");
            sql.append("from budget ");
            sql.append("where user_id=?0 and status=1 ");
            args.add(userId);
            int index = 1;
            if (type != null) {
                sql.append("and type=?" + (index++) + " ");
                args.add(type.getValue());
            }
            if (period != null) {
                sql.append("and period=?" + (index++) + " ");
                args.add(period.getValue());
            }
            sql.append(") as aa ");
            if (statType == BudgetStatType.NAME) {
                sql.append("group by name ");
            } else if (statType == BudgetStatType.TYPE) {
                sql.append("group by type ");
            } else if (statType == BudgetStatType.PERIOD) {
                sql.append("group by period ");
            }
            List<BudgetStat> list = this.getEntityListWithClassSQL(sql.toString(), 0, 0,
                    BudgetStat.class, args.toArray());
            if (statType == BudgetStatType.TYPE) {
                for (BudgetStat aas : list) {
                    //logger.debug(aas.getId().getClass());
                    BudgetType it = BudgetType.getBudgetType(Integer.valueOf(aas.getId().toString()));
                    if (it == null) {
                        aas.setName("未知");
                    } else {
                        aas.setName(it.getName());
                    }
                }
            } else if (statType == BudgetStatType.PERIOD) {
                for (BudgetStat aas : list) {
                    PeriodType it = PeriodType.getPeriodType(Integer.valueOf(aas.getId().toString()));
                    if (it == null) {
                        aas.setName("未知");
                    } else {
                        aas.setName(it.getName());
                    }
                }
            }
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预算分析异常", e);
        }
    }


    /**
     * 获取用户预算列表
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Budget> getActiveUserBudget(Long userId, Boolean bindFlow) {
        try {
            String hql = "from Budget where status=?0 ";
            List args = new ArrayList();
            args.add(CommonStatus.ENABLE);
            int index = 1;
            if (userId != null) {
                hql += " and userId=?" + (index++) + " ";
                args.add(userId);
            }
            if (true == bindFlow) {
                hql += " and feeType is not null" ;
            }
            hql += " order by userId";
            List<Budget> list = this.getEntityListNoPageHQL(hql, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预算分析异常", e);
        }
    }

    /**
     * @param userId
     * @param bindFlow
     * @param hasEpt   是否有期望付款时间
     * @return
     */
    public List<Budget> getActiveUserBudget(Long userId, String name, Boolean bindFlow, Boolean hasEpt) {
        try {
            String hql = "from Budget where status=?0 ";
            List args = new ArrayList();
            args.add(CommonStatus.ENABLE);
            int index = 1;
            if (userId != null) {
                hql += " and userId=?" + (index++) + " ";
                args.add(userId);
            }
            if (true == bindFlow) {
                hql += " and feeType is not null" ;
            }
            if (StringUtil.isNotEmpty(name)) {
                hql += " and name like '%" + name + "%' ";
            }
            if (hasEpt != null) {
                if (hasEpt) {
                    hql += " and expectPaidTime is not null";
                } else {
                    hql += " and expectPaidTime is null";
                }
            }
            hql += " order by userId";
            List<Budget> list = this.getEntityListNoPageHQL(hql, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预算分析异常", e);
        }
    }


    /**
     * 预算日志是否存在
     */
    public boolean isBudgetLogExit(String bussKey, Long userId, Long budgetLogId, Long budgetId) {
        BudgetLog budgetLog = this.selectBudgetLog(bussKey, userId, budgetLogId, budgetId);
        return budgetLog == null ? false : true;
    }

    /**
     * 根据bussKey查询预算日志
     * 统计类型的不需要指定某个具体的预算，直接根据bussKey和userId来决定
     */
    public BudgetLog selectBudgetLog(String bussKey, Long userId, Long budgetLogId, Long budgetId) {
        try {
            String hql = "from BudgetLog where bussKey=?0 and userId=?1 ";
            if (budgetLogId != null) {
                hql += " and id!=" + budgetLogId;
            }
            if (budgetId != null) {
                hql += " and budget.id=" + budgetId;
            }else{
                hql += " and budget.id is null";
            }
            BudgetLog budgetLog = (BudgetLog) this.getEntityForOne(hql, bussKey, userId);
            return budgetLog;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据bussKey查询预算日志异常", e);
        }
    }

    /**
     * 保存预算日志(日终统计)
     */
    public void saveStatBudgetLog(List<Budget> ccList, BudgetLog budgetLog, boolean isRedo) {
        try {
            if (isRedo) {
                //删除快照
                BudgetLog hisBl = this.selectBudgetLog(budgetLog.getBussKey(), budgetLog.getUserId());
                if (hisBl != null) {
                    String hql = "delete from BudgetSnapshot where budgetLogId=?0 ";
                    this.updateEntities(hql, hisBl.getId());
                }
            }
            this.saveBudgetLog(budgetLog, isRedo);
            //保存预算快照
            List<BudgetSnapshot> ss = new ArrayList<>();
            for (Budget b : ccList) {
                BudgetSnapshot snapshot = new BudgetSnapshot();
                BeanCopy.copyProperties(b, snapshot);
                snapshot.setId(null);
                snapshot.setBudgetLogId(budgetLog.getId());
                snapshot.setFromId(b.getId());
                ss.add(snapshot);
            }
            this.saveEntities(ss.toArray());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    " 保存预算日志(日终统计)异常", e);
        }

    }

    /**
     * 保存预算日志
     */
    public void saveBudgetLog(BudgetLog budgetLog, boolean isRedo) {
        try {
            if (isRedo) {
                String hql = "delete from BudgetLog where bussKey=?0 and userId=?1 and budget is null";
                this.updateEntities(hql, budgetLog.getBussKey(), budgetLog.getUserId());
            }
            this.saveEntity(budgetLog);
            Budget budget = budgetLog.getBudget();
            if (budget != null) {
                budget.setLastPaidTime(budgetLog.getOccurDate());
                if (budget.getFirstPaidTime() == null) {
                    budget.setFirstPaidTime(budgetLog.getOccurDate());
                }
                if (budget.getPeriod() == PeriodType.ONCE) {
                    //关闭该预算
                    budget.setStatus(CommonStatus.DISABLE);
                }
                this.updateEntity(budget);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "保存预算日志异常", e);
        }
    }

    /**
     * 保存预算时间线
     */
    public void saveBudgetTimeline(BudgetTimeline timeline, boolean isRedo) {
        try {
            if (isRedo) {
                String hql = "delete from BudgetTimeline where bussKey=?0 and userId=?1 and bussDay=?2 ";
                this.updateEntities(hql, timeline.getBussKey(), timeline.getUserId(), timeline.getBussDay());
            }
            this.saveEntity(timeline);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "梦想统计异常", e);
        }
    }

    /**
     * 重新保存预算时间线
     */
    public void reSaveBudgetTimeline(List<BudgetTimeline> datas, String bussKey, Long userId) {
        try {
            String hql = "delete from BudgetTimeline where bussKey=?0 and userId=?1";
            this.updateEntities(hql, bussKey, userId);
            this.saveEntities(datas.toArray());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "重新保存预算时间线异常", e);
        }
    }

    /**
     * 根据bussKey查询预算流水
     */
    public List<BudgetTimeline> selectBudgetTimelineList(String bussKey, Long userId) {
        try {
            String hql = "from BudgetTimeline where bussKey=?0 and userId=?1 order by bussDay";

            return this.getEntityListNoPageHQL(hql, bussKey, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据bussKey查询预算流水异常", e);
        }
    }

    /**
     * 根据bussKey查询预算流水
     */
    public BudgetLog selectBudgetLog(String bussKey, Long userId) {
        try {
            String hql = "from BudgetLog where bussKey=?0 and userId=?1 and budget is null";
            return (BudgetLog) this.getEntityForOne(hql, bussKey, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据bussKey查询预算流水异常", e);
        }
    }

    /**
     * 获取用户预算即收入分析
     *
     * @param period
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserBudgetAndIncomeStat> statUserBudgetAndIncome(Date startTime, Date endTime, Long userId, PeriodType period) {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select bl.user_id as userId,bl.occur_date as occurDate,bl.budget_amount as budgetAmount,");
            sql.append("bl.nc_amount as ncAmount,bl.bc_amount as bcAmount,bl.tr_amount as trAmount,");
            sql.append("tt.totalIncome ");
            sql.append(" from budget_log bl ");
            sql.append("left join  ");
            sql.append("(select incomeDate,sum(amount) as totalIncome from ( ");
            sql.append("select CAST(DATE_FORMAT(occur_time,'%Y%m') AS signed) as incomeDate,amount from income ");
            sql.append("where user_id=?0 and occur_time>=?1 and occur_time<=?2 ) ");
            sql.append("as tt group by incomeDate) as tt ");
            sql.append("on CAST(DATE_FORMAT(bl.occur_date,'%Y%m') AS signed) = tt.incomeDate ");
            sql.append("where bl.user_id=?3 and bl.occur_date>=?4 and bl.occur_date<=?5 ");
            sql.append("and bl.period=?6 and budget_id is null ");
            sql.append("order by bl.occur_date ");
            List args = new ArrayList();
            args.add(userId);
            args.add(startTime);
            args.add(endTime);
            args.add(userId);
            args.add(startTime);
            args.add(endTime);
            args.add(period.getValue());
            List<UserBudgetAndIncomeStat> list = this.getEntityListWithClassSQL(sql.toString(), 0, 0,
                    UserBudgetAndIncomeStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预算分析异常", e);
        }
    }

}
