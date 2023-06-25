package cn.mulanbay.ai.ml.processor;

import cn.mulanbay.ai.ml.dataset.ModuleHandle;
import cn.mulanbay.ai.ml.dataset.bean.ModuleFile;
import cn.mulanbay.ai.ml.dataset.impl.ModuleHandleFileImpl;
import cn.mulanbay.common.util.StringUtil;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluatorBuilder;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.PMMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模型评估器管理
 *
 * @author fenghong
 * @create 2023-06-23
 */
@Component
public class ModelEvaluatorManager {

    private static final Logger logger = LoggerFactory.getLogger(ModelEvaluatorManager.class);

    private static Map<String, Evaluator> evaluatorMap = new ConcurrentHashMap<String, Evaluator>();

    /**
     * 模型文件路径
     */
    @Value("${ml.pmml.modulePath}")
    protected String modulePath;

    @Autowired(required = false)
    ModuleHandle moduleHandle;

    /**
     * 初始化评估器
     */
    public Evaluator createEvaluator(String folder,String moduleFile){
        try {
            if(StringUtil.isEmpty(folder)||StringUtil.isEmpty(moduleFile)){
                logger.warn("{} 模型文件为空，无法进行初始化");
                logger.warn("modulePath:{},moduleFile:{}",folder,moduleFile);
                return null;
            }
            FileInputStream inputStream = new FileInputStream(folder+"/"+moduleFile);
            PMML pmml = PMMLUtil.unmarshal(inputStream);
            ModelEvaluatorBuilder modelEvaluatorBuilder = new ModelEvaluatorBuilder(pmml);
            ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
            modelEvaluatorBuilder.setModelEvaluatorFactory(modelEvaluatorFactory);
            Evaluator modelEvaluator = modelEvaluatorBuilder.build();
            modelEvaluator.verify();
            logger.info(moduleFile+"模型加载成功");
            return modelEvaluator;
        } catch (Exception e) {
            logger.error("加载模型文件"+moduleFile+"异常",e);
        }
        return null;
    }

    /**
     * 获取模型文件
     * @param code
     * @return
     */
    public String getModuleFile(String code){
        ModuleFile mf = moduleHandle.getModuleFile(code);
        return mf.getFileName();
    }

    @PostConstruct
    public void initModuleFiles(){
        if(this.moduleHandle==null){
            //默认为文件实现
            logger.debug("模型文件处理器为空，采用默认的文件实现");
            ModuleHandleFileImpl fi = new ModuleHandleFileImpl();
            fi.load();
            this.moduleHandle = fi;
        }
    }

    /**
     * 初始化评估器
     *
     * @param code
     */
    public synchronized void initEvaluator(String code){
        String modeFile = this.getModuleFile(code);
        Evaluator evaluator = this.createEvaluator(modulePath,modeFile);
        evaluatorMap.put(code,evaluator);
        logger.info("初始化了{}评估器",code);
    }

    /**
     * 获取评估器
     * @param code
     * @return
     */
    public Evaluator getEvaluator(String code){
        return evaluatorMap.get(code);
    }
}
