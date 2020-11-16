package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.persistent.service.GeoService;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 县
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController {

    @Autowired
    GeoService geoService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getDistrictList")
    public ResultBean getDistrictList(Integer cityId) {
        return callback(geoService.getDistrictList(cityId));
    }

}
