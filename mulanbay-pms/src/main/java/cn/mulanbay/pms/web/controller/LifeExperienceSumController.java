package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.Company;
import cn.mulanbay.pms.persistent.domain.LifeExperience;
import cn.mulanbay.pms.persistent.domain.LifeExperienceSum;
import cn.mulanbay.pms.persistent.enums.ExperienceType;
import cn.mulanbay.pms.persistent.service.CompanyService;
import cn.mulanbay.pms.persistent.service.LifeExperienceService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceReviseFormRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceSumAnalyseSearch;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceSumFormRequest;
import cn.mulanbay.pms.web.bean.request.life.LifeExperienceSumSearch;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人生经历汇总
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/lifeExperienceSum")
public class LifeExperienceSumController extends BaseController {

    private static Class<LifeExperienceSum> beanClass = LifeExperienceSum.class;

    @Autowired
    CompanyService companyService;

    @Autowired
    LifeExperienceService lifeExperienceService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(LifeExperienceSumSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("year", Sort.DESC);
        pr.addSort(sort);
        PageResult<LifeExperienceSum> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid LifeExperienceSumFormRequest bean) {
        LifeExperienceSum lifeExperienceSum = new LifeExperienceSum();
        BeanCopy.copyProperties(bean, lifeExperienceSum);
        lifeExperienceSum.setCreatedTime(new Date());
        baseService.saveObject(lifeExperienceSum);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        LifeExperienceSum bean = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid LifeExperienceSumFormRequest bean) {
        LifeExperienceSum lifeExperienceSum = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, lifeExperienceSum);
        lifeExperienceSum.setLastModifyTime(new Date());
        baseService.updateObject(lifeExperienceSum);
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
     * 统计分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse")
    public ResultBean analyse(LifeExperienceSumAnalyseSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("year", Sort.ASC);
        pr.addSort(sort);
        PageResult<LifeExperienceSum> qr = baseService.getBeanResult(pr);
        String title = "人生经历汇总分析";
        ChartShadowData chartShadowData = new ChartShadowData();
        chartShadowData.setTitle(title);
        chartShadowData.addLegend("学习");
        chartShadowData.addLegend("旅行");
        chartShadowData.addLegend("工作");
        chartShadowData.addLegend("休息");
        ChartShadowSerieData studySerie = new ChartShadowSerieData("学习", "总量");
        ChartShadowSerieData travelSerie = new ChartShadowSerieData("旅行", "总量");
        ChartShadowSerieData workSerie = new ChartShadowSerieData("工作", "总量");
        ChartShadowSerieData restSerie = new ChartShadowSerieData("休息", "总量");
        int totalDays = 0;
        int totalStudyDays = 0;
        int totalWorkDays = 0;
        int totalTravelDays = 0;
        int totalRestDays = 0;
        for (LifeExperienceSum bean : qr.getBeanList()) {
            chartShadowData.addYaxis(String.valueOf(bean.getYear()));
            studySerie.addData(String.valueOf(bean.getStudyDays()));
            travelSerie.addData(String.valueOf(bean.getTravelDays()));
            workSerie.addData(String.valueOf(bean.getWorkDays()));
            int restDays = bean.getTotalDays() - bean.getStudyDays() - bean.getTravelDays() - bean.getWorkDays();
            restSerie.addData(String.valueOf(restDays));
            totalDays += bean.getTotalDays();
            totalStudyDays += bean.getStudyDays();
            totalTravelDays += bean.getTravelDays();
            totalWorkDays += bean.getWorkDays();
            totalRestDays += restDays;
        }
        chartShadowData.addSerie(studySerie);
        chartShadowData.addSerie(travelSerie);
        chartShadowData.addSerie(workSerie);
        chartShadowData.addSerie(restSerie);

        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("总时间统计");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("天数");
        //学习
        chartPieData.getXdata().add(String.valueOf(totalStudyDays));
        ChartPieSerieDetailData studyDataDetail = new ChartPieSerieDetailData();
        studyDataDetail.setName("学习");
        studyDataDetail.setValue(totalStudyDays);
        serieData.getData().add(studyDataDetail);

        //工作
        chartPieData.getXdata().add(String.valueOf(totalWorkDays));
        ChartPieSerieDetailData workDataDetail = new ChartPieSerieDetailData();
        workDataDetail.setName("工作");
        workDataDetail.setValue(totalWorkDays);
        serieData.getData().add(workDataDetail);

        //旅行
        chartPieData.getXdata().add(String.valueOf(totalTravelDays));
        ChartPieSerieDetailData travelDataDetail = new ChartPieSerieDetailData();
        travelDataDetail.setName("旅行");
        travelDataDetail.setValue(totalTravelDays);
        serieData.getData().add(travelDataDetail);

        //休息
        chartPieData.getXdata().add(String.valueOf(totalRestDays));
        ChartPieSerieDetailData restDataDetail = new ChartPieSerieDetailData();
        restDataDetail.setName("休息");
        restDataDetail.setValue(totalRestDays);
        serieData.getData().add(restDataDetail);

        chartPieData.setSubTitle("共:" + totalDays + "天");
        chartPieData.getDetailData().add(serieData);

        Map<String, BaseChartData> res = new HashMap<>();
        res.put("chartShadowData", chartShadowData);
        res.put("chartPieData", chartPieData);
        return callback(res);
    }

