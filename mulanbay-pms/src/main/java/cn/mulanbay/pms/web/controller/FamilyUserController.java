package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.FamilyUser;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.enums.FamilyUserStatus;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.FamilyService;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.family.FamilyUserInviteRequest;
import cn.mulanbay.pms.web.bean.request.family.FamilyUserJoinRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 家庭用户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/familyUser")
public class FamilyUserController extends BaseController {

    private static Class<FamilyUser> beanClass = FamilyUser.class;

    @Autowired
    FamilyService familyService;

    @Autowired
    AuthService authService;

    /**
     * 邀请
     *
     * @return
     */
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public ResultBean invite(@RequestBody @Valid FamilyUserInviteRequest formRequest) {
        //用户验证
        User user = authService.getUserByUsernameOrPhone(formRequest.getUsername());
        if (user == null) {
            return callbackErrorCode(ErrorCode.USER_NOTFOUND);
        }
        //判断是否已经加入家庭,一个人只能加入一个家庭
        FamilyUser fu = familyService.getFamilyUser(user.getId());
        if (fu != null) {
            return callbackErrorCode(PmsErrorCode.FAMILY_JOINED);
        }
        FamilyUser familyUser = new FamilyUser();
        BeanCopy.copyProperties(formRequest, familyUser);
        familyUser.setUserId(user.getId());
        familyUser.setStatus(FamilyUserStatus.APPLYING);
        if (StringUtil.isEmpty(formRequest.getAliasName())) {
            familyUser.setAliasName(user.getUsername());
        }
        familyUser.setCreatedTime(new Date());
        baseService.saveObject(familyUser);
        return callback(null);
    }

    /**
     * 加入
     *
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResultBean join(@RequestBody @Valid FamilyUserJoinRequest formRequest) {
        FamilyUser fu = baseService.getObjectWithUser(beanClass, formRequest.getId(), formRequest.getUserId());
        if (formRequest.getJoin() == true) {
            fu.setStatus(FamilyUserStatus.PASSED);
            fu.setLastModifyTime(new Date());
            baseService.updateObject(fu);
        } else {
            baseService.deleteObject(fu);
        }
        return callback(null);
    }

    /**
     * 申请脱离
     *
     * @return
     */
    @RequestMapping(value = "/applySeparate", method = RequestMethod.POST)
    public ResultBean applySeparate(@RequestBody @Valid CommonBeanGetRequest formRequest) {
        FamilyUser fu = baseService.getObjectWithUser(beanClass, formRequest.getId(), formRequest.getUserId());
        fu.setStatus(FamilyUserStatus.SEP_APPLYING);
        fu.setLastModifyTime(new Date());
        baseService.updateObject(fu);
        return callback(null);
    }

    /**
     * 脱离
     *
     * @return
     */
    @RequestMapping(value = "/separate", method = RequestMethod.POST)
    public ResultBean separate(@RequestBody @Valid CommonBeanGetRequest formRequest) {
        FamilyUser fu = baseService.getObject(beanClass, formRequest.getId());
        this.checkAdmin(fu.getFamilyId(), formRequest.getUserId());
        baseService.deleteObject(fu);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanGetRequest formRequest) {
        FamilyUser fu = baseService.getObject(beanClass, formRequest.getId());
        this.checkAdmin(fu.getFamilyId(), formRequest.getUserId());
        baseService.deleteObject(fu);
        return callback(null);
    }

    private void checkAdmin(Long familyId, Long userId) {
        FamilyUser fu = familyService.getAdminFamilyUser(userId);
        if (fu == null) {
            throw new ApplicationException(ErrorCode.USER_FAMILY_NO_ADMIN);
        }
    }
}
