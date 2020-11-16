package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.UserCalendarConfig;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.usercalendar.UserCalendarConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.usercalendar.UserCalendarConfigSearch;
import cn.mulanbay.pms.web.bean.request.usercalendar.UserCalendarConfigTreeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
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

/**
 * 用户日历配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userCalendarConfig")
public class UserCalendarConfigController extends BaseController {

    private static Class<UserCalendarConfig> beanClass = UserCalendarConfig.class;

    @Autowired
    UserCalendarService userCalendarService;

    /**
     * 用户日历配置树（筛选用户级别）
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getUserCalendarConfigForUserTree")
    public ResultBean getUserCalendarConfigForUserTree(UserCalendarConfigTreeSearch sf) {
        try {
            List<UserCalendarConfig> gtList = userCalendarService.getUserCalendarConfigList(sf.getLevel());
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (UserCalendarConfig gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户日历配置树异常",
                    e);
        }
    }

    /**
     * 用户日历配置树
     *
     * @return
     */
    @RequestMapping(value = "/getUserCalendarConfigTree")
    public ResultBean getUserCalendarConfigTree(Boolean needRoot) {

        try {
            UserCalendarConfigSearch ncSearch = new UserCalendarConfigSearch();
            ncSearch.setPage(PageRequest.NO_PAGE);
            PageResult<UserCalendarConfig> pr = getConfigData(ncSearch);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (UserCalendarConfig gt : pr.getBeanList()) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
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
    public ResultBean getData(UserCalendarConfigSearch sf) {
        return callbackDataGrid(getConfigData(sf));
    }

    private PageResult<UserCalendarConfig> getConfigData(UserCalendarConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserCalendarConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserCalendarConfigFormRequest formRequest) {
        UserCalendarConfig bean = new UserCalendarConfig();
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
        UserCalendarConfig br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserCalendarConfigFormRequest formRequest) {
        UserCalendarConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 删除
     * todo 该功能其实不能提供，即使提供也要级联删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(UserCalendarConfig.class, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

}
