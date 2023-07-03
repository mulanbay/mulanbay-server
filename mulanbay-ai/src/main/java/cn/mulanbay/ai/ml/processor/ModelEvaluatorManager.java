package cn.mulanbay.ai.ml.processor;

import cn.mulanbay.ai.ml.dataset.ModelHandle;
import cn.mulanbay.ai.ml.dataset.bean.ModelFile;
import cn.mulanbay.ai.ml.dataset.impl.ModelHandleFileImpl;
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
    @Value("${ml.pmml.modelPath}")
    protected String modelPath;

    @Autowired(required = false)
    ModelHandle modelHandle;

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
        ModelFile mf = modelHandle.getModelFile(code);
        if(mf==null){
            logger.warn("找不到code={}的模型配置",code);
            return null;
        }
        return mf.getFileName();
    }

    @PostConstruct
    public void initModuleFiles(){
        if(this.modelHandle==null){
            //默认为文件实现
            logger.debug("模型文件处理器为空，采用默认的文件实现");
            ModelHandleFileImpl fi = new ModelHandleFileImpl();
            fi.load();
            this.modelHandle = fi;
        }
    }

    /**
     * 删除评估器
     *
     * @param code
     */
    public void removeEvaluator(String code){
        evaluatorMap.remove(code);
    }

    /**
     * 初始化评估器
     *
     * @param code
     */
    public void initEvaluator(String code){
        String modeFile = this.getModuleFile(code);
        this.initEvaluator(code,modeFile);
    }

    /**
     * 初始化评估器
     *
     * @param modeFile
     */
    public synchronized boolean initEvaluator(String code,String modeFile){
        Evaluator evaluator = this.createEvaluator(modelPath,modeFile);
        if(evaluator==null){
            return false;
        }
        evaluatorMap.put(code,evaluator);
        logger.info("初始化了{}评估器",code);
        return true;
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
