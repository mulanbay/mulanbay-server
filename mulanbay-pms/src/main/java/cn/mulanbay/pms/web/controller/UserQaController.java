package cn.mulanbay.pms.web.controller;

import cn.mulanbay.ai.nlp.processor.NLPProcessor;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.common.ConfigKey;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.domain.UserQa;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.QaService;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.system.UserQaSearch;
import cn.mulanbay.pms.web.bean.request.system.UserQaWordCloudSearch;
import cn.mulanbay.pms.web.bean.response.chart.ChartNameValueVo;
import cn.mulanbay.pms.web.bean.response.chart.ChartWorldCloudData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户QA
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userQa")
public class UserQaController extends BaseController {

    private static Class<UserQa> beanClass = UserQa.class;

    @Autowired
    AuthService authService;

    @Autowired
    QaService qaService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    NLPProcessor nlpProcessor;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserQaSearch sf) {
        //用户查询
        if(StringUtil.isNotEmpty(sf.getUsername())){
            User user = authService.getUserByUsernameOrPhone(sf.getUsername());
            if (user == null) {
                return callbackErrorCode(ErrorCode.USER_NOTFOUND);
            }
            sf.setUserId(user.getId());
        }
        PageResult<UserQa> res = qaService.getUserQaResult(sf);
        return callbackDataGrid(res);
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserQa bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 词云
     *
     * @return
     */
    @RequestMapping(value = "/statWordCloud", method = RequestMethod.GET)
    public ResultBean statWordCloud(@Valid UserQaWordCloudSearch sf) {
        List<String> reList = qaService.getRequestList(sf);
        Map<String,Integer> statData = new HashMap<>();
        Integer num = systemConfigHandler.getIntegerConfig(ConfigKey.NLP_USERQA_REQUESTCONTENT_EKNUM);
        for(String req : reList){
            //先分词
            List<String> keywords =  nlpProcessor.extractKeyword(req,num);
            for(String s : keywords){
                Integer n = statData.get(s);
                if(n==null){
                    statData.put(s,1);
                }else{
                    statData.put(s,n+1);
                }
            }
        }
        ChartWorldCloudData chartData = new ChartWorldCloudData();
        for(String key : statData.keySet()){
            ChartNameValueVo dd = new ChartNameValueVo();
            dd.setName(key);
            dd.setValue(statData.get(key));
            chartData.addData(dd);
        }
        chartData.setTitle("用户QA词云");
        return callback(chartData);
    }

}
