package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.UserShares;
import cn.mulanbay.pms.persistent.domain.UserSharesScore;
import cn.mulanbay.pms.persistent.domain.UserSharesScoreConfig;
import cn.mulanbay.pms.persistent.dto.SharesPriceAnalyseStat;
import cn.mulanbay.pms.persistent.dto.UserSharesWarnDataStat;
import cn.mulanbay.pms.persistent.dto.UserSharesWarnDateStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.WardType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.fund.UserSharesPriceAnalyseSearch;
import cn.mulanbay.pms.web.bean.request.fund.UserSharesWarnDateStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SharesService extends BaseHibernateDao {

    /**
     * 获取需要监控的股票
     *
     * @return
     */
    public List<UserShares> getRemindUserShares() {
        try {
            String hql = "from UserShares where remind=1 and status=1 ";
            return this.getEntityListNoPageHQL(hql);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要监控的股票异常", e);
        }
    }

    /**
     * 获取评分记录
     *
     * @return
     */
    public UserSharesScore getUserSharesScore(Long userSharesId, Long userId, Long id, WardType type) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from UserSharesScore where userShares.id=?0 and userId=?1 ");
            List args = new ArrayList();
            args.add(userSharesId);
            args.add(userId);
            if (id != null) {
                args.add(id);
                if (type == WardType.FORWARD) {
                    sb.append("and id<?2 ");
                } else {
                    sb.append("and id>?2 ");
                }
            }
            if (type == WardType.FORWARD) {
                sb.append("order by createdTime desc ");
            } else {
                sb.append("order by createdTime asc ");
            }
            return (UserSharesScore) this.getEntityForOne(sb.toString(), args.toArray());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要统计的股票异常", e);
        }
    }

    /**
     * 获取用户评分配置
     *
     * @return
     */
    public UserSharesScoreConfig getUserSharesScoreConfig(Long userId) {
        try {
            String hql = "from UserSharesScoreConfig where userId=?0 ";
            return (UserSharesScoreConfig) this.getEntityForOne(hql, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户评分配置异常", e);
        }
    }

    /**
     * 获取需要统计的股票
     *
     * @return
     */
    public List<UserShares> getActiveUserShares(Long userId) {
        try {
            String hql = "from UserShares where status=1 ";
            if (userId != null) {
                hql += " and userId=" + userId;
            }
            hql += " order by userId";
            return this.getEntityListNoPageHQL(hql);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要统计的股票异常", e);
        }
    }

    /**
     * 更新股票评分
     *
     * @return
     */
    public void updateUserSharesScore(Long id, Integer score) {
        try {
            String hql = "update UserShares set score = ?0 where id=?1 ";
            this.updateEntities(hql, score, id);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "更新股票评分异常", e);
        }
    }

    /**
     * 用户股票警告统计
     *
     * @param sf
     * @return
     */
    public List<UserSharesWarnDateStat> statDateUserSharesWarn(UserSharesWarnDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,warn_type as warnType,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("created_time", dateGroupType) + "as indexValue, ");
            sb.append("warn_type ");
            sb.append("from user_shares_warn ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue,warn_type ");
            sb.append(" order by indexValue,warn_type");
            List<UserSharesWarnDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(),
                    pr.getPageSize(), UserSharesWarnDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "用户股票警告统计异常", e);
        }
    }

    /**
     * 用户股票统计
     *
     * @param sf
     * @return
     */
    public List<SharesPriceAnalyseStat> statSharesPriceAnalyse(UserSharesPriceAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT sp.current_price as currentPrice,sp.occur_time as occurTime, ");
            sb.append(" sp.turn_over as turnOver, ");
            sb.append(" uss.price_score as priceScore,uss.asset_score as assetScore,uss.fg_score as fgScore, ");
            sb.append("  uss.ss_score as ssScore,uss.risk_score  as riskScore ");
            sb.append(" from shares_price sp ");
            sb.append(" left join user_shares_score uss ");
            sb.append(" on sp.id = uss.shares_price_id ");
            sb.append(pr.getParameterString());
            sb.append(" order by sp.occur_time ");
            List<SharesPriceAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(),
                    pr.getPageSize(), SharesPriceAnalyseStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "用户股票统计异常", e);
        }
    }

    /**
     * 用户股票警告统计
     *
     * @param sf
     * @return
     */
    public List<UserSharesWarnDataStat> statDataUserSharesWarn(UserSharesWarnDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            UserSharesWarnDateStatSearch.DataGroupType dataGroupType = sf.getDataGroupType();
            String field = "";
            if (dataGroupType == UserSharesWarnDateStatSearch.DataGroupType.WARN_TYPE) {
                field = "warn_type";
            } else {
                field = "user_shares_id";
            }
            StringBuffer sb = new StringBuffer();
            sb.append("select " + field + " as groupId,count(0) as totalCount from user_shares_warn ");
            sb.append(pr.getParameterString());
            sb.append(" group by " + field);
            List<UserSharesWarnDataStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(),
                    pr.getPageSize(), UserSharesWarnDataStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "用户股票警告统计异常", e);
        }
    }

}
