package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.qa.QaHandler;
import cn.mulanbay.pms.handler.qa.bean.QaResult;
import cn.mulanbay.pms.persistent.domain.QaConfig;
import cn.mulanbay.pms.persistent.enums.QaMessageSource;
import cn.mulanbay.pms.persistent.enums.QaResultType;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.system.QaConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.system.QaConfigSearch;
import cn.mulanbay.pms.web.bean.request.system.QaTestReq;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.ChartTreeData;
import cn.mulanbay.pms.web.bean.response.chart.ChartTreeDetailData;
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
 * QA配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/qaConfig")
public class QaConfigController extends BaseController {

    private static Class<QaConfig> beanClass = QaConfig.class;

    @Autowired
    QaHandler qaHandler;

    /**
     * @return
     */
    @RequestMapping(value = "/getQaConfigTree")
    public ResultBean getQaConfigTree(CommonTreeSearch cts) {

        try {
            QaConfigSearch sf = new QaConfigSearch();
            sf.setPage(PageRequest.NO_PAGE);
            PageResult<QaConfig> pr = this.getResult(sf);
            List<TreeBean> list = getQaTree(pr.getBeanList());
            return callback(TreeBeanUtil.addRoot(list, cts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取QA配置树异常",
                    e);
        }
    }

    private List<TreeBean> getQaTree(List<QaConfig> qaList) {
        List<TreeBean> list = new ArrayList<TreeBean>();
        for (QaConfig qc : qaList) {
            if (qc.getParentId() == null) {
                TreeBean tb = new TreeBean();
                tb.setId(qc.getId().toString());
                tb.setText(qc.getName());
                setTreeChildren(tb, qaList);
                list.add(tb);
            }
        }
        return list;
    }

    private void setTreeChildren(TreeBean tb, List<QaConfig> qaList) {
        for (QaConfig qc : qaList) {
            Long parentId = qc.getParentId();
            if (parentId == null) {
                continue;
            } else if (tb.getId().equals(parentId.toString())) {
                TreeBean child = new TreeBean();
                child.setId(qc.getId().toString());
                child.setText(qc.getName());
                tb.addChild(child);
                setTreeChildren(child, qaList);
            }
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(QaConfigSearch sf) {
        return callbackDataGrid(getResult(sf));
    }

    private PageResult<QaConfig> getResult(QaConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<QaConfig> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid QaConfigFormRequest formRequest) {
        QaConfig bean = new QaConfig();
        BeanCopy.copyProperties(formRequest, bean);
        checkQaConfig(bean);
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
        QaConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 检查配置
     *
     * @param qc
     */
    private void checkQaConfig(QaConfig qc) {
        QaResultType resultType = qc.getResultType();
        if (resultType == QaResultType.DIRECT) {
            if (StringUtil.isEmpty(qc.getReplayContent())) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "回复内容不能为空");
            }
        } else if (resultType == QaResultType.REFER) {
            if (qc.getReferQaId() == null) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "跳转处理器不能为空");
            }
        } else if (resultType == QaResultType.SQL) {
            if (StringUtil.isEmpty(qc.getReplayContent())) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "SQL代码不能为空");
            }
            if (StringUtil.isEmpty(qc.getReplayTitle())) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "标题不能为空");
            }
            if (StringUtil.isEmpty(qc.getColumnTemplate())) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "列模板不能为空");
            }
        } else if (resultType == QaResultType.CODE) {
            if (StringUtil.isEmpty(qc.getHandleCode())) {
                throw new ApplicationException(PmsErrorCode.FROM_CHECK_FAIL, "处理代码不能为空");
            }
        }
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid QaConfigFormRequest formRequest) {
        QaConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        checkQaConfig(bean);
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
     * 文本QA请求
     *
     * @return
     */
    @RequestMapping(value = "/textReq", method = RequestMethod.POST)
    public ResultBean textReq(@RequestBody QaTestReq tr) {
        String sessionId = request.getRequestedSessionId();
        QaResult cr = qaHandler.handleMessage(QaMessageSource.WECHAT, tr.getContent(), tr.getUserId(), sessionId);
        return callback(cr);
    }

    /**
     * 重新加载缓存
     *
     * @return
     */
    @RequestMapping(value = "/reloadCache", method = RequestMethod.POST)
    public ResultBean reloadCache() {
        qaHandler.reload();
        return callback(null);
    }

    /**
     * 拓扑结构
     *
     * @return
     */
    @RequestMapping(value = "/treeView", method = RequestMethod.GET)
    public ResultBean treeView() {
        QaConfigSearch sf = new QaConfigSearch();
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        pr.setPage(0);
        Sort sort = new Sort("parentId", Sort.ASC);
        pr.addSort(sort);
        List<QaConfig> list = baseService.getBeanList(pr);
        ChartTreeDetailData data = getQaTreeView(list);
        ChartTreeData treeData = new ChartTreeData();
        treeData.setData(data);
        return callback(treeData);
    }

    private ChartTreeDetailData getQaTreeView(List<QaConfig> qaList) {
        ChartTreeDetailData data = new ChartTreeDetailData(0, "根");
        for (QaConfig qc : qaList) {
            if (qc.getParentId() == null) {
                ChartTreeDetailData tb = new ChartTreeDetailData();
                tb.setName(qc.getName());
                tb.setValue(1);
                setTreeChildren(tb, qaList);
                data.addChild(tb);
            }
        }
        return data;
    }

    private void setTreeChildren(ChartTreeDetailData tb, List<QaConfig> qaList) {
        for (QaConfig qc : qaList) {
            Long parentId = qc.getParentId();
            if (parentId == null) {
                continue;
            } else {
                QaConfig parent = this.getQaConfig(parentId, qaList);
                if (tb.getName().equals(parent.getName())) {
                    ChartTreeDetailData child = new ChartTreeDetailData();
                    child.setName(qc.getName());
                    child.setValue(1);
                    tb.addChild(child);
                    setTreeChildren(child, qaList);
                }
            }
        }
    }

    private QaConfig getQaConfig(Long id, List<QaConfig> list) {
        for (QaConfig qc : list) {
            if (id.longValue() == qc.getId()) {
                return qc;
            }
        }
        return null;
    }
}