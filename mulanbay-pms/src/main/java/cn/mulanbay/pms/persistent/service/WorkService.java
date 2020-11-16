package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.dto.WorkOvertimeDateStat;
import cn.mulanbay.pms.persistent.dto.WorkOvertimeStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.work.WorkOvertimeDateStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 工作
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class WorkService extends BaseHibernateDao {

    /**
     *   统计加班时间
     *
     * @param sf
     * @return
     */
    public List<WorkOvertimeDateStat> statDateWorkOvertime(WorkOvertimeDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,sum(hours) as totalHours ,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("work_date", dateGroupType) + "as indexValue,");
            sb.append("hours from work_overtime ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<WorkOvertimeDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), WorkOvertimeDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "加班统计异常", e);
        }
    }

    @SuppressWarnings("unchecked")
    public WorkOvertimeStat getWorkOvertimeStat(PageRequest pr) {
        try {
            String sql = "select count(*) as totalCount,sum(hours) as totalHours from work_overtime";
            sql += pr.getParameterString();
            List<WorkOvertimeStat> list = this.getEntityListWithClassSQL(sql, pr.getPage(), pr.getPageSize(), WorkOvertimeStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取购买记录统计异常", e);
        }
    }

}
