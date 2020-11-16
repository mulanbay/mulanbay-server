package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.ScoreConfigGroup;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.UserScoreService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.user.ScoreConfigGroupCTFormRequest;
import cn.mulanbay.pms.web.bean.request.user.ScoreConfigGroupFormRequest;
import cn.mulanbay.pms.web.bean.request.user.ScoreConfigGroupSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评分配置组
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/scoreConfigGroup")
public class ScoreConfigGroupController extends BaseController {

    private static Class<ScoreConfigGroup> beanClass = ScoreConfigGroup.class;

    @Autowired
    UserScoreService userScoreService;

    /**
     * 获取数据组树
     *
     * @return
     */
    @RequestMapping(value = "/getScoreConfigGroupTree")
    public ResultBean getScoreConfigGroupTree(ScoreConfigGroupSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            List<ScoreConfigGroup> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (ScoreConfigGroup gt : gtList) {
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
    public ResultBean getData(ScoreConfigGroupSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<ScoreConfigGroup> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ScoreConfigGroupFormRequest formRequest) {
        ScoreConfigGroup bean = new ScoreConfigGroup();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }

    /**
     * 以模板新增
     *
     * @return
     */
    @RequestMapping(value = "/createByTemplate", method = RequestMethod.POST)
    public ResultBean createByTemplate(@RequestBody @Valid ScoreConfigGroupCTFormRequest formRequest) {
        userScoreService.createGroupFromTemplate(formRequest.getTemplateId(), formRequest.getCode(), formRequest.getName());
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        ScoreConfigGroup bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ScoreConfigGroupFormRequest formRequest) {
        ScoreConfigGroup bean = baseService.getObject(beanClass, formRequest.getId());
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

}
