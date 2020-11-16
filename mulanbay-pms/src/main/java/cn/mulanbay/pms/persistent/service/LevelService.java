package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.LevelConfig;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LevelService extends BaseHibernateDao {

    /**
     * 获取预判定用户等级
     *
     * @param score
     * @param points
     * @return
     */
    public LevelConfig getPreJudgeLevel(Integer score, Integer points) {
        try {
            String hql = "from LevelConfig where points<=?0 and score<=?1 order by level desc";
            return (LevelConfig) this.getEntityForOne(hql, points, score);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预判定用户等级异常", e);
        }
    }

    /**
     * 获取用户等级配置
     *
     * @param level
     * @return
     */
    public LevelConfig getLevelConfig(Integer level) {
        try {
            String hql = "from LevelConfig where level=?0";
            return (LevelConfig) this.getEntityForOne(hql, level);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取用户等级配置异常", e);
        }
    }

}
