package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.CommonSqlDto;
import cn.mulanbay.pms.persistent.dto.PlanReportAvgStat;
import cn.mulanbay.pms.persistent.dto.PlanReportPlanCommendDto;
import cn.mulanbay.pms.persistent.dto.PlanReportResultGroupStat;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.plan.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlanService extends BaseHibernateDao {

    private static final Logger logger = LoggerFactory.getLogger(PlanService.class);

    /**
     * 删除计划报告数据
     *
     * @param sf
     * @return
     */
    public void deletePlanReportData(PlanReportDataCleanSearch sf) {
        try {
            String hql = "delete from PlanReport ";
            PageRequest pr = sf.buildQuery();
            hql += pr.getParameterString();
            if (sf.getCleanType() == PlanReportDataCleanType.BOTH_ZERO) {
                hql += " and reportCountValue=0 and reportValue=0 ";
            } else if (sf.getCleanType() == PlanReportDataCleanType.ONCE_ZERO) {
                hql += " and (reportCountValue=0 or reportValue=0) ";
            }
            this.updateEntities(hql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "删除计划报告数据异常", e);
        }
    }

    /**
     * 删除计划配置
     *
     * @param id
     * @return
     */
    public void deletePlanConfig(Long id) {
        try {
            String hql2 = "delete from StatValueConfig where fid=?0 and type=?1";
            this.updateEntities(hql2, id, StatValueType.PLAN);

            String hql = "delete from PlanConfig where id=?0 ";
            this.updateEntities(hql, id);

        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_DELETE_ERROR,
                    "删除计划配置计异常", e);
        }
    }

    /**
     * 获取首次统计日期
     *
     * @param bean
     * @return
     */
    public Date getFirstStatDay(PlanConfig bean,Long userId) {
        try {
            String dateField = bean.getDateField();
            int index = dateField.lastIndexOf(".");
            if (index > 0) {
                //解决有别名的问题
                dateField = dateField.substring(index + 1, dateField.length());
            }
            String hql = "select min(" + dateField + ") from " + bean.getRelatedBeans();
            String userField = bean.getUserField();
            if (!StringUtil.isEmpty(userField)) {
                int indexU = userField.lastIndexOf(".");
                if (indexU > 0) {
                    //解决有别名的问题
                    userField = userField.substring(indexU + 1, userField.length());
                }
                hql += " where " + userField + "=" + userId;
            }
            Date date = (Date) this.getEntityForOne(hql);
            return date;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "删除计划配置计异常", e);
        }
    }


    /**
     * 人工统计数据
     *
     * @param sf
     * @return
     */
    public void manualStat(PlanReportManualStat sf) {
        try {
            if (sf.getUserPlanId() == null) {
                StringBuffer sb = new StringBuffer();
                List args = new ArrayList();
                sb.append("from UserPlan where status=?0 ");
                args.add(CommonStatus.ENABLE);
                if (sf.getPlanType() != null) {
                    sb.append("and planConfig.planType=?1 ");
                    args.add(sf.getPlanType());
                }
                sb.append("order by orderIndex ");
                //全部
                List<UserPlan> list = this.getEntityListNoPageHQL(sb.toString(), args.toArray());
                for (UserPlan pc : list) {
                    this.manualStat(pc, sf.getStartDate(), sf.getEndDate(), sf.getUserId(), sf.getStatType());
                }
            } else {
                //单个
                UserPlan pc = (UserPlan) this.getEntityById(UserPlan.class, sf.getUserPlanId());
                this.manualStat(pc, sf.getStartDate(), sf.getEndDate(), sf.getUserId(), sf.getStatType());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "人工统计数据异常", e);
        }
    }

    /**
     * 计划推荐
     *
     * @param sf
     * @return
     */
    public PlanReportPlanCommendDto planCommend(PlanReportPlanCommendSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select AVG(reportCountValue) as reportCountValue,AVG(reportValue) as reportValue ");
            sb.append("from plan_report ");
            sb.append(pr.getParameterString());
            List<PlanReportPlanCommendDto> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(),
                    pr.getPageSize(), PlanReportPlanCommendDto.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "计划推荐异常", e);
        }
    }

    /**
     * 统计
     *
     * @param userPlan
     * @param startDate
     * @param endDate
     * @param userId
     */
    public void manualStat(UserPlan userPlan, Date startDate, Date endDate, Long userId, ManualStatType statType) {
        try {
            startDate = getFirstStatDayByType(userPlan.getPlanConfig().getPlanType(), startDate);
            if (endDate == null) {
                //最多只能统计30天之前的
                endDate = DateUtil.getDate(-30);
            }
            int bussDay = 0;
            String selectsql = "select count(0) from plan_report where user_plan_id=?0 and user_id=?1 and buss_day=?2";
            int startBussDay = this.calDateValue(userPlan.getPlanConfig().getPlanType(), startDate);
            int endBussDay = this.calDateValue(userPlan.getPlanConfig().getPlanType(), endDate);
            Date currentCaldate = startDate;
            int currentYear = 0;
            UserPlanConfigValue currentPcv = null;
            for (bussDay = startBussDay; bussDay <= endBussDay; ) {
                int year = DateUtil.getYear(currentCaldate);
                if (year > currentYear) {
                    //获取年份的配置值
                    currentPcv = this.getNearestUserPlanConfigValue(year, userPlan.getId());
                    currentYear = year;
                }
                if (statType == ManualStatType.STAT_MISS) {
                    //检查数据库中是否存在
                    List ll = this.getEntityListNoPageSQL(selectsql, userPlan.getId(), userId, bussDay);
                    BigInteger bi = (BigInteger) ll.get(0);
                    if (bi.intValue() > 0) {
                        //说明数据库中已经有数据，跳过
                        logger.debug(userPlan.getTitle() + "在" + bussDay + "已经有数据，跳过.");
                    } else {
                        saveOrUpdatePlanReport(userPlan, bussDay, userId, currentPcv, currentCaldate);
                    }
                } else {
                    saveOrUpdatePlanReport(userPlan, bussDay, userId, currentPcv, currentCaldate);
                }
                //设定下一个绑定值
                currentCaldate = addStatDate(userPlan.getPlanConfig().getPlanType(), currentCaldate);
                bussDay = calDateValue(userPlan.getPlanConfig().getPlanType(), currentCaldate);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "手动统计计划报告异常", e);
        }
    }

    /**
     * 获取第一天的统计
     *
     * @param planType
     * @param date
     * @return
     */
    public Date getFirstStatDayByType(PlanType planType, Date date) {
        String dateString;
        if (planType == PlanType.MONTH) {
            //每月第一天
            dateString = DateUtil.getFormatDate(date, "yyyy-MM" + "-01");
        } else if (planType == PlanType.YEAR) {
            //每年第一天
            dateString = DateUtil.getFormatDate(date, "yyyy") + "-01-01";
        } else {
            return date;
        }
        return DateUtil.getDate(dateString, DateUtil.FormatDay1);
    }

    /**
     * 重新统计计划报告
     *
     * @param ids
     */
    public void reStatPlanReports(String ids, PlanReportReStatCompareType reStatCompareType, Integer compareYear) {
        try {
            Long[] idArr = NumberUtil.stringArrayToLongArray(ids.split(","));
            for (Long id : idArr) {
                PlanReport planReport = (PlanReport) this.getEntityById(PlanReport.class, id);
                Integer year;
                if (reStatCompareType == PlanReportReStatCompareType.ORIGINAL) {
                    year = planReport.getPlanConfigYear();
                } else if (reStatCompareType == PlanReportReStatCompareType.ORIGINAL_LATEST) {
                    year = DateUtil.getYear(planReport.getBussStatDate());
                } else if (reStatCompareType == PlanReportReStatCompareType.SPECIFY) {
                    year = compareYear;
                } else {
                    year = DateUtil.getYear(new Date());
                }
                if (year == null) {
                    year = DateUtil.getYear(planReport.getBussStatDate());
                }
                //
                UserPlanConfigValue pcv = this.getNearestUserPlanConfigValue(year, planReport.getUserPlan().getId());
                saveOrUpdatePlanReport(planReport.getUserPlan(), planReport.getBussDay(), planReport.getUserId(), pcv, planReport.getBussStatDate());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "重新统计计划报告异常", e);
        }
    }

    /**
     * 更新或者保存计划报告
     *
     * @param userPlan
     * @param bussDay
     * @param userId
     * @param currentPcv
     * @param currentCaldate
     */
    private void saveOrUpdatePlanReport(UserPlan userPlan, int bussDay, Long userId, UserPlanConfigValue currentPcv, Date currentCaldate) {
        try {
            // 删除数据库里数据
            String deletesql = "delete from plan_report where user_plan_id=?0 and user_id=?1 and buss_day=?2";
            this.execSqlUpdate(deletesql, userPlan.getId(), userId, bussDay);
            PlanReport pr = this.statPlanReport(userPlan, bussDay, userId, currentPcv, currentCaldate, PlanReportDataStatFilterType.ORIGINAL);
            if (pr != null) {
                this.saveEntity(pr);
            } else {
                logger.debug("计划[" + userPlan.getTitle() + "],用户[" + userId + "]在[" + bussDay + "]没有数据.");
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "更新或者保存计划报告异常", e);
        }
    }

    /**
     * 统计出计划报表
     *
     * @param userPlan
     * @param bussDay
     * @param userId
     * @param userPlanConfigValue
     * @param currentCaldate
     * @return
     */
    public PlanReport statPlanReport(UserPlan userPlan, int bussDay, Long userId, UserPlanConfigValue userPlanConfigValue, Date currentCaldate, PlanReportDataStatFilterType filterType) {
        try {
            List<Object[]> rr = null;
            PlanConfig planConfig = userPlan.getPlanConfig();
            CommonSqlDto sqlBean = this.calSqlBean(userPlan, bussDay, userId, filterType);
            logger.debug("转换后的sql为:\n" + sqlBean.getSqlContent());
            if (planConfig.getSqlType() == SqlType.HQL) {
                rr = this.getEntityListNoPageHQL(sqlBean.getSqlContent(), sqlBean.getArgs().toArray());
            } else {
                rr = this.getEntityListNoPageSQL(sqlBean.getSqlContent(), sqlBean.getArgs().toArray());
            }
            if (!rr.isEmpty()) {
                Object[] oo = rr.get(0);
                if (oo[0] == null && oo[1] == null) {
                    //可能出现全部没有数据
                    return null;
                }
                PlanReport pr = new PlanReport();
                pr.setUserId(userId);
                pr.setBussDay(bussDay);
                pr.setCreatedTime(new Date());
                pr.setName(userPlan.getTitle() + "(" + bussDay + ")");
                pr.setUserPlan(userPlan);
                pr.setReportCountValue(Long.valueOf(oo[0].toString()));
                if (userPlanConfigValue != null) {
                    pr.setPlanCountValue(userPlanConfigValue.getPlanCountValue());
                    pr.setPlanValue(userPlanConfigValue.getPlanValue());
                    pr.setPlanConfigYear(userPlanConfigValue.getYear());
                }
                pr.setBussStatDate(currentCaldate);
                if (oo.length > 1) {
                    if (oo[1] == null) {
                        pr.setReportValue(0L);
                    } else {
                        pr.setReportValue(Long.valueOf(oo[1].toString()));
                    }
                }
                if (userPlanConfigValue != null) {
                    //设置统计结果
                    if (planConfig.getCompareType() == CompareType.MORE) {
                        if (pr.getReportCountValue().longValue() >= pr.getPlanCountValue().longValue()) {
                            pr.setCountValueStatResult(PlanStatResultType.ACHIEVED);
                        } else {
                            pr.setCountValueStatResult(PlanStatResultType.UN_ACHIEVED);
                        }
                        if (pr.getPlanValue() == 0) {
                            pr.setValueStatResult(PlanStatResultType.SKIP);
                        } else if (pr.getReportValue().longValue() >= pr.getPlanValue().longValue()) {
                            pr.setValueStatResult(PlanStatResultType.ACHIEVED);
                        } else {
                            pr.setValueStatResult(PlanStatResultType.UN_ACHIEVED);
                        }
                    } else {
                        if (pr.getReportCountValue().longValue() <= pr.getPlanCountValue().longValue()) {
                            pr.setCountValueStatResult(PlanStatResultType.ACHIEVED);
                        } else {
                            pr.setCountValueStatResult(PlanStatResultType.UN_ACHIEVED);
                        }
                        if (pr.getPlanValue() == 0) {
                            pr.setValueStatResult(PlanStatResultType.SKIP);
                        } else if (pr.getReportValue().longValue() <= pr.getPlanValue().longValue()) {
                            pr.setValueStatResult(PlanStatResultType.ACHIEVED);
                        } else {
                            pr.setValueStatResult(PlanStatResultType.UN_ACHIEVED);
                        }
                    }
                }
                return pr;
            } else {
                return null;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "统计出计划报表异常", e);
        }
    }

    /**
     * 计算封装SQL
     *
     * @param userPlan
     * @param bussDay
     * @param userId
     * @param filterType
     * @return
     */
    private CommonSqlDto calSqlBean(UserPlan userPlan, int bussDay, Long userId, PlanReportDataStatFilterType filterType) {
        CommonSqlDto bean = new CommonSqlDto();
        StringBuffer sb = new StringBuffer();
        PlanConfig planConfig = userPlan.getPlanConfig();
        String sqlContent = planConfig.getSqlContent();
        sqlContent = MysqlUtil.replaceBindValues(sqlContent, userPlan.getBindValues());
        sb.append(sqlContent);
        if (!sqlContent.contains("where")) {
            sb.append(" where 1=1");
        }
        int index = 0;
        if (filterType == PlanReportDataStatFilterType.ORIGINAL || filterType == PlanReportDataStatFilterType.NO_USER) {
            //添加时间过滤
            sb.append(" and " + getDateFilterString(planConfig.getDateField(), planConfig.getPlanType(), index));
            bean.addArg(bussDay);
            index++;
        }
        if (filterType == PlanReportDataStatFilterType.ORIGINAL || filterType == PlanReportDataStatFilterType.NO_DATE) {
            String userField = planConfig.getUserField();
            if (!StringUtil.isEmpty(userField)) {
                //添加用户过滤
                sb.append(" and " + userField + "=?" + index + " ");
                bean.addArg(userId);
                index++;
            }
        }
        bean.setSqlContent(sb.toString());
        return bean;
    }

    private String getDateFilterString(String dateField, PlanType planType, int index) {
        if (planType == PlanType.YEAR) {
            return "CAST(DATE_FORMAT(" + dateField + ",'%Y') AS signed)=?" + index;
        } else if (planType == PlanType.MONTH) {
            return "CAST(DATE_FORMAT(" + dateField + ",'%Y%m') AS signed)=?" + index;
        } else if (planType == PlanType.DAY) {
            return "CAST(DATE_FORMAT(" + dateField + ",'%Y%m%dd') AS signed)=?" + index;
        } else {
            throw new ApplicationException(PmsErrorCode.PLAN_CONFIG_PLAN_TYPE_NOT_SUPPORT);
        }
    }

    /**
     * 统计出计划报表
     *
     * @param userPlan
     * @param date     指定日期
     * @param userId
     * @return
     */
    public PlanReport statPlanReport(UserPlan userPlan, Date date, Long userId, PlanReportDataStatFilterType filterType) {
        int bussDay = calDateValue(userPlan.getPlanConfig().getPlanType(), date);
        int year = DateUtil.getYear(date);
        if (filterType != PlanReportDataStatFilterType.ORIGINAL) {
            //不是原始的没有比对意义
            return this.statPlanReport(userPlan, bussDay, userId, null, date, filterType);
        } else {
            UserPlanConfigValue userPlanConfigValue = this.getNearestUserPlanConfigValue(year, userPlan.getId());
            return this.statPlanReport(userPlan, bussDay, userId, userPlanConfigValue, date, filterType);
        }
    }

    /**
     * 获取最近的用户计划配置值
     *
     * @param year
     * @param userPlanId
     * @return
     */
    public UserPlanConfigValue getNearestUserPlanConfigValue(int year, Long userPlanId) {
        try {
            //取绝对值
            String sql = "select abs(year-" + year + ") as tt,t.id from user_plan_config_value t where t.user_plan_id=?0 order by tt asc, created_time desc ";
            List<Object[]> list = this.getEntityListNoPageSQL(sql, userPlanId);
            if (list.isEmpty()) {
                return null;
            } else {
                Long id = Long.valueOf(list.get(0)[1].toString());
                UserPlanConfigValue pcv = (UserPlanConfigValue) this.getEntityById(UserPlanConfigValue.class, id);
                return pcv;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最近的用户计划配置值异常", e);
        }
    }

    private Date addStatDate(PlanType planType, Date date) {
        if (planType == PlanType.DAY) {
            return DateUtil.getDate(1, date);
        } else if (planType == PlanType.WEEK) {
            return DateUtil.getDateWeek(1, date);
        } else if (planType == PlanType.MONTH) {
            return DateUtil.getDateMonth(1, date);
        } else if (planType == PlanType.YEAR) {
            return DateUtil.getDateYear(1, date);
        } else {
            return null;
        }
    }

    /**
     * 计算日期的数字类型值
     *
     * @param planType
     * @param date
     * @return
     */
    public int calDateValue(PlanType planType, Date date) {
        if (planType == PlanType.DAY) {
            return Integer.valueOf(DateUtil.getFormatDate(date, "yyyyMMdd"));
        } else if (planType == PlanType.WEEK) {
            //TODO
            return Integer.valueOf(DateUtil.getFormatDate(date, "yyyyMMdd"));
        } else if (planType == PlanType.MONTH) {
            return Integer.valueOf(DateUtil.getFormatDate(date, "yyyyMM"));
        } else if (planType == PlanType.YEAR) {
            return Integer.valueOf(DateUtil.getFormatDate(date, "yyyy"));
        }
        return 0;
    }

    /**
     * 获取结果的分类统计
     *
     * @param sf
     * @param type:0针对count 1针对value
     * @return
     */
    public List<PlanReportResultGroupStat> statPlanReportResultGroup(PlanReportResultGroupStatSearch sf, int type) {
        try {
            String groupField = "count_value_stat_result";
            if (type == 1) {
                groupField = "value_stat_result";
            }
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select " + groupField + " as resultType,count(0) as totalCount");
            sb.append(" from plan_report ");
            sb.append(pr.getParameterString());
            sb.append(" group by " + groupField);
            List<PlanReportResultGroupStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), PlanReportResultGroupStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最近的计划配置值异常", e);
        }
    }

    /**
     * 获取平均值
     *
     * @param sf
     * @return
     */
    public List<PlanReportAvgStat> statPlanReportAvg(PlanReportAvgStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select user_plan_id as userPlanId,AVG(report_count_value) as reportCountValue,AVG(report_value) as reportValue ");
            sb.append(" from plan_report ");
            sb.append(pr.getParameterString());
            if (sf.getPlanType() != null) {
                //过滤
                sb.append("and user_plan_id in (select id from plan_config where plan_type=" + sf.getPlanType().ordinal() + ")");
            }
            sb.append(" group by user_plan_id");
            List<PlanReportAvgStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), PlanReportAvgStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取平均值异常", e);
        }
    }

    /**
     * 获取用户计划
     *
     * @param userId
     * @return
     */
    public List<UserPlan> getUserPlanList(Long userId, String relatedBeans, PlanType planType) {
        try {
            List args = new ArrayList();
            args.add(userId);
            StringBuffer sb = new StringBuffer();
            sb.append("from UserPlan where userId=?0 ");
            int index = 1;
            if (!StringUtil.isEmpty(relatedBeans)) {
                sb.append("and planConfig.relatedBeans=?" + (index++) + " ");
                args.add(relatedBeans);
            }
            if (planType != null) {
                sb.append("and planConfig.planType=?" + (index++) + " ");
                args.add(planType);
            }
            sb.append("order by planConfig.planType ,orderIndex ");
            List<UserPlan> list = this.getEntityListNoPageHQL(sb.toString(), args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户计划异常", e);
        }
    }

    /**
     * 获取用户计划报告时间线
     *
     * @param userPlanId
     * @return
     */
    public PlanReportTimeline getPlanReportTimeline(Long userPlanId, Date bussStatDate) {
        try {
            String hql = "from PlanReportTimeline where userPlan.id=?0 and bussStatDate=?1 ";
            return (PlanReportTimeline) this.getEntityForOne(hql, userPlanId, bussStatDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户计划异常", e);
        }
    }

    /**
     * 获取用户计划报告时间线
     *
     * @return
     */
    public List<PlanReportTimeline> getPlanReportTimelineList(Date startDate, Date endDate, Long userPlanId) {
        try {
            String hql = "from PlanReportTimeline where bussStatDate>=?0 and bussStatDate<=?1 and userPlan.id=?2 order by bussStatDate";
            List<PlanReportTimeline> list = this.getEntityListNoPageHQL(hql, startDate, endDate, userPlanId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户计划报告时间线异常", e);
        }
    }

    /**
     * 获取计划配置
     * 需要根据用户级别判断
     *
     * @return
     */
    public PlanConfig getPlanConfig(Long id, Integer userLevel) {
        try {
            String hql = "from PlanConfig where id=?0 and level<=?1 ";
            return (PlanConfig) this.getEntityForOne(hql, id, userLevel);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取提醒配置列表异常", e);
        }
    }


    /**
     * 重新保存计划报告时间线
     */
    public void reSavePlanReportTimeline(List<PlanReportTimeline> datas, Integer bussDay, Long userId,Long userPlanId) {
        try {
            String hql = "delete from PlanReportTimeline where bussDay=?0 and userId=?1 and userPlan.id=?2";
            this.updateEntities(hql, bussDay, userId,userPlanId);
            this.saveEntities(datas.toArray());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "重新保存计划报告时间线异常", e);
        }
    }


}
