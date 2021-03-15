package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.LifeExperience;
import cn.mulanbay.pms.persistent.domain.LifeExperienceDetail;
import cn.mulanbay.pms.persistent.service.LifeExperienceService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceDetailFormRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceDetailSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 人生经历明细
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/lifeExperienceDetail")
public class LifeExperienceDetailController extends BaseController {

    private static Class<LifeExperienceDetail> beanClass = LifeExperienceDetail.class;

    @Autowired
    LifeExperienceService lifeExperienceService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData")
    public ResultBean getData(LifeExperienceDetailSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("occurDate", Sort.ASC);
        pr.addSort(s);
        PageResult<LifeExperienceDetail> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid LifeExperienceDetailFormRequest formRequest) {
        LifeExperienceDetail bean = new LifeExperienceDetail();
        BeanCopy.copyProperties(formRequest, bean);
        LifeExperience lifeExperience = this.getUserEntity(LifeExperience.class, formRequest.getLifeExperienceId(), formRequest.getUserId());
        bean.setLifeExperience(lifeExperience);
        bean.setCost(0.0);
        bean.setCreatedTime(new Date());
        lifeExperienceService.saveOrUpdateLifeExperienceDetail(bean, true);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        LifeExperienceDetail bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid LifeExperienceDetailFormRequest formRequest) {
        LifeExperienceDetail bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        LifeExperience lifeExperience = this.getUserEntity(LifeExperience.class, formRequest.getLifeExperienceId(), formRequest.getUserId());
        bean.setLifeExperience(lifeExperience);
        bean.setLastModifyTime(new Date());
        lifeExperienceService.saveOrUpdateLifeExperienceDetail(bean, true);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        Long[] ll = NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(","));
        for (Long id : ll) {
            lifeExperienceService.deleteLifeExperienceDetail(id, deleteRequest.getUserId(), false);
        }
        return callback(null);
    }

    /**
     * 获取国际位置
     *
     * @param country
     * @return
     */
    @RequestMapping(value = "/getCountryLocation", method = RequestMethod.GET)
    public ResultBean getCountryLocation(String country) {
        String s = lifeExperienceService.getCountryLocation(country);
        return callback(s);
    }

    /**
     * 根据城市位置
     *
     * @param city
     * @return
     */
    @RequestMapping(value = "/getCityLocation", method = RequestMethod.GET)
    public ResultBean getCityLocation(String city) {
        String s = lifeExperienceService.getCityLocation(city);
        return callback(s);
    }

}
