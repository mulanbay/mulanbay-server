package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.ReportConfig;
import cn.mulanbay.pms.persistent.domain.UserReportConfig;
import cn.mulanbay.pms.persistent.domain.UserReportRemind;
import cn.mulanbay.pms.persistent.dto.ReportResult;
import cn.mulanbay.pms.persistent.enums.SqlType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportService extends BaseHibernateDao {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);


    /**
     * 报表统计
     *
     * @param unc
     * @param userId
     * @param i
     * @return
     */
    public ReportResult getReportResult(UserReportConfig unc, Long userId, int year, int i) {
        List args = new ArrayList();
        args.add(year);
        ReportConfig rc = unc.getReportConfig();
        if (null != rc.getUserBand() && rc.getUserBand()) {
            args.add(userId);
        }
        ReportResult reportResult = new ReportResult();
        reportResult.setReportConfig(rc);
        String sqlContent = rc.getSqlContent();
        sqlContent = MysqlUtil.replaceBindValues(sqlContent, unc.getBindValues());
        try {
            List<Object[]> rr = null;
            if (rc.getSqlType() == SqlType.HQL) {
                rr = this.getEntityListNoPageHQL(sqlContent, args.toArray());
            } else {
                rr = this.getEntityListNoPageSQL(sqlContent, args.toArray());
            }

            if (rr.isEmpty()) {
                reportResult.setResult("无数据!");
            } else {
                int columns = rc.getResultColumns();
                if (columns == 1) {
                    //一个的需要特别对待
                    Object rs = rr.get(0);
                    String ss = MessageFormat.format(rc.getResultTemplate(), rs);
                    reportResult.setResult(ss);
                } else {
                    Object[] rs = rr.get(0);
                    List formatArgs = new ArrayList();
                    //根据配置动态获取
                    for (int mm = 0; mm < rc.getResultColumns(); mm++) {
                        if (rs[mm] == null) {
                            formatArgs.add(0);
                        } else {
                            formatArgs.add(rs[mm]);
                        }
                    }
                    String ss = MessageFormat.format(rc.getResultTemplate(), formatArgs.toArray());
                    reportResult.setResult(ss);
                }
            }
        } catch (Exception e) {
            logger.error("获取统计数据异常", e);
            reportResult.setResult("获取数据异常!");
        }
        reportResult.setId(i);
        reportResult.setTitle(unc.getTitle());
        return reportResult;
    }

    /**
     * @param userReportConfigId
     * @return
     */
    public UserReportRemind getUserReportRemind(Long userReportConfigId, Long userId) {
        try {
            String hql = "from UserReportRemind where userReportConfig.id=?0 and userId=?1 ";
            return (UserReportRemind) this.getEntityForOne(hql, userReportConfigId, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "查找用户提醒异常", e);
        }
    }

    /**
     * 获取报表配置
     * 需要根据用户级别判断
     *
     * @return
     */
    public ReportConfig getReportConfig(Long id, Integer userLevel) {
        try {
            String hql = "from ReportConfig where id=?0 and level<=?1 ";
            return (ReportConfig) this.getEntityForOne(hql, id, userLevel);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取报表配置异常", e);
        }
    }

}
