package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.persistent.domain.UserQa;
import cn.mulanbay.pms.persistent.dto.NameCountDto;
import cn.mulanbay.pms.web.bean.request.diet.DietWordCloudSearch;
import cn.mulanbay.pms.web.bean.request.system.UserQaSearch;
import cn.mulanbay.pms.web.bean.request.system.UserQaWordCloudSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 账户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Service
@Transactional
public class QaService extends BaseHibernateDao {

    /**
     * 获取列表
     * @param sf
     * @return
     */
    public PageResult<UserQa> getUserQaResult(UserQaSearch sf) {
        try {
            PageResult<UserQa> qb = new PageResult<UserQa>();
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select new UserQa(id,userId,appId,openId,source,requestContent,replyQaId,sessionId,createdTime,lastModifyTime) from UserQa ");
            String paraStr = pr.getParameterString();
            sb.append(paraStr);
            sb.append(" order by createdTime desc ");
            Object[] values = pr.getParameterValue();
            if (pr.getPage() > 0&&pr.isNeedTotal()) {
                long maxRow = this.getCount("select count(0) from UserQa " + paraStr,
                        values);
                qb.setMaxRow(maxRow);
            }
            List<UserQa> list = this.getEntityListHQL(sb.toString(),
                    pr.getPage(), pr.getPageSize(), values);
            qb.setBeanList(list);
            qb.setPage(pr.getPage());
            return qb;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,"获取列表数据异常", e);
        }
    }

    /**
     * 获取请求列表
     *
     * @param sf
     * @return
     */
    public List<String> getRequestList(UserQaWordCloudSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select requestContent from UserQa");
            sb.append(pr.getParameterString());
            List<String> list = this.getEntityListNoPageHQL(sb.toString(),pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取请求列表异常", e);
        }
    }
}
