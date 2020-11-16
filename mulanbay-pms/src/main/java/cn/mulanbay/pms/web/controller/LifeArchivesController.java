package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.LifeArchives;
import cn.mulanbay.pms.persistent.service.LifeExperienceService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeArchivesFormRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeArchivesSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 人生档案
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/lifeArchives")
public class LifeArchivesController extends BaseController {

    private static Class<LifeArchives> beanClass = LifeArchives.class;

    @Autowired
    LifeExperienceService lifeExperienceService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(LifeArchivesSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("date", sf.getSortType());
        pr.addSort(sort);
        PageResult<LifeArchives> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid LifeArchivesFormRequest formRequest) {
        LifeArchives bean = null;
        if (formRequest.getSourceId() != null) {
            bean = lifeExperienceService.getLifeArchives(formRequest.getUserId(), formRequest.getRelatedBeans(), formRequest.getSourceId());
        }
        if (bean == null) {
            bean = new LifeArchives();
            BeanCopy.copyProperties(formRequest, bean);
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        } else {
            Long id = bean.getId();
            BeanCopy.copyProperties(formRequest, bean);
            bean.setLastModifyTime(new Date());
            bean.setId(id);
            baseService.updateObject(bean);
        }
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        LifeArchives bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 获取原始信息
     *
     * @return
     */
    @RequestMapping(value = "/getSource", method = RequestMethod.GET)
    public ResultBean getSource(@Valid CommonBeanGetRequest getRequest) {
        LifeArchives bean = baseService.getObject(beanClass, getRequest.getId());
        Object o = baseService.getObject(bean.getRelatedBeans(), bean.getSourceId(), "id");
        return callback(o);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid LifeArchivesFormRequest formRequest) {
        LifeArchives bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
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
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

}
