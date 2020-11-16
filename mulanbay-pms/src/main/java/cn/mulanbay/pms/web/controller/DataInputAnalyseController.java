package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.persistent.domain.DataInputAnalyse;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.dto.DataInputAnalyseStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.data.DataInputAnalyseFormRequest;
import cn.mulanbay.pms.web.bean.request.data.DataInputAnalyseSearch;
import cn.mulanbay.pms.web.bean.request.data.DataInputAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieData;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieSerieDetailData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据录入分析
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/dataInputAnalyse")
public class DataInputAnalyseController extends BaseController {

    private static Class<DataInputAnalyse> beanClass = DataInputAnalyse.class;

    @Autowired
    DataService dataService;

    @Autowired
    AuthService authService;

    /**
     * 获取数据树
     * @return
     */
    @RequestMapping(value = "/getDataInputAnalyseTree")
    public ResultBean getDataInputAnalyseTree(Boolean needRoot) {
        try {
            DataInputAnalyseSearch sf = new DataInputAnalyseSearch();
            sf.setStatus(CommonStatus.ENABLE);
            sf.setPage(PageRequest.NO_PAGE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            PageResult<DataInputAnalyse> qr = baseService.getBeanResult(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<DataInputAnalyse> gtList = qr.getBeanList();
            for (DataInputAnalyse gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取数据树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(DataInputAnalyseSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        PageResult<DataInputAnalyse> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid DataInputAnalyseFormRequest formRequest) {
        DataInputAnalyse bean = new DataInputAnalyse();
        BeanCopy.copyProperties(formRequest, bean, true);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(Long id) {
        DataInputAnalyse br = baseService.getObject(beanClass, id);
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid DataInputAnalyseFormRequest formRequest) {
        DataInputAnalyse bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean, true);
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

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(DataInputAnalyseStatSearch sf) {
        String username = sf.getUsername();
        if (StringUtil.isNotEmpty(username)) {
            User user = authService.getUserByUsernameOrPhone(sf.getUsername());
            if (user == null) {
                return callbackErrorInfo("未找到相关用户");
            } else {
                sf.setUserId(user.getId());
            }
        }
        List<DataInputAnalyseStat> list = dataService.statDataInputAnalyse(sf);
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("数据录入延迟分析");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("分析");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (DataInputAnalyseStat bean : list) {
            chartPieData.getXdata().add(bean.getGroupName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getGroupName());
            dataDetail.setValue(bean.getTotalCount());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount()));
        }
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.intValue()) + "次");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }


}
