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
import cn.mulanbay.pms.persistent.enums.QaResultType;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.system.QaConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.system.QaConfigSearch;
import cn.mulanbay.pms.web.bean.request.system.QaTestReq;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.ChartGraphData;
import cn.mulanbay.pms.web.bean.response.system.QaConfigVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
            List<QaConfig> qaList = baseService.getBeanList(beanClass,0,0,null);
            //构建
            CopyOnWriteArrayList<QaConfig> newList = new CopyOnWriteArrayList();
            newList.addAll(qaList);
            List<TreeBean> tbList = this.getTreeChildren(newList,-1L);
            return callback(TreeBeanUtil.addRoot(tbList, cts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取QA配置树异常",
                    e);
        }
    }

    /**
     * 构建
     * @param list
     * @return
     */
    private List<TreeBean> getTreeChildren(CopyOnWriteArrayList<QaConfig> list, long pid) {
        List<TreeBean> children = new LinkedList<>();
        for (QaConfig qa : list) {
            long p = qa.getParentId()==null ? -1L:qa.getParentId().longValue();
            if(p==pid){
                TreeBean child = new TreeBean();
                child.setId(qa.getId().toString());
                child.setText(qa.getName());
                children.add(child);
                list.remove(qa);
                child.setChildren(this.getTreeChildren(list,qa.getId()));
            }
        }
        return children;
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
        Sort sort1 = new Sort("parentId", Sort.ASC);
        pr.addSort(sort1);
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
        QaResult cr = qaHandler.handleMessage(tr.getSource(), tr.getContent(), tr.getUserId(), sessionId);
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
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean ration() {
        Sort sort = new Sort("parentId", Sort.ASC);
        List<QaConfig> qaList = baseService.getBeanList(beanClass,0,0,sort);
        int n = qaList.size();
        Map<String, QaConfigVo> qcMap = new HashMap<>();
        for(int i=0;i<n;i++){
            QaConfig qc = qaList.get(i);
            QaConfigVo vo = new QaConfigVo();
            vo.setId(qc.getId());
            vo.setName(qc.getName());
            qcMap.put(qc.getId().toString(),vo);
        }
        ChartGraphData chartGraphData = new ChartGraphData();
        chartGraphData.addItem("根",0);
        for(int i=0;i<n;i++){
            QaConfig qc = qaList.get(i);
            Long parentId  = qc.getParentId();
            int level = 1;
            if(parentId==null){
                chartGraphData.addLink("根",qc.getName());
            }else{
                QaConfigVo parent = qcMap.get(parentId.toString());
                chartGraphData.addLink(parent.getName(),qc.getName());
                int pl = parent.getLevel();
                //增加级数
                QaConfigVo my = qcMap.get(qc.getId().toString());
                level = parent.getLevel()+1;
                my.setLevel(level);
                qcMap.put(qc.getId().toString(),my);
            }
            chartGraphData.addItem(qc.getName(),level);
            if(qc.getReferQaId()!=null){
                QaConfigVo refer = qcMap.get(qc.getReferQaId().toString());
                chartGraphData.addLink(qc.getName(),refer.getName(),"跳转",1);
            }
        }
        chartGraphData.setTitle("QA拓扑结构");
        return callback(chartGraphData);
    }

}
