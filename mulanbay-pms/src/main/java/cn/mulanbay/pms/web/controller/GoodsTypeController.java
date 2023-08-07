package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.GoodsType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.buy.GoodsTreeSearch;
import cn.mulanbay.pms.web.bean.request.buy.GoodsTypeFormRequest;
import cn.mulanbay.pms.web.bean.request.buy.GoodsTypeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.buy.GoodsTypeVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController extends BaseController {

    private static Class<GoodsType> beanClass = GoodsType.class;

    /**
     * @return
     */
    @RequestMapping(value = "/getGoodsTypeTree")
    public ResultBean getGoodsTypeTree(GoodsTreeSearch goodsTreeSearch) {

        try {
            GoodsTypeSearch sf = new GoodsTypeSearch();
            sf.setPage(PageRequest.NO_PAGE);
            sf.setPid(goodsTreeSearch.getPid());
            sf.setStatus(CommonStatus.ENABLE);
            sf.setUserId(goodsTreeSearch.getUserId());
            PageResult<GoodsType> pr = getGoodsTypeResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<GoodsType> gtList = pr.getBeanList();
            for (GoodsType gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            GoodsTreeSearch.RootType rootType = goodsTreeSearch.getRootType();
            if (rootType == null || rootType == GoodsTreeSearch.RootType.COMMON) {
                return callback(TreeBeanUtil.addRoot(list, goodsTreeSearch.getNeedRoot()));
            } else {
                TreeBean root = new TreeBean();
                root.setId("0");
                root.setText("根");
                root.setChildren(list);
                List<TreeBean> newList = new ArrayList<TreeBean>();
                newList.add(root);
                return callback(newList);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取商品类型树异常",
                    e);
        }
    }

    /**
     * 获取商品类型树（前面接口采用树形结构）
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(GoodsTypeSearch sf) {
        PageResult<GoodsType> pageResult = getGoodsTypeResult(sf);
        //构造树
        List<GoodsTypeVo> voList = this.getChildren(0,pageResult.getBeanList());
        PageResult<GoodsTypeVo> result = new PageResult();
        result.setBeanList(voList);
        result.setMaxRow(pageResult.getMaxRow());
        result.setPageSize(pageResult.getPageSize());
        return callbackDataGrid(result);
    }

    /**
     * 获取子列表
     * @param pid
     * @param list
     * @return
     */
    private List<GoodsTypeVo> getChildren(int pid,List<GoodsType> list){
        List<GoodsTypeVo> children = new ArrayList<>();
        for(GoodsType cc : list){
            int myPid = cc.getParentId().intValue();
            if(myPid==pid){
                GoodsTypeVo child = new GoodsTypeVo();
                BeanCopy.copyProperties(cc, child);
                child.setParentId(cc.getParentId());
                child.setParentName(cc.getParentName());
                //寻找下一个子列表
                List<GoodsTypeVo> c2 = getChildren(cc.getId(), list);
                child.setChildren(c2);
                //加入到结果集
                children.add(child);
            }
        }
        return children;
    }

    /**
     * 获取商品
     *
     * @return
     */
    @RequestMapping(value = "/getData2", method = RequestMethod.GET)
    public ResultBean getData2(GoodsTypeSearch sf) {
        PageResult<GoodsType> pageResult = getGoodsTypeResult(sf);
        return callbackDataGrid(pageResult);
    }


    private PageResult<GoodsType> getGoodsTypeResult(GoodsTypeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<GoodsType> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid GoodsTypeFormRequest formRequest) {
        GoodsType goodsType = new GoodsType();
        BeanCopy.copyProperties(formRequest, goodsType);
        GoodsType parent = baseService.getObject(beanClass, formRequest.getParentId());
        goodsType.setParent(parent);
        if (goodsType.getBehaviorName() == null) {
            goodsType.setBehaviorName(goodsType.getName());
        }
        baseService.saveObject(goodsType);
        return callback(goodsType);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        GoodsType bean = this.getUserEntity(beanClass, getRequest.getId().intValue(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid GoodsTypeFormRequest formRequest) {
        GoodsType goodsType = this.getUserEntity(beanClass, formRequest.getId().intValue(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, goodsType);
        GoodsType parent = baseService.getObject(GoodsType.class, formRequest.getParentId());
        goodsType.setParent(parent);
        if (goodsType.getBehaviorName() == null) {
            goodsType.setBehaviorName(goodsType.getName());
        }
        baseService.updateObject(goodsType);
        return callback(goodsType);
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
