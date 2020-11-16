package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.Income;
import cn.mulanbay.pms.persistent.dto.IncomeDateStat;
import cn.mulanbay.pms.persistent.dto.IncomeSummaryStat;
import cn.mulanbay.pms.persistent.dto.IncomeTypeStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.fund.IncomeDateStatSearch;
import cn.mulanbay.pms.web.bean.request.fund.IncomeStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IncomeService extends BaseHibernateDao {

    /**
     * 获取收入总的统计
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public IncomeSummaryStat incomeSummaryStat(Long userId, Date startTime, Date endTime) {
        try {
            String sql = "select count(*) as totalCount,sum(amount) as totalAmount from income where user_id=?0 and occur_time>=?1 and occur_time<=?2 ";
            List<IncomeSummaryStat> list = this.getEntityListWithClassSQL(sql, 0, 0, IncomeSummaryStat.class, userId, startTime, endTime);
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取收入总的统计计异常", e);
        }
    }

    /**
     * 收入统计
     *
     * @param sf
     * @return
     */
    public List<IncomeDateStat> statDateIncome(IncomeDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,sum(amount) as totalAmount ,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("occur_time", dateGroupType) + "as indexValue,");
            sb.append("amount from income ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<IncomeDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), IncomeDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "收入统计异常", e);
        }
    }

    /**
     * 收入统计
     *
     * @param sf
     * @return
     */
    public List<IncomeTypeStat> statIncome(IncomeStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select type as indexValue,sum(amount) as totalAmount ,count(0) as totalCount ");
            sb.append("from income ");
            sb.append(pr.getParameterString());
            sb.append("group by type ");
            List<IncomeTypeStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), IncomeTypeStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "收入统计异常", e);
        }
    }

    /**
     * 根据消费记录获取收入
     *
     * @param buyRecordId
     * @return
     */
    public Income getIncomeByBuyRecord(Long buyRecordId) {
        try {
            String hql = "from Income where buyRecordId=?0 ";
            return (Income) this.getEntityForOne(hql, buyRecordId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据消费记录获取收入异常", e);
        }
    }
}
