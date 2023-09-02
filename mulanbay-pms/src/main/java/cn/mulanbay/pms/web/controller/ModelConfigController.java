package cn.mulanbay.pms.web.controller;

import cn.mulanbay.ai.ml.manager.EvaluateProcessorManager;
import cn.mulanbay.ai.ml.manager.ModelEvaluatorManager;
import cn.mulanbay.ai.ml.processor.AbstractEvaluateProcessor;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.FileUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.ModelConfig;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.ModelConfigService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.modelConfig.ModelConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.modelConfig.ModelConfigPublishRequest;
import cn.mulanbay.pms.web.bean.request.modelConfig.ModelConfigSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机器学习模型配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/modelConfig")
public class ModelConfigController extends BaseController {

    private static Class<ModelConfig> beanClass = ModelConfig.class;

    /**
     * 模型文件路径
     */
    @Value("${ml.pmml.modelPath}")
    protected String modelPath;

    @Autowired
    ModelEvaluatorManager modelEvaluatorManager;

    @Autowired
    EvaluateProcessorManager evaluateProcessorManager;

    @Autowired
    ModelConfigService modelConfigService;

    /**
     * Processor树
     * @return
     */
    @RequestMapping(value = "/getProcessorTree")
    public ResultBean getProcessorTree(Boolean needRoot) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<AbstractEvaluateProcessor> processorList = evaluateProcessorManager.getProcessorList();
            for (AbstractEvaluateProcessor gt : processorList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getCode());
                tb.setText(gt.getHandlerName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取Processor树异常",
                    e);
        }
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ModelConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<ModelConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@Valid ModelConfigFormRequest formRequest, @RequestParam(name="file",required = false) MultipartFile file)  throws IOException {
        this.storeFile(file,formRequest.getFileName());
        ModelConfig bean = new ModelConfig();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        //手动发布
        bean.setStatus(CommonStatus.DISABLE);
        baseService.saveObject(bean);
        return callback(bean);
    }

    /**
     * 存储文件
     * @param file
     * @param fileName
     * @throws IOException
     */
    private void storeFile(MultipartFile file,String fileName) throws IOException{
        if (file!=null&&!file.isEmpty()) {
            // 获取原文件名
            String filename = fileName;
            // 创建文件实例
            File filePath = new File(modelPath, filename);
            FileUtil.checkPathExits(filePath);
            // 写入文件
            file.transferTo(filePath);
        }
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        ModelConfig bean = baseService.getObject(ModelConfig.class,getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@Valid ModelConfigFormRequest formRequest, @RequestParam(name="file",required = false) MultipartFile file)  throws IOException {
        this.storeFile(file,formRequest.getFileName());
        ModelConfig bean = baseService.getObject(ModelConfig.class,formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
    }

    /**
     * 发布
     *
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResultBean publish(@RequestBody @Valid ModelConfigPublishRequest re) {
        ModelConfig bean = baseService.getObject(ModelConfig.class,re.getId());
        boolean b = modelEvaluatorManager.initEvaluator(bean.getCode(),bean.getFileName());
        if(!b){
            return callbackErrorInfo("初始化模型失败");
        }
        modelConfigService.publish(bean);
        return callback(bean);
    }

    /**
     * 刷新
     * 不需要更新数据库
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResultBean refresh(@RequestBody @Valid ModelConfigPublishRequest re) {
        ModelConfig bean = baseService.getObject(ModelConfig.class,re.getId());
        boolean b = modelEvaluatorManager.initEvaluator(bean.getCode(),bean.getFileName());
        if(!b){
            return callbackErrorInfo("初始化模型失败");
        }
        return callback(null);
    }


    /**
     * 撤销
     * 不需要更新数据库
     * @return
     */
    @RequestMapping(value = "/revoke", method = RequestMethod.POST)
    public ResultBean revoke(@RequestBody @Valid ModelConfigPublishRequest re) {
        ModelConfig bean = baseService.getObject(ModelConfig.class,re.getId());
        modelEvaluatorManager.removeEvaluator(bean.getCode());
        bean.setStatus(CommonStatus.DISABLE);
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
        String[] ids = deleteRequest.getIds().split(",");
        for(String id : ids){
            ModelConfig bean = baseService.getObject(ModelConfig.class,Long.valueOf(id));
            if(bean.getStatus()==CommonStatus.ENABLE){
                modelEvaluatorManager.removeEvaluator(bean.getCode());
            }
            baseService.deleteObject(ModelConfig.class,Long.valueOf(id));
        }
        return callback(null);
    }

}
