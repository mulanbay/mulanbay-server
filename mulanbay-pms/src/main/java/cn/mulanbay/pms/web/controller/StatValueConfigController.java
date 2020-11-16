package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.StatValueConfig;
import cn.mulanbay.pms.persistent.dto.StatValueConfigDto;
import cn.mulanbay.pms.persistent.enums.StatValueSource;
import cn.mulanbay.pms.persistent.service.StatValueConfigService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.common.GetStatValueConfig;
import cn.mulanbay.pms.web.bean.request.common.GetStatValueConfigs;
import cn.mulanbay.pms.web.bean.request.plan.StatValueConfigSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 提醒、计划、图表等通用配置值项目
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/statValueConfig")
public class StatValueConfigController extends BaseController {

    private static Class<StatValueConfig> beanClass = StatValueConfig.class;

    @Autowired
    StatValueConfigService statValueConfigService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(StatValueConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<StatValueConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }


    /**
     * 获取值的配置列表
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getConfigs", method = RequestMethod.GET)
    public ResultBean getConfigs(GetStatValueConfigs sf) {
        List<StatValueConfigDto> list = statValueConfigService.getStatValueConfig(sf.getFid(), sf.getType(), sf.getUserId());
        return callback(list);
    }

    /**
     * 获取值的配置列表
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public ResultBean getConfig(GetStatValueConfig sf) {
        StatValueConfig svc = baseService.getObject(beanClass, sf.getId());
        StatValueConfigDto bean = statValueConfigService.getStatValueConfigBean(svc, sf.getPid(), sf.getUserId());
        return callback(bean);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody StatValueConfig bean) {
        checkBean(bean);
        baseService.saveObject(bean);
        return callback(null);
    }

    private void checkBean(StatValueConfig bean) {
        StatValueSource source = bean.getSource();
        switch (source) {
            case SQL: {
                if (StringUtil.isEmpty(bean.getSqlContent())) {
                    throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "数据库查询语句不能为空");
                }
                break;
            }
            case ENUM: {
                if (StringUtil.isEmpty(bean.getEnumClass()) || bean.getEnumIdType() == null) {
                    throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "枚举类名或枚举字段类型不能为空");
                }
                break;
            }
            case DATA_DICT: {
                if (StringUtil.isEmpty(bean.getDictGroupCode())) {
                    throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "数据字典组不能为空");
                }
                break;
            }
            case JSON: {
                if (StringUtil.isEmpty(bean.getJsonData())) {
                    throw new ApplicationException(ErrorCode.DO_BUSS_ERROR, "Json数据不能为空");
                }
                break;
            }
            default:
                break;

        }
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(Long id) {
        StatValueConfig br = baseService.getObject(beanClass, id);
        return callback(br);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody StatValueConfig bean) {
        checkBean(bean);
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
