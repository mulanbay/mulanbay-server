package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.SportMilestone;
import cn.mulanbay.pms.persistent.domain.SportType;
import cn.mulanbay.pms.persistent.service.SportExerciseService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.SportMilestoneRefresh;
import cn.mulanbay.pms.web.bean.request.sport.SportMilestoneFormRequest;
import cn.mulanbay.pms.web.bean.request.sport.SportMilestoneSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
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
 * 运动里程碑
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/sportMilestone")
public class SportMilestoneController extends BaseController {

    private static Class<SportMilestone> beanClass = SportMilestone.class;

    @Autowired
    SportExerciseService sportExerciseService;

    /**
     * 获取运动里程碑树
     *
     * @return
     */
    @RequestMapping(value = "/getSportMilestoneTree")
    public ResultBean getSportMilestoneTree(CommonTreeSearch cts) {

        try {
            SportMilestoneSearch sf = new SportMilestoneSearch();
            sf.setPage(PageRequest.NO_PAGE);
            sf.setUserId(cts.getUserId());
            PageResult<SportMilestone> pr = getSportMilestonePageResult(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<SportMilestone> gtList = pr.getBeanList();
            for (SportMilestone gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, cts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取运动里程碑树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(SportMilestoneSearch sf) {
        return callbackDataGrid(getSportMilestonePageResult(sf));
    }

    private PageResult<SportMilestone> getSportMilestonePageResult(SportMilestoneSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<SportMilestone> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid SportMilestoneFormRequest formRequest) {
        SportMilestone bean = new SportMilestone();
        BeanCopy.copyProperties(formRequest, bean);
        SportType sportType = this.getUserEntity(SportType.class, formRequest.getSportTypeId(), formRequest.getUserId());
        bean.setSportType(sportType);
        bean.setCreatedTime(new Date());
        checkSportMilestone(bean);
        baseService.saveObject(bean);
        return callback(null);
    }

    private void checkSportMilestone(SportMilestone bean) {
        //1： 判断里程和时间
        if (bean.getKilometres() == null && bean.getMinutes() == null) {
            throw new ApplicationException(PmsErrorCode.SPROT_MILESTONE_KM_MN_NULL);
        } else if (bean.getKilometres() == null && bean.getMinutes() != null) {
            throw new ApplicationException(PmsErrorCode.SPROT_MILESTONE_KM_NULL);
        }
        if (bean.getId() == null) {
            //新增
            // 排序号是否连续
            Short maxOrderIndex = sportExerciseService.getMaxOrderIndexOfSportMilestone(bean.getSportType().getId(), bean.getId());
            if (maxOrderIndex == null) {
                if (bean.getOrderIndex() != 1) {
                    throw new ApplicationException(PmsErrorCode.SPROT_MILESTONE_ORDER_INDEX_ERROR);
                }
            } else {
                if (bean.getOrderIndex() - 1 != maxOrderIndex.shortValue()) {
                    throw new ApplicationException(PmsErrorCode.SPROT_MILESTONE_ORDER_INDEX_ERROR);
                }
            }
        } else {
            //todo 判断是否重复
        }

    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        SportMilestone bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid SportMilestoneFormRequest formRequest) {
        SportMilestone bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        SportType sportType = this.getUserEntity(SportType.class, formRequest.getSportTypeId(), formRequest.getUserId());
        bean.setSportType(sportType);
        checkSportMilestone(bean);
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
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }


    /**
     * 刷新
     *
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResultBean refresh(SportMilestoneRefresh sf) {
        sportExerciseService.updateAndRefreshSportMilestone(sf.isReInit(), sf.getSportTypeId());
        return callback(null);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(SportMilestoneSearch sf) {
        PageResult<SportMilestone> pr = this.getSportMilestonePageResult(sf);
        ChartData chartData = new ChartData();
        if (sf.getSportTypeId() == null) {
            chartData.setTitle("里程碑统计");
        } else {
            SportType sportType = this.getUserEntity(SportType.class, sf.getSportTypeId(), sf.getUserId());
            chartData.setTitle("[" + sportType.getName() + "]里程碑统计");
        }
        chartData.setLegendData(new String[]{"天"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("天");
        for (SportMilestone bean : pr.getBeanList()) {
            String name = bean.getName();
            if (bean.getAlais() != null) {
                name += "(" + bean.getAlais() + ")";
            }
            chartData.getXdata().add(name);
            yData1.getData().add(bean.getCostDays());
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }
}
