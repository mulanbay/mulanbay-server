package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.Family;
import cn.mulanbay.pms.persistent.domain.FamilyUser;
import cn.mulanbay.pms.persistent.service.FamilyService;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.family.FamilyFormRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 家庭
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/family")
public class FamilyController extends BaseController {

    private static Class<Family> beanClass = Family.class;

    @Autowired
    FamilyService familyService;

    /**
     * 我的家庭
     *
     * @return
     */
    @RequestMapping(value = "/getMyFamily", method = RequestMethod.GET)
    public ResultBean getMyFamily(@Valid UserCommonRequest ucr) {
        Map res = new HashMap<>();
        FamilyUser fu = familyService.getFamilyUser(ucr.getUserId());
        res.put("myInfo", fu);
        if (fu != null) {
            List<FamilyUser> fuList = familyService.getFamilyUserList(fu.getFamilyId());
            res.put("userList", fuList);
            Family family = baseService.getObject(beanClass, fu.getFamilyId());
            res.put("family", family);
        } else {
            res.put("userList", new ArrayList<>());
        }
        return callback(res);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid FamilyFormRequest formRequest) {
        //判断是否已经加入家庭
        FamilyUser fu = familyService.getFamilyUser(formRequest.getUserId());
        if (fu != null) {
            return callbackErrorCode(PmsErrorCode.FAMILY_JOINED);
        }
        Family family = new Family();
        BeanCopy.copyProperties(formRequest, family);
        familyService.createFamily(family);
        return callback(null);
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        Family family = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(family);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid FamilyFormRequest formRequest) {
        Family family = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, family);
        family.setLastModifyTime(new Date());
        baseService.updateObject(family);
        return callback(null);
    }

    /**
     * 解散
     *
     * @return
     */
    @RequestMapping(value = "/dismiss", method = RequestMethod.POST)
    public ResultBean dismiss(@RequestBody @Valid CommonBeanGetRequest formRequest) {
        Family family = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        familyService.dismissFamily(family.getId());
        return callback(null);
    }

}
