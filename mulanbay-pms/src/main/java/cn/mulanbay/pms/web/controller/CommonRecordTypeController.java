package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.CommonRecordType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.CommonRecordService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordTypeFormRequest;
import cn.mulanbay.pms.web.bean.request.commonrecord.CommonRecordTypeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用记录类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/commonRecordType")
public class CommonRecordTypeController extends BaseController {

    private static Class<CommonRecordType> beanClass = CommonRecordType.class;

    @Autowired
    CommonRecordService commonRecordService;

    /**
     * 获取类型树
     *
     * @return
     */
    @RequestMapping(value = "/getCommonRecordTypeTree")
    public ResultBean getCommonRecordTypeTree(CommonRecordTypeSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            sf.setPage(PageRequest.NO_PAGE);
            PageResult<CommonRecordType> pr = getCommonRecordTypeResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<CommonRecordType> gtList = pr.getBeanList();
            for (CommonRecordType gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "通用记录类型异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(CommonRecordTypeSearch sf) {
        return callbackDataGrid(getCommonRecordTypeResult(sf));
    }

    private PageResult<CommonRecordType> getCommonRecordTypeResult(CommonRecordTypeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<CommonRecordType> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid CommonRecordTypeFormRequest formRequest) {
        CommonRecordType bean = new CommonRecordType();
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
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        CommonRecordType bean = this.getUserEntity(beanClass, getRequest.getId().intValue(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid CommonRecordTypeFormRequest formRequest) {
        CommonRecordType bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
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
        String[] ids = deleteRequest.getIds().split(",");
        for (String s : ids) {
            CommonRecordType bean = this.getUserEntity(beanClass, Integer.valueOf(s), deleteRequest.getUserId());
            commonRecordService.deleteCommonRecordType(bean);

        }
        return callback(null);
    }


}
