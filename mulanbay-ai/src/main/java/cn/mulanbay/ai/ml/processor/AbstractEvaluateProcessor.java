package cn.mulanbay.ai.ml.processor;

import cn.mulanbay.ai.ml.processor.bean.EvaluateTarget;
import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.StringUtil;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluatorBuilder;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.evaluator.TargetField;
import org.jpmml.model.PMMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 评估处理基类
 *
 * @author fenghong
 * @create 2023-06-21
 */
public abstract class AbstractEvaluateProcessor extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEvaluateProcessor.class);

    /**
     * 模型文件路径
     */
    @Value("${ml.pmml.modulePath}")
    protected String modulePath;

    public AbstractEvaluateProcessor(String handlerName) {
        super(handlerName);
    }

    @Override
    public void init() {
        super.init();
        this.initEvaluator();
    }

    /**
     * 获取模型文件
     * todo 不同的用户应该有不同的模型(习惯不一样)
     * @return
     */
    public abstract String getModuleFile();

    /**
     * 评估器
     */
    Evaluator modelEvaluator;

    /**
     * 初始化评估器
     */
    protected void initEvaluator(){
        String moduleFile = this.getModuleFile();
        try {
            if(StringUtil.isEmpty(modulePath)||StringUtil.isEmpty(moduleFile)){
                logger.warn("{} 模型文件为空，无法进行初始化",this.handlerName);
                logger.warn("modulePath:{},moduleFile:{}",modulePath,moduleFile);
                return;
            }
            FileInputStream inputStream = new FileInputStream(modulePath+"/"+moduleFile);
            PMML pmml = PMMLUtil.unmarshal(inputStream);
            ModelEvaluatorBuilder modelEvaluatorBuilder = new ModelEvaluatorBuilder(pmml);
            ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
            modelEvaluatorBuilder.setModelEvaluatorFactory(modelEvaluatorFactory);
            modelEvaluator = modelEvaluatorBuilder.build();
            modelEvaluator.verify();
            logger.info(moduleFile+"模型加载成功");
        } catch (Exception e) {
            logger.error("加载模型文件"+moduleFile+"异常",e);
        }
    }

    /**
     * 评估，单个标签
     * @param args
     * @return
     */
    public Float evaluate(Map<FieldName, Number> args,String label){
        Map<FieldName, ?> results = modelEvaluator.evaluate(args);
        Object rateFieldValue = results.get(FieldName.create(label));
        return rateFieldValue == null ? null:Float.valueOf(rateFieldValue.toString());
    }

    /**
     * 返回所有标签的评估预测结果集
     *
     * @param args
     * @return
     */
    public List<EvaluateTarget> evaluate(Map<FieldName, Number> args){
        Map<FieldName, ?> results = modelEvaluator.evaluate(args);
        List<TargetField> targetFields = modelEvaluator.getTargetFields();
        List<EvaluateTarget> ets = new ArrayList<>();
        for (TargetField targetField : targetFields) {
            FieldName targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            EvaluateTarget et = new EvaluateTarget();
            et.setFieldName(targetFieldName.getValue());
            et.setValue(targetFieldValue);
            ets.add(et);
        }
        return ets;
    }
}
