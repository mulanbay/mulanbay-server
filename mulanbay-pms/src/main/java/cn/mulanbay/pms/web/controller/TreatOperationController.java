package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.TreatOperation;
import cn.mulanbay.pms.persistent.domain.TreatRecord;
import cn.mulanbay.pms.persistent.dto.TreatOperationStat;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.health.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手术
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/treatOperation")
public class TreatOperationController extends BaseController {

    private static Class<TreatOperation> beanClass = TreatOperation.class;

    @Autowired
    TreatService treatService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TreatOperationSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("treatDate", Sort.DESC);
        pr.addSort(s);
        PageResult<TreatOperation> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 获取看病用药的手术分类列表
     *
     * @return
     */
    @RequestMapping(value = "/getTreatOperationCategoryTree")
    public ResultBean getTreatOperationCategoryTree(TreatOperationCategorySearch sf) {

        try {
            List<String> categoryList = treatService.getTreatOperationCategoryList(sf);
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
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取看病手术的疾病分类列表异常",
                    e);
        }
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid TreatOperationFormRequest formRequest) {
        TreatOperation bean = new TreatOperation();
        BeanCopy.copyProperties(formRequest, bean);
        TreatRecord treatRecord = this.getUserEntity(TreatRecord.class, formRequest.getTreatRecordId(), formRequest.getUserId());
        bean.setTreatRecord(treatRecord);
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
        TreatOperation bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid TreatOperationFormRequest formRequest) {
        TreatOperation bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        TreatRecord treatRecord = this.getUserEntity(TreatRecord.class, formRequest.getTreatRecordId(), formRequest.getUserId());
        bean.setTreatRecord(treatRecord);
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
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 总的概要统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(TreatOperationStatSearch sf) {
        List<TreatOperationStat> data = treatService.treatOperationStat(sf);
        TreatOperationStat total = new TreatOperationStat();
        BigInteger n = BigInteger.ZERO;
        BigDecimal totalFee = BigDecimal.ZERO;
        for (TreatOperationStat bean : data) {
            n = n.add(bean.getTotalCount());
            totalFee = totalFee.add(bean.getTotalFee());
        }
        total.setName("小计");
        total.setTotalCount(n);
        total.setTotalFee(totalFee);
        data.add(total);
        return callback(data);
    }

    /**
     * 获取最近一次的手术
     *
     * @return
     */
    @RequestMapping(value = "/getLastTreatOperation", method = RequestMethod.GET)
    public ResultBean getLastTreatOperation(@Valid TreatOperationLastGetRequest getRequest) {
        TreatOperation bean = treatService.getLastTreatOperation(getRequest.getName(), getRequest.getUserId());
        return callback(bean);
    }


}
