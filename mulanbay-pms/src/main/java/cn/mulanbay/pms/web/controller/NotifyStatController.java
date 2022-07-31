package cn.mulanbay.pms.web.controller;

import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.NotifyStatHandler;
import cn.mulanbay.pms.persistent.domain.UserNotify;
import cn.mulanbay.pms.persistent.dto.NotifyResult;
import cn.mulanbay.pms.web.bean.request.notify.NotifyStatSearch;
import cn.mulanbay.pms.web.bean.request.notify.UserNotifyStatDeleteRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 提醒统计
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/notifyStat")
public class NotifyStatController extends BaseController {

    @Value("${system.notifyStat.queryExpireSeconds}")
    int queryExpireSeconds;

    @Autowired
    NotifyStatHandler notifyStatHandler;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(NotifyStatSearch sf) {
        PageRequest pr = sf.buildQuery();
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        pr.setBeanClass(UserNotify.class);

        PageResult<UserNotify> unResult = baseService.getBeanResult(pr);
        List<NotifyResult> list = new ArrayList<>();
        int i = 1;
        for (UserNotify un : unResult.getBeanList()) {
            NotifyResult nr = notifyStatHandler.getNotifyResult(un, queryExpireSeconds);
            nr.setId(i);
            list.add(nr);
            i++;
        }
        PageResult<NotifyResult> res = new PageResult<>(sf.getPage(), sf.getPageSize());
        res.setBeanList(list);
        res.setMaxRow(unResult.getMaxRow());
        return callbackDataGrid(res);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteCache", method = RequestMethod.POST)
    public ResultBean deleteCache(@RequestBody @Valid UserNotifyStatDeleteRequest unsd) {
        if(unsd.getUserNotifyId()==null){
            //删除全部
            notifyStatHandler.deleteCache(unsd.getUserId());
        }else{
            notifyStatHandler.deleteCache(unsd.getUserId(),unsd.getUserNotifyId());
        }
        return callback(null);
    }

}
