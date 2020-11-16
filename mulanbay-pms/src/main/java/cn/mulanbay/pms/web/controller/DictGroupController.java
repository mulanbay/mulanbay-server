package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.DictGroup;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.DictService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.dict.DictGroupFormRequest;
import cn.mulanbay.pms.web.bean.request.dict.DictGroupSearch;
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
 * 数据字典组
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/dictGroup")
public class DictGroupController extends BaseController {

    private static Class<DictGroup> beanClass = DictGroup.class;

    @Autowired
    DictService dictService;

    /**
     * 获取数据组树
     *
     * @return
     */
    @RequestMapping(value = "/getDictGroupTree")
    public ResultBean getDictGroupTree(DictGroupSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            sf.setPage(PageRequest.NO_PAGE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort sort = new Sort("orderIndex", Sort.ASC);
            pr.addSort(sort);
            List<DictGroup> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (DictGroup gt : gtList) {
                TreeBean tb = new TreeBean();
                if ("code".equals(sf.getIdField())) {
                    tb.setId(gt.getCode());
                } else {
                    tb.setId(gt.getId().toString());
                }
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取数据组树异常",
                    e);
        }
    }


    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(DictGroupSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.DESC);
        pr.addSort(sort);
        PageResult<DictGroup> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid DictGroupFormRequest formRequest) {
        DictGroup bean = new DictGroup();
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
        DictGroup bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid DictGroupFormRequest formRequest) {
        DictGroup bean = baseService.getObject(beanClass, formRequest.getId());
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
        String[] ss = deleteRequest.getIds().split(",");
        for(String s : ss){
            dictService.deleteDictGroup(Long.valueOf(s));
        }
        return callback(null);
    }

}
