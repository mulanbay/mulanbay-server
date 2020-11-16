package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.thread.EnhanceThread;
import cn.mulanbay.common.thread.ThreadManager;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.web.bean.response.thread.ThreadVo;
import cn.mulanbay.web.bean.request.PageSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程管理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/thread")
public class ThreadController extends BaseController {

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
        List<EnhanceThread> list = ThreadManager.getInstance().getThreads(fromIndex, toIndex);
        PageResult<ThreadVo> res = new PageResult<>(p, ps);
        List<ThreadVo> beanList = new ArrayList();
        for (EnhanceThread et : list) {
            ThreadVo tb = new ThreadVo();
            BeanCopy.copyProperties(et, tb);
            beanList.add(tb);
        }
        res.setBeanList(beanList);
        res.setMaxRow(ThreadManager.getInstance().getThreadCount());
        return callbackDataGrid(res);
    }

    /**
     * 获取线程详情
     * @return
     */
    @RequestMapping(value = "/getThreadInfo", method = RequestMethod.GET)
    public ResultBean getThreadInfo(Long id) {
        EnhanceThread thread = ThreadManager.getInstance().getThread(id);
        return callback(thread.getThreadInfo());
    }

}
