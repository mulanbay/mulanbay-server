package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.SportType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.sport.SportTypeFormRequest;
import cn.mulanbay.pms.web.bean.request.sport.SportTypeSearch;
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
 * 运动类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/sportType")
public class SportTypeController extends BaseController {

    private static Class<SportType> beanClass = SportType.class;

    /**
     * 为锻炼管理界面的下拉菜单使用
     *
     * @return
     */
    @RequestMapping(value = "/getSportTypeTree")
    public ResultBean getSportTypeTree(SportTypeSearch sf) {

        try {
            sf.setStatus(CommonStatus.ENABLE);
            sf.setPage(PageRequest.NO_PAGE);
            PageResult<SportType> pr = getSportTypePageResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<SportType> gtList = pr.getBeanList();
            for (SportType gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取运动类型树异常",
                    e);
        }
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SportTypeSearch sf) {
        return callbackDataGrid(getSportTypePageResult(sf));
    }

    private PageResult<SportType> getSportTypePageResult(SportTypeSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<SportType> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SportTypeFormRequest formRequest) {
        SportType bean = new SportType();
        BeanCopy.copyProperties(formRequest, bean);
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        SportType bean = this.getUserEntity(beanClass, getRequest.getId().intValue(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SportTypeFormRequest formRequest) {
        SportType bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        baseService.updateObject(bean);
        return callback(bean);
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
