package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.AccountSnapshotInfo;
import cn.mulanbay.pms.persistent.service.AccountService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.fund.AccountSnapshotInfoSearch;
import cn.mulanbay.pms.web.bean.request.fund.DeleteAccountSnapshotInfoSearch;
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
 * 账户快照信息
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/accountSnapshotInfo")
public class AccountSnapshotInfoController extends BaseController {

    private static Class<AccountSnapshotInfo> beanClass = AccountSnapshotInfo.class;

    @Autowired
    AccountService accountService;

    /**
     * 获取快照树
     * @return
     */
    @RequestMapping(value = "/getSnapshotInfoTree")
    public ResultBean getSnapshotInfoTree(AccountSnapshotInfoSearch sf) {
        try {
            //分页，获取最新的20页
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort s = new Sort("createdTime", Sort.DESC);
            pr.addSort(s);
            List<AccountSnapshotInfo> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (AccountSnapshotInfo gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取账户树异常",
                    e);
        }
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid DeleteAccountSnapshotInfoSearch bdr) {
        accountService.deleteSnapshot(bdr.getUserId(), bdr.getId());
        return callback(null);
    }
}
