package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.OperationLog;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import cn.mulanbay.pms.persistent.dto.OperationLogDateStat;
import cn.mulanbay.pms.persistent.dto.OperationLogStat;
import cn.mulanbay.pms.persistent.dto.OperationLogTreeStat;
import cn.mulanbay.pms.persistent.dto.SystemLogAnalyseStat;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.FunctionType;
import cn.mulanbay.pms.persistent.enums.LogCompareType;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.log.OperationLogDateStatSearch;
import cn.mulanbay.pms.web.bean.request.log.OperationLogStatSearch;
import cn.mulanbay.pms.web.bean.request.log.OperationLogTreeStatSearch;
import cn.mulanbay.pms.web.bean.request.log.SystemLogAnalyseStatSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * 日志
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class LogService extends BaseHibernateDao {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    /**
     * 获取最近的操作日志比较(根据比较主体)
     * 包含：新增、修改、删除
     *
     * @param target
     * @param compareType
     * @return
     */
    public OperationLog getNearestCompareLog(OperationLog target, LogCompareType compareType) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from OperationLog where systemFunction.id in ");
            sb.append("(select id from SystemFunction where beanName=?0 and functionType in (0,1,2) ) ");
            sb.append("and id!=?1 ");
            sb.append("and idValue=?2 ");
            if (compareType == LogCompareType.EARLY) {
                sb.append("and occurEndTime<=?3 order by occurEndTime desc");
            } else {
                sb.append("and occurEndTime>=?3 order by occurEndTime asc");
            }
            OperationLog log = (OperationLog) this.getEntityForOne(sb.toString(), target.getSystemFunction().getBeanName(), target.getId(), target.getIdValue(), target.getOccurEndTime());
            return log;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最近的操作日志比较异常", e);
        }
    }

    /**
     * 重新设置操作日志的功能点
     *
     * @param needReSet 如果true，说明以前已经有的也要重新设置
     * @return
     */
    public void setOperationLogFunctionId(boolean needReSet) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("update operation_log t set system_function_id= ");
            sb.append("(select id from system_function s where s.url_address=t.url_address and s.support_methods = t.method limit 1) ");
            if (!needReSet) {
                sb.append("where t.system_function_id is null ");
            }
            this.execSqlUpdate(sb.toString());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "重新设置操作日志的功能点异常", e);
        }
    }

    /**
     * 获取未配置的url
     *
     * @return
     */
    public List<Object[]> getUnConfigedUrlsForOperationLog() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ol.url_address,ol.method ");
            sb.append("from operation_log ol  ");
            sb.append("where ol.url_address not in (select url_address from system_function) ");
            sb.append("order by ol.url_address ");
            List<Object[]> list = this.getEntityListNoPageSQL(sb.toString());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取未配置的url异常", e);
        }
    }

    /**
     * 获取idValue为空的数据
     *
     * @return
     */
    public List<OperationLog> getOperationLogByIdValueWithNull() {
        try {
            String hql = "from OperationLog where idValue is null ";
            List list = this.getEntityListNoPageHQL(hql);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取idValue为空的数据异常", e);
        }
    }

    /**
     * 重新设置操作日志的功能点
     *
     * @param log
     * @return
     */
    public void setIdValue(OperationLog log) {
        try {
            if (log.getSystemFunction() == null) {
                logger.warn("操作日志id=" + log.getId() + "还没有关联功能点");
                return;
            }
            Map map = (Map) JsonUtil.jsonToBean(log.getParas(), Map.class);
            if (map == null || map.isEmpty()) {
                logger.warn("操作日志id=" + log.getId() + "无任何请求参数");
                return;
            }
            String idValue = (String) map.get(log.getSystemFunction().getIdField());
            if (!StringUtil.isEmpty(idValue)) {
                String hql = "update OperationLog set idValue=?0 where id=?1 ";
                this.updateEntities(hql, idValue, log.getId());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "重新设置操作日志的功能点异常", e);
        }
    }

    /**
     * 获取修改类的系统功能点，每个业务类只有一个
     *
     * @param beanName
     * @return
     */
    public SystemFunction getEditSystemFunction(String beanName) {
        try {
            String hql = "from SystemFunction where beanName=?0 and functionType=?1 ";
            return (SystemFunction) this.getEntityForOne(hql, beanName, FunctionType.EDIT);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取修改类的系统功能点异常", e);
        }
    }

    /**
     * 获取修改类的系统功能点，每个业务类只有一个
     *
     * @param beanName
     * @return
     */
    public SystemFunction getParentSystemFunction(Long beanName) {
        try {
            String hql = "from SystemFunction where beanName=?0 and functionType=?1 ";
            return (SystemFunction) this.getEntityForOne(hql, beanName, FunctionType.EDIT);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取修改类的系统功能点异常", e);
        }
    }

    /**
     * 获取最近一次的操作记录
     * 包含新增、修改、删除
     *
     * @param idValue
     * @param beanName
     * @return
     */
    public OperationLog getLatestOperationLog(String idValue, String beanName) {
        try {
            String hql = "from OperationLog where idValue=?0 and systemFunction.id in ";
            hql += "(select id from SystemFunction where beanName=?1 and functionType in (0,1,2) ) ";
            hql += "order by occurStartTime desc ";
            return (OperationLog) this.getEntityForOne(hql, idValue, beanName);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一次的修改记录异常", e);
        }
    }

    /**
     * 更新错误代码的执行次数
     *
     * @param code
     * @return
     */
    public void updateErrorCodeCount(Integer code, int addCount) {
        try {
            String sql = "update error_code_define set count=count+" + addCount + " where code=?0 ";
            this.execSqlUpdate(sql, code);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新错误代码的执行次数异常", e);
        }
    }

    /**
     * 操作日志统计
     *
     * @param sf
     * @return
     */
    public List<OperationLogTreeStat> treeStatOperationLog(OperationLogTreeStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select rr.*,pp.name as pname from ( ");
            sb.append("select syt.*,sf.name,sf.pid from ");
            sb.append("(SELECT system_function_id as functionId,count(0) as totalCount FROM operation_log ");
            sb.append(pr.getParameterString());
            sb.append(" group by system_function_id) as syt,");
            sb.append("system_function sf where syt.functionId = sf.id and sf.tree_stat=1 ");
            sb.append(") as rr ");
            sb.append("left join system_function pp ");
            sb.append("on rr.pid=pp.id ");
            List args = pr.getParameterValueList();
            List<OperationLogTreeStat> list = this.getEntityListWithClassSQL(sb.toString(), 0, 0, OperationLogTreeStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "操作日志统计异常", e);
        }
    }

    /**
     * 操作日志统计(分页)
     *
     * @param sf
     * @return
     */
    public List<OperationLogStat> statOperationLog(OperationLogStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT sf.name,count(0) as totalCount FROM operation_log ol,system_function sf ");
            sb.append("where ol.system_function_id = sf.id ");
            sb.append(pr.getParameterString());
            sb.append(" group by sf.name ");
            sb.append("order by totalCount desc ");
            List args = pr.getParameterValueList();
            List<OperationLogStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), OperationLogStat.class, args.toArray());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "操作日志统计(分页)异常", e);
        }
    }

    /**
     * 获取系统日志
     *
     * @param id
     * @return
     */
    public SystemLog getSystemLog(Long id) {
        try {
            String hql = "from SystemLog where id=?0 ";
            return (SystemLog) this.getEntityForOne(hql, id);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取系统日志异常", e);
        }
    }

    /**
     * 操作日志统计
     *
     * @param sf
     * @return
     */
    public List<OperationLogDateStat> statDateOperationLog(OperationLogDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            pr.setNeedWhere(false);
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,count(0) as totalCount ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("ol.occur_end_time", dateGroupType) + "as indexValue ");
            sb.append("FROM operation_log ol,system_function sf ");
            sb.append("where ol.system_function_id = sf.id ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<OperationLogDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), OperationLogDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "操作日志统计异常", e);
        }
    }

    /**
     * 系统日志统计
     *
     * @param sf
     * @return
     */
    public List<SystemLogAnalyseStat> analyseStatDateOperationLog(SystemLogAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            String groupField = sf.getGroupField();
            sb.append("select "+groupField+" as id,count(0) as totalCount ");
            sb.append("from system_log ");
            sb.append(pr.getParameterString());
            sb.append("group by "+groupField);
            List<SystemLogAnalyseStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), SystemLogAnalyseStat.class, pr.getParameterValue());
            for(SystemLogAnalyseStat as : list){
                if(as.getId()==null){
                    as.setName("未知");
                }else if("exception_class_name".equals(groupField)|| "error_code".equals(groupField)){
                    as.setName(as.getId().toString());
                }else if("log_level".equals(groupField)){
                    LogLevel ll = LogLevel.getLogLevel(Integer.valueOf(as.getId().toString()));
                    as.setName(ll.getName());
                }
            }
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "系统日志统计异常", e);
        }
    }
}
