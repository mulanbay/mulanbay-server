package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.ReportConfig;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.report.ReportConfigForUserTreeSearch;
import cn.mulanbay.pms.web.bean.request.report.ReportConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.report.ReportConfigSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/reportConfig")
public class ReportConfigController extends BaseController {

    private static Class<ReportConfig> beanClass = ReportConfig.class;

    /**
     * 为锻炼管理界面的下拉菜单使用
     *
     * @return
     */
    @RequestMapping(value = "/getReportConfigForUserTree")
    public ResultBean getReportConfigForUserTree(ReportConfigForUserTreeSearch treeSf) {

        try {
            ReportConfigSearch sf = new ReportConfigSearch();
            sf.setPage(PageRequest.NO_PAGE);
            sf.setLevel(treeSf.getLevel());
            List<TreeBean> list = this.createReportConfigTree(sf);
            return callback(TreeBeanUtil.addRoot(list, treeSf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    /**
     * 为锻炼管理界面的下拉菜单使用
     *
     * @return
     */
    @RequestMapping(value = "/getReportConfigTree")
    public ResultBean getReportConfigTree(Boolean needRoot) {

        try {
            ReportConfigSearch sf = new ReportConfigSearch();
            sf.setPage(PageRequest.NO_PAGE);
            List<TreeBean> list = this.createReportConfigTree(sf);
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    private List<TreeBean> createReportConfigTree(ReportConfigSearch sf) {
        PageResult<ReportConfig> pr = getReportConfigData(sf);
        List<TreeBean> list = new ArrayList<TreeBean>();
        List<ReportConfig> gtList = pr.getBeanList();
        for (ReportConfig gt : gtList) {
            TreeBean tb = new TreeBean();
            tb.setId(gt.getId().toString());
            tb.setText(gt.getName());
            list.add(tb);
        }
        return list;
    }

    /**
     * 获取加班记录列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ReportConfigSearch sf) {
        return callbackDataGrid(getReportConfigData(sf));
    }

    private PageResult<ReportConfig> getReportConfigData(ReportConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<ReportConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ReportConfigFormRequest formRequest) {
        ReportConfig bean = new ReportConfig();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        bean.setResultColumns(calculateResultColumns(bean));
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        ReportConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ReportConfigFormRequest formRequest) {
        ReportConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        bean.setResultColumns(calculateResultColumns(bean));
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 计算返回列的数量
     *
     * @param bean
     * @return
     */
    private int calculateResultColumns(ReportConfig bean) {
        //先获取第一个select与from之间的字符，根据逗号分析数量
        String sqlContent = bean.getSqlContent();
        int first = sqlContent.indexOf("select");
        int last = sqlContent.indexOf("from");
        String column = sqlContent.substring(first, last);
        //如果没有一个逗号，说明是1个
        int n = StringUtil.countChar(column, ",") + 1;
        int m = StringUtil.countChar(bean.getResultTemplate(), "}");
        if (n != m) {
            //抛异常
            throw new ApplicationException(PmsErrorCode.REPORT_CONFIG_SQL_ERROR,
                    "配置错误，SQL的返回结果数量=" + n + "，而返回结果模板的配置数量=" + m);
        }
        //再验证与resultTemplate比对数量是否一致
        return n;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(ReportConfig.class, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

}
