package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.CityLocation;
import cn.mulanbay.pms.persistent.domain.LifeExperience;
import cn.mulanbay.pms.persistent.service.LifeExperienceService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.life.CityLocationSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 城市位置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/cityLocation")
public class CityLocationController extends BaseController {

    private static Class<CityLocation> beanClass = CityLocation.class;

    @Autowired
    LifeExperienceService lifeExperienceService;

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(CityLocationSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", Sort.DESC);
        pr.addSort(s);
        PageResult<LifeExperience> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody CityLocation bean) {
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(Long id) {
        CityLocation br = baseService.getObject(beanClass, id);
        return callback(br);
    }

    /**
     * 根据名称获取
     *
     * @param location
     * @return
     */
    @RequestMapping(value = "/getByLocation", method = RequestMethod.GET)
    public ResultBean getByLocation(String location) {
        CityLocation br = lifeExperienceService.getCityLocationByLocation(location);
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody CityLocation bean) {
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
        baseService.deleteObjects(CityLocation.class, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 初始化数据
     * 测试使用，当时第一次初始化数据时使用，原始数据来自于百度
     *
     * @return
     */
    @RequestMapping(value = "/intData", method = RequestMethod.POST)
    public ResultBean intData() {
        String s = JsonUtil.beanToJson(this.request.getParameterMap());
        String datastring = s.substring(2, s.length() - 7).replaceAll("\\\\", "");
        Map<String, List> data = (Map<String, List>) JsonUtil.jsonToBean(datastring, Map.class);
        List<CityLocation> list = new ArrayList<>();
        for (String key : data.keySet()) {
            List ll = data.get(key);
            CityLocation bean = new CityLocation();
            bean.setCreatedTime(new Date());
            bean.setLat(Double.valueOf(ll.get(1).toString()));
            bean.setLocation(key);
            bean.setLon(Double.valueOf(ll.get(0).toString()));
            bean.setRemark("自动导入");
            list.add(bean);
        }
        baseService.saveObjects(list);
        return callback(null);
    }

}
