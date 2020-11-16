package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.ScoreConfig;
import cn.mulanbay.pms.persistent.domain.ScoreConfigGroup;
import cn.mulanbay.pms.persistent.domain.UserScore;
import cn.mulanbay.pms.persistent.domain.UserScoreDetail;
import cn.mulanbay.pms.persistent.dto.UserScorePointsCompareDto;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.LogCompareType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 用户评分
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class UserScoreService extends BaseHibernateDao {

    /**
     * 保存用户评分
     *
     * @param us
     * @param list
     * @param redo 重新保存
     */
    public void saveUserScore(UserScore us, List<UserScoreDetail> list, boolean redo) {
        try {
            if (redo) {
                String hh = "from UserScore where userId=?0 and startTime=?1 and endTime=?2 ";
                UserScore old = (UserScore) this.getEntityForOne(hh, us.getUserId(), us.getStartTime(), us.getEndTime());
                if (old != null) {
                    String hql = "delete from UserScoreDetail where userScoreId=?0 ";
                    this.updateEntities(hql, old.getId());
                    this.removeEntity(old);
                }
            }
            this.saveEntity(us);
            for (UserScoreDetail dd : list) {
                dd.setUserScoreId(us.getId());
                this.saveEntity(dd);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR, "保存用户评分失败！", e);
        }
    }

    /**
     * 获取评分配置
     *
     * @return
     */
    public List<ScoreConfig> selectActiveScoreConfigList(String code) {
        try {
            String hh = "from ScoreConfigGroup where code=?0 and status=1 ";
            ScoreConfigGroup group = (ScoreConfigGroup) this.getEntityForOne(hh, code);
            String hql = "from ScoreConfig where status=1 and groupId=?0 ";
            return this.getEntityListNoPageHQL(hql, group.getId());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取积分配置异常", e);
        }
    }

    /**
     * 获取用户最新的评分
     *
     * @param userId
     * @return
     */
    public UserScore getLatestScore(Long userId) {
        try {
            String hql = "from UserScore where userId=?0 order by endTime desc";
            return (UserScore) this.getEntityForOne(hql, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取预判定用户等级异常", e);
        }
    }

    /**
     * 获取用户评分列表
     *
     * @param userId
     * @return
     */
    public List<UserScore> getList(Long userId, int n) {
        try {
            String hql = "from UserScore where userId=?0 order by endTime desc";
            return this.getEntityListHQL(hql, 1, n, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户评分列表异常", e);
        }
    }

    /**
     * 获取评分详情
     *
     * @return
     */
    public List<UserScoreDetail> selectUserScoreDetailList(Long userScoreId) {
        try {
            String hql = "from UserScoreDetail where userScoreId=?0 ";
            return this.getEntityListNoPageHQL(hql, userScoreId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取评分详情异常", e);
        }
    }

    /**
     * 获取积分的原始值
     *
     * @return
     */
    public double getScoreValue(String sql, Long userId, Date start, Date end) {
        try {
            List list = this.getEntityListNoPageSQL(sql, userId, start, end);
            Object o = list.get(0);
            if (o == null) {
                return 0;
            } else {
                return Double.valueOf(o.toString());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取积分的原始值异常", e);
        }
    }

    /**
     * 获取需要积分统计的用户Id
     *
     * @return
     */
    public List<Long> selectNeedStatScoreUserIdList() {
        try {
            String hql = "select userId from UserSetting where statScore=1";
            return this.getEntityListNoPageHQL(hql);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要积分统计的用户Id异常", e);
        }
    }

    /**
     * 积分和评分比对
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<UserScorePointsCompareDto> scorePointsCompare(Long userId, Date startTime, Date endTime) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select ss.*,pp.points from ");
            sb.append("(select date ,avg(score) as score from (");
            sb.append("SELECT  CAST(DATE_FORMAT(end_time,'%Y%m%d') AS signed) as date,score FROM user_score ");
            sb.append("where user_id=?0 and end_time>=?1 and end_time<=?2 ");
            sb.append(") as res group by date) as ss, ");
            sb.append("(select date ,avg(points) as points from ( ");
            sb.append("SELECT  CAST(DATE_FORMAT(created_time,'%Y%m%d') AS signed) as date,after_points as points FROM user_reward_point_record ");
            sb.append("where user_id=?3 and created_time>=?4 and created_time<=?5 ");
            sb.append(") as res group by date) as pp ");
            sb.append("where ss.date = pp.date ");
            sb.append("order by ss.date ");

            List<UserScorePointsCompareDto> list = this.getEntityListWithClassSQL(sb.toString(), -1, 0,
                    UserScorePointsCompareDto.class, userId, startTime, endTime, userId, startTime, endTime);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "积分和评分比对异常", e);
        }
    }

    /**
     * 获取需要积分统计的用户Id
     *
     * @return
     */
    public void createGroupFromTemplate(Long templateId, String code, String name) {
        try {
            ScoreConfigGroup group = new ScoreConfigGroup();
            group.setCode(code);
            group.setName(name);
            group.setCreatedTime(new Date());
            group.setStatus(CommonStatus.DISABLE);
            group.setRemark("从模板信息,templateId=" + templateId);
            this.saveEntity(group);
            String hql = "from ScoreConfig where groupId=?0";
            List<ScoreConfig> scList = this.getEntityListNoPageHQL(hql, templateId);
            for (ScoreConfig sc : scList) {
                ScoreConfig newSc = new ScoreConfig();
                BeanCopy.copyProperties(sc, newSc);
                newSc.setId(null);
                newSc.setGroupId(group.getId());
                newSc.setCreatedTime(new Date());
                newSc.setLastModifyTime(null);
                this.saveEntity(newSc);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要积分统计的用户Id异常", e);
        }
    }

    /**
     * 获取最近的ID
     * @param currentId
     * @param userId
     * @param compareType
     * @return
     */
    public Long getNearestId(Long currentId,Long userId, LogCompareType compareType){
        try {
            String hql=null;
            if(compareType==LogCompareType.EARLY){
               hql = "select id from UserScore where id <?0 and userId=?1 order by id desc";
            }else{
                hql = "select id from UserScore where id >?0 and userId=?1 order by id asc";
            }
            return (Long) this.getEntityForOne(hql,currentId,userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近的ID异常", e);
        }
    }
}
