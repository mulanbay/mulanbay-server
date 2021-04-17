package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerManager;
import cn.mulanbay.business.handler.HandlerResult;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.web.bean.request.handler.HandClassRequest;
import cn.mulanbay.pms.web.bean.request.handler.HandCmdRequest;
import cn.mulanbay.pms.web.bean.response.handler.HandlerVo;
import cn.mulanbay.web.bean.request.PageSearch;
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
 * 处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/handler")
public class HandlerController extends BaseController {

    @Autowired
    HandlerManager handlerManager;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(PageSearch sf) {
        int p = sf.getPage();
        int ps = sf.getPageSize();
        int fromIndex = (p - 1) * ps;
        int toIndex = p * ps;
        List<BaseHandler> handlerList = handlerManager.getHandlerList();
        if (StringUtil.isEmpty(handlerList)) {
            return callbackDataGrid(new PageResult<>(p, ps));
        }
        int l = handlerList.size();
        if (toIndex > l) {
            toIndex = l;
        }
        PageResult<HandlerVo> res = new PageResult<>(p, ps);
        List<HandlerVo> beanList = new ArrayList();
        for (int i = fromIndex; i < toIndex; i++) {
            HandlerVo tb = new HandlerVo();
            BaseHandler bh = handlerList.get(i);
            tb.setId(i+1L);
            tb.setHandlerName(bh.getHandlerName());
            tb.setClassName(bh.getClass().getName());
            tb.setHash(bh.hashCode());
            beanList.add(tb);
        }
        res.setBeanList(beanList);
        res.setMaxRow(handlerList.size());
        return callbackDataGrid(res);
    }

    /**
     * 获取处理器详情
     * @param className
     * @return
     */
    @RequestMapping(value = "/getHandlerInfo", method = RequestMethod.GET)
    public ResultBean getHandlerInfo(String className) {
        BaseHandler baseHandler = handlerManager.getHandler(className);
        return callback(baseHandler.getHandlerInfo());
    }

    /**
     * 获取处理器执行的命令
     * @param className
     * @return
     */
    @RequestMapping(value = "/getSupportCmd", method = RequestMethod.GET)
    public ResultBean getSupportCmd(String className) {
        BaseHandler baseHandler = handlerManager.getHandler(className);
        return callback(baseHandler.getSupportCmdList());
    }

    /**
     * 处理器接受命令
     * @param hc
     * @return
     */
    @RequestMapping(value = "/handCmd", method = RequestMethod.POST)
    public ResultBean handCmd(@RequestBody @Valid HandCmdRequest hc) {
        BaseHandler baseHandler = handlerManager.getHandler(hc.getClassName());
        HandlerResult hr = baseHandler.handle(hc.getCmd());
        return callback(hr);
    }

    /**
     * 自检
     * @param hc
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResultBean check(@RequestBody @Valid HandClassRequest hc) {
        BaseHandler baseHandler = handlerManager.getHandler(hc.getClassName());
        boolean b = baseHandler.selfCheck();
        return callback(b);
    }

    /**
     * 重载
     * @param hc
     * @return
     */
    @RequestMapping(value = "/reload", method = RequestMethod.POST)
    public ResultBean reload(@RequestBody @Valid HandClassRequest hc) {
        BaseHandler baseHandler = handlerManager.getHandler(hc.getClassName());
        return callback(true);
    }

}
