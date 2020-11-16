package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.persistent.service.GeoService;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 城市管理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController {

    @Autowired
    GeoService geoService;

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getCityList")
    public ResultBean getCityList(Integer provinceId) {
        return callback(geoService.getCityList(provinceId));
    }

}
