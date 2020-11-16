package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.UserBehaviorConfig;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorConfigSearch;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorConfigTreeSearch;
import cn.mulanbay.pms.web.bean.request.userbehavior.UserBehaviorConfigFormRequest;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户行为分析配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userBehaviorConfig")
public class UserBehaviorConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserBehaviorConfigController.class);

    private static Class<UserBehaviorConfig> beanClass = UserBehaviorConfig.class;

    @Autowired
    DataService dataService;

    /**
     * 获取类型树
     *
     * @return
     */
    @RequestMapping(value = "/getUserBehaviorTypeTree")
    public ResultBean getUserBehaviorTypeTree(CommonTreeSearch sf) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (UserBehaviorType type : UserBehaviorType.values()) {
                TreeBean tb = new TreeBean();
                tb.setId(type.toString());
                tb.setText(type.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    /**
     * 行为配置树
     *
     * @return
     */
    @RequestMapping(value = "/getUserBehaviorConfigTree")
    public ResultBean getUserBehaviorConfigTree(UserBehaviorConfigTreeSearch sf) {
        try {
            List<TreeBean> result = dataService.getUserBehaviorConfigTree(null, null);
            return callback(TreeBeanUtil.addRoot(result, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取行为配置树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserBehaviorConfigSearch sf) {
        PageResult<UserBehaviorConfig> pr = getUserBehaviorConfigData(sf);
        return callbackDataGrid(pr);
    }

    private PageResult<UserBehaviorConfig> getUserBehaviorConfigData(UserBehaviorConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserBehaviorConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserBehaviorConfigFormRequest formRequest) {
        UserBehaviorConfig bean = new UserBehaviorConfig();
        BeanCopy.copyProperties(formRequest, bean);
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
        UserBehaviorConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserBehaviorConfigFormRequest formRequest) {
        UserBehaviorConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
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
        for (String s : deleteRequest.getIds().split(",")) {
            baseService.deleteObject(UserBehaviorConfig.class, Long.valueOf(s));
        }
        return callback(null);
    }

}
