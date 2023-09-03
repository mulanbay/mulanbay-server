package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.CommonRecord;
import cn.mulanbay.pms.persistent.domain.CommonRecordType;
import cn.mulanbay.pms.persistent.dto.CommonRecordAnalyseStat;
import cn.mulanbay.pms.persistent.dto.CommonRecordStat;
import cn.mulanbay.pms.persistent.enums.NearestType;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordKeywordsSearch;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordAnalyseSearch;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordNameTreeSearch;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordNearestSearch;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordStatSearch;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class CommonRecordService extends BaseHibernateDao {

    /**
     * 删除通用记录类型
     *
     * @param commonRecordType
     */
    public void deleteCommonRecordType(CommonRecordType commonRecordType) {
        try {
            //删除曲子
            String hql = "delete from CommonRecord where commonRecordType.id=?0";
            this.updateEntities(hql, commonRecordType.getId());
            //删除记录
            this.removeEntity(commonRecordType);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "删除通用记录类型异常", e);
        }
    }

    /**
     * 分析
     *
     * @param sf 查询条件
     * @return
     */
    public List<CommonRecordAnalyseStat> analyse(CommonRecordAnalyseSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String fieldName = sf.getGroupField();
            StringBuffer sb = new StringBuffer();
            sb.append("select * from ( ");
            sb.append("select " + fieldName + " as name,count(0) as totalCount,sum(value) as totalValue from common_record a ");
            sb.append(pr.getParameterString());
            sb.append("group by " + fieldName + " ) as res ");

            List<CommonRecordAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(),CommonRecordAnalyseStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "通用记录分享异常", e);
        }
    }

    /**
     * 统计
     *
     * @param sf
     * @return
     */
    public CommonRecordStat statCommonRecord(CommonRecordStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select min(occur_time) as minDate,max(occur_time) as maxDate,sum(value) as totalValue ,count(0) as totalCount ");
            sb.append(" from common_record ");
            sb.append(pr.getParameterString());
            List<CommonRecordStat> list = this.getEntityListWithClassSQL(sb.toString(), 0, 0, CommonRecordStat.class, pr.getParameterValue());
            return list.get(0);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "统计异常", e);
        }
    }

    /**
     * 获取名称列表
     *
     * @return
     */
    public List<String> getNameList(CommonRecordNameTreeSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String sql = "select distinct name from common_record ";
            sql += pr.getParameterString();
            return this.getEntityListNoPageSQL(sql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取名称列表异常", e);
        }
    }

    /**
     * 获取最近一条记录
     *
     * @return
     */
    public CommonRecord getNearest(CommonRecordNearestSearch sf) {
        try {
            NearestType nearestType = sf.getNearestType();
            PageRequest pr = sf.buildQuery();
            String hql = "from CommonRecord ";
            hql += pr.getParameterString();
            if(nearestType==NearestType.MIN_TIME){
                hql+= " order by occurTime asc";
            }else if(nearestType==NearestType.MAX_TIME){
                hql+= " order by occurTime desc";
            }else if(nearestType==NearestType.MIN_VALUE){
                hql+= " order by value asc";
            }else if(nearestType==NearestType.MAX_VALUE){
                hql+= " order by value desc";
            }
            return (CommonRecord) this.getEntityForOne(hql, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一条记录异常", e);
        }
    }
}
