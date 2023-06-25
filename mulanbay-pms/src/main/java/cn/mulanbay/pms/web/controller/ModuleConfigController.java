package cn.mulanbay.pms.web.controller;

import cn.mulanbay.ai.ml.processor.ModelEvaluatorManager;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.FileUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.ModuleConfig;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.ModuleConfigService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.moduleConfig.ModuleConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.moduleConfig.ModuleConfigPublishRequest;
import cn.mulanbay.pms.web.bean.request.moduleConfig.ModuleConfigSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 机器学习模型配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/moduleConfig")
public class ModuleConfigController extends BaseController {

    private static Class<ModuleConfig> beanClass = ModuleConfig.class;

    /**
     * 模型文件路径
     */
    @Value("${ml.pmml.modulePath}")
    protected String modulePath;

    @Autowired
    ModelEvaluatorManager modelEvaluatorManager;

    @Autowired
    ModuleConfigService moduleConfigService;
    /**
     * 获取任务列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ModuleConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.DESC);
        pr.addSort(sort);
        PageResult<ModuleConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@Valid ModuleConfigFormRequest formRequest,@RequestParam(name="file",required = false) MultipartFile file)  throws IOException {
        this.storeFile(file,formRequest.getFileName());
        ModuleConfig bean = new ModuleConfig();
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
            File filePath = new File(modulePath, filename);
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
        ModuleConfig bean = baseService.getObject(ModuleConfig.class,getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@Valid ModuleConfigFormRequest formRequest,@RequestParam(name="file",required = false) MultipartFile file)  throws IOException {
        this.storeFile(file,formRequest.getFileName());
        ModuleConfig bean = baseService.getObject(ModuleConfig.class,formRequest.getId());
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
    public ResultBean publish(@RequestBody @Valid ModuleConfigPublishRequest re) {
        ModuleConfig bean = baseService.getObject(ModuleConfig.class,re.getId());
        boolean b = modelEvaluatorManager.initEvaluator(bean.getCode(),bean.getFileName());
        if(!b){
            return callbackErrorInfo("初始化模型失败");
        }
        moduleConfigService.publish(bean);
        return callback(bean);
    }

    /**
     * 刷新
     * 不需要更新数据库
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResultBean refresh(@RequestBody @Valid ModuleConfigPublishRequest re) {
        ModuleConfig bean = baseService.getObject(ModuleConfig.class,re.getId());
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
    public ResultBean revoke(@RequestBody @Valid ModuleConfigPublishRequest re) {
        ModuleConfig bean = baseService.getObject(ModuleConfig.class,re.getId());
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
            ModuleConfig bean = baseService.getObject(ModuleConfig.class,Long.valueOf(id));
            if(bean.getStatus()==CommonStatus.ENABLE){
                modelEvaluatorManager.removeEvaluator(bean.getCode());
            }
            baseService.deleteObject(ModuleConfig.class,Long.valueOf(id));
        }
        return callback(null);
    }

}
