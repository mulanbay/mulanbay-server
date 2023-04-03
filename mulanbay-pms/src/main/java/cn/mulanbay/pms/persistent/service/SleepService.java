package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.CommonRecord;
import cn.mulanbay.pms.persistent.domain.Sleep;
import cn.mulanbay.pms.persistent.dto.SleepAnalyseStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.SleepStatType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.sleep.SleepAnalyseStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 睡眠
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class SleepService extends BaseHibernateDao {


    public List<CommonRecord> getSleepRecord() {
        try {
            String hql = "from CommonRecord where commonRecordType.id in (6,7)";
            return this.getEntityListNoPageHQL(hql);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取睡眠记录异常", e);
        }
    }

    /**
     * 获取睡眠
     *
     * @param sleepDate
     * @return
     */
    public Sleep getSleep(Date sleepDate) {
        try {
            String hql = "from Sleep where sleepDate=?0 ";
            return (Sleep) this.getEntityForOne(hql, sleepDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取睡眠异常", e);
        }
    }

    /**
     * 获取最近的没有起床信息的睡眠
     *
     * @param fromTime
     * @return
     */
    public Sleep getNearUnGetUp(Date fromTime,Long userId) {
        try {
            String hql = "from Sleep where userId=?0 and getUpTime is null and sleepTime>=?1 ";
            return (Sleep) this.getEntityForOne(hql, userId,fromTime);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近的没有起床信息的睡眠异常", e);
        }
    }

    /**
     * 睡眠分析统计
     *
     * @param sf
     * @return
     */
    public List<SleepAnalyseStat> statSleepAnalyse(SleepAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            if (sf.getYgroupType() == SleepStatType.DURATION) {
                sb.append("select " + MysqlUtil.dateTypeMethod("sleep_date", sf.getXgroupType()) + " as xValue,");
                sb.append(" total_minutes as yValue ");
            } else if (sf.getYgroupType() == SleepStatType.SLEEP_TIME) {
                sb.append("select " + MysqlUtil.dateTypeMethod("sleep_time", sf.getXgroupType()) + " as xValue,");
                sb.append(MysqlUtil.dateTypeMethod("sleep_time", DateGroupType.HOURMINUTE) + " as yValue ");
            } else if (sf.getYgroupType() == SleepStatType.GETUP_TIME) {
                sb.append("select " + MysqlUtil.dateTypeMethod("get_up_time", sf.getXgroupType()) + " as xValue,");
                sb.append(MysqlUtil.dateTypeMethod("get_up_time", DateGroupType.HOURMINUTE) + " as yValue ");
            }else if (sf.getYgroupType() == SleepStatType.WAKEUP_COUNT) {
                sb.append("select " + MysqlUtil.dateTypeMethod("sleep_time", sf.getXgroupType()) + " as xValue,");
                sb.append("wake_up_count as yValue ");
            } else {
                sb.append("select " + MysqlUtil.dateTypeMethod("sleep_date", sf.getXgroupType()) + " as xValue,");
                sb.append("quality as yValue ");
            }
            sb.append(" from sleep ");
            sb.append(pr.getParameterString());
            //sb.append(" and sleep_time is not null and get_up_time is not null ");
            List args = pr.getParameterValueList();
            List<SleepAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), SleepAnalyseStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "睡眠分析统计异常", e);
        }
    }
}
