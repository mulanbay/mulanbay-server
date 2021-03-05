package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.BodyAbnormalRecordGroupType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.bodyabnormal.BodyAbnormalCategorySearch;
import cn.mulanbay.pms.web.bean.request.bodyabnormal.BodyAbnormalRecordDateStatSearch;
import cn.mulanbay.pms.web.bean.request.bodyabnormal.BodyAbnormalRecordStatSearch;
import cn.mulanbay.pms.web.bean.request.bodybasicInfo.BodyBasicInfoDateStatSearch;
import cn.mulanbay.pms.web.bean.request.health.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 看病
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class TreatService extends BaseHibernateDao {

    /**
     * 获取看病手术的分类列表，统计聚合
     *
     * @return
     */
    public List<String> getTreatOperationCategoryList(TreatOperationCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ");
            sb.append(sf.getGroupField());
            sb.append(" from TreatOperation ");
            sb.append(pr.getParameterString());
            sb.append(" order by ");
            sb.append(sf.getGroupField());
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病手术的分类列表异常", e);
        }
    }

    /**
     * 获取检测的分类列表，统计聚合
     *
     * @return
     */
    public List<String> getTreatTestCategoryList(TreatTestCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct name");
            sb.append(" from TreatTest ");
            sb.append(pr.getParameterString());
            sb.append(" order by name");
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取检测的分类列表异常", e);
        }
    }


    /**
     * 获取看病用药的分类列表，统计聚合
     *
     * @return
     */
    public List<String> getTreatDrugCategoryList(TreatDrugCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ");
            sb.append(sf.getGroupField());
            sb.append(" from TreatDrug ");
            sb.append(pr.getParameterString());
            sb.append(" order by ");
            sb.append(sf.getGroupField());
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病用药的分类列表异常", e);
        }
    }


    /**
     * 获取看病分类列表，统计聚合
     *
     * @return
     */
    public List<String> getTreatCategoryList(TreatCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ");
            sb.append(sf.getGroupField());
            sb.append(" from TreatRecord ");
            sb.append(pr.getParameterString());
            sb.append(" order by ");
            sb.append(sf.getGroupField());
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病分类列表异常", e);
        }
    }

    /**
     * 获取症状或者器官的各种分类归类
     *
     * @return
     */
    public List<String> getBodyAbnormalCategoryList(BodyAbnormalCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ");
            sb.append(sf.getGroupField());
            sb.append(" from BodyAbnormalRecord ");
            sb.append(pr.getParameterString());
            sb.append(" order by ");
            sb.append(sf.getGroupField());
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取症状或者器官的各种分类归类异常", e);
        }
    }

    /**
     * 获取看病记录总的统计
     *
     * @param sf
     * @return
     */
    public TreatRecordSummaryStat statTreatRecord(TreatRecordSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select count(0) as totalCount,");
            sb.append("sum(registeredFee) as totalRegisteredFee,");
            sb.append("sum(drugFee) as totalDrugFee,");
            sb.append("sum(operationFee) as totalOperationFee,");
            sb.append("sum(totalFee) as totalTotalFee,");
            sb.append("sum(medicalInsurancePaidFee) as totalMedicalInsurancePaidFee,");
            sb.append("sum(personalPaidFee) as totalPersonalPaidFee,");
            sb.append("max(treatDate) as maxTreatDate");
            sb.append(" from TreatRecord ");
            sb.append(pr.getParameterString());
            List<TreatRecordSummaryStat> list = this.getEntityListWithClassHQL(sb.toString(), pr.getPage(), pr.getPageSize(), TreatRecordSummaryStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病记录统计异常", e);
        }
    }

    /**
     * 获取看病记录分析
     *
     * @param sf
     * @return
     */
    public List<TreatRecordAnalyseStat> treatRecordAnalyseStat(TreatRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String groupField = sf.getGroupField();
            String feeField = sf.getFeeField();
            StringBuffer sb = new StringBuffer();
            sb.append("select " + groupField + " as name,");
            sb.append("count(0) as totalCount,");
            sb.append("sum(" + feeField + ") as totalFee");
            sb.append(" from treat_record ");
            sb.append(pr.getParameterString());
            sb.append(" group by " + groupField);
            sb.append(" order by totalCount desc");
            List<TreatRecordAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), TreatRecordAnalyseStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病记录分析异常", e);
        }
    }

    /**
     * 获取看病记录手术统计
     *
     * @param sf
     * @return
     */
    public List<TreatOperationStat> treatOperationStat(TreatOperationStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            StringBuffer sb = new StringBuffer();
            String field = sf.getGroupField();
            sb.append("select name,count(0) as totalCount,sum(total_fee) as totalFee from (");
            sb.append("select treatOperation." + field + " as name,treatRecord.total_fee from treat_operation treatOperation,treat_record treatRecord ");
            sb.append("where treatOperation.treat_record_id= treatRecord.id ");
            sb.append(pr.getParameterString());
            sb.append(") as aa");
            sb.append(" group by name order by totalCount desc");
            List<TreatOperationStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), TreatOperationStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病记录手术统计异常", e);
        }
    }

    /**
     * 删除看病记录
     *
     * @param treatRecord
     */
    public void deleteTreatRecord(TreatRecord treatRecord) {
        try {
            // step 1 删除看病记录中的手术记录
            String hql = "delete from TreatOperation where treatRecord.id=?0 ";
            this.updateEntities(hql, treatRecord.getId());

            // step 2 删除看病记录中的用药记录
            hql = "delete from TreatDrug where treatRecord.id=?0 ";
            this.updateEntities(hql, treatRecord.getId());

            // step 3 删除看病记录
            this.removeEntity(treatRecord);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_DELETE_ERROR,
                    "删除看病记录异常", e);
        }
    }

    /**
     * 获取身体不适统计
     *
     * @param sf
     * @return
     */
    public List<BodyAbnormalRecordStat> bodyAbnormalRecordStat(BodyAbnormalRecordStatSearch sf) {
        try {
            BodyAbnormalRecordGroupType groupField = sf.getGroupField();
            PageRequest pr = sf.buildQuery();
            List args = pr.getParameterValueList();
            StringBuffer sb = new StringBuffer();
            sb.append("select name,count(0) as totalCount,sum(last_days) as totalLastDays,max(occur_date) as maxOccurDate,min(occur_date) as minOccurDate ");
            sb.append("from (select ");
            if (groupField == BodyAbnormalRecordGroupType.DISEASE || groupField == BodyAbnormalRecordGroupType.ORGAN) {
                sb.append(groupField.getField() + " as name");
            } else {
                //数字转换为字符
                sb.append("CAST(" + groupField.getField() + " AS CHAR) as name");
            }
            sb.append(",last_days,occur_date from body_abnormal_record ");
            sb.append(pr.getParameterString());
            int lastIndex = pr.getFirstIndex();
            if (!StringUtil.isEmpty(sf.getName())) {
                //添加检索
                sb.append(" and " + groupField.getField() + " like ?" + lastIndex);
                args.add("%" + sf.getName() + "%");
            }
            sb.append(") tt group by name order by totalCount desc");
            List<BodyAbnormalRecordStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BodyAbnormalRecordStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取身体不适统计异常", e);
        }
    }

    /**
     * 统计身体不适的基于时间的统计
     *
     * @param sf
     * @return
     */
    public List<BodyAbnormalRecordDateStat> statDateBodyAbnormalRecord(BodyAbnormalRecordDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount, ");
            sb.append("sum(last_days) as totalLastDays ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("occur_date", dateGroupType) + "as indexValue,");
            sb.append("last_days from body_abnormal_record ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<BodyAbnormalRecordDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BodyAbnormalRecordDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "身体不适的基于时间的统计异常", e);
        }
    }

    /**
     * 统计身体不适的基于时间的统计
     *
     * @param sf
     * @return
     */
    public BodyBasicInfoAvgStat statAvgBodyBasicInfo(BodyBasicInfoDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select avg(weight) as avgWeight,avg(height) as avgHeight ");
            sb.append("from body_basic_info ");
            sb.append(pr.getParameterString());
            BodyBasicInfoAvgStat stat = (BodyBasicInfoAvgStat) this.getEntityListWithClassSQLForOne(sb.toString(), BodyBasicInfoAvgStat.class, pr.getParameterValue());
            return stat;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "身体不适的基于时间的统计异常", e);
        }
    }

    /**
     * 统计身体基本情况的基于时间的统计
     *
     * @param sf
     * @return
     */
    public List<BodyBasicInfoDateStat> statDateBodyBasicInfo(BodyBasicInfoDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount, ");
            sb.append("sum(weight) as totalWeight,sum(height) as totalHeight,sum(bmi) as totalBmi ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("record_date", dateGroupType) + "as indexValue,");
            sb.append("weight,height,bmi from body_basic_info ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<BodyBasicInfoDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), BodyBasicInfoDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "统计身体基本情况的基于时间的统计异常", e);
        }
    }

    /**
     * 获取最新的看病记录
     *
     * @param name
     * @param groupField
     * @return
     */
    public TreatRecord getLatestTreatRecord(String name, BodyAbnormalRecordGroupType groupField, Long userId) {
        try {
            String fieldName = "organ";
            if (groupField == BodyAbnormalRecordGroupType.DISEASE) {
                fieldName = "disease";
            }
            StringBuffer sb = new StringBuffer();
            sb.append("from TreatRecord ");
            sb.append("where " + fieldName + "=? and userId=?0");
            sb.append("and treatDate=(");
            sb.append("select max(treatDate) from TreatRecord  where " + fieldName + "=?1 and userId=?2 )");
            return (TreatRecord) this.getEntityForOne(sb.toString(), name, userId, name, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最新的看病记录异常", e);
        }
    }

    /**
     * 获取最新的看病记录
     *
     * @param sf
     * @return
     */
    public TreatRecordAnalyseDetailStat statDetailTreatRecordAnalyse(TreatRecordAnalyseDetailStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String paraString = pr.getParameterString();
            List args = pr.getParameterValueList();
            StringBuffer statSql = new StringBuffer();
            statSql.append("select count(0) as totalCount,sum(total_fee) as totalFee ,min(treat_date) as minTreatDate,max(treat_date) as maxTreatDate ");
            statSql.append("from treat_record ");
            statSql.append(paraString);
            TreatRecordAnalyseDetailStat stat = (TreatRecordAnalyseDetailStat) this.getEntityListWithClassSQLForOne(statSql.toString(), TreatRecordAnalyseDetailStat.class, args.toArray());
            //获取详情
            int lastIndex = pr.getFirstIndex();
            if (stat.getMinTreatDate() != null) {
                String hql = "from TreatRecord " + paraString + " and treatDate = ?" + lastIndex;
                List newArgs = new ArrayList();
                newArgs.addAll(args);
                newArgs.add(stat.getMinTreatDate());
                TreatRecord minTreatRecord = (TreatRecord) this.getEntityForOne(hql, newArgs.toArray());
                stat.setMinTreatRecord(minTreatRecord);
            }
            if (stat.getMaxTreatDate() != null) {
                String hql = "from TreatRecord " + paraString + " and treatDate = ?" + lastIndex;
                List newArgs = new ArrayList();
                newArgs.addAll(args);
                newArgs.add(stat.getMaxTreatDate());
                TreatRecord maxTreatRecord = (TreatRecord) this.getEntityForOne(hql, newArgs.toArray());
                stat.setMaxTreatRecord(maxTreatRecord);
            }
            return stat;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最新的看病记录异常", e);
        }
    }

    /**
     * 保存用药详情
     *
     * @param list
     * @return
     */
    public void saveTreatDrugDetailList(List<TreatDrugDetail> list) {
        for (TreatDrugDetail bean : list) {
            this.saveOrUpdateTreatDrugDetail(bean);
        }
    }

    /**
     * 保存或者更新用药详情
     *
     * @param bean
     * @return
     */
    public void saveOrUpdateTreatDrugDetail(TreatDrugDetail bean) {
        try {
            if (bean.getId() == null) {
                this.saveEntity(bean);
            } else {
                this.updateEntity(bean);
            }
            Date compareDate = DateUtil.getDate(bean.getOccurTime(), DateUtil.FormatDay1);
            //更新药品记录的数据
            String hql = "update TreatDrug set beginDate=?0 where id=?1 and (beginDate is null or beginDate>?2 ) ";
            this.updateEntities(hql, compareDate, bean.getTreatDrug().getId(), compareDate);
            String hql2 = "update TreatDrug set endDate=?0 where id=?1 and (endDate is null or endDate<?2 ) ";
            this.updateEntities(hql2, compareDate, bean.getTreatDrug().getId(), compareDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "保存或者更新用药详情异常", e);
        }
    }

    /**
     * 统计看病记录
     *
     * @param sf
     * @return
     */
    public List<TreatRecordDateStat> statDateTreatRecord(TreatRecordDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            String feeField = sf.getFeeField();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount, ");
            sb.append("sum(" + feeField + ") as totalFee ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("treat_date", dateGroupType) + "as indexValue,");
            sb.append(feeField + " from treat_record ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<TreatRecordDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), TreatRecordDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "统计看病记录异常", e);
        }
    }

    /**
     * 获取需要提醒的药品
     * 条件为：1设置了提醒 2还处在用药期间
     *
     * @param date
     * @return
     */
    public List<TreatDrug> getNeedRemindDrug(Date date) {
        try {

            String hql = "from TreatDrug where remind=1 and beginDate<=?0 and endDate>=?1 ";
            return this.getEntityListNoPageHQL(hql, date, date);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要提醒的药品异常", e);
        }
    }

    /**
     * 获取需要提醒的手术
     * 条件为：包含复查时间
     *
     * @param beginDate
     * @return
     */
    public List<TreatOperation> getNeedRemindOperation(Date beginDate, Date endDate, Long userId) {
        try {

            String hql = "from TreatOperation where reviewDate is not null and reviewDate>=?0 and reviewDate<=?1 ";
            if (userId != null) {
                hql += " and userId=" + userId;
            }
            return this.getEntityListNoPageHQL(hql, beginDate, endDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要提醒的药品异常", e);
        }
    }

    /**
     * 需要在日历显示的药品
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<TreatDrug> getDrugForCalendar(Long userId, String name, Date startDate, Date endDate) {
        try {
            String hql = "select new TreatDrug(id,name,unit,perDay,perTimes,eu, ec,useWay,beginDate,endDate) from TreatDrug where userId=?0 and beginDate<=?1 and endDate>=?2 ";
            if (StringUtil.isNotEmpty(name)) {
                hql += " and name like '%" + name + "%'";
            }
            return this.getEntityListNoPageHQL(hql, userId, endDate, startDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要提醒的药品异常", e);
        }
    }


    /**
     * 获取手术
     * 条件为：包含复查时间
     *
     * @param minDate
     * @return
     */
    public TreatOperation getOperation(Date minDate, Long userId, String name) {
        try {

            String hql = "from TreatOperation where userId =?0 and name=?1 and treatDate>=?2 ";
            return (TreatOperation) this.getEntityForOne(hql, userId, name, minDate);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取手术异常", e);
        }
    }

    /**
     * 获取用药次数
     *
     * @param date
     * @return
     */
    public long getDrugDetailCount(Long treatDrugId, Date date) {
        try {
            Date startTime = DateUtil.getFromMiddleNightDate(date);
            Date endTime = DateUtil.getTodayTillMiddleNightDate(date);
            String hql = "select count(0) as n from TreatDrugDetail where treatDrug.id=?0 and occurTime>=?1 and occurTime<=?2 ";
            return this.getCount(hql, treatDrugId, startTime, endTime);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药次数异常", e);
        }
    }

    /**
     * 获取看病记录关键字
     *
     * @return
     */
    public List<String> getTagsList(TreatRecordTagsSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            String sql = "select distinct tags from treat_record where tags is not null ";
            sql += pr.getParameterString();
            sql += " order by tags desc";
            return this.getEntityListSQL(sql, 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病记录关键字异常", e);
        }
    }

    /**
     * 获取用药统计
     *
     * @param sf
     * @return
     */
    public List<TreatDrugDetailStat> treatDrugDetailStat(TreatDrugDetailStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select td.id ,td.name,td.treat_date as treatDate, ");
            sb.append("min(tdd.occur_time) as minTime,max(tdd.occur_time) as maxTime,count(0) as totalCount,datediff(max(tdd.occur_time) ,min(tdd.occur_time)) as days ");
            sb.append("from treat_drug_detail tdd,treat_drug td ");
            sb.append("where tdd.treat_drug_id = td.id ");
            pr.setNeedWhere(false);
            sb.append(pr.getParameterString());
            sb.append("group by td.id ,td.name ");
            sb.append("order by td.name ");
            String sql = sb.toString();
            if (sf.isMergeSameName()) {
                StringBuffer nn = new StringBuffer();
                nn.append("select name,min(minTime) as minTime,max(maxTime) as maxTime,sum(totalCount) as totalCount,sum(days) as days ");
                nn.append("from ( ");
                nn.append(sql);
                nn.append(") as res group by name ");
                nn.append("order by maxTime desc ");
                sql = nn.toString();
            }
            List<TreatDrugDetailStat> list = this.getEntityListWithClassSQL(sql, pr.getPage(), pr.getPageSize(), TreatDrugDetailStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药统计异常", e);
        }
    }

    /**
     * 获取最近一次的检查
     *
     * @param name
     * @param userId
     * @return
     */
    public TreatTest getLastTreatTest(String name, Long userId) {
        try {
            String hql = "from TreatTest where name=?0 and userId=?1 order by testDate desc ";
            return (TreatTest) this.getEntityForOne(hql, name, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一次的检查异常", e);
        }
    }

    /**
     * 获取最近一次的手术
     *
     * @param name
     * @param userId
     * @return
     */
    public TreatOperation getLastTreatOperation(String name, Long userId) {
        try {

            String hql = "from TreatOperation where name=?0 and userId=?1 order by treatDate desc ";
            return (TreatOperation) this.getEntityForOne(hql, name, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一次的手术异常", e);
        }
    }

    /**
     * 获取最近一次的药品
     *
     * @param name
     * @param userId
     * @return
     */
    public TreatDrug getLastTreatDrug(String name, Long userId) {
        try {

            String hql = "from TreatDrug where name=?0 and userId=?1 order by treatDate desc ";
            return (TreatDrug) this.getEntityForOne(hql, name, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一次的药品异常", e);
        }
    }


    /**
     * 获取疾病概况统计
     *
     * @param sf
     * @return
     */
    public List<TreatRecordFullStat> treatRecordFullStat(TreatRecordFullStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT tags,count(0) as totalCount,min(treat_date) as minTreatDate,max(treat_date)  as maxTreatDate,");
            sb.append("sum(registered_fee) as registeredFee,sum(drug_fee) as drugFee,sum(operation_fee) as operationFee, ");
            sb.append("sum(medical_insurance_paid_fee) as medicalInsurancePaidFee,sum(personal_paid_fee) as personalPaidFee,sum(total_fee) as totalFee ");
            sb.append("FROM treat_record ");
            sb.append("where tags is not null ");
            sb.append(pr.getParameterString());
            sb.append(" group by tags");
            sb.append(" order by minTreatDate desc");
            List<TreatRecordFullStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), TreatRecordFullStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取看病总的分析异常", e);
        }
    }

    /**
     * 疾病概况统计
     *
     * @param sf
     * @return
     */
    public long getMaxRowOfTreatRecordFullStat(TreatRecordFullStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            StringBuffer sb = new StringBuffer();
            sb.append("select count(0) from (");
            sb.append("select tags,count(0) n FROM treat_record where tags is not null ");
            sb.append(pr.getParameterString());
            sb.append(" group by tags ) as tt");
            long n = this.getCountSQL(sb.toString(), pr.getParameterValue());
            return n;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "疾病概况统计异常", e);
        }
    }

    /**
     * 获取用药明细列表日历统计
     *
     * @param sf
     * @return
     */
    public List<TreatDrugDetail> getTreatDrugDetailCalendarStatList(TreatDrugDetailSearch sf,Long treatDrugId,boolean mergeSameName) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("from TreatDrugDetail ");
            sb.append(pr.getParameterString());
            List paras = pr.getParameterValueList();
            if(mergeSameName){
                TreatDrug tt = (TreatDrug) this.getEntityById(TreatDrug.class,treatDrugId);
                int nextIndex = pr.getFirstIndex();
                sb.append(" and treatDrug.id in (select id from TreatDrug where name=?"+(nextIndex++)+" and userId=?"+(nextIndex++)+")");
                paras.add(tt.getName());
                paras.add(sf.getUserId());
            }else{
                sb.append(" and treatDrug.id=?"+pr.getFirstIndex());
                paras.add(treatDrugId);
            }
            List<TreatDrugDetail> list = this.getEntityListNoPageHQL(sb.toString(), paras.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药明细列表日历统计异常", e);
        }
    }

    /**
     * 获取用药明细的时间列表
     * @param treatDrugId
     * @param startDate
     * @param endDate
     * @param userId
     * @param mergeSameName
     * @return
     */
    public List<Date> getDrugDetailDateList(Long treatDrugId,Date startDate,Date endDate,Long userId,boolean mergeSameName) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select occurTime from TreatDrugDetail ");
            sb.append("where userId=?0 and occurTime>=?1 and occurTime<=?2 ");
            if(!mergeSameName){
                sb.append("and treatDrug.id=?3 ");
                sb.append(" order by occurTime");
                List<Date> list = this.getEntityListNoPageHQL(sb.toString(), userId,startDate,endDate,treatDrugId);
                return list;
            }else{
                TreatDrug td = (TreatDrug) this.getEntityById(TreatDrug.class,treatDrugId);
                sb.append("and treatDrug.id in (select id from TreatDrug where name=?3) ");
                List<Date> list = this.getEntityListNoPageHQL(sb.toString(), userId,startDate,endDate,td.getName());
                return list;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药明细的时间列表异常", e);
        }
    }

    /**
     * 获取用药中的药品列表
     * @param date 比较日期
     * @param userId
     * @return
     */
    public List<TreatDrug> getActiveTreatDrugList(Date date,Long userId) {
        try {
            String hql = "from TreatDrug where userId=?0 and beginDate<=?1 and endDate>=?2 ";
            return this.getEntityListNoPageHQL(hql,userId,date,date);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药中的药品列表异常", e);
        }
    }

    /**
     * 获取用药详情列表
     * @param date 比较日期
     * @param userId
     * @return
     */
    public List<TreatDrugDetail> getTreatDrugDetailList(Date date,Long userId,Long treatDrugId) {
        try {
            Date end = DateUtil.getTodayTillMiddleNightDate(date);
            String hql = "from TreatDrugDetail where userId=?0 and occurTime>=?1 and occurTime<=?2 and treatDrug.id=?3 order by occurTime";
            return this.getEntityListNoPageHQL(hql,userId,date,end,treatDrugId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用药详情列表异常", e);
        }
    }
}
