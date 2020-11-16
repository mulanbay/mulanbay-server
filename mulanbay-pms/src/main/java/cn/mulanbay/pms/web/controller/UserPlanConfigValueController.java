package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanConfigValue;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanConfigValueFormRequest;
import cn.mulanbay.pms.web.bean.request.plan.UserPlanConfigValueSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 计划配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userPlanConfigValue")
public class UserPlanConfigValueController extends BaseController {

    private static Class<UserPlanConfigValue> beanClass = UserPlanConfigValue.class;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserPlanConfigValueSearch sf) {
        return callbackDataGrid(getUserPlanConfigValueData(sf));
    }

    private PageResult<UserPlanConfigValue> getUserPlanConfigValueData(UserPlanConfigValueSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("year", Sort.DESC);
        pr.addSort(s);
        PageResult<UserPlanConfigValue> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserPlanConfigValueFormRequest formRequest) {
        UserPlanConfigValue bean = new UserPlanConfigValue();
        BeanCopy.copyProperties(formRequest, bean);
        UserPlan userPlan = this.getUserEntity(UserPlan.class, formRequest.getUserPlanId(), formRequest.getUserId());
        bean.setUserPlan(userPlan);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserPlanConfigValue bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserPlanConfigValueFormRequest formRequest) {
        UserPlanConfigValue bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        UserPlan userPlan = this.getUserEntity(UserPlan.class, formRequest.getUserPlanId(), formRequest.getUserId());
        bean.setUserPlan(userPlan);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
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
}
