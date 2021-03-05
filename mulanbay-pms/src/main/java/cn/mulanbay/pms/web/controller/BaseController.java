package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.MessageHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.ValidateError;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.Constant;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.TokenHandler;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.pms.web.bean.request.BaseYoyStatSearch;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.pms.web.bean.response.DataGrid;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ScatterChartData;
import cn.mulanbay.pms.web.bean.response.chart.ScatterChartDetailData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller的基类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected BaseService baseService;

    @Autowired
    protected MessageHandler messageHandler;

    @Autowired
    protected TokenHandler tokenHandler;

    @Autowired
    CacheHandler cacheHandler;

    /**
     * 统计图里面的子标题是否需要总的统计值
     */
    @Value("${system.chart.subTitle.hasTotal}")
    boolean chartSubTitleHasTotal;

    private static ResultBean defaultResultBean = new ResultBean();

    private static List emptyList = new ArrayList<>();

    /**
     * 获取当前用户的编号
     * 一般来说，当前用户编号都是在类ControllerHandler里设置，无需再各个controller手动获取
     * @see cn.mulanbay.pms.web.aop.ControllerHandler
     * todo 如果在jwt中保存当前用户编号，就可以不用再一次去redis里获取LoginUser再拿用户编号
     * @return
     */
    protected Long getCurrentUserId() {
        LoginUser lu = tokenHandler.getLoginUser(request);
        return lu == null ? null : lu.getUserId();
    }

    /**
     * 操作实体并且设置ID值，主要提供给操作日志使用
     * 目前针对新增
     *
     * @param id 需要保存的hibernate实体对象的ID值
     */
    protected void setOperateBeanId(Object id) {
        if (id != null) {
            String cacheKey = CacheKey.getKey(CacheKey.USER_OPERATE_OP, request.getRequestedSessionId(), request.getServletPath());
            cacheHandler.set(cacheKey, id.toString(), 300);
        }
    }

    /**
     * 跟easyui结合
     *
     * @param pr
     * @return
     */
    protected ResultBean callbackDataGrid(PageResult<?> pr) {
        ResultBean rb = new ResultBean();
        DataGrid dg = new DataGrid();
        dg.setPage(pr.getPage());
        dg.setTotal(pr.getMaxRow());
        dg.setRows(pr.getBeanList() == null ? emptyList : pr.getBeanList());
        rb.setData(dg);
        return rb;
    }


    protected ResultBean callback(Object o) {
        if (o == null) {
            return defaultResultBean;
        }
        ResultBean rb = new ResultBean();
        rb.setData(o);
        return rb;
    }

    /**
     * 直接返回错误代码
     *
     * @param errorCode
     * @return
     */
    protected ResultBean callbackErrorCode(int errorCode) {
        ResultBean rb = new ResultBean();
        rb.setCode(errorCode);
        ValidateError ve = messageHandler.getErrorCodeInfo(errorCode);
        rb.setMessage(ve.getErrorInfo());
        return rb;
    }

    /**
     * 直接返回错误信息
     *
     * @param msg
     * @return
     */
    protected ResultBean callbackErrorInfo(String msg) {
        ResultBean rb = new ResultBean();
        rb.setCode(ErrorCode.DO_BUSS_ERROR);
        rb.setMessage(msg);
        return rb;
    }


    protected String getIpAddress() {
        return IPAddressUtil.getIpAddress(request);
    }

    /**
     * 获取日期的标题，只要用于报表的子标题
     *
     * @param sf
     * @return
     */
    protected String getDateTitle(DateStatSearch sf) {
        if (sf.getStartDate() == null && sf.getEndDate() == null) {
            return "";
        } else if (sf.getStartDate() != null && sf.getEndDate() == null) {
            return "从" + DateUtil.getFormatDate(sf.getStartDate(), DateUtil.FormatDay1) + "开始";
        } else if (sf.getStartDate() == null && sf.getEndDate() != null) {
            return "截止" + DateUtil.getFormatDate(sf.getEndDate(), DateUtil.FormatDay1);
        } else {
            return DateUtil.getFormatDate(sf.getStartDate(), DateUtil.FormatDay1) + "~" +
                    DateUtil.getFormatDate(sf.getEndDate(), DateUtil.FormatDay1);
        }
    }

    /**
     * 获取日期的标题，只要用于报表的子标题
     *
     * @param sf
     * @param total
     * @return
     */
    protected String getDateTitle(DateStatSearch sf, String total) {
        String dateTitle = this.getDateTitle(sf);
        if (chartSubTitleHasTotal) {
            if (!dateTitle.isEmpty()) {
                dateTitle += "\n";
            }
            dateTitle += "总共:" + total;
        }
        return dateTitle;
    }

    /**
     * 初始化同期对比数据
     *
     * @param sf
     * @param title
     * @param subTitle
     * @return
     */
    protected ChartData initYoyCharData(BaseYoyStatSearch sf, String title, String subTitle) {
        ChartData chartData = new ChartData();
        chartData.setTitle(title);
        chartData.setSubTitle(subTitle);
        if (sf.getDateGroupType() == DateGroupType.MONTH) {
            for (int i = 1; i <= Constant.MAX_MONTH; i++) {
                chartData.getIntXData().add(i);
                chartData.getXdata().add(i + "月份");
            }
        } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
            for (int i = 1; i <= Constant.MAX_WEEK; i++) {
                chartData.getIntXData().add(i);
                chartData.getXdata().add("第" + i + "周");
            }
        }
        return chartData;
    }

    /**
     * 获取用户的数据
     * 查找实体时id和userId同时绑定
     * todo 后期可以根据当前用户身份，如果是管理员则直接根据id查询
     *
     * @param c
     * @param id
     * @param userId
     * @param <T>
     * @return
     */
    protected <T> T getUserEntity(Class<T> c, Serializable id, Long userId) {
        T bean = baseService.getObjectWithUser(c, id, userId);
        if (bean == null) {
            // 找不到直接抛异常
            throw new ApplicationException(PmsErrorCode.USER_ENTITY_NOT_FOUND);
        }
        return bean;
    }

    /**
     * 删除用户数据
     *
     * @param c
     * @param ids
     * @param userId
     */
    protected void deleteUserEntity(Class c, Serializable[] ids, Long userId) {
        baseService.deleteObjectsWithUser(c, ids, userId);
    }

    /**
     * 删除用户数据
     *
     * @param c
     * @param strIds
     * @param userId
     */
    protected void deleteUserEntity(Class c, String strIds,Class idClass, Long userId) {
        baseService.deleteObjectsWithUser(c,strIds,idClass,userId);
    }


    /**
     * 获取时间区间
     *
     * @param dateGroupType
     * @param date
     * @return
     */
    protected Date[] getStatDateRange(DateGroupType dateGroupType, Date date) {
        Date[] dd = new Date[2];
        if (dateGroupType == DateGroupType.DAY) {
            dd[0] = DateUtil.getFromMiddleNightDate(date);
            dd[1] = DateUtil.getTodayTillMiddleNightDate(date);
        } else if (dateGroupType == DateGroupType.MONTH) {
            dd[0] = DateUtil.getFromMiddleNightDate(DateUtil.getFirstDayOfMonth(date));
            Date endDate = DateUtil.getLastDayOfMonth(date);
            dd[1] = DateUtil.getTodayTillMiddleNightDate(endDate);
        } else {
            int year = Integer.valueOf(DateUtil.getFormatDate(date, "yyyy"));
            dd[0] = DateUtil.getDate(year + "-01-01 00:00:00", DateUtil.Format24Datetime);
            dd[1] = DateUtil.getDate(year + "-12-31 23:59:59", DateUtil.Format24Datetime);
        }
        return dd;
    }

    /**
     * 生成基于时分统计的散点图形数据
     * @param dateList
     * @param title
     * @param name
     * @return
     */
    protected ScatterChartData createHMChartData(List<Date> dateList ,String title,String name){
        ScatterChartData chartData = new ScatterChartData();
        chartData.setTitle(title);
        chartData.setxUnit("");
        chartData.setyUnit("点");
        chartData.addLegent(name);
        ScatterChartDetailData detailData = new ScatterChartDetailData();
        detailData.setName(name);
        double totalX = 0;
        int n = 0;
        for (Date date : dateList) {
            String x = DateUtil.getFormatDate(date,"yyyyMMdd");
            String y = DateUtil.getFormatDate(date,"HH.mm");
            detailData.addData(new Object[]{x, y});
            totalX += Double.valueOf(y);
            n++;
        }
        detailData.setxAxisAverage(totalX / n);
        chartData.addSeriesData(detailData);
        return chartData;
    }
}
