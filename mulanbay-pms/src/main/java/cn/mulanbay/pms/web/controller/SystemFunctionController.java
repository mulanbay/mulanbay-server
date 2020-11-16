package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.ClazzUtils;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.Account;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.dto.common.SystemFunctionBean;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.LogService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.auth.SystemFunctionFormRequest;
import cn.mulanbay.pms.web.bean.request.auth.SystemFunctionSearch;
import cn.mulanbay.pms.web.bean.request.auth.SystemFunctionTreeRequest;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 系统功能点
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/systemFunction")
public class SystemFunctionController extends BaseController {

    private static Class<SystemFunction> beanClass = SystemFunction.class;

    @Autowired
    DataService dataService;

    @Autowired
    LogService logService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * 功能点菜单树
     *
     * @return
     */
    @RequestMapping(value = "/getSystemFunctionMenuTree")
    public ResultBean getSystemFunctionMenuTree() {
        try {
            List<SystemFunctionBean> list = dataService.getSystemFunctionMenu();
            List<TreeBean> treeBeans = new ArrayList<>();
            for (SystemFunctionBean sfb : list) {
                TreeBean treeBean = new TreeBean();
                treeBean.setId(sfb.getId().toString());
                treeBean.setText(sfb.getName());
                treeBean.setPid(sfb.getPid() == null ? null : sfb.getPid().toString());
                treeBeans.add(treeBean);
            }
            TreeBean root = new TreeBean();
            root.setId("0");
            root.setText("根");
            TreeBean result = TreeBeanUtil.changToTree(root, treeBeans);
            List<TreeBean> resultList = new ArrayList<TreeBean>();
            resultList.add(result);
            return callback(resultList);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取功能点菜单树异常",
                    e);
        }
    }

    /**
     * 获取功能点树，包含所有功能点
     *
     * @return
     */
    @RequestMapping(value = "/getSystemFunctionTree")
    public ResultBean getSystemFunctionTree(SystemFunctionTreeRequest cts) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            PageRequest pr = cts.buildQuery();
            pr.setBeanClass(beanClass);
            List<SystemFunction> gtList = baseService.getBeanList(pr);
            TreeBean root = new TreeBean();
            root.setId("0");
            root.setText("根");
            root.setChildren(getFunctionTree2(root, gtList));
            if (cts.getNeedRoot() == null || cts.getNeedRoot() == false) {
                root.setId(null);
            }
            list.add(root);
            return callback(list);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取功能点树异常",
                    e);
        }
    }

    public List<TreeBean> getFunctionTree2(TreeBean tb, List<SystemFunction> list) {
        List<TreeBean> treeBeanList = new ArrayList<>();
        for (SystemFunction sf : list) {
            SystemFunction parent = sf.getParent();
            if (parent != null && tb.getId().equals(parent.getId().toString())) {
                TreeBean child = new TreeBean();
                child.setId(sf.getId().toString());
                child.setText(sf.getName());
                if (sf.getSecAuth()) {
                    child.setIconCls("icon-2");
                } else if (sf.getPermissionAuth()) {
                    child.setIconCls("icon-auth");
                }
                treeBeanList.add(child);
                List<TreeBean> children = getFunctionTree2(child, list);
                child.setChildren(children);
            }
        }
        return treeBeanList;
    }

    public List<TreeBean> getFunctionTree(TreeBean tb, List<SystemFunctionBean> list) {
        List<TreeBean> treeBeanList = new ArrayList<>();
        for (SystemFunctionBean sf : list) {
            BigInteger pid = sf.getPid();
            if (pid != null && tb.getId().equals(pid.toString())) {
                TreeBean child = new TreeBean();
                child.setId(sf.getId().toString());
                child.setText(sf.getName());
                treeBeanList.add(child);
                List<TreeBean> children = getFunctionTree(child, list);
                child.setChildren(children);
            }
        }
        return treeBeanList;
    }

    /**
     * 获取列表（树形结构页面使用）
     *
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResultBean getList(SystemFunctionSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setPage(0);
        pr.setBeanClass(beanClass);
        Sort sort1 = new Sort("parent.id", Sort.ASC);
        pr.addSort(sort1);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        List<SystemFunction> list = baseService.getBeanList(pr);
        return callback(list);
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SystemFunctionSearch sf) {
        return callbackDataGrid(getSystemFunctionResult(sf));
    }

    private PageResult<SystemFunction> getSystemFunctionResult(SystemFunctionSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<SystemFunction> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SystemFunctionFormRequest formRequest) {
        checkFormBean(formRequest);
        SystemFunction bean = new SystemFunction();
        BeanCopy.copyProperties(formRequest, bean, true);
        if (formRequest.getParentId() != null) {
            SystemFunction parent = baseService.getObject(beanClass, formRequest.getParentId());
            bean.setParent(parent);
        }
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
    public ResultBean get(Long id) {
        SystemFunction br = baseService.getObject(beanClass, id);
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SystemFunctionFormRequest formRequest) {
        checkFormBean(formRequest);
        SystemFunction bean = baseService.getObject(beanClass, formRequest.getId());
        ;
        BeanCopy.copyProperties(formRequest, bean, true);
        if (formRequest.getParentId() != null) {
            SystemFunction parent = baseService.getObject(beanClass, formRequest.getParentId());
            bean.setParent(parent);
        }
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    private void checkFormBean(SystemFunctionFormRequest formRequest) {
        if (true == formRequest.getRouter()) {
            if (StringUtil.isEmpty(formRequest.getPath())) {
                throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "路由地址不能为空");
            }
            if (false == formRequest.getFrame()) {
                if (StringUtil.isEmpty(formRequest.getComponent())) {
                    throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "组件路径不能为空");
                }
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
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 刷新系统缓存
     *
     * @return
     */
    @RequestMapping(value = "/refreshSystemConfig", method = RequestMethod.POST)
    public ResultBean refreshSystemConfig() {
        systemConfigHandler.reloadFunctions();
        return callback(null);
    }

    /**
     * 映射实体
     *
     * @return
     */
    @RequestMapping(value = "/getDomainClassNamesTree", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultBean getDomainClassNamesTree(Boolean needRoot) {
        List<TreeBean> treeBeans = new ArrayList<>();
        String packageName1 = Account.class.getPackage().getName();
        List<String> list = ClazzUtils.getClazzName(packageName1, false);
        String packageName2 = TaskTrigger.class.getPackage().getName();
        List<String> listSc = ClazzUtils.getClazzName(packageName2, false);
        list.addAll(listSc);
        Collections.sort(list);
        for (String s : list) {
            TreeBean treeBean = new TreeBean();
            int n = s.lastIndexOf(".");
            String className = s.substring(n + 1, s.length());
            treeBean.setId(className);
            treeBean.setText(className);
            treeBeans.add(treeBean);
        }
        return callback(TreeBeanUtil.addRoot(treeBeans, needRoot));
    }

}
