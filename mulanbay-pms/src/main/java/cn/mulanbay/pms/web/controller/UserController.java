package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.*;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.UserRoleDto;
import cn.mulanbay.pms.persistent.enums.AuthType;
import cn.mulanbay.pms.persistent.enums.FamilyMode;
import cn.mulanbay.pms.persistent.enums.MonitorBussType;
import cn.mulanbay.pms.persistent.service.*;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.auth.UserFormRequest;
import cn.mulanbay.pms.web.bean.request.auth.UserScoreRequest;
import cn.mulanbay.pms.web.bean.request.auth.UserSearch;
import cn.mulanbay.pms.web.bean.request.auth.UserSystemMonitorRequest;
import cn.mulanbay.pms.web.bean.request.user.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.auth.UseScoreVo;
import cn.mulanbay.pms.web.bean.response.user.UserInfoResponse;
import cn.mulanbay.pms.web.bean.response.user.UserProfileResponse;
import cn.mulanbay.web.bean.response.ResultBean;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 用户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static Class<User> beanClass = User.class;

    @Value("${user.avatar.path}")
    String avatarFilePath;

    @Autowired
    AuthService authService;

    @Autowired
    DataService dataService;

    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    UserScoreHandler userScoreHandler;

    @Autowired
    WxpayHandler wxpayHandler;

    @Autowired
    WechatService wechatService;

    @Autowired
    LevelService levelService;

    @Autowired
    FamilyService familyService;

    @Autowired
    ThreadPoolHandler threadPoolHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;
    /**
     * 用户树
     * @return
     */
    @RequestMapping(value = "/getUserTree")
    public ResultBean getUserTree(Boolean needRoot) {
        try {
            UserSearch sf = new UserSearch();
            PageResult<User> pageResult = getUserResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<User> gtList = pageResult.getBeanList();
            for (User gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getUsername());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户树异常",
                    e);
        }
    }

    /**
     * 获取用户角色树
     *
     * @param urt
     * @return
     */
    @RequestMapping(value = "/getUserRoleTree")
    public ResultBean getUserRoleTree(UserRoleTreeRequest urt) {
        try {
            List<UserRoleDto> urList = authService.selectUserRoleBeanList(urt.getUserId());
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (UserRoleDto ur : urList) {
                TreeBean tb = new TreeBean();
                tb.setId(ur.getRoleId().toString());
                tb.setText(ur.getRoleName());
                if (ur.getUserRoleId() != null) {
                    tb.setChecked(true);
                }
                list.add(tb);
            }
            Boolean b = urt.getSeparate();
            if (b != null && b) {
                Map map = new HashMap<>();
                map.put("treeData", list);
                List checkedKeys = new ArrayList();
                for (UserRoleDto sf : urList) {
                    if (sf.getUserRoleId() != null) {
                        checkedKeys.add(sf.getRoleId().longValue());
                    }
                }
                map.put("checkedKeys", checkedKeys);
                return callback(map);
            } else {
                return callback(TreeBeanUtil.addRoot(list, urt.getNeedRoot()));
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户角色树异常",
                    e);
        }
    }

    /**
     * 获取用户系统监控树
     *
     * @param urt
     * @return
     */
    @RequestMapping(value = "/getSystemMonitorTree")
    public ResultBean getSystemMonitorTree(UserRoleTreeRequest urt) {
        try {
            List<SystemMonitorUser> urList = authService.selectSystemMonitorUserList(urt.getUserId());
            List<TreeBean> treeBeans = new ArrayList<>();
            List checkedKeys = new ArrayList();
            for (MonitorBussType sfb : MonitorBussType.values()) {
                if (sfb == MonitorBussType.ALL) {
                    continue;
                }
                TreeBean treeBean = new TreeBean();
                treeBean.setId(String.valueOf(sfb.getValue()));
                treeBean.setText(sfb.getName());
                if (checkMonitorExit(sfb, urList)) {
                    treeBean.setChecked(true);
                    checkedKeys.add(sfb.getValue());
                }
                treeBeans.add(treeBean);
            }
            Boolean b = urt.getSeparate();
            if (b != null && b) {
                Map map = new HashMap<>();
                map.put("treeData", treeBeans);
                map.put("checkedKeys", checkedKeys);
                return callback(map);
            } else {
                return callback(TreeBeanUtil.addRoot(treeBeans, urt.getNeedRoot()));
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户系统监控树异常",
                    e);
        }
    }

    private boolean checkMonitorExit(MonitorBussType v, List<SystemMonitorUser> urList) {
        if (StringUtil.isEmpty(urList)) {
            return false;
        } else {
            for (SystemMonitorUser smu : urList) {
                if (smu.getBussType() == v) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 保存用户监控
     *
     * @return
     */
    @RequestMapping(value = "/saveSystemMonitor", method = RequestMethod.POST)
    public ResultBean saveSystemMonitor(@RequestBody @Valid UserSystemMonitorRequest ur) {
        authService.saveUserSystemMonitor(ur.getUserId(), ur.getBussTypes());
        return callback(null);
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserSearch sf) {
        PageResult<User> pageResult = getUserResult(sf);
        return callbackDataGrid(pageResult);
    }

    private PageResult<User> getUserResult(UserSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        PageResult<User> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserFormRequest bean) {
        User user = new User();
        BeanCopy.copyProperties(bean, user);
        // 密码设置
        String encodePassword = tokenHandler.encodePassword(bean.getPassword());
        user.setPassword(encodePassword);
        user.setSecAuthType(bean.getSecAuthType());
        user.setCreatedTime(new Date());
        user.setLevel(3);
        user.setPoints(0);
        UserSetting us = new UserSetting();
        //us.setUserId(user.getId());
        us.setSendEmail(true);
        us.setSendWxMessage(true);
        us.setStatScore(false);
        us.setCreatedTime(new Date());
        authService.createUser(user, us);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        User br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserFormRequest bean) {
        User user = baseService.getObject(beanClass, bean.getId());
        String originalPawword = user.getPassword();
        BeanCopy.copyProperties(bean, user);
        String password = bean.getPassword();
        if (null != password && !password.isEmpty()) {
            // 密码设置
            String encodePassword = tokenHandler.encodePassword(bean.getPassword());
            user.setPassword(encodePassword);
        } else {
            user.setPassword(originalPawword);
        }
        user.setLastModifyTime(new Date());
        UserSetting us = authService.getUserSetting(user.getId());
        us.setLastModifyTime(new Date());
        authService.updateUser(user, us);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        String[] ss = deleteRequest.getIds().split(",");
        for(String s : ss){
            authService.deleteUser(Long.valueOf(s));
        }
        return callback(null);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getMyInfo", method = RequestMethod.GET)
    public ResultBean getMyInfo() {
        Long userId = this.getCurrentUserId();
        UserInfoResponse response = this.getUserInfo(userId);
        return callback(response);
    }

    private UserInfoResponse getUserInfo(Long userId) {
        User user = baseService.getObject(beanClass, userId);
        UserSetting us = authService.getUserSetting(userId);
        UserInfoResponse response = new UserInfoResponse();
        BeanCopy.copyProperties(user, response);
        BeanCopy.copyProperties(us, response);
        return response;
    }

    /**
     * 获取用户信息（新版本使用）
     *
     * @return
     */
    @RequestMapping(value = "/getMyInfoWithPerms", method = RequestMethod.GET)
    public ResultBean getMyInfoWithPerms() {
        LoginUser loginUser = tokenHandler.getLoginUser(request);
        Long roleId = loginUser.getRoleId();
        Long userId = loginUser.getUserId();
        UserInfoResponse user = this.getUserInfo(userId);
        //设置家庭组信息
        user.setFamilyMode(loginUser.getFamilyMode());
        //user.setFamilyMode(FamilyMode.F);
        if (loginUser.getFamilyMode() == FamilyMode.F) {
            //给前端筛选使用
            List<TreeBean> familyUserList = new ArrayList<>();
            List<Long> userIdList = loginUser.getUserIdList();
            for (Long l : userIdList) {
                TreeBean tb = new TreeBean();
                tb.setId(l.toString());
                FamilyUser fu = familyService.getFamilyUser(l);
                tb.setText(fu.getAliasName());
                familyUserList.add(tb);
            }
            user.setFamilyUserList(familyUserList);
        }
        Map map = new HashMap();
        map.put("user", user);
        map.put("roles", new String[]{"admin"});
        List<String> perms = authService.selectRoleFPermsList(roleId);
        map.put("permissions", perms);
        return callback(map);
    }

    /**
     * 获取用户信息（新版本使用）
     *
     * @return
     */
    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public ResultBean getProfile() {
        LoginUser loginUser = tokenHandler.getLoginUser(request);
        Long roleId = loginUser.getRoleId();
        Long userId = loginUser.getUserId();
        UserProfileResponse ups = new UserProfileResponse();
        User user = baseService.getObject(User.class, userId);
        UserSetting us = authService.getUserSetting(userId);
        BeanCopy.copyProperties(user, ups);
        BeanCopy.copyProperties(us, ups);
        if (roleId != null) {
            Role role = baseService.getObject(Role.class, roleId);
            ups.setRoleName(role.getName());
        }
        LevelConfig lc = levelService.getLevelConfig(user.getLevel());
        ups.setAvatar(systemConfigHandler.getPictureFullUrl(ups.getAvatar()));
        ups.setLevelName(lc.getName());
        return callback(ups);
    }

    /**
     * 更新用户信息（新版本使用）
     *
     * @return
     */
    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ResultBean editProfile(@RequestBody @Valid UserProfileRequest upr) {
        User user = baseService.getObject(beanClass, upr.getUserId());
        if (upr.getSecAuthType() == AuthType.WECHAT) {
            //检查微信绑定信息
            UserWxpayInfo wx = wxpayHandler.getWxpayInfo(user.getId());
            if (StringUtil.isEmpty(wx.getOpenId())) {
                return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_WECHAT_NULL_);
            }
        }
        BeanCopy.copyProperties(upr, user);
        baseService.updateObject(user);
        return callback(null);
    }

    /**
     * 用户自己修改密码
     *
     * @return
     */
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public ResultBean editPassword(@RequestBody @Valid UserPasswordEditRequest eui) {
        User user = baseService.getObject(beanClass, eui.getUserId());
        String pp = tokenHandler.encodePassword(eui.getOldPassword());
        if (!user.getPassword().equals(pp)) {
            return callbackErrorCode(PmsErrorCode.USER_PASSWORD_ERROR);
        }
        String newPP = tokenHandler.encodePassword(eui.getNewPassword());
        user.setPassword(newPP);
        user.setLastModifyTime(new Date());
        baseService.updateObject(user);
        return callback(null);
    }

    /**
     * 用户自己修改个人信息
     *
     * @return
     */
    @RequestMapping(value = "/editMyInfo", method = RequestMethod.POST)
    public ResultBean editMyInfo(@RequestBody @Valid EditMyInfoRequest eui) {
        User user = baseService.getObject(beanClass, eui.getUserId());
        String pp = tokenHandler.encodePassword(eui.getPassword());
        if (!user.getPassword().equals(pp)) {
            return callbackErrorCode(PmsErrorCode.USER_PASSWORD_ERROR);
        }
        if (eui.getSecAuthType() == AuthType.SMS && StringUtil.isEmpty(eui.getPhone())) {
            return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_PHONE_NULL_);
        }
        if (eui.getSecAuthType() == AuthType.EMAIL && StringUtil.isEmpty(eui.getEmail())) {
            return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_EMAIL_NULL_);
        }
        if (eui.getSecAuthType() == AuthType.WECHAT) {
            //检查微信绑定信息
            UserWxpayInfo wx = wxpayHandler.getWxpayInfo(user.getId());
            if (StringUtil.isEmpty(wx.getOpenId())) {
                return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_WECHAT_NULL_);
            }
        }
        UserSetting us = authService.getUserSetting(eui.getUserId());
        user.setUsername(eui.getUsername());
        user.setNickname(eui.getNickname());
        user.setBirthday(eui.getBirthday());
        user.setPhone(eui.getPhone());
        user.setEmail(eui.getEmail());
        user.setSecAuthType(eui.getSecAuthType());
        user.setLastModifyTime(new Date());
        if (!StringUtil.isEmpty(eui.getNewPassword())) {
            // 密码设置
            String encodePassword = tokenHandler.encodePassword(eui.getNewPassword());
            user.setPassword(encodePassword);
        }
        baseService.updateObject(user);
        BeanCopy.copyProperties(eui, us);
        us.setStatScore(true);
        us.setLastModifyTime(new Date());
        baseService.updateObject(us);
        return callback(null);
    }

    /**
     * 获取用户评分
     *
     * @return
     */
    @RequestMapping(value = "/getScore", method = RequestMethod.GET)
    public ResultBean getScore(UserScoreRequest sf) {
        Date date = sf.getEndDate();
        if (date == null) {
            date = DateUtil.getDate(0);
        }
        List<UseScoreVo> res = this.getUseScoreBean(sf.getUserId(), date);
        return callback(res);
    }

    /**
     * 获取用户评分(和上一天的比对)
     *
     * @return
     */
    @RequestMapping(value = "/getScoreCompare", method = RequestMethod.GET)
    public ResultBean getScoreCompare(UserScoreRequest sf) {
        Date date = sf.getEndDate();
        if (date == null) {
            date = DateUtil.getDate(0);
        }
        List<UseScoreVo> nowData = this.getUseScoreBean(sf.getUserId(), date);
        Date beforeDate = DateUtil.getDate(-1, date);
        List<UseScoreVo> beforeData = this.getUseScoreBean(sf.getUserId(), beforeDate);
        Map map = new HashMap<>();
        map.put("nowData", nowData);
        map.put("beforeData", beforeData);
        return callback(map);
    }

    /**
     * 获取积分
     *
     * @param userId
     * @param date
     * @return
     */
    private List<UseScoreVo> getUseScoreBean(Long userId, Date date) {
        List<UserScoreDetail> list = userScoreHandler.getUseScore(userId, date);
        List<UseScoreVo> res = new ArrayList<>();
        for (UserScoreDetail dd : list) {
            UseScoreVo bean = new UseScoreVo();
            BeanCopy.copyProperties(dd, bean);
            BeanCopy.copyProperties(dd.getScoreConfig(), bean);
            bean.setScoreConfigId(dd.getScoreConfig().getId());
            res.add(bean);
        }
        return res;
    }

    /**
     * 获取微信账号
     *
     * @return
     */
    @RequestMapping(value = "/getUserWxpayInfo", method = RequestMethod.GET)
    public ResultBean getUserWxpayInfo(Long userId) {
        UserWxpayInfo uw = wechatService.getUserWxpayInfo(userId, wxpayHandler.getAppId());
        if (uw == null) {
            uw = new UserWxpayInfo();
            uw.setUserId(userId);
        }
        return callback(uw);
    }

    /**
     * 修改用户微信信息
     *
     * @return
     */
    @RequestMapping(value = "/editUserWxpayInfo", method = RequestMethod.POST)
    public ResultBean editUserWxpayInfo(@RequestBody @Valid EditUserWxpayInfoRequest eui) {
        if (eui.getId() == null) {
            UserWxpayInfo uw = new UserWxpayInfo();
            BeanCopy.copyProperties(eui, uw);
            uw.setAppId(wxpayHandler.getAppId());
            uw.setCreatedTime(new Date());
            baseService.saveObject(uw);
        } else {
            UserWxpayInfo uw = baseService.getObject(UserWxpayInfo.class, eui.getId());
            BeanCopy.copyProperties(eui, uw);
            uw.setLastModifyTime(new Date());
            baseService.updateByMergeObject(uw);
        }
        return callback(null);
    }

    /**
     * 离线
     *
     * @return
     */
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    public ResultBean offline(@RequestBody @Valid UserCommonRequest ucr) {
        return callback(null);
    }

    /**
     * 删除用户数据
     *
     * @return
     */
    @RequestMapping(value = "/deleteUserData", method = RequestMethod.POST)
    public ResultBean deleteUserData(@RequestBody @Valid UserManagerRequest ucr) {
        threadPoolHandler.pushThread(new Runnable() {
            @Override
            public void run() {
                baseService.updateJobProcedure("delete_user_data", ucr.getUserId());
            }
        });
        return callback(null);
    }

    /**
     * 初始化用户数据
     *
     * @return
     */
    @RequestMapping(value = "/initUserData", method = RequestMethod.POST)
    public ResultBean initUserData(@RequestBody @Valid UserManagerRequest ucr) {
        threadPoolHandler.pushThread(new Runnable() {
            @Override
            public void run() {
                dataService.initUserData(ucr.getUserId());
            }
        });
        return callback(null);
    }

    /**
     * 获取默认城市
     *
     * @return
     */
    @RequestMapping(value = "/getResidentCity", method = RequestMethod.GET)
    public ResultBean getResidentCity(UserCommonRequest ucr) {
        UserSetting us = authService.getUserSettingForCache(ucr.getUserId());
        return callback(us.getResidentCity());
    }

    /**
     * 头像上传
     */
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ResultBean uploadAvatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // 获取文件存储路径（绝对路径）
            String path = avatarFilePath;
            // 获取原文件名
            String extractFilename = this.extractFilename(file);
            // 创建文件实例
            File filePath = new File(path, extractFilename);
            FileUtil.checkPathExits(filePath);
            // 写入文件
            file.transferTo(filePath);
            //更新数据库
            authService.updateAvatar(this.getCurrentUserId(), extractFilename);
            return callback(extractFilename);
        } else {
            return callbackErrorInfo("文件为空");
        }
    }

    /**
     * 编码文件名
     */
    private final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtil.getFormatDate(new Date(), "yyyyMMdd") + "/" + StringUtil.genUUID() + "." + extension;
        return "/" + fileName;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtil.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

}
