package cn.mulanbay.pms.web.controller;

import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统维护类（目前无使用）
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/maintain")
public class MaintainController extends BaseController {

    @Autowired
    CacheHandler cacheHandler;

    /**
     * 删除缓存
     *
     * @return
     */
    @RequestMapping(value = "/deleteCache", method = RequestMethod.GET)
    public ResultBean deleteCache(String key) {
        cacheHandler.delete(key);
        return callback(null);
    }

    /**
     * 获取缓存
     *
     * @return
     */
    @RequestMapping(value = "/getCache", method = RequestMethod.GET)
    public ResultBean getCache(String key) {
        return callback(cacheHandler.get(key));
    }

}
