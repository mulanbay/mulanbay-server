package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.AfterEightHourStat;
import cn.mulanbay.pms.persistent.dto.CommonRecordDateStat;
import cn.mulanbay.pms.persistent.dto.CommonSqlDto;
import cn.mulanbay.pms.persistent.dto.DataInputAnalyseStat;
import cn.mulanbay.pms.persistent.dto.common.SystemFunctionBean;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordDateStatSearch;
import cn.mulanbay.pms.web.bean.request.data.AfterEightHourAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.request.data.DataInputAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DataService extends BaseHibernateDao {

    @Autowired
    UserPlanService userPlanService;

    /**
     * 获取平均值
     *
     * @param sf
     * @return
     */
    public List<DataInputAnalyseStat> statDataInputAnalyse(DataInputAnalyseStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            String hql = "from DataInputAnalyse ";
            hql += pr.getParameterString();
            List<DataInputAnalyse> list = this.getEntityListNoPageHQL(hql, pr.getParameterValue());
            CommonSqlDto sqlBean = this.calSqlBean(list, sf.getStartDate(), sf.getEndDate(), sf.getUserId());
            String sql = "";
            if (sf.getStatType() == DataInputAnalyseStatSearch.StatType.DAY) {
                //按照延期的天来统计
                StringBuffer sb = new StringBuffer();
                sb.append("select res as groupName,count(0) as totalCount from \n");
                sb.append("( \n");
                sb.append("select name, \n");
                sb.append("CASE WHEN n <0 THEN '超前' \n");
                sb.append("WHEN n=0  THEN '当天' \n");
                sb.append("WHEN 1<=n<=3  THEN '1-3天' \n");
                sb.append("WHEN 3<n<=7  THEN '3天到一个星期内' \n");
                sb.append("WHEN 7<n<=30  THEN '一个星期到一个月内' \n");
                sb.append("ELSE '超过一个月' \n");
                sb.append("END as res \n");
                sb.append("from ( \n");
                sb.append(sqlBean.getSqlContent());
                sb.append(" ) as rr ");
                if (!StringUtil.isEmpty(sf.getCompareValue())) {
                    sb.append(" where " + sf.getCompareValue());

                }
                sb.append(" ) as tt ");
                sb.append(" group by res \n");
                sql = sb.toString();
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("select name as groupName,count(0) as totalCount \n");
                sb.append("from ( \n");
                sb.append(sqlBean.getSqlContent());
                sb.append(" ) as rr ");
                if (!StringUtil.isEmpty(sf.getCompareValue())) {
                    sb.append(" where " + sf.getCompareValue());

                }
                sb.append(" group by name \n");
                sql = sb.toString();
            }
            List<DataInputAnalyseStat> result = this.getEntityListWithClassSQL(sql, 0, 0, DataInputAnalyseStat.class, sqlBean.getArgs().toArray());
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取平均值异常", e);
        }
    }

    /**
     * 计算封装SQL
     *
     * @param list
     * @param startDate
     * @param endDate
     * @param userId
     * @return
     */
    private CommonSqlDto calSqlBean(List<DataInputAnalyse> list, Date startDate, Date endDate, Long userId) {
        CommonSqlDto bean = new CommonSqlDto();
        StringBuffer sb = new StringBuffer();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            DataInputAnalyse dia = list.get(i);
            if (n > 1 && i > 0) {
                sb.append(" \n union \n ");
            }
            sb.append("select '" + dia.getName() + "' as name,(CAST(DATE_FORMAT(" + dia.getInputField() + ",'%Y%m%d') AS signed)-CAST(DATE_FORMAT(" + dia.getBussField() + ",'%Y%m%d') AS signed)) as n from " + dia.getTableName() + " \n");
            sb.append("where " + dia.getBussField() + " is not null ");
            int index = 0;
            if (startDate != null) {
                sb.append("and " + dia.getBussField() + ">=?" + (index++) + " ");
                bean.addArg(startDate);
            }
            if (endDate != null) {
                sb.append("and " + dia.getBussField() + "<=?" + (index++) + " ");
                bean.addArg(endDate);
            }
            if (userId != null && !StringUtil.isEmpty(dia.getUserField())) {
                sb.append("and " + dia.getUserField() + "=?" + (index++) + " ");
                bean.addArg(userId);
            }
        }
        bean.setSqlContent(sb.toString());
        return bean;
    }

    /**
     * 获取功能点的菜单列表
     *
     * @return
     */
    public List<SystemFunctionBean> getSystemFunctionMenu() {
        try {
            String sql = "select id,name,pid from system_function where function_data_type=?0 or function_data_type =?1 order by pid,order_index ";
            List<SystemFunctionBean> result = this.getEntityListWithClassSQL(sql, -1, 0,
                    SystemFunctionBean.class, FunctionDataType.M.ordinal(), FunctionDataType.C.ordinal());
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取功能点的菜单列表异常", e);
        }
    }

    /**
     * 获取功能点的列表
     *
     * @return
     */
    public List<SystemFunctionBean> getSystemFunctionList() {
        try {
            String sql = "select id,name,pid from system_function order by pid ";
            List<SystemFunctionBean> result = this.getEntityListWithClassSQL(sql, -1, 0, SystemFunctionBean.class);
            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取功能点的列表异常", e);
        }
    }

    public List<AfterEightHourStat> statAfterEightHour(AfterEightHourAnalyseStatSearch sf) {
        if (sf.getChartType() == ChartType.PIE || sf.getChartType() == ChartType.BAR) {
            return this.statAfterEightHourForPie(sf);
        } else {
            return this.statAfterEightHourForScatter(sf);
        }
    }

    /**
     * 获取八小时外的统计
     *
     * @return
     */
    public List<AfterEightHourStat> statAfterEightHourForPie(AfterEightHourAnalyseStatSearch sf) {
        try {
            List<AfterEightHourStat> result = new ArrayList<>();
            Date endTime = DateUtil.getDate(DateUtil.getFormatDate(sf.getEndDate(), DateUtil.FormatDay1) + " 23:59:59", DateUtil.Format24Datetime);
            // 音乐练习
            String sql = "select mi.name as name,count(0) as totalCount,sum(minutes) as totalTime,'音乐' as groupName from music_practice mp,music_instrument mi ";
            sql += "where mp.music_instrument_id = mi.id and mp.user_id=?0 and mp.practice_date>=?1 and mp.practice_date<=?2 group by mi.name";
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.GROUP) {
                sql = "select groupName,sum(totalCount) as totalCount,sum(totalTime) as totalTime from (" + sql + ") as aaa ";
            }
            List<AfterEightHourStat> list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            // 锻炼
            sql = "select st.name as name,count(0) as totalCount,sum(se.minutes) as totalTime,'锻炼' as groupName from sport_exercise se,sport_type st ";
            sql += "where se.sport_type_id = st.id and se.user_id=?0 and se.exercise_date>=?1 and se.exercise_date<=?2 group by st.name";
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.GROUP) {
                sql = "select groupName,sum(totalCount) as totalCount,sum(totalTime) as totalTime from (" + sql + ") as aaa ";
            }
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            //阅读
            sql = "select '看书' as name,count(0) as totalCount,sum(minutes) as totalTime,'看书' as groupName from reading_record_detail where user_id=?0 ";
            sql += "and read_time>=?1 and read_time<=?2 ";
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.GROUP) {
                sql = "select groupName,sum(totalCount) as totalCount,sum(totalTime) as totalTime from (" + sql + ") as aaa ";
            }
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            // 通用
            sql = "select crt.name as name,count(0) as totalCount,sum(value) as totalTime,'其他' as groupName from common_record cr,common_record_type crt  ";
            sql += "where cr.common_record_type_id = crt.id and cr.user_id=?0 and crt.over_work_stat=1 and cr.occur_time>=?1 and cr.occur_time<=?2 group by crt.name";
            if (sf.getDataGroup() == AfterEightHourAnalyseStatSearch.DataGroup.GROUP) {
                sql = "select groupName,sum(totalCount) as totalCount,sum(totalTime) as totalTime from (" + sql + ") as aaa ";
            }
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);

            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取八小时外的统计异常", e);
        }
    }

    /**
     * 根据散点图统计
     *
     * @param sf
     * @return
     */
    public List<AfterEightHourStat> statAfterEightHourForScatter(AfterEightHourAnalyseStatSearch sf) {
        try {
            List<AfterEightHourStat> result = new ArrayList<>();
            Date endTime = DateUtil.getDate(DateUtil.getFormatDate(sf.getEndDate(), DateUtil.FormatDay1) + " 23:59:59", DateUtil.Format24Datetime);
            // 音乐练习
            String sql = "select mi.name as name,WEEKDAY(mp.practice_date)+1 as indexValue,count(0) as totalCount,sum(minutes) as totalTime from music_practice mp,music_instrument mi ";
            sql += "where mp.music_instrument_id = mi.id and mp.user_id=?0 and mp.practice_date>=?1 and mp.practice_date<=?2 group by mi.name,indexValue";
            List<AfterEightHourStat> list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            // 锻炼
            sql = "select st.name as name,WEEKDAY(se.exercise_date)+1 as indexValue,count(0) as totalCount,sum(se.minutes) as totalTime from sport_exercise se,sport_type st ";
            sql += "where se.sport_type_id = st.id and se.user_id=?0 and se.exercise_date>=?1 and se.exercise_date<=?2 group by st.name,indexValue";
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            //阅读
            sql = "select '阅读' as name,WEEKDAY(read_time)+1 as indexValue,count(0) as totalCount,sum(minutes) as totalTime from reading_record_detail where user_id=?0 ";
            sql += "and read_time>=?1 and read_time<=?2 group by indexValue";
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);
            // 通用
            sql = "select crt.name as name,WEEKDAY(cr.occur_time)+1 as indexValue,count(0) as totalCount,sum(value) as totalTime from common_record cr,common_record_type crt  ";
            sql += "where cr.common_record_type_id = crt.id and cr.user_id=?0 and crt.over_work_stat=1 and cr.occur_time>=?1 and cr.occur_time<=?2 group by crt.name,indexValue";
            list = this.getEntityListWithClassSQL(sql, -1, 0,
                    AfterEightHourStat.class, sf.getUserId(), sf.getStartDate(), endTime);
            result.addAll(list);

            return result;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取功能点的菜单列表异常", e);
        }
    }


    /**
     * 统计通用记录的基于时间的统计
     *
     * @param sf
     * @return
     */
    public List<CommonRecordDateStat> statDateCommonRecord(CommonRecordDateStatSearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            DateGroupType dateGroupType = sf.getDateGroupType();
            StringBuffer sb = new StringBuffer();
            sb.append("select indexValue,");
            sb.append("count(0) as totalCount, ");
            sb.append("sum(value) as totalValue ");
            sb.append("from (");
            sb.append("select" + MysqlUtil.dateTypeMethod("occur_time", dateGroupType) + "as indexValue,");
            sb.append("value from common_record ");
            sb.append(pr.getParameterString());
            sb.append(") tt group by indexValue ");
            sb.append(" order by indexValue");
            List<CommonRecordDateStat> list = this.getEntityListWithClassSQL(sb.toString(), pr.getPage(), pr.getPageSize(), CommonRecordDateStat.class, pr.getParameterValue());
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "通用记录的基于时间的统计异常", e);
        }
    }

    /**
     * 初始化用户数据
     *
     * @param userId
     */
    public void initUserData(Long userId) {
        initAccountData(userId);
        initBookCategoryData(userId);
        initBuyTypeData(userId);
        initCommonRecordTypeData(userId);
        initConsumeTypeData(userId);
        initGoodsTypeData(userId);
        initMusicInstrumentData(userId);
        initSportTypeData(userId);
        initUserBehaviorData(userId);
        initUserChartData(userId);
        initUserNotifyData(userId);
        initUserPlanData(userId);
        initUserReportConfigData(userId);
    }

    /**
     * 初始化账户
     *
     * @param userId
     */
    public void initAccountData(Long userId) {
        try {
            String hql = "from Account where userId=0";
            List<Account> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Account tm = list.get(i);
                    Account a = new Account();
                    a.setAmount(tm.getAmount());
                    a.setName(tm.getName());
                    a.setStatus(tm.getStatus());
                    a.setType(tm.getType());
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化账户异常", e);
        }
    }

    /**
     * 初始化图书分类
     *
     * @param userId
     */
    public void initBookCategoryData(Long userId) {
        try {
            String hql = "from BookCategory where userId=0";
            List<BookCategory> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    BookCategory tm = list.get(i);
                    BookCategory a = new BookCategory();
                    a.setName(tm.getName());
                    a.setOrderIndex(tm.getOrderIndex());
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化图书分类异常", e);
        }
    }

    /**
     * 初始化购买来源
     *
     * @param userId
     */
    public void initBuyTypeData(Long userId) {
        try {
            String hql = "from BuyType where userId=0 and status=1";
            List<BuyType> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    BuyType tm = list.get(i);
                    BuyType a = new BuyType();
                    a.setName(tm.getName());
                    a.setOrderIndex(tm.getOrderIndex());
                    a.setStatus(tm.getStatus());
                    a.setUserId(userId);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化购买来源异常", e);
        }
    }

    /**
     * 初始化通用记录类型
     *
     * @param userId
     */
    public void initCommonRecordTypeData(Long userId) {
        try {
            String hql = "from CommonRecordType where userId=0 and status=1";
            List<CommonRecordType> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    CommonRecordType tm = list.get(i);
                    CommonRecordType a = new CommonRecordType();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化通用记录类型异常", e);
        }
    }

    /**
     * 初始化人生经历的消费类型
     *
     * @param userId
     */
    public void initConsumeTypeData(Long userId) {
        try {
            String hql = "from ConsumeType where userId=0 and status=1";
            List<ConsumeType> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ConsumeType tm = list.get(i);
                    ConsumeType a = new ConsumeType();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化人生经历的消费类型异常", e);
        }
    }

    /**
     * 初始化商品类型
     *
     * @param userId
     */
    public void initGoodsTypeData(Long userId) {
        try {
            String hql = "from GoodsType where userId=0 and status=1";
            List<GoodsType> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    GoodsType tm = list.get(i);
                    GoodsType a = new GoodsType();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化商品类型异常", e);
        }
    }

    /**
     * 初始化乐器
     *
     * @param userId
     */
    public void initMusicInstrumentData(Long userId) {
        try {
            String hql = "from MusicInstrument where userId=0";
            List<MusicInstrument> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    MusicInstrument tm = list.get(i);
                    MusicInstrument a = new MusicInstrument();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化乐器异常", e);
        }
    }

    /**
     * 初始化运动类型
     *
     * @param userId
     */
    public void initSportTypeData(Long userId) {
        try {
            String hql = "from SportType where userId=0 and status=1";
            List<SportType> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    SportType tm = list.get(i);
                    SportType a = new SportType();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化运动类型异常", e);
        }
    }

    /**
     * 初始化用户行为
     *
     * @param userId
     */
    public void initUserBehaviorData(Long userId) {
        try {
            String hql = "from UserBehavior where userId=0 and status=1";
            List<UserBehavior> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    UserBehavior tm = list.get(i);
                    UserBehavior a = new UserBehavior();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化用户行为异常", e);
        }
    }

    /**
     * 初始化用户图表
     *
     * @param userId
     */
    public void initUserChartData(Long userId) {
        try {
            String hql = "from UserChart where userId=0 and status=1";
            List<UserChart> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    UserChart tm = list.get(i);
                    UserChart a = new UserChart();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    a.setId(null);
                    this.saveEntity(a);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化用户图表异常", e);
        }
    }

    /**
     * 初始化用户提醒
     *
     * @param userId
     */
    public void initUserNotifyData(Long userId) {
        try {
            String hql = "from UserNotify where userId=0 and status=1";
            List<UserNotify> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    UserNotify tm = list.get(i);
                    UserNotify a = new UserNotify();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    a.setId(null);
                    this.saveEntity(a);
                    //查询配置
                    String ss = "from UserNotifyRemind where userNotify.id=?0";
                    UserNotifyRemind retm = (UserNotifyRemind) this.getEntityForOne(ss, tm.getId());
                    UserNotifyRemind remind = new UserNotifyRemind();
                    BeanCopy.copyProperties(retm, remind);
                    remind.setId(null);
                    remind.setUserNotify(a);
                    remind.setLastRemindTime(null);
                    remind.setCreatedTime(new Date());
                    remind.setLastModifyTime(null);
                    this.saveEntity(remind);
                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化用户提醒异常", e);
        }
    }

    /**
     * 初始化用户计划
     *
     * @param userId
     */
    public void initUserPlanData(Long userId) {
        try {
            String hql = "from UserPlan where userId=0 and status=1";
            List<UserPlan> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    UserPlan tm = list.get(i);
                    UserPlan a = new UserPlan();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    a.setId(null);
                    userPlanService.saveUsePlan(a);
                }
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化用户计划异常", e);
        }
    }

    /**
     * 初始化用户报表
     *
     * @param userId
     */
    public void initUserReportConfigData(Long userId) {
        try {
            String hql = "from UserReportConfig where userId=0 and status=1";
            List<UserReportConfig> list = this.getEntityListNoPageHQL(hql);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    UserReportConfig tm = list.get(i);
                    UserReportConfig a = new UserReportConfig();
                    BeanCopy.copyProperties(tm, a);
                    a.setUserId(userId);
                    a.setCreatedTime(new Date());
                    a.setId(null);
                    this.saveEntity(a);
                    //查询配置
                    String ss = "from UserReportRemind where userReportConfig.id=?0";
                    UserReportRemind retm = (UserReportRemind) this.getEntityForOne(ss, tm.getId());
                    if (retm != null) {
                        UserReportRemind remind = new UserReportRemind();
                        BeanCopy.copyProperties(retm, remind);
                        remind.setId(null);
                        remind.setUserReportConfig(a);
                        remind.setLastRemindTime(null);
                        remind.setCreatedTime(new Date());
                        remind.setLastModifyTime(null);
                        this.saveEntity(remind);
                    }

                }

            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "初始化用户报表异常", e);
        }
    }

    /**
     * 获取用户行为模板配置树
     *
     * @param userLevel
     * @param status
     * @return
     */
    public List<TreeBean> getUserBehaviorConfigTree(Integer userLevel, CommonStatus status) {
        try {
            String hql = "from UserBehaviorConfig where 1=1 ";
            if (userLevel != null) {
                hql += " and level<=" + userLevel;
            }
            if (status != null) {
                hql += " and status=" + status.ordinal();
            }
            hql += " order by behaviorType ";
            List<UserBehaviorConfig> list = this.getEntityListNoPageHQL(hql);
            if (list.isEmpty()) {
                return new ArrayList<>();
            } else {
                List<TreeBean> result = new ArrayList<>();
                UserBehaviorType current = list.get(0).getBehaviorType();
                TreeBean currentTb = new TreeBean();
                currentTb.setId("P_" + current.name());
                currentTb.setText(current.getName());
                int n = list.size();
                for (int i = 0; i < n; i++) {
                    UserBehaviorConfig pc = list.get(i);
                    TreeBean tb = new TreeBean();
                    tb.setId(pc.getId().toString());
                    tb.setText(pc.getName());
                    if (pc.getBehaviorType() != current) {
                        result.add(currentTb);
                        current = pc.getBehaviorType();
                        currentTb = new TreeBean();
                        currentTb.setId("P_" + current.name());
                        currentTb.setText(current.getName());
                        currentTb.addChild(tb);
                    } else {
                        currentTb.addChild(tb);
                    }
                    if (i == n - 1) {
                        result.add(currentTb);
                    }
                }
                return result;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取用户行为模板配置树异常", e);
        }
    }

}
