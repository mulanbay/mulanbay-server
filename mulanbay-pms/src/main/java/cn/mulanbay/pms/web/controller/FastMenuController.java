package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.persistent.domain.FastMenu;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.dto.FastMenuDto;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.user.UserFastMenuRequest;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快捷菜单
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/fastMenu")
public class FastMenuController extends BaseController {

    private static Class<FastMenu> beanClass = FastMenu.class;

    @Autowired
    AuthService authService;

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResultBean getList(UserCommonRequest ucr) {
        List<FastMenuDto> list = authService.selectUserFastMenu(ucr.getUserId());
        return callback(list);
    }

    /**
     * 树形结构
     *
     * @return
     */
    @RequestMapping(value = "/getMenuTree", method = RequestMethod.GET)
    public ResultBean getMenuTree(UserCommonRequest ucr) {
        LoginUser loginUser = tokenHandler.getLoginUser(request);
        Long roleId = loginUser.getRoleId();
        List<SystemFunction> sfList = authService.selectRoleFunctionMenuList(roleId, true);
        List<TreeBean> funcTree = this.getFunctionTree(sfList, 0L);
        Map map = new HashMap<>();
        map.put("treeData", funcTree);
        List checkedKeys = new ArrayList();
        List<FastMenuDto> list = authService.selectUserFastMenu(ucr.getUserId());
        for (FastMenuDto sf : list) {
            checkedKeys.add(sf.getFunctionId());
        }
        map.put("checkedKeys", checkedKeys);
        return callback(map);
    }

    private List<TreeBean> getFunctionTree(List<SystemFunction> list, long pid) {
        List<TreeBean> res = new ArrayList<>();
        for (SystemFunction sf : list) {
            if (sf.getParentId() == pid) {
                TreeBean tb = new TreeBean();
                tb.setId(sf.getId().toString());
                tb.setText(sf.getName());
                res.add(tb);
                List<TreeBean> children = getFunctionTree(list, sf.getId().longValue());
                tb.setChildren(children);
            }
        }
        return res;
    }

    /**
     * 保存快捷菜单
     *
     * @return
     */
    @RequestMapping(value = "/saveFastMenu", method = RequestMethod.POST)
    public ResultBean saveFastMenu(@RequestBody @Valid UserFastMenuRequest ur) {
        authService.saveFastMenu(ur.getUserId(), ur.getFunctionIds());
        return callback(null);
    }

}
