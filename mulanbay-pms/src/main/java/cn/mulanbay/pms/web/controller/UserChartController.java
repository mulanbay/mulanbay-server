package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.ChartConfig;
import cn.mulanbay.pms.persistent.domain.UserChart;
import cn.mulanbay.pms.persistent.enums.BussType;
import cn.mulanbay.pms.persistent.service.ChartService;
import cn.mulanbay.pms.persistent.util.MysqlUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.chart.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.UserChartParaGetResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 用户图表
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userChart")
public class UserChartController extends BaseController {

    private static Class<UserChart> beanClass = UserChart.class;

    @Autowired
    ChartService chartService;

    private Map<String, List<UserChart>> changeToUserChartMap(List<UserChart> gtList) {
        Map<String, List<UserChart>> map = new TreeMap<>();
        for (UserChart nc : gtList) {
            List<UserChart> list = map.get(nc.getChartConfig().getRelatedBeans());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(nc);
            map.put(nc.getChartConfig().getRelatedBeans(), list);
        }
        return map;
    }


    /**
     * 获取用户图表树
     *
     * @return
     */
    @RequestMapping(value = "/getUserChartTree")
    public ResultBean getUserChartTree(UserChartTreeSearch sf) {
        try {
            //给页面做上一个下一个使用
            List<Long> usIds = new ArrayList<>();
            List<TreeBean> list = new ArrayList<TreeBean>();
            UserChartSearch uns = new UserChartSearch();
            BeanCopy.copyProperties(sf, uns);
            uns.setPage(PageRequest.NO_PAGE);
            PageRequest pr = uns.buildQuery();
            pr.setBeanClass(beanClass);
            List<UserChart> unList = baseService.getBeanList(pr);
            Map<String, List<UserChart>> map = this.changeToUserChartMap(unList);
            for (String key : map.keySet()) {
                TreeBean tb = new TreeBean();
                BussType bt = BussType.getBussType(key);
                tb.setId("P_" + bt.name());
                if (bt == null) {
                    tb.setText("未分类");
                } else {
                    tb.setText(bt.getName());
                }
                List<UserChart> ll = map.get(key);
                for (UserChart nc : ll) {
                    TreeBean child = new TreeBean();
                    child.setId(nc.getId().toString());
                    child.setText(nc.getTitle());
                    tb.addChild(child);
                    usIds.add(nc.getId());
                }
                list.add(tb);
            }
            Map res = new HashMap();
            res.put("treeData", TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
            res.put("treeIds", usIds);
            return callback(res);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户图表树异常",
                    e);
        }
    }

    /**
     * 获取用户图表树（简单）
     *
     * @return
     */
    @RequestMapping(value = "/getUserChartTreeSm")
    public ResultBean getUserChartTreeSm(UserChartTreeSearch sf) {
        try {
            //给页面做上一个下一个使用
            List<Long> usIds = new ArrayList<>();
            List<TreeBean> list = new ArrayList<TreeBean>();
            UserChartSearch uns = new UserChartSearch();
            BeanCopy.copyProperties(sf, uns);
            uns.setPage(-1);
            PageRequest pr = uns.buildQuery();
            pr.setBeanClass(beanClass);
            List<UserChart> unList = baseService.getBeanList(pr);
            for (UserChart nc : unList) {
                TreeBean child = new TreeBean();
                child.setId(nc.getId().toString());
                child.setText(nc.getTitle());
                list.add(child);
                usIds.add(nc.getId());
            }
            Map res = new HashMap();
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserChartSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserChart> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserChartFormRequest formRequest) {
        UserChart bean = new UserChart();
        BeanCopy.copyProperties(formRequest, bean);
        ChartConfig chartConfig = chartService.getChartConfig(formRequest.getChartConfigId(), formRequest.getLevel());
        if (chartConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setChartConfig(chartConfig);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserChart bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserChartFormRequest formRequest) {
        UserChart bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        ChartConfig chartConfig = chartService.getChartConfig(formRequest.getChartConfigId(), formRequest.getLevel());
        if (chartConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setChartConfig(chartConfig);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 在首页显示的图表ID
     *
     * @return
     */
    @RequestMapping(value = "/getShowIndexChart", method = RequestMethod.GET)
    public ResultBean getShowIndexChart(@Valid UserChartShowIndexSearch sis) {
        UserChart userChart = chartService.getShowIndexChart(sis.getUserId(), sis.getIndex());
        return callback(userChart);
    }

    /**
     * 查询参数
     *
     * @return
     */
    @RequestMapping(value = "/getChartPara", method = RequestMethod.GET)
    public ResultBean getChartPara(@Valid UserChartParaGetRequest getRequest) {
        UserChart bean = this.getUserEntity(beanClass, getRequest.getUserChartId(), getRequest.getUserId());
        UserChartParaGetResponse res = new UserChartParaGetResponse();
        res.setChartType(bean.getChartConfig().getChartType());
        res.setTitle(bean.getTitle());
        res.setUrl(bean.getChartConfig().getUrl());
        res.setDetailUrl(bean.getChartConfig().getDetailUrl());
        String startDate = DateUtil.getFormatDate(getRequest.getStartDate(), DateUtil.FormatDay1);
        String endDate = DateUtil.getFormatDate(getRequest.getEndDate(), DateUtil.FormatDay1);
        String allBindValues = startDate + "," + endDate;
        //写模板的参数时，开始时间和结束时间的占位符必须是{0},{1}
        if (StringUtil.isNotEmpty(bean.getBindValues())) {
            allBindValues += "," + bean.getBindValues();
        }
        String pp = MysqlUtil.replaceBindValues2(bean.getChartConfig().getPara(), allBindValues);
        res.setPara(pp);
        res.setBindValues(allBindValues);
        res.setSupportDate(bean.getChartConfig().getSupportDate());
        return callback(res);
    }

}
