package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.persistent.domain.ReportConfig;
import cn.mulanbay.pms.persistent.domain.UserReportConfig;
import cn.mulanbay.pms.persistent.domain.UserReportRemind;
import cn.mulanbay.pms.persistent.service.ReportService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.report.UserReportConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.report.UserReportConfigSearch;
import cn.mulanbay.pms.web.bean.request.report.UserReportRemindFormRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 用户报表配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userReportConfig")
public class UserReportConfigController extends BaseController {

    private static Class<UserReportConfig> beanClass = UserReportConfig.class;

    @Autowired
    ReportService reportService;

    @Autowired
    CacheHandler cacheHandler;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserReportConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserReportConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserReportConfigFormRequest formRequest) {
        UserReportConfig bean = new UserReportConfig();
        BeanCopy.copyProperties(formRequest, bean);
        ReportConfig reportConfig = reportService.getReportConfig(formRequest.getReportConfigId(), formRequest.getLevel());
        if (reportConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setReportConfig(reportConfig);
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
        UserReportConfig bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserReportConfigFormRequest formRequest) {
        UserReportConfig bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        ReportConfig reportConfig = reportService.getReportConfig(formRequest.getReportConfigId(), formRequest.getLevel());
        if (reportConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setReportConfig(reportConfig);
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
     * 获取提醒篇日志
     *
     * @return
     */
    @RequestMapping(value = "/getRemind", method = RequestMethod.GET)
    public ResultBean getRemind(@Valid CommonBeanGetRequest getRequest) {
        UserReportRemind userReportRemind = reportService.getUserReportRemind(getRequest.getId(), getRequest.getUserId());
        return callback(userReportRemind);
    }

    /**
     * 增加/修改用户报表提醒配置
     *
     * @return
     */
    @RequestMapping(value = "/addOrEditRemind", method = RequestMethod.POST)
    public ResultBean addOrEditRemind(@RequestBody @Valid UserReportRemindFormRequest formRequest) {
        UserReportRemind bean = null;
        if (formRequest.getId() != null) {
            bean = this.getUserEntity(UserReportRemind.class, formRequest.getId(), formRequest.getUserId());
            BeanCopy.copyProperties(formRequest, bean);
            UserReportConfig userReportConfig = this.getUserEntity(UserReportConfig.class, formRequest.getUserReportConfigId(), formRequest.getUserId());
            bean.setUserReportConfig(userReportConfig);
            bean.setLastModifyTime(new Date());
            baseService.updateObject(bean);
            //只要修改过重新开始计算提醒
            cacheHandler.delete("userReportConfig:" + this.getCurrentUserId() + ":" + bean.getUserReportConfig().getId());
        } else {
            bean = new UserReportRemind();
            BeanCopy.copyProperties(formRequest, bean);
            UserReportConfig userReportConfig = this.getUserEntity(UserReportConfig.class, formRequest.getUserReportConfigId(), formRequest.getUserId());
            bean.setUserReportConfig(userReportConfig);
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        }
        return callback(null);
    }

}
