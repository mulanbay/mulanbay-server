package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.ErrorCodeDefine;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.log.ErrorCodeDefineFormRequest;
import cn.mulanbay.pms.web.bean.request.log.ErrorCodeDefineGetRequest;
import cn.mulanbay.pms.web.bean.request.log.ErrorCodeDefineSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 错误代码
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/errorCodeDefine")
public class ErrorCodeDefineController extends BaseController {

    private static Class<ErrorCodeDefine> beanClass = ErrorCodeDefine.class;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ErrorCodeDefineSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("code", Sort.ASC);
        pr.addSort(sort);
        PageResult<ErrorCodeDefine> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ErrorCodeDefineFormRequest formRequest) {
        createBean(formRequest);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid ErrorCodeDefineGetRequest getRequest) {
        ErrorCodeDefine bean = baseService.getObject(beanClass, getRequest.getCode());
        return callback(bean);
    }

    private void createBean(ErrorCodeDefineFormRequest formRequest) {
        ErrorCodeDefine bean = new ErrorCodeDefine();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCount(0);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ErrorCodeDefineFormRequest formRequest) {
        ErrorCodeDefine bean = baseService.getObject(beanClass, formRequest.getCode());
        if (bean == null) {
            createBean(formRequest);
        } else {
            BeanCopy.copyProperties(formRequest, bean);
            bean.setLastModifyTime(new Date());
            baseService.updateObject(bean);
        }
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
        for (String s : ids) {
            baseService.deleteObject(beanClass, Integer.valueOf(s));
        }
        return callback(null);
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    @RequestMapping(value = "/reloadCacheConfig", method = RequestMethod.POST)
    public ResultBean reloadCacheConfig() {
        return callback(null);
    }
}
