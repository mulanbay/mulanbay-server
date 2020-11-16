package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.CommonResult;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.CommandHandler;
import cn.mulanbay.pms.persistent.domain.CommandConfig;
import cn.mulanbay.pms.web.bean.request.system.CommandConfigSearch;
import cn.mulanbay.pms.web.bean.request.system.CommandSendRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 命令配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/commandConfig")
public class CommandConfigController extends BaseController {

    private static Class<CommandConfig> beanClass = CommandConfig.class;

    @Autowired
    CommandHandler commandHandler;

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(CommandConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<CommandConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 发送（不能同时操作同一个命令）
     *
     * @return
     */
    @RequestMapping(value = "/sendCmd", method = RequestMethod.POST)
    public ResultBean sendCmd(@RequestBody @Valid CommandSendRequest csr) {
        CommonResult cr = commandHandler.handleCmd(csr.getId(), csr.isSync(), csr.getUserId());
        if (cr.getCode() != ErrorCode.SUCCESS) {
            return callbackErrorCode(cr.getCode());
        } else {
            return callback(cr.getInfo());
        }
    }


}
