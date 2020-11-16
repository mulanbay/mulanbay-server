package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.TreatDrug;
import cn.mulanbay.pms.persistent.domain.TreatRecord;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.health.TreatDrugCategorySearch;
import cn.mulanbay.pms.web.bean.request.health.TreatDrugFormRequest;
import cn.mulanbay.pms.web.bean.request.health.TreatDrugLastGetRequest;
import cn.mulanbay.pms.web.bean.request.health.TreatDrugSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
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

/**
 * 药品
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/treatDrug")
public class TreatDrugController extends BaseController {

    private static Class<TreatDrug> beanClass = TreatDrug.class;

    @Autowired
    TreatService treatService;

    /**
     * 获取看病用药的疾病分类列表
     *
     * @return
     */
    @RequestMapping(value = "/getTreatDrugCategoryTree")
    public ResultBean getTreatDrugCategoryTree(TreatDrugCategorySearch sf) {

        try {
            List<String> categoryList = treatService.getTreatDrugCategoryList(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            int i = 0;
            for (String gt : categoryList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt);
                tb.setText(gt);
                list.add(tb);
                i++;
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取看病用药的疾病分类列表异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TreatDrugSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("createdTime", Sort.DESC);
        pr.addSort(s);
        PageResult<TreatDrug> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid TreatDrugFormRequest formRequest) {
        TreatDrug bean = new TreatDrug();
        BeanCopy.copyProperties(formRequest, bean);
        TreatRecord treatRecord = this.getUserEntity(TreatRecord.class, formRequest.getTreatRecordId(), formRequest.getUserId());
        bean.setTreatRecord(treatRecord);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        TreatDrug bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid TreatDrugFormRequest formRequest) {
        TreatDrug bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        TreatRecord treatRecord = this.getUserEntity(TreatRecord.class, formRequest.getTreatRecordId(), formRequest.getUserId());
        bean.setTreatRecord(treatRecord);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 最近一次的用药
     *
     * @return
     */
    @RequestMapping(value = "/getLastTreatDrug", method = RequestMethod.GET)
    public ResultBean getLastTreatDrug(@Valid TreatDrugLastGetRequest getRequest) {
        TreatDrug bean = treatService.getLastTreatDrug(getRequest.getName(), getRequest.getUserId());
        return callback(bean);
    }
}
