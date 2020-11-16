package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.business.handler.lock.RedisLock;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.Dream;
import cn.mulanbay.pms.persistent.domain.DreamRemind;
import cn.mulanbay.pms.persistent.dto.DreamStat;
import cn.mulanbay.pms.persistent.enums.DreamStatus;
import cn.mulanbay.pms.web.bean.request.dream.DreamStatListSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DreamService extends BaseHibernateDao {

    public List<DreamStat> getDreamStat(PageRequest pr, DreamStatListSearch.GroupType groupType) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select ");
            sb.append(groupType.getColumn());
            sb.append(",count(0) as totalCount from dream ");
            sb.append(pr.getParameterString());
            sb.append("group by ");
            sb.append(groupType.getColumn());
            sb.append(" order by totalCount desc");
            List<DreamStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), DreamStat.class, pr.getParameterValue());

            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "梦想统计异常", e);
        }
    }

    /**
     * 获取待刷新进度的梦想
     */
    public List<Dream> getNeedRefreshRateDream(Long userId) {
        try {
            String hql = "from Dream where userPlan.id is not null and status in(?0,?1) and userId=?2 ";
            List<Dream> list = this.getEntityListNoPageHQL(hql, DreamStatus.CREATED, DreamStatus.STARTED, userId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "梦想统计异常", e);
        }
    }

    /**
     * 获取待刷新进度的梦想
     */
    public List<Dream> getNeedRefreshRateDream() {
        try {
            String hql = "from Dream where userPlan.id is not null and status in(?0,?1) ";
            List<Dream> list = this.getEntityListNoPageHQL(hql, DreamStatus.CREATED, DreamStatus.STARTED);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "梦想统计异常", e);
        }
    }

    @RedisLock(key = "('pms:distributeLock:dream:').concat(#id)", keyType = RedisLock.KeyType.SPEL, sleepMills = 1000)
    public Dream getDream(Long id) {
        try {
            return (Dream) this.getEntityById(Dream.class, id);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取梦想异常", e);
        }
    }

    /**
     * 查找梦想提醒
     *
     * @param dreamId
     */
    public DreamRemind getRemindByDream(Long dreamId, Long userId) {
        try {
            String hql = "from DreamRemind where dream.id=?0 and userId=?1 ";
            return (DreamRemind) this.getEntityForOne(hql, dreamId, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "查找梦想提醒异常", e);
        }
    }

    /**
     * 获取需要提醒的梦想列表
     *
     * @return
     */
    public List<Dream> getNeedRemindDream() {
        try {
            String hql = "from Dream where status=?0 and remind=?1 ";
            return this.getEntityListNoPageHQL(hql, DreamStatus.STARTED, true);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要提醒的梦想列表异常", e);
        }
    }

    /**
     * 更新最后提醒时间
     *
     * @param remindId
     */
    public void updateLastRemindTime(Long remindId, Date date) {
        try {
            String hql = "update DreamRemind set lastRemindTime=?0 where id=?1 ";
            this.updateEntities(hql, date, remindId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新最后提醒时间异常", e);
        }
    }


}
