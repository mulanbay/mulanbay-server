package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.pms.persistent.domain.Country;
import cn.mulanbay.pms.persistent.service.GeoService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 国家
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/country")
public class CountryController extends BaseController {

    @Autowired
    GeoService geoService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getAll")
    public ResultBean getAll() {
        return callback(geoService.getCountryList());
    }

    /**
     * 分类树
     *
     * @return
     */
    @RequestMapping(value = "/getCountryTree")
    public ResultBean getCountryTree(Boolean needRoot) {
        try {
            List<Country> countryList = geoService.getCountryList();
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (Country gt : countryList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getCnName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "分类树异常",
                    e);
        }
    }

}
