package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.cache.MCache;
import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.MLConstant;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.ReportHandler;
import cn.mulanbay.pms.handler.UserScoreHandler;
import cn.mulanbay.pms.persistent.domain.PlanConfig;
import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanRemind;
import cn.mulanbay.pms.persistent.enums.PlanReportDataStatFilterType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.pms.persistent.service.PlanService;
import cn.mulanbay.pms.persistent.service.UserPlanService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanFormRequest;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanRemindFormRequest;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanSearch;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanTreeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.plan.PlanReportPredictVo;
import cn.mulanbay.pms.web.bean.response.plan.UserPlanVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户计划
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userPlan")
public class UserPlanController extends BaseController {


    private static Class<UserPlan> beanClass = UserPlan.class;

    @Autowired
    UserPlanService userPlanService;

    @Autowired
    PlanService planService;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    ReportHandler reportHandler;

    @Autowired
    UserScoreHandler userScoreHandler;
    /**
     * 用户计划树
     *
     * @return
     */
    @RequestMapping(value = "/getUserPlanTree")
    public ResultBean getUserPlanTree(UserPlanTreeSearch sf) {
        try {
            // 用户模板
            List<UserPlan> list = planService.getUserPlanList(sf.getUserId(), sf.getRelatedBeans(), sf.getPlanType());
            if (list.isEmpty()) {
                return callback(TreeBeanUtil.addRoot(new ArrayList<>(), sf.getNeedRoot()));
            }
            List<TreeBean> result = new ArrayList<>();
            PlanType current = list.get(0).getPlanConfig().getPlanType();
            TreeBean typeTreeBean = new TreeBean();
            //父节点不设置值，导致treeset的显示有点问题
            typeTreeBean.setId(current.name());
            typeTreeBean.setText(current.getName());
            int n = list.size();
            for (int i = 0; i < n; i++) {
                UserPlan pc = list.get(i);
                if (pc.getPlanConfig().getPlanType() == current) {
                    TreeBean tb = new TreeBean();
                    tb.setId(pc.getId().toString());
                    tb.setText(pc.getTitle());
                    typeTreeBean.addChild(tb);
                }
                if (pc.getPlanConfig().getPlanType() != current) {
                    current = pc.getPlanConfig().getPlanType();
                    result.add(typeTreeBean);
                    typeTreeBean = new TreeBean();
                    typeTreeBean.setId(current.name());
                    typeTreeBean.setText(current.getName());
                    TreeBean tb = new TreeBean();
                    tb.setId(pc.getId().toString());
                    tb.setText(pc.getTitle());
                    typeTreeBean.addChild(tb);
                }
                if (i == n - 1) {
                    //最后一个
                    result.add(typeTreeBean);
                }
            }
            return callback(TreeBeanUtil.addRoot(result, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户计划树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserPlanSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserPlan> qr = baseService.getBeanResult(pr);
        Boolean predict = sf.getPredict();
        if (sf.getStatNow() != null && sf.getStatNow()) {
            //需要统计当前的报表
            Date now = new Date();
            long userId = sf.getUserId();
            PageResult<UserPlanVo> result = new PageResult<>();
            List<UserPlanVo> list = new ArrayList<>();
            for (UserPlan pc : qr.getBeanList()) {
                UserPlanVo vo = new UserPlanVo();
                BeanCopy.copyProperties(pc, vo);
                //设置PlanReport
                PlanReport report = planService.statPlanReport(pc, now, userId, sf.getFilterType());
                vo.setPlanReport(report);
                if(predict){
                    Map<String,Float> pv = reportHandler.predictPlanReport(report);
                    if(pv!=null){
                        vo.setPredictCount(pv.get(MLConstant.PLAN_REPORT_COUNT_LABEL)*report.getPlanCountValue());
                        vo.setPredictValue(pv.get(MLConstant.PLAN_REPORT_VALUE_LABEL)*report.getPlanValue());
                    }
                }
                list.add(vo);
            }
            result.setBeanList(list);
            result.setMaxRow(qr.getMaxRow());
            result.setPageSize(pr.getPageSize());
            return callbackDataGrid(result);
        } else {
            return callbackDataGrid(qr);

        }
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserPlanFormRequest formRequest) {
        UserPlan bean = new UserPlan();
        BeanCopy.copyProperties(formRequest, bean);
        PlanConfig planConfig = planService.getPlanConfig(formRequest.getPlanConfigId(), formRequest.getLevel());
        if (planConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setPlanConfig(planConfig);
        userPlanService.saveUsePlan(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserPlan bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/getStat", method = RequestMethod.GET)
    //@MCache(key = "'userPlan:stat:'+ #cbr.userId+':'+ #cbr.id+':'+ #cbr.predict")
    public ResultBean getStat(@Valid CommonBeanGetRequest cbr) {
        UserPlan userPlan = this.getUserEntity(beanClass, cbr.getId(), cbr.getUserId());
        PlanReport planReport = planService.statPlanReport(userPlan, new Date(), cbr.getUserId(), PlanReportDataStatFilterType.ORIGINAL);
        Boolean predict = cbr.getPredict();
        if(predict){
            PlanReportPredictVo vo = reportHandler.predictAndSetPlanReport(planReport);
            return callback(vo);
        }else{
            return callback(planReport);
        }
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserPlanFormRequest formRequest) {
        UserPlan bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        PlanConfig planConfig = planService.getPlanConfig(formRequest.getPlanConfigId(), formRequest.getLevel());
        if (planConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setPlanConfig(planConfig);
        bean.setLastModifyTime(new Date());
        userPlanService.updateUsePlan(bean);
        return callback(bean);
    }


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        String[] ss = deleteRequest.getIds().split(",");
        for (String s : ss) {
            UserPlan bean = this.getUserEntity(beanClass, Long.valueOf(s), deleteRequest.getUserId());
            userPlanService.deleteUsePlan(bean);
        }
        return callback(null);
    }


    /**
     * 提醒配置
     *
     * @return
     */
    @RequestMapping(value = "/getRemind", method = RequestMethod.GET)
    public ResultBean getRemind(@Valid CommonBeanGetRequest getRequest) {
        UserPlanRemind userPlanRemind = userPlanService.getRemindByUserPlan(getRequest.getId(), getRequest.getUserId());
        return callback(userPlanRemind);
    }

    /**
     * 增加/修改用户计划提醒
     *
     * @return
     */
    @RequestMapping(value = "/addOrEditRemind", method = RequestMethod.POST)
    public ResultBean addOrEditRemind(@RequestBody @Valid UserPlanRemindFormRequest formRequest) {
        UserPlanRemind bean = null;
        if (formRequest.getId() != null) {
            bean = this.getUserEntity(UserPlanRemind.class, formRequest.getId(), formRequest.getUserId());
            BeanCopy.copyProperties(formRequest, bean);
            UserPlan userPlan = this.getUserEntity(beanClass, formRequest.getUserPlanId(), formRequest.getUserId());
            bean.setUserPlan(userPlan);
            bean.setLastModifyTime(new Date());
            baseService.updateObject(bean);
            //只要修改过重新开始计算提醒
            String key = CacheKey.getKey(CacheKey.USER_PLAN_NOTIFY, formRequest.getUserId().toString(), bean.getUserPlan().getId().toString());
            cacheHandler.delete(key);
        } else {
            bean = new UserPlanRemind();
            BeanCopy.copyProperties(formRequest, bean);
            UserPlan userPlan = this.getUserEntity(beanClass, formRequest.getUserPlanId(), formRequest.getUserId());
            bean.setUserPlan(userPlan);
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        }
        return callback(null);
    }

}
