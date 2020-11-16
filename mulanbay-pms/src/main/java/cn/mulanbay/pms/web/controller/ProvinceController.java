package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.persistent.service.GeoService;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 省份
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/province")
public class ProvinceController extends BaseController {

    @Autowired
    GeoService geoService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getAll")
    public ResultBean getAll() {
        return callback(geoService.getProvinceList());
    }
}
