package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.dto.DiaryStat;
import cn.mulanbay.pms.web.bean.request.diary.DiaryStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DiaryService extends BaseHibernateDao {

    /**
     * 基本的统计
     *
     * @param sf
     * @return
     */
    public DiaryStat statDiary(DiaryStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select count(*) as totalCount,sum(words) as totalWords,sum(pieces) as totalPieces from diary";
            sql += pr.getParameterString();
            List<DiaryStat> list = this.getEntityListWithClassSQL(sql, pr.getPage(), pr.getPageSize(), DiaryStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取日记统计异常", e);
        }
    }
}
