package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.NotifyStatHandler;
import cn.mulanbay.pms.persistent.domain.NotifyConfig;
import cn.mulanbay.pms.persistent.domain.UserNotify;
import cn.mulanbay.pms.persistent.domain.UserNotifyRemind;
import cn.mulanbay.pms.persistent.dto.NotifyResult;
import cn.mulanbay.pms.persistent.enums.BussType;
import cn.mulanbay.pms.persistent.enums.NotifyType;
import cn.mulanbay.pms.persistent.service.NotifyService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifyFormRequest;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifyRemindFormRequest;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifySearch;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifyTreeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 用户提醒数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userNotify")
public class UserNotifyController extends BaseController {


    private static Class<UserNotify> beanClass = UserNotify.class;

    @Autowired
    NotifyService notifyService;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    NotifyStatHandler notifyStatHandler;

    private Map<String, List<UserNotify>> changeToUserNotifyMap(List<UserNotify> gtList) {
        Map<String, List<UserNotify>> map = new TreeMap<>();
        for (UserNotify nc : gtList) {
            List<UserNotify> list = map.get(nc.getNotifyConfig().getRelatedBeans());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(nc);
            map.put(nc.getNotifyConfig().getRelatedBeans(), list);
        }
        return map;
    }


    /**
     * 获取用户提醒列表树
     *
     * @return
     */
    @RequestMapping(value = "/getUserNotifyTree")
    public ResultBean getUserNotifyTree(UserNotifyTreeSearch sf) {

        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            UserNotifySearch uns = new UserNotifySearch();
            BeanCopy.copyProperties(sf, uns);
            uns.setPage(-1);
            PageRequest pr = uns.buildQuery();
            pr.setBeanClass(beanClass);
            List<UserNotify> unList = baseService.getBeanList(pr);
            Map<String, List<UserNotify>> map = this.changeToUserNotifyMap(unList);
            for (String key : map.keySet()) {
                TreeBean tb = new TreeBean();
                BussType bt = BussType.getBussType(key);
                tb.setId("P_" + bt.name());
                if (bt == null) {
                    tb.setText("未分类");
                } else {
                    tb.setText(bt.getName());
                }
                List<UserNotify> ll = map.get(key);
                for (UserNotify nc : ll) {
                    TreeBean child = new TreeBean();
                    child.setId(nc.getId().toString());
                    child.setText(nc.getTitle());
                    tb.addChild(child);
                }
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户提醒列表树异常",
                    e);
        }
    }

    /**
     * 获取用户提醒列表树
     *
     * @return
     */
    @RequestMapping(value = "/getUserNotifyTreeNm")
    public ResultBean getUserNotifyTreeNm(UserNotifyTreeSearch sf) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            UserNotifySearch uns = new UserNotifySearch();
            BeanCopy.copyProperties(sf, uns);
            uns.setPage(-1);
            PageRequest pr = uns.buildQuery();
            pr.setBeanClass(beanClass);
            List<UserNotify> unList = baseService.getBeanList(pr);
            for (UserNotify nc : unList) {
                TreeBean child = new TreeBean();
                child.setId(nc.getId().toString());
                child.setText(nc.getTitle());
                list.add(child);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户提醒列表树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserNotifySearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserNotify> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserNotifyFormRequest formRequest) {
        UserNotify bean = new UserNotify();
        BeanCopy.copyProperties(formRequest, bean);
        NotifyConfig notifyConfig = notifyService.getNotifyConfig(formRequest.getNotifyConfigId(), formRequest.getLevel());
        if (notifyConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setNotifyConfig(notifyConfig);
        checkUserNotify(bean);
        bean.setCreatedTime(new Date());
        notifyService.saveOrUpdateUserNotify(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserNotify bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/getStat", method = RequestMethod.GET)
    public ResultBean getStat(@Valid CommonBeanGetRequest getRequest) {
        UserNotify bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        NotifyResult notifyResult = notifyService.getNotifyResult(bean, getRequest.getUserId());
        return callback(notifyResult);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserNotifyFormRequest formRequest) {
        UserNotify bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        NotifyConfig notifyConfig = notifyService.getNotifyConfig(formRequest.getNotifyConfigId(), formRequest.getLevel());
        if (notifyConfig == null) {
            return callbackErrorCode(PmsErrorCode.USER_ENTITY_NOT_ALLOWED);
        }
        bean.setNotifyConfig(notifyConfig);
        checkUserNotify(bean);
        bean.setLastModifyTime(new Date());
        notifyService.saveOrUpdateUserNotify(bean);
        return callback(bean);
    }

    private void checkUserNotify(UserNotify userNotify) {
        if (userNotify.getNotifyConfig().getNotifyType() == NotifyType.LESS) {
            //小于类型
            if (userNotify.getWarningValue() > userNotify.getAlertValue()) {
                throw new ApplicationException(PmsErrorCode.NOTIFY_WARNING_VALUE_LESS_ALERT);
            }
        } else {
            //大于类型
            if (userNotify.getWarningValue() < userNotify.getAlertValue()) {
                throw new ApplicationException(PmsErrorCode.NOTIFY_WARNING_VALUE_LESS_ALERT);
            }
        }
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
     * 获取提醒设置
     * 这里的id为UserNotify的ID
     *
     * @return
     */
    @RequestMapping(value = "/getRemind", method = RequestMethod.GET)
    public ResultBean getRemind(@Valid CommonBeanGetRequest getRequest) {
        UserNotifyRemind userNotifyRemind = notifyService.getUserNotifyRemind(getRequest.getId(), getRequest.getUserId());
        return callback(userNotifyRemind);
    }

    /**
     * 增加/修改提醒设置
     *
     * @return
     */
    @RequestMapping(value = "/addOrEditRemind", method = RequestMethod.POST)
    public ResultBean addOrEditRemind(@RequestBody @Valid UserNotifyRemindFormRequest formRequest) {
        UserNotifyRemind bean = null;
        if (formRequest.getId() != null) {
            bean = this.getUserEntity(UserNotifyRemind.class, formRequest.getId(), formRequest.getUserId());
            BeanCopy.copyProperties(formRequest, bean);
            UserNotify userNotify = this.getUserEntity(beanClass, formRequest.getUserNotifyId(), formRequest.getUserId());
            bean.setUserNotify(userNotify);
            bean.setLastModifyTime(new Date());
            baseService.updateObject(bean);
            //只要修改过重新开始计算提醒
            String key = CacheKey.getKey(CacheKey.USER_NOTIFY, formRequest.getUserId().toString(), bean.getUserNotify().getId().toString());
            cacheHandler.delete(key);
        } else {
            bean = new UserNotifyRemind();
            BeanCopy.copyProperties(formRequest, bean);
            UserNotify userNotify = this.getUserEntity(beanClass, formRequest.getUserNotifyId(), formRequest.getUserId());
            bean.setUserNotify(userNotify);
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        }
        return callback(null);
    }

    /**
     * 清除缓存
     *
     * @return
     */
    @RequestMapping(value = "/deleteStatCache", method = RequestMethod.POST)
    public ResultBean deleteStatCache() {
        Long n = notifyStatHandler.deleteCache(this.getCurrentUserId());
        return callback(n);
    }

}
