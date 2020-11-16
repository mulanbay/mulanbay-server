package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.BuyType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.buy.BuyTypeDeleteRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyTypeFormRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyTypeGetRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyTypeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 购买类型（消费来源）
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/buyType")
public class BuyTypeController extends BaseController {

    private static Class<BuyType> beanClass = BuyType.class;

    /**
     * 来源树
     * @return
     */
    @RequestMapping(value = "/getBuyTypeTree")
    public ResultBean getBuyTypeTree(BuyTypeSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            PageResult<BuyType> pr = getBuyTypeResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<BuyType> gtList = pr.getBeanList();
            for (BuyType gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取购买来源树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BuyTypeSearch sf) {
        return callbackDataGrid(getBuyTypeResult(sf));
    }

    private PageResult<BuyType> getBuyTypeResult(BuyTypeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<BuyType> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BuyTypeFormRequest bean) {
        BuyType buyType = new BuyType();
        BeanCopy.copyProperties(bean, buyType);
        baseService.saveObject(buyType);
        this.setOperateBeanId(buyType.getId());
        return callback(buyType);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid BuyTypeGetRequest gr) {
        BuyType buyType = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(buyType);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BuyTypeFormRequest bean) {
        BuyType buyType = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, buyType);
        baseService.updateObject(buyType);
        return callback(buyType);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid BuyTypeDeleteRequest deleteRequest) {
        //todo 先判断旗下是否有消费记录

        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Integer.class,deleteRequest.getUserId());
        return callback(null);
    }

}
