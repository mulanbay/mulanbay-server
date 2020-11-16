package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.domain.UserCalendarConfig;
import cn.mulanbay.pms.persistent.dto.CalendarLogDto;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-03-12 21:55
 */
@Service
@Transactional
public class UserCalendarService extends BaseHibernateDao {

    /**
     * 获取用户日历
     *
     * @param userId
     * @param bussIdentityKey
     * @param expireTime
     * @return
     */
    public UserCalendar getUserCalendar(Long userId, String bussIdentityKey, Date expireTime) {
        try {
            String hql = "from UserCalendar where userId =?0 and bussIdentityKey=?1 and expireTime>=?2 ";
            return (UserCalendar) this.getEntityForOne(hql, userId, bussIdentityKey, expireTime);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取用户日历异常", e);

        }
    }

    /**
     * 获取用户日历
     *
     * @param userId
     * @param sourceType
     * @param sourceId
     * @return
     */
    public UserCalendar getUserCalendar(Long userId, UserCalendarSource sourceType,String sourceId) {
        try {
            String hql = "from UserCalendar where userId =?0 and sourceType=?1 and sourceId=?2 ";
            return (UserCalendar) this.getEntityForOne(hql, userId, sourceType, sourceId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取用户日历异常", e);

        }
    }

    /**
     * 获取用户目前需要的日历
     *
     * @return
     */
    public List<UserCalendar> getCurrentUserCalendarList(Long userId) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from UserCalendar where expireTime>=?0 and finishedTime is null ");
            if (userId != null) {
                sb.append("and userId=" + userId);
            } else {
                sb.append("order by userId");
            }
            return this.getEntityListNoPageHQL(sb.toString(), new Date());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取用户目前需要的日历异常", e);

        }
    }

    /**
     * @param userId
     * @param needFinished 是否需要已经完成的
     * @param needPeriod   是否需要周期性的
     * @param startDate
     * @param endDate
     * @return
     */
    public List<UserCalendar> getCurrentUserCalendarList(Long userId, String name, UserCalendarSource sourceType, Boolean needFinished, Boolean needPeriod, Date startDate, Date endDate) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from UserCalendar where userId=?0 ");
            sb.append("and bussDay<=?1 and expireTime>=?2 ");
            if (false == needFinished) {
                sb.append("and finishType is null ");
            }
            if (false == needPeriod) {
                sb.append("and period=0 ");
            }
            if (sourceType != null) {
                sb.append("and sourceType=" + sourceType.ordinal());
            }
            if (StringUtil.isNotEmpty(name)) {
                sb.append("and (title like '%" + name + "%' or content like '%" + name + "%')");
            }
            return this.getEntityListNoPageHQL(sb.toString(), userId, endDate, startDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取用户目前需要的日历异常", e);

        }
    }


    /**
     * 获取用户目前需要的日历数
     *
     * @return
     */
    public Long getTodayUserCalendarCount(Long userId) {
        try {
            String hql = "select count(0) from UserCalendar where expireTime>=?0 and finishedTime is null and userId=?1 ";

            return this.getCount(hql, new Date(), userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR, "获取用户目前需要的日历数异常", e);

        }
    }


    /**
     * 更新用户日历
     *
     * @param uc
     * @param newDate
     */
    public void updateUserCalendarToDate(UserCalendar uc, Date newDate, Long messageId) {
        try {
            uc.setBussDay(newDate);
            uc.setDelayCounts(uc.getDelayCounts() + 1);
            uc.setLastModifyTime(new Date());
            uc.setMessageId(messageId);
            this.updateEntity(uc);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR, "更新用户日历异常", e);
        }
    }

    /**
     * 添加用户日历
     *
     * @param uc
     */
    public void addUserCalendarToDate(UserCalendar uc) {
        try {
            uc.setDelayCounts(0);
            if (uc.getAllDay() == null) {
                uc.setAllDay(true);
            }
            if (uc.getReadOnly() == null) {
                uc.setReadOnly(false);
            }
            if (uc.getPeriod() == null) {
                uc.setPeriod(PeriodType.ONCE);
            }
            uc.setCreatedTime(new Date());
            this.saveEntity(uc);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR, "添加用户日历异常", e);
        }
    }

    /**
     * 更新用户日历为已经完成
     *
     * @param userId
     * @param bussIdentityKey
     * @param finishedTime
     * @param finishType
     */
    public void updateUserCalendarForFinish(Long userId, String bussIdentityKey, Date finishedTime, UserCalendarFinishType finishType, Long finishSourceId) {
        try {
            String hql = "update UserCalendar set finishedTime=?0,finishType=?1,lastModifyTime=?2,finishSourceId=?3 where bussIdentityKey=?4 and userId=?5 ";
            this.updateEntities(hql, finishedTime, finishType, new Date(), finishSourceId, bussIdentityKey, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR, "更新用户日历为已经完成异常", e);

        }
    }

    /**
     * 更新用户日历为过期
     *
     * @param now
     */
    public int updateUserCalendarForExpired(Date now) {
        try {
            String hql = "update UserCalendar set finishType=?0,lastModifyTime=?1 " +
                    "where expireTime<=?2 and finishedTime is null and finishType is null";
            return this.updateEntities(hql, UserCalendarFinishType.EXPIRED, now, now);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR, "更新用户日历为过期异常", e);

        }
    }

    /**
     * 获取用户日历配置列表
     *
     * @return
     */
    public List<UserCalendarConfig> getUserCalendarConfigList(Integer minLevel) {
        try {
            String hql = "from UserCalendarConfig where status=?0 and level<=?1 ";
            return this.getEntityListNoPageHQL(hql, CommonStatus.ENABLE, minLevel);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户日历配置列表异常", e);
        }
    }

    /**
     * 获取用户日历的执行流水日志
     *
     * @return
     */
    public List<CalendarLogDto> getCalendarLogResultList(Long userId, Date startDate, Date endDate, Long calendarConfigId, String bindValues) {
        try {
            UserCalendarConfig ucc = (UserCalendarConfig) this.getEntityById(UserCalendarConfig.class, calendarConfigId);
            String sqlContent = ucc.getSqlContent();
            sqlContent = MysqlUtil.replaceBindValues(sqlContent, bindValues);
            List<Object[]> rr = null;
            Object[] args = new Object[]{userId, startDate, endDate};
            if (ucc.getSqlType() == SqlType.HQL) {
                rr = this.getEntityListNoPageHQL(sqlContent, args);
            } else {
                rr = this.getEntityListNoPageSQL(sqlContent, args);
            }
            List<CalendarLogDto> res = new ArrayList<>();
            for (Object[] oo : rr) {
                CalendarLogDto clr = new CalendarLogDto();
                clr.setDate((Date) oo[0]);
                clr.setValue(oo[1].toString());
                if (oo.length > 2) {
                    clr.setUnit(oo[2] == null ? null : oo[2].toString());
                }
                if (oo.length > 3) {
                    clr.setName(oo[3] == null ? null : oo[3].toString());
                }
                res.add(clr);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户日历的执行流水日志异常", e);
        }
    }

}
