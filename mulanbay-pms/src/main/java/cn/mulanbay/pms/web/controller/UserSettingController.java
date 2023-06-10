package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.pms.persistent.domain.UserSetting;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.web.bean.request.user.UserSettingFormRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 用户设置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userSetting")
public class UserSettingController extends BaseController {

    private static Class<UserSetting> beanClass = UserSetting.class;

    @Autowired
    AuthService authService;

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean get() {
        Long userId = this.getCurrentUserId();
        UserSetting bean = authService.getUserSetting(userId);
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserSettingFormRequest formRequest) {
        Long userId = this.getCurrentUserId();
        UserSetting bean = authService.getUserSetting(userId);
        BeanCopy.copyProperties(formRequest, bean);
        bean.setStatScore(true);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

}
