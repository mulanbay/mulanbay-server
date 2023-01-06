package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.ConfigKey;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.domain.UserOperationConfig;
import cn.mulanbay.pms.persistent.service.UserBehaviorService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.userbehavior.UserOperationConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.userbehavior.UserOperationConfigSearch;
import cn.mulanbay.pms.web.bean.request.userbehavior.UserOperationStatSearch;
import cn.mulanbay.pms.web.bean.request.userbehavior.UserOperationWordCloudSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.ChartNameValueVo;
import cn.mulanbay.pms.web.bean.response.chart.ChartWorldCloudData;
import cn.mulanbay.pms.web.bean.response.user.UserOperationResponse;
import cn.mulanbay.pms.web.bean.response.user.UserOperationVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.*;

/**
 * 用户操作习惯配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userOperationConfig")
public class UserOperationConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserOperationConfigController.class);

    private static Class<UserOperationConfig> beanClass = UserOperationConfig.class;

    @Autowired
    UserBehaviorService userBehaviorService;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * 获取用户操作习惯树
     * @param cts
     * @return
     */
    @RequestMapping(value = "/getUserOperationConfigTree")
    public ResultBean getUserOperationConfigTree(CommonTreeSearch cts) {

        try {
            UserOperationConfigSearch sf = new UserOperationConfigSearch();
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort sort = new Sort("behaviorType", Sort.ASC);
            pr.addSort(sort);
            pr.setPage(0);
            List<UserOperationConfig> configList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<>();
            Map<String, List<UserOperationConfig>> map = changeToMap(configList);
            int id = 0;
            for (String key : map.keySet()) {
                TreeBean tb = new TreeBean();
                //设置一个不会再数据库出现的ID，这样多选时也额外无法去除
                tb.setId(String.valueOf(--id));
                tb.setText(key);
                List<UserOperationConfig> ll = map.get(key);
                for (UserOperationConfig nc : ll) {
                    TreeBean child = new TreeBean();
                    child.setId(nc.getId().toString());
                    child.setText(nc.getName());
                    tb.addChild(child);
                }
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, cts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户操作习惯树异常",
                    e);
        }
    }

    private Map<String, List<UserOperationConfig>> changeToMap(List<UserOperationConfig> configList) {
        Map<String, List<UserOperationConfig>> map = new TreeMap<>();
        for (UserOperationConfig nc : configList) {
            List<UserOperationConfig> list = map.get(nc.getBehaviorTypeName());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(nc);
            map.put(nc.getBehaviorTypeName(), list);
        }
        return map;
    }


    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserOperationConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<UserOperationConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserOperationConfigFormRequest formRequest) {
        UserOperationConfig bean = new UserOperationConfig();
        BeanCopy.copyProperties(formRequest, bean);
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
        UserOperationConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserOperationConfigFormRequest formRequest) {
        UserOperationConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
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
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }


    /**
     * 用户操作记录统计
     *
     * @return
     */
    @RequestMapping(value = "/userOperationStat", method = RequestMethod.GET)
    public ResultBean userOperationStat(@Valid UserOperationStatSearch sf) {
        return callback(getUserOperationList(sf));
    }

    private List<UserOperationResponse> getUserOperationList(UserOperationStatSearch sf) {
        List<UserOperationConfig> list = userBehaviorService.getUserOperationConfigList(sf.getConfigIds(), sf.getBehaviorType());
        int page = sf.getPage();
        int pageSize = sf.getPageSize();
        int index = 0;
        List<UserOperationResponse> res = new ArrayList<>();
        for (UserOperationConfig uoc : list) {
            List<Object[]> opList = userBehaviorService.getUserOperationList(uoc, sf.getStartTime(), sf.getEndTime(), sf.getUserId(), page, pageSize);
            UserOperationResponse uor = new UserOperationResponse();
            uor.setId(index++);
            uor.setTitle(uoc.getName());
            for (Object[] oo : opList) {
                int n = oo.length;
                //第一个默认是时间
                Date d = (Date) oo[0];
                String title = oo[1] == null ? "无" : oo[1].toString();
                List paras = new ArrayList();
                for (int i = 1; i < n; i++) {
                    paras.add(oo[i] == null ? "无" : oo[i].toString());
                }
                String line = MessageFormat.format(uoc.getColumnTemplate(), paras.toArray());
                uor.addUserOperation(d, line, title, uoc.getBehaviorType());
            }
            res.add(uor);
        }
        return res;
    }

    /**
     * 我的词云
     *
     * @return
     */
    @RequestMapping(value = "/wordCloudStat", method = RequestMethod.GET)
    public ResultBean wordCloudStat(@Valid UserOperationWordCloudSearch sf) {
        //不分页
        sf.setPage(0);
        List<UserOperationResponse> res = this.getUserOperationList(sf);
        Map<String,Integer> statData = new HashMap<>();
        Integer num = systemConfigHandler.getIntegerConfig(ConfigKey.NLP_USEROPERATION_EKNUM);
        for (UserOperationResponse uo : res) {
            List<UserOperationVo> operations = uo.getOperations();
            for (UserOperationVo op : operations) {
                //先分词
                List<String> list = ahaNLPHandler.extractKeyword(op.getTitle(),num);
                for(String s : list){
                    Integer n = statData.get(s);
                    if(n==null){
                        statData.put(s,1);
                    }else{
                        statData.put(s,n+1);
                    }
                }
            }
        }
        ChartWorldCloudData chartData = new ChartWorldCloudData();
        for(String key : statData.keySet()){
            ChartNameValueVo dd = new ChartNameValueVo();
            dd.setName(key);
            dd.setValue(statData.get(key).intValue());
            chartData.addData(dd);
        }
        chartData.setTitle("我的词云");
        return callback(chartData);
    }
}
