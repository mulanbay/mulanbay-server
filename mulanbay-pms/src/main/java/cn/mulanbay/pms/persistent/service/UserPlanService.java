package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.PlanConfig;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanConfigValue;
import cn.mulanbay.pms.persistent.domain.UserPlanRemind;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.schedule.enums.TriggerType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserPlanService extends BaseHibernateDao {

    /**
     * 保存用户计划
     *
     * @param bean
     */
    public void saveUsePlan(UserPlan bean) {
        try {
            bean.setCreatedTime(new Date());
            this.saveEntity(bean);
            PlanConfig planConfig = (PlanConfig) this.getEntityById(PlanConfig.class, bean.getPlanConfig().getId());
            // 保存默认的值
            UserPlanConfigValue upcv = new UserPlanConfigValue();
            upcv.setCreatedTime(new Date());
            upcv.setPlanCountValue(planConfig.getDefaultPlanCountValue());
            upcv.setPlanValue(planConfig.getDefaultPlanValue());
            upcv.setUserPlan(bean);
            upcv.setUserId(bean.getUserId());
            upcv.setYear(DateUtil.getYear(new Date()));
            this.saveEntity(upcv);
            checkAndSaveUserPlanRemind(bean);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "保存用户计划异常", e);
        }
    }

    /**
     * 更新用户计划
     *
     * @param bean
     */
    public void updateUsePlan(UserPlan bean) {
        try {
            bean.setLastModifyTime(new Date());
            this.updateEntity(bean);
            checkAndSaveUserPlanRemind(bean);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新用户计划异常", e);
        }
    }

    /**
     * 检查及保存用户计划提醒
     *
     * @return
     */
    public void checkAndSaveUserPlanRemind(UserPlan bean) {
        try {
            //检查提醒配置
            UserPlanRemind remind = this.getRemindByUserPlan(bean.getId(), bean.getUserId());
            if (remind == null) {
                //生成默认
                remind = new UserPlanRemind();
                remind.setCreatedTime(new Date());
                remind.setFinishedRemind(true);
                remind.setFormTimePassedRate(50);
                remind.setRemark("由表单页面自动生成");
                remind.setRemindTime("08:30");
                remind.setTriggerInterval(1);
                remind.setTriggerType(TriggerType.DAY);
                remind.setUserId(bean.getUserId());
                remind.setUserPlan(bean);
                this.saveEntity(remind);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "检查及保存用户计划提醒异常", e);
        }
    }

    /**
     * 删除用户计划
     *
     * @param userPlan
     */
    public void deleteUsePlan(UserPlan userPlan) {
        try {
            String sql = "delete from user_plan_remind where userPlanId=?0 ";
            this.execSqlUpdate(sql, userPlan.getId());
            //删除计划值
            sql = "delete from user_plan_config_value where userPlanId=?0";
            this.execSqlUpdate(sql, userPlan.getId());
            //删除计划配置
            sql = "delete from user_plan where id=?0";
            this.execSqlUpdate(sql, userPlan.getId());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_DELETE_ERROR,
                    "删除用户计划异常", e);
        }
    }

    /**
     * 查找用户计划提醒
     *
     * @param userPlanId
     */
    public Long getRemindIdByUserPlan(Long userPlanId) {
        try {
            String hql = "select id from UserPlanRemind where userPlan.id=?0";
            return (Long) this.getEntityForOne(hql, userPlanId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "查找用户计划提醒异常", e);
        }
    }

    /**
     * 查找用户计划提醒信息，很少的数据
     *
     * @param id
     */
    public UserPlanRemind getRemindNotifyInfo(Long id) {
        try {
            String hql = "select new UserPlanRemind(id,triggerType,triggerIntervel,remindTime) from UserPlanRemind where id=?0";
            return (UserPlanRemind) this.getEntityForOne(hql, id);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "查找用户计划提醒异常", e);
        }
    }

    /**
     * 查找用户计划提醒
     *
     * @param userPlanId
     */
    public UserPlanRemind getRemindByUserPlan(Long userPlanId, Long userId) {
        try {
            String hql = "from UserPlanRemind where userPlan.id=?0 and userId=?1";
            return (UserPlanRemind) this.getEntityForOne(hql, userPlanId, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "查找用户计划提醒异常", e);
        }
    }

    /**
     * 更新最后提醒时间
     *
     * @param remindId
     */
    public void updateLastRemindTime(Long remindId, Date date) {
        try {
            String hql = "update UserPlanRemind set lastRemindTime=?0 where id=?1";
            this.updateEntities(hql, date, remindId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新最后提醒时间异常", e);
        }
    }

    /**
     * 获取需要提醒的用户计划列表
     *
     * @param planType
     * @return
     */
    public List<UserPlan> getNeedRemindUserPlan(PlanType planType) {
        try {
            String hql = "from UserPlan where planConfig.planType=?0 and status=?1 and remind=?2 ";
            return this.getEntityListNoPageHQL(hql, planType, CommonStatus.ENABLE, true);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要提醒的用户计划列表异常", e);
        }
    }

    /**
     * 获取有效的用户计划列表
     *
     * @return
     */
    public List<UserPlan> getActiveUserPlan() {
        try {
            String hql = "from UserPlan where status=?0 ";
            return this.getEntityListNoPageHQL(hql, CommonStatus.ENABLE);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取有效的用户计划列表异常", e);
        }
    }

    /**
     * 获取月度预算配置
     *
     * @return
     */
    public UserPlanConfigValue getMonthBudgetConfig(Long userId) {
        try {
            String hql = "from UserPlanConfigValue where userPlan.id = (select id from UserPlan where planConfig.id=31 and userId=?0 ) order by year desc ";
            List<UserPlanConfigValue> list = this.getEntityListHQL(hql, 1, 1, userId);
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取月度预算配置异常", e);
        }
    }

}
