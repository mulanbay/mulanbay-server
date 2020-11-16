package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.BusinessTrip;
import cn.mulanbay.pms.persistent.domain.Company;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.work.BusinessTripFormRequest;
import cn.mulanbay.pms.web.bean.request.work.BusinessTripSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 出差管理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/businessTrip")
public class BusinessTripController extends BaseController {

    private static Class<BusinessTrip> beanClass = BusinessTrip.class;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BusinessTripSearch sf) {
        return callbackDataGrid(getBusinessTripResult(sf));
    }

    private PageResult<BusinessTrip> getBusinessTripResult(BusinessTripSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("tripDate", Sort.DESC);
        pr.addSort(sort);
        PageResult<BusinessTrip> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BusinessTripFormRequest formRequest) {
        Company company = this.getUserEntity(Company.class, formRequest.getCompanyId(), formRequest.getUserId());
        BusinessTrip bean = new BusinessTrip();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCompany(company);
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
        BusinessTrip bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BusinessTripFormRequest formRequest) {
        BusinessTrip bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        Company company = this.getUserEntity(Company.class, formRequest.getCompanyId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCompany(company);
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
    @ResponseBody
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

}
