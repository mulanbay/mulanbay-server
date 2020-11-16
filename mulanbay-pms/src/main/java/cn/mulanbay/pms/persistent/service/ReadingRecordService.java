package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.BookCategory;
import cn.mulanbay.pms.persistent.domain.ReadingRecord;
import cn.mulanbay.pms.persistent.domain.ReadingRecordDetail;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.BookLanguage;
import cn.mulanbay.pms.persistent.enums.BookType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ReadingStatus;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordDateStatSearch;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordDetailDateStatSearch;
import cn.mulanbay.pms.web.bean.request.read.ReadingRecordStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReadingRecordService extends BaseHibernateDao {


    /**
     * 阅读统计
     *
     * @param sf
     * @return
     */
    public ReadingRecordReadedStat statReadedReadingRecord(ReadingRecordStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select count(0) as totalCount,");
            sb.append("sum(cost_days) as totalCostDays");
            sb.append(" from reading_record ");
            sb.append(pr.getParameterString());
            sb.append(" and cost_days is not null ");
            List<ReadingRecordReadedStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), ReadingRecordReadedStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "阅读统计异常", e);
        }
    }

    /**
     * 阅读统计
     *
     * @param sf
     * @return
     */
    public List<ReadingRecordDateStat> statDateReadingRecord(ReadingRecordDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("finished_date", dateGroupType) + "as indexValue");
            sb.append(" from reading_record ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<ReadingRecordDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), ReadingRecordDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "阅读统计异常", e);
        }
    }

    /**
     * 阅读时间统计
     *
     * @param sf
     * @return
     */
    public List<ReadingRecordDetailDateStat> statDateReadingRecordDetail(ReadingRecordDetailDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount,sum(minutes) as totalMinutes ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("read_time", dateGroupType) + "as indexValue,minutes ");
            sb.append(" from reading_record_detail ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<ReadingRecordDetailDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), ReadingRecordDetailDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "阅读时间统计异常", e);
        }
    }

    /**
     * 根据阅读的完成天数统计（结束时间--开始时间）
     *
     * @param sf
     * @return
     */
    public List<BigInteger> statReadingRecordAnalyseByPeriod(ReadingRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            StringBuffer sb = new StringBuffer();
            sb.append("select datediff(finished_date,begin_date) as days from reading_record ");
            sb.append("where begin_date is not null and finished_date is not null ");
            sb.append(pr.getParameterString());
            List<BigInteger> list = this.getEntityListSQL(sb.toString(), 0, 0, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据阅读的完成天数统计异常", e);
        }

    }

    /**
     * 根据阅读的阅读时间统计（结束时间--开始时间）
     *
     * @param sf
     * @return
     */
    public List<BigDecimal> statReadingRecordAnalyseByTime(ReadingRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select minutes from (");
            sb.append("select reading_record_id,sum(minutes) as minutes from reading_record_detail ");
            sb.append("where reading_record_id in ");
            sb.append("( select id from reading_record ");
            sb.append(pr.getParameterString());
            sb.append(") group by reading_record_id ) as res ");
            List<BigDecimal> list = this.getEntityListSQL(sb.toString(), 0, 0, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据阅读的阅读时间统计异常", e);
        }

    }

    /**
     * 总阅读时间（分钟）
     *
     * @param id
     * @return
     */
    public Long getCostTimes(Long id) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select sum(minutes) as minutes from reading_record_detail ");
            sb.append("where reading_record_id =?0 ");
            List<BigDecimal> list = this.getEntityListSQL(sb.toString(), 0, 0, id);
            if (list.isEmpty() || list.get(0) == null) {
                return 0L;
            } else {
                return list.get(0).longValue();
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "总阅读时间异常", e);
        }

    }


    /**
     * 获取阅读分析
     *
     * @param sf
     * @return
     */
    public List<ReadingRecordAnalyseStat> statReadingRecordAnalyse(ReadingRecordAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sql = new StringBuffer();
            ReadingRecordAnalyseStatSearch.GroupType groupType = sf.getGroupType();
            sql.append("select " + groupType.getFieldName());
            sql.append(",count(*) cc from reading_record ");
            sql.append(pr.getParameterString());
            sql.append(" group by " + groupType.getFieldName());
            List<Object[]> list = this.getEntityListNoPageSQL(sql.toString(), pr.getParameterValue());
            List<ReadingRecordAnalyseStat> result = new ArrayList<ReadingRecordAnalyseStat>();
            for (Object[] oo : list) {
                ReadingRecordAnalyseStat bb = new ReadingRecordAnalyseStat();
                Object nameFiled = oo[0];
                if (nameFiled == null) {
                    bb.setName("未知");
                } else {
                    Object serierIdObj = oo[0];
                    if (serierIdObj == null) {
                        //防止为NULL
                        serierIdObj = "0";
                    }
                    String name = getSerierName(serierIdObj.toString(), groupType);
                    bb.setName(name);
                }
                double value = Double.valueOf(oo[1].toString());
                bb.setValue(value);
                result.add(bb);
            }
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取阅读分析异常", e);
        }
    }

    private String getSerierName(String idStr, ReadingRecordAnalyseStatSearch.GroupType groupType) {
        try {
            if (groupType == ReadingRecordAnalyseStatSearch.GroupType.BOOKCATEGORY) {
                BookCategory bookCategory = (BookCategory) this.getEntityById(BookCategory.class, Long.valueOf(idStr));
                return bookCategory.getName();
            } else if (groupType == ReadingRecordAnalyseStatSearch.GroupType.SCORE) {
                return idStr;
            } else if (groupType == ReadingRecordAnalyseStatSearch.GroupType.BOOKTYPE) {
                BookType bookType = BookType.getBookType(Integer.valueOf(idStr));
                return bookType == null ? idStr : bookType.getName();
            } else if (groupType == ReadingRecordAnalyseStatSearch.GroupType.LANGUAGE) {
                BookLanguage language = BookLanguage.getLanguage(Integer.valueOf(idStr));
                return language == null ? idStr : language.getName();
            } else if (groupType == ReadingRecordAnalyseStatSearch.GroupType.STATUS) {
                ReadingStatus readingStatus = ReadingStatus.getReadingStatus(Integer.valueOf(idStr));
                return readingStatus == null ? idStr : readingStatus.getName();
            } else {
                return idStr;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取阅读分析分类名称异常", e);
        }
    }

    /**
     * 设置阅读状态为阅读中
     *
     * @param readingRecordId
     */
    public void updateReadingStatusAsReading(Long readingRecordId, Date date) {
        try {
            //无论是否已经读完，都更新为在读,应该加一个状态是否改变的判断
            String hql = "update ReadingRecord set status=?0,lastModifyTime=?1 where id=?2 ";
            this.updateEntities(hql, ReadingStatus.READING, date, readingRecordId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "设置阅读状态为阅读中异常", e);
        }
    }

    /**
     * 更新或者新增阅读明细
     *
     * @param bean
     * @param update
     */
    public void saveOrUpdateReadingRecordDetail(ReadingRecordDetail bean, boolean update) {
        try {
            if (update) {
                this.updateEntity(bean);
            } else {
                this.saveEntity(bean);
            }
            this.updateReadingStatusAsReading(bean.getReadingRecord().getId(), bean.getReadTime());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "设置阅读状态为阅读中异常", e);
        }
    }

    /**
     * 删除阅读记录
     *
     * @param bean
     */
    public void deleteReadingRecord(ReadingRecord bean) {
        try {
            String hql = "delete from ReadingRecordDetail where readingRecord.id=?0 ";
            this.updateEntities(hql, bean.getId());

            this.removeEntity(bean);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "删除阅读记录异常", e);
        }
    }

    /**
     * 获取总阅读时间
     *
     * @param startDate
     */
    public ReadingDetailSummaryStat statReadingDetailSummary(Date startDate, Date endDate, Long userId) {
        try {
            String sql = "select count(*) as totalCount,sum(minutes) as totalMinutes from reading_record_detail where user_id=?0 and read_time>=?1 and read_time<=?2 ";
            List<ReadingDetailSummaryStat> list = this.getEntityListWithClassSQL(sql, 0, 0, ReadingDetailSummaryStat.class, userId, startDate, endDate);
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取总阅读时间异常", e);
        }
    }

    /**
     * 查询阅读记录
     */
    public ReadingRecord selectReadingRecord(String isbn, Long userId, Long id) {
        try {
            String hql = "from ReadingRecord where isbn=?0 and userId=?1 ";
            if (id != null) {
                hql += " and id!=" + id;
            }
            ReadingRecord rr = (ReadingRecord) this.getEntityForOne(hql, isbn, userId);
            return rr;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据bussKey查询预算日志异常", e);
        }
    }

    /**
     * 查询阅读的最早与最晚时间
     *
     * @param id
     * @return
     */
    public ReadingRecordTimeStat selectReadTimeStat(Long id) {
        try {
            String sql = "select min(read_time) as minDate,max(read_time) as maxDate from reading_record_detail where reading_record_id=?0 ";
            List<ReadingRecordTimeStat> list = this.getEntityListWithClassSQL(sql, -1, 0, ReadingRecordTimeStat.class, id);
            if (!list.isEmpty()) {
                return list.get(0);
            } else {
                return new ReadingRecordTimeStat();
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "查询阅读的最早与最晚时间异常", e);
        }
    }

}
