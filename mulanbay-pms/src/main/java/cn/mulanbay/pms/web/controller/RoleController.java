package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.Role;
import cn.mulanbay.pms.persistent.dto.RoleFunctionDto;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.auth.RoleFormRequest;
import cn.mulanbay.pms.web.bean.request.auth.RoleFunctionRequest;
import cn.mulanbay.pms.web.bean.request.auth.RoleSearch;
import cn.mulanbay.pms.web.bean.request.auth.UserRoleRequest;
import cn.mulanbay.pms.web.bean.request.user.RoleFunctionTreeRequest;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

/**
 * 角色
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static Class<Role> beanClass = Role.class;

    @Autowired
    AuthService authService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * @return
     */
    @RequestMapping(value = "/getRoleTree")
    public ResultBean getRoleTree(Boolean needRoot) {
        try {
            RoleSearch sf = new RoleSearch();
            sf.setPage(PageRequest.NO_PAGE);
            PageResult<Role> pr = this.getRoleResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<Role> gtList = pr.getBeanList();
            for (Role gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取角色树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(RoleSearch sf) {
        return callbackDataGrid(getRoleResult(sf));
    }

    private PageResult<Role> getRoleResult(RoleSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<Role> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid RoleFormRequest formRequest) {
        Role bean = new Role();
        BeanCopy.copyProperties(formRequest, bean);
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
        Role bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid RoleFormRequest formRequest) {
        Role bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
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
        String[] ids = deleteRequest.getIds().split(",");
        for (String id : ids) {
            authService.deleteRole(Long.valueOf(id));
        }
        return callback(null);
    }

    /**
     * 刷新系统缓存
     *
     * @return
     */
    @RequestMapping(value = "/refreshSystemConfig", method = RequestMethod.POST)
    public ResultBean refreshSystemConfig() {
        systemConfigHandler.reloadRoleFunctions();
        return callback(null);
    }

    /**
     * 保存用户角色
     *
     * @return
     */
    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    public ResultBean saveUserRole(@RequestBody @Valid UserRoleRequest ur) {
        authService.saveUserRole(ur.getUserId(), ur.getRoleIds());
        return callback(null);
    }

    /**
     * 保存角色功能点
     *
     * @return
     */
    @RequestMapping(value = "/saveRoleFunction", method = RequestMethod.POST)
    public ResultBean saveRoleFunction(@RequestBody @Valid RoleFunctionRequest ur) {
        authService.saveRoleFunction(ur.getRoleId(), ur.getFunctionIds());
        return callback(null);
    }

    /**
     * 获取角色功能点树
     *
     * @param urt
     * @return
     */
    @RequestMapping(value = "/getRoleFunctionTree")
    public ResultBean getRoleFunctionTree(RoleFunctionTreeRequest urt) {
        try {
            List<RoleFunctionDto> rfList = authService.selectRoleFunctionBeanList(urt.getRoleId());
            List<TreeBean> list = new ArrayList<TreeBean>();
            TreeBean root = new TreeBean();
            root.setId("0");
            root.setText("根");
            root.setChildren(getFunctionTree(root, rfList));
            root.setChecked(false);
            list.add(root);
            Boolean b = urt.getSeparate();
            if (b != null && b) {
                Map map = new HashMap<>();
                map.put("treeData", list);
                List checkedKeys = new ArrayList();
                for (RoleFunctionDto sf : rfList) {
                    if (sf.getRoleFunctionId() != null) {
                        checkedKeys.add(sf.getFunctionId());
                    }
                }
                map.put("checkedKeys", checkedKeys);
                return callback(map);
            } else {
                return callback(list);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取角色功能点树异常",
                    e);
        }
    }

    private List<TreeBean> getFunctionTree(TreeBean tb, List<RoleFunctionDto> list) {
        List<TreeBean> treeBeanList = new ArrayList<>();
        for (RoleFunctionDto sf : list) {
            BigInteger pid = sf.getPid();
            if (pid != null && tb.getId().equals(pid.toString())) {
                TreeBean child = new TreeBean();
                child.setId(sf.getFunctionId().toString());
                child.setText(sf.getFunctionName());
                if (sf.getRoleFunctionId() != null) {
                    tb.setChecked(true);
                }
                treeBeanList.add(child);
                List<TreeBean> children = getFunctionTree(child, list);
                child.setChildren(children);
            }
        }
        return treeBeanList;
    }

}
