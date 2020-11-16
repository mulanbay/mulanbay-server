package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.Company;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CompanyService extends BaseHibernateDao {

    /**
     * 获取公司列表
     *
     * @param year
     * @return
     */
    public List<Company> selectCompanyList(int year, Long userId) {
        try {
            Date beginDate = DateUtil.getDate(year + "-01-01", DateUtil.FormatDay1);
            Date endDate = DateUtil.getDate(year + "-12-31", DateUtil.FormatDay1);

            String hql = "from Company where userId=?0 and (entryDate<=?1 and quitDate>=?2 ) ";
            return this.getEntityListNoPageHQL(hql, userId, endDate, beginDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取公司列表异常", e);
        }
    }
}
