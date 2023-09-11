package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.SystemConfig;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.system.SystemConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.system.SystemConfigSearch;
import cn.mulanbay.pms.web.bean.response.system.PropertyVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 系统配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseController {

    private static Class<SystemConfig> beanClass = SystemConfig.class;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    private Environment environment;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SystemConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<SystemConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SystemConfigFormRequest formRequest) {
        SystemConfig bean = new SystemConfig();
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
        SystemConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 获取配置
     *
     * @return
     */
    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    public ResultBean getProperties() {
        List<PropertyVo> list = new ArrayList<>();
        for (PropertySource<?> propertySource : ((AbstractEnvironment) environment).getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                for (String name : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                    if (name != null) {
                        PropertyVo vo = new PropertyVo();
                        vo.setKey(name);
                        vo.setValue(environment.getProperty(name));
                        vo.setSource(propertySource.getName());
                        list.add(vo);
                    }
                }
            }
        }

        //使用List接口的方法排序
        list.sort((o1, o2) -> o1.getKey().compareTo(o2.getKey()));
        return callback(list);
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SystemConfigFormRequest formRequest) {
        SystemConfig bean = baseService.getObject(beanClass, formRequest.getId());
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
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 刷新系统缓存
     *
     * @return
     */
    @RequestMapping(value = "/refreshCache", method = RequestMethod.POST)
    public ResultBean refreshCache() {
        systemConfigHandler.reloadConfigs();
        return callback(null);
    }

}
