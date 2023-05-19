package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.ConsumeHandler;
import cn.mulanbay.pms.handler.bean.GoodsLifetimeMatchBean;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.domain.GoodsLifetime;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.buy.*;
import cn.mulanbay.pms.web.bean.response.buy.GoodsLifetimeGetAndMatchVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 商品寿命配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/goodsLifetime")
public class GoodsLifetimeController extends BaseController {

    private static Class<GoodsLifetime> beanClass = GoodsLifetime.class;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    @Autowired
    ConsumeHandler consumeHandler;

    /**
     * 获取列表
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(GoodsLifetimeSearch sf) {
        return callbackDataGrid(getGoodsLifetimeResult(sf));
    }

    private PageResult<GoodsLifetime> getGoodsLifetimeResult(GoodsLifetimeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<GoodsLifetime> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid GoodsLifetimeFormRequest formRequest) {
        GoodsLifetime bean = new GoodsLifetime();
        BeanCopy.copyProperties(formRequest, bean);
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
        GoodsLifetime bean = baseService.getObject(beanClass,getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid GoodsLifetimeFormRequest formRequest) {
        GoodsLifetime bean = baseService.getObject(beanClass,formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
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
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 商品寿命匹配
     *
     * @return
     */
    @RequestMapping(value = "/aiMatch", method = RequestMethod.POST)
    public ResultBean aiMatch(@RequestBody @Valid GoodsNameAiMatchRequest mr) {
        GoodsLifetimeMatchBean bean = consumeHandler.matchLifetime(mr.getGoodsName());
        if(bean==null){
            return callback(null);
        }
        return callback(bean);
    }

    /**
     * 商品寿命匹配
     *
     * @return
     */
    @RequestMapping(value = "/getAndMath", method = RequestMethod.POST)
    public ResultBean getAndMath(@RequestBody @Valid GoodsLifetimeGetAndMatchRequest mr) {
        GoodsLifetimeSearch sf = new GoodsLifetimeSearch();
        sf.setPage(mr.getPage());
        sf.setPageSize(mr.getPageSize());
        sf.setNeedTotal(false);
        PageResult<GoodsLifetime> pr = this.getGoodsLifetimeResult(sf);
        GoodsLifetimeMatchBean match = consumeHandler.matchLifetime(mr.getGoodsName(),pr.getBeanList(),false);
        GoodsLifetimeGetAndMatchVo vo = new GoodsLifetimeGetAndMatchVo();
        vo.setConfigs(pr);
        vo.setMatch(match);
        return callback(vo);
    }

    /**
     * 商品寿命匹配
     *
     * @return
     */
    @RequestMapping(value = "/compareAndMath", method = RequestMethod.POST)
    public ResultBean compareAndMath(@RequestBody @Valid GoodsLifetimeCompareAndMatchRequest mr) {
        GoodsLifetime bean = baseService.getObject(beanClass,mr.getId());
        float m = ahaNLPHandler.sentenceSimilarity(bean.getKeywords(),mr.getGoodsName());
        GoodsLifetimeMatchBean match = new GoodsLifetimeMatchBean();
        BeanCopy.copyProperties(bean,match);
        match.setMatch(m);
        return callback(match);
    }
}