    /**
     * 修正
     *
     * @return
     */
    @RequestMapping(value = "/revise", method = RequestMethod.POST)
    public ResultBean revise(@RequestBody @Valid LifeExperienceReviseFormRequest bean) {
        LifeExperienceSum les = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        int totalDays = DateUtil.getDays(les.getYear());
        les.setTotalDays(totalDays);
        List<Company> list = companyService.selectCompanyList(les.getYear(), bean.getUserId());
        int nc = 0;
        //无效公司记录
        int unc = 0;
        Map res = new HashMap();
        int totalWorkDays = 0;
        for (Company c : list) {
            int ey = DateUtil.getYear(c.getEntryDate());
            int qy = DateUtil.getYear(c.getQuitDate());
            nc++;
            if (ey < les.getYear() && qy > les.getYear()) {
                //第一种情况：入职和离职都在该年外
                totalWorkDays += totalDays;
            } else if (ey == les.getYear() && qy == les.getYear()) {
                //第二种情况：入职和离职都在该年内
                totalWorkDays += DateUtil.getIntervalDays(c.getEntryDate(), c.getQuitDate());
            } else if (ey < les.getYear() && qy >= les.getYear()) {
                //第三种情况：入职在年外，离职在该年内
                Date date = DateUtil.getYearFirst(les.getYear());
                totalWorkDays += DateUtil.getIntervalDays(date, c.getQuitDate());
            } else if (ey <= les.getYear() && qy > les.getYear()) {
                //第四种情况：入职在年内，离职在该年外
                Date date = DateUtil.getLastDayOfYear(les.getYear());
                totalWorkDays += DateUtil.getIntervalDays(c.getEntryDate(), date);
            } else {
                unc++;
            }
        }
        les.setWorkDays(totalWorkDays);
        res.put("nc", nc);
        res.put("unc", unc);
        List<LifeExperience> leList = lifeExperienceService.selectLifeExperienceList(les.getYear(), bean.getUserId());
        int totalTravelDays = 0;
        int totalStudyDays = 0;
        int nle = 0;
        int unle = 0;
        for (LifeExperience le : leList) {
            int ey = DateUtil.getYear(le.getStartDate());
            int qy = DateUtil.getYear(le.getEndDate());
            nle++;
            int v = 0;
            if (ey < les.getYear() && qy > les.getYear()) {
                //第一种情况：入职和离职都在该年外
                v += totalDays;
            } else if (ey == les.getYear() && qy == les.getYear()) {
                //第二种情况：入职和离职都在该年内
                v += DateUtil.getIntervalDays(le.getStartDate(), le.getEndDate());
            } else if (ey < les.getYear() && qy >= les.getYear()) {
                //第三种情况：入职在年外，离职在该年内
                Date date = DateUtil.getYearFirst(les.getYear());
                v += DateUtil.getIntervalDays(date, le.getEndDate());
            } else if (ey <= les.getYear() && qy > les.getYear()) {
                //第四种情况：入职在年内，离职在该年外
                Date date = DateUtil.getLastDayOfYear(les.getYear());
                v += DateUtil.getIntervalDays(le.getStartDate(), date);
            } else {
                unle++;
            }
            //第一种情况：入职和离职都在该年外
            if (le.getType() == ExperienceType.TRAVEL) {
                totalTravelDays += v;
            } else if (le.getType() == ExperienceType.STUDY) {
                totalStudyDays += v;
            }
        }
        res.put("nle", nle);
        res.put("unle", unle);
        res.put("year", les.getYear());
        les.setTravelDays(totalTravelDays);
        les.setStudyDays(totalStudyDays);
        les.setLastModifyTime(new Date());
        baseService.updateObject(les);
        return callback(res);
    }


}
