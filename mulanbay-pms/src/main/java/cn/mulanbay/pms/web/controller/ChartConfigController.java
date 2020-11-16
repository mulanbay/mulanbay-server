package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.StringCoderUtil;
import cn.mulanbay.pms.persistent.domain.ChartConfig;
import cn.mulanbay.pms.persistent.enums.BussType;
import cn.mulanbay.pms.persistent.service.ChartService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.chart.ChartConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.chart.ChartConfigSearch;
import cn.mulanbay.pms.web.bean.request.chart.ChartConfigTreeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 图表配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/chartConfig")
public class ChartConfigController extends BaseController {

    private static Class<ChartConfig> beanClass = ChartConfig.class;

    @Autowired
    ChartService chartService;

    /**
     * 图表配置模板选项列表(用户使用，需要判断用户级别)
     *
     * @return
     */
    @RequestMapping(value = "/getChartConfigForUserTree")
    public ResultBean getChartConfigForUserTree(ChartConfigTreeSearch sf) {
        try {
            List<ChartConfig> gtList = chartService.getChartConfigList(sf.getLevel());
            List<TreeBean> list = this.createChartConfigTree(gtList);
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取图表树异常",
                    e);
        }
    }


    /**
     * 为锻炼管理界面的下拉菜单使用
     *
     * @return
     */
    @RequestMapping(value = "/getChartConfigTree")
    public ResultBean getChartConfigTree(ChartConfigTreeSearch sf) {
        try {
            ChartConfigSearch ncSearch = new ChartConfigSearch();
            ncSearch.setPage(PageRequest.NO_PAGE);
            PageResult<ChartConfig> pr = getChartConfigData(ncSearch);
            List<ChartConfig> gtList = pr.getBeanList();
            List<TreeBean> list = this.createChartConfigTree(gtList);
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取乐器树异常",
                    e);
        }
    }

    private List<TreeBean> createChartConfigTree(List<ChartConfig> gtList) {
        List<TreeBean> list = new ArrayList<TreeBean>();
        Map<String, List<ChartConfig>> map = this.changeToChartConfigMap(gtList);
        for (String key : map.keySet()) {
            TreeBean tb = new TreeBean();
            BussType bt = BussType.getBussType(key);
            tb.setId("P_" + bt.name());
            if (bt == null) {
                tb.setText("未分类");
            } else {
                tb.setText(bt.getName());
            }
            List<ChartConfig> ll = map.get(key);
            for (ChartConfig nc : ll) {
                TreeBean child = new TreeBean();
                child.setId(nc.getId().toString());
                child.setText(nc.getTitle());
                tb.addChild(child);
            }
            list.add(tb);
        }
        return list;
    }

    private Map<String, List<ChartConfig>> changeToChartConfigMap(List<ChartConfig> gtList) {
        Map<String, List<ChartConfig>> map = new TreeMap<>();
        for (ChartConfig nc : gtList) {
            List<ChartConfig> list = map.get(nc.getRelatedBeans());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(nc);
            map.put(nc.getRelatedBeans(), list);
        }
        return map;
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ChartConfigSearch sf) {
        return callbackDataGrid(getChartConfigData(sf));
    }

    private PageResult<ChartConfig> getChartConfigData(ChartConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<ChartConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid ChartConfigFormRequest formRequest) {
        ChartConfig bean = new ChartConfig();
        BeanCopy.copyProperties(formRequest, bean);
        checkChartConfig(bean);
        String para = StringCoderUtil.decodeJson(formRequest.getPara());
        ;
        bean.setPara(para);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        ChartConfig br = baseService.getObject(beanClass, getRequest.getId());
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid ChartConfigFormRequest formRequest) {
        ChartConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        checkChartConfig(bean);
        String para = StringCoderUtil.decodeJson(formRequest.getPara());
        ;
        bean.setPara(para);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    public void checkChartConfig(ChartConfig bean) {

    }

    /**
     * 删除
     * todo 该功能其实不能提供，即使提供也要级联删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

}
