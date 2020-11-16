package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.schedule.TaskServerSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.schedule.domain.TaskServer;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度服务器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/taskServer")
public class TaskServerController extends BaseController {

    private static Class<TaskServer> beanClass = TaskServer.class;

    /**
     * 获取服务器树
     * @return
     */
    @RequestMapping(value = "/getTaskServerTree")
    public ResultBean getBuyTypeTree(Boolean needRoot) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<TaskServer> gtList = baseService.getBeanList(beanClass, 0, 0, null);
            for (TaskServer gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getDeployId());
                tb.setText(gt.getDeployId());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取服务器树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(TaskServerSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("startTime", Sort.DESC);
        pr.addSort(s1);
        PageResult<TaskServer> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }
}
