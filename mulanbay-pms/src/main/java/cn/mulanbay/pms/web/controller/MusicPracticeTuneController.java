package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.MusicPractice;
import cn.mulanbay.pms.persistent.domain.MusicPracticeTune;
import cn.mulanbay.pms.persistent.dto.MusicPracticeTuneLevelStat;
import cn.mulanbay.pms.persistent.dto.MusicPracticeTuneNameStat;
import cn.mulanbay.pms.persistent.dto.MusicPracticeTuneStat;
import cn.mulanbay.pms.persistent.service.MusicPracticeService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.music.*;
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
import java.util.List;

/**
 * 练习曲子记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/musicPracticeTune")
public class MusicPracticeTuneController extends BaseController {

    private static Class<MusicPracticeTune> beanClass = MusicPracticeTune.class;

    @Autowired
    MusicPracticeService musicPracticeService;

    /**
     * 获取曲子列表树
     *
     * @return
     */
    @RequestMapping(value = "/getMusicPracticeTuneTree")
    public ResultBean getMusicPracticeTuneTree(MusicPracticeTuneTreeSearch sf) {

        try {
            List<String> tuneList = musicPracticeService.getMusicPracticeTuneList(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            int i = 0;
            for (String ss : tuneList) {
                TreeBean tb = new TreeBean();
                tb.setId(ss);
                tb.setText(ss);
                list.add(tb);
                i++;
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取曲子列表树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData")
    public ResultBean getData(MusicPracticeTuneSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("musicPractice.practiceDate", Sort.DESC);
        pr.addSort(s);
        PageResult<MusicPracticeTune> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid MusicPracticeTuneFormRequest formRequest) {
        MusicPracticeTune bean = new MusicPracticeTune();
        BeanCopy.copyProperties(formRequest, bean);
        MusicPractice musicPractice = this.getUserEntity(MusicPractice.class, formRequest.getMusicPracticeId(), formRequest.getUserId());
        bean.setMusicPractice(musicPractice);
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        MusicPracticeTune bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid MusicPracticeTuneFormRequest formRequest) {
        MusicPracticeTune bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        MusicPractice musicPractice = this.getUserEntity(MusicPractice.class, formRequest.getMusicPracticeId(), formRequest.getUserId());
        bean.setMusicPractice(musicPractice);
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
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(MusicPracticeTuneSearch sf) {
        List<MusicPracticeTuneStat> list = musicPracticeService.statTune(sf);
        ChartData chartData = new ChartData();
        chartData.setTitle(musicPracticeService.getMusicInstrumentName(sf.getMusicInstrumentId()) + "练习曲子统计");
        chartData.setUnit("次");
        chartData.setLegendData(new String[]{"次数"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        for (MusicPracticeTuneStat bean : list) {
            chartData.getXdata().add(bean.getName());
            yData1.getData().add(bean.getTotalTimes());
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }

    /**
     * 曲子名称统计
     *
     * @return
     */
    @RequestMapping(value = "/nameStat", method = RequestMethod.GET)
    public ResultBean nameStat(MusicPracticeTuneNameStatSearch sf) {
        MusicPracticeTune bean = this.getUserEntity(MusicPracticeTune.class, sf.getId(), sf.getUserId());
        MusicPracticeTuneNameStat stat = musicPracticeService.tuneNameStat(sf.getUserId(), bean.getTune(), bean.getMusicPractice().getMusicInstrument().getId(), sf.getAllMi());
        return callback(stat);
    }

    /**
     * 曲子水平统计
     *
     * @return
     */
    @RequestMapping(value = "/levelStat", method = RequestMethod.GET)
    public ResultBean levelStat(MusicPracticeTuneLevelStatSearch sf) {
        MusicPracticeTune bean = this.getUserEntity(MusicPracticeTune.class, sf.getId(), sf.getUserId());
        List<MusicPracticeTuneLevelStat> list = musicPracticeService.tuneLevelStat(sf.getUserId(), bean.getTune(), bean.getMusicPractice().getMusicInstrument().getId());
        return callback(list);
    }
}
