package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.PriceRegion;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.buy.PriceRegionFormRequest;
import cn.mulanbay.pms.web.bean.request.buy.PriceRegionSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 价格区间
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/priceRegion")
public class PriceRegionController extends BaseController {

    private static Class<PriceRegion> beanClass = PriceRegion.class;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(PriceRegionSearch sf) {
        return callbackDataGrid(getPriceRegionResult(sf));
    }

    private PageResult<PriceRegion> getPriceRegionResult(PriceRegionSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<PriceRegion> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid PriceRegionFormRequest formRequest) {
        PriceRegion bean = new PriceRegion();
        BeanCopy.copyProperties(formRequest, bean);
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        PriceRegion bean = baseService.getObjectWithUser(beanClass, getRequest.getId().intValue(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid PriceRegionFormRequest formRequest) {
        PriceRegion bean = baseService.getObjectWithUser(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
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
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Integer.class,deleteRequest.getUserId());
        return callback(null);
    }


}
