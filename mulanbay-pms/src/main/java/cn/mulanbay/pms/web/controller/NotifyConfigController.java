package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.NotifyConfig;
import cn.mulanbay.pms.persistent.enums.BussType;
import cn.mulanbay.pms.persistent.service.NotifyService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.notify.NotifyConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.notify.NotifyConfigSearch;
import cn.mulanbay.pms.web.bean.request.notify.NotifyConfigTreeSearch;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifyConfigTreeSearch;
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
 * 提醒配置,用于提醒所有的个人事项
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/notifyConfig")
public class NotifyConfigController extends BaseController {

    private static Class<NotifyConfig> beanClass = NotifyConfig.class;

    @Autowired
    NotifyService notifyService;

    /**
     * 提醒配置模板选项列表(用户使用，需要判断用户级别)
     *
     * @return
     */
    @RequestMapping(value = "/getNotifyConfigForUserTree")
    public ResultBean getNotifyConfigForUserTree(UserNotifyConfigTreeSearch sf) {
        try {
            List<NotifyConfig> gtList = notifyService.getNotifyConfigList(sf.getLevel());
            List<TreeBean> list = this.createNotifyConfigTree(gtList);
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    /**
     * 提醒树
     *
     * @return
     */
    @RequestMapping(value = "/getNotifyConfigTree")
    public ResultBean getNotifyConfigTree(NotifyConfigTreeSearch sf) {

        try {
            NotifyConfigSearch ncSearch = new NotifyConfigSearch();
            ncSearch.setPage(PageRequest.NO_PAGE);
            ncSearch.setRelatedBeans(sf.getRelatedBeans());
            PageRequest pr = ncSearch.buildQuery();
            pr.setBeanClass(beanClass);
            Sort s = new Sort("relatedBeans", Sort.ASC);
            pr.addSort(s);
            List<NotifyConfig> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = this.createNotifyConfigTree(gtList);
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取提醒树异常",
                    e);
        }
    }

    private List<TreeBean> createNotifyConfigTree(List<NotifyConfig> gtList) {
        List<TreeBean> result = new ArrayList<>();
        BussType current = BussType.getBussType(gtList.get(0).getRelatedBeans());
        TreeBean typeTreeBean = new TreeBean();
        typeTreeBean.setId("P_" + current.name());
        typeTreeBean.setText(current.getName());
        int n = gtList.size();
        for (int i = 0; i < n; i++) {
            NotifyConfig pc = gtList.get(i);
            TreeBean tb = new TreeBean();
            tb.setId(pc.getId().toString());
            tb.setText(pc.getName());
            BussType m = BussType.getBussType(pc.getRelatedBeans());
            if (m == current) {
                typeTreeBean.addChild(tb);
            }
            if (m != current) {
                current = m;
                result.add(typeTreeBean);
                typeTreeBean = new TreeBean();
                typeTreeBean.setId("P_" + current.name());
                typeTreeBean.setText(current.getName());
                typeTreeBean.addChild(tb);
            }
            if (i == n - 1) {
                //最后一个
                result.add(typeTreeBean);
            }
        }
        return result;
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(NotifyConfigSearch sf) {
        return callbackDataGrid(getNotifyConfigData(sf));
    }

    private PageResult<NotifyConfig> getNotifyConfigData(NotifyConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<NotifyConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid NotifyConfigFormRequest formRequest) {
        NotifyConfig bean = new NotifyConfig();
        BeanCopy.copyProperties(formRequest, bean);
        checkNotifyConfig(bean);
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
        NotifyConfig br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid NotifyConfigFormRequest formRequest) {
        NotifyConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        checkNotifyConfig(bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    public void checkNotifyConfig(NotifyConfig bean) {
        //notifyService.getNotifyResult(bean,this.getCurrentUserId(),0);
    }

    /**
     * 删除
     * todo 该功能其实不能提供，即使提供也要级联删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        for (String s : deleteRequest.getIds().split(",")) {
            notifyService.deleteNotifyConfig(Long.valueOf(s));
        }
        return callback(null);
    }

}
