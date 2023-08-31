package cn.mulanbay.ai.ml.processor;

import cn.mulanbay.ai.ml.manager.ModelEvaluatorManager;
import cn.mulanbay.business.handler.BaseHandler;
import org.jpmml.evaluator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
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

    @Autowired
    ModelEvaluatorManager modelEvaluatorManager;

    private String code;

    public AbstractEvaluateProcessor(String handlerName,String code) {
        super(handlerName);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public void init() {
        super.init();
        modelEvaluatorManager.initEvaluator(code);
    }

    protected Evaluator getEvaluator(){
        return modelEvaluatorManager.getEvaluator(code);
    }

    /**
     * 转换为浮点
     * @param targetValue
     * @return
     */
    protected Double convertValueToDouble(Object targetValue){
        if(targetValue==null){
            logger.warn("标签的预测值为空");
            return null;
        }
        if (targetValue instanceof Computable) {
            Computable computable = (Computable) targetValue;
            Object v = computable.getResult();
            return Double.valueOf(v.toString());
        }else{
            return Double.valueOf(targetValue.toString());
        }
    }
    /**
     * 评估，单个标签
     * @param args
     * @return
     */
    public Double evaluateDouble(Map<String, Number> args, String label){
        Object targetValue = this.evaluate(args,label);
        return this.convertValueToDouble(targetValue);
    }

    /**
     * 评估，单个标签
     * @param args
     * @return
     */
    public Object evaluate(Map<String, Number> args,String label){
        Evaluator modelEvaluator = this.getEvaluator();
        Map<String, ?> results = modelEvaluator.evaluate(args);
        Object rateFieldValue = results.get(label);
        return rateFieldValue;
    }

    /**
     * 返回所有标签的评估预测结果集
     *
     * @param args
     * @return
     */
    public Map<String,Object> evaluate(Map<String, Number> args){
        Evaluator modelEvaluator = this.getEvaluator();
        Map<String, ?> results = modelEvaluator.evaluate(args);
        List<TargetField> targetFields = modelEvaluator.getTargetFields();
        Map<String,Object> ets = new HashMap<>();
        for (TargetField targetField : targetFields) {
            String targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            ets.put(targetFieldName,targetFieldValue);
        }
        return ets;
    }

    /**
     * 返回所有标签的评估预测结果集(结果是浮点类型)
     *
     * @param args
     * @return
     */
    public Map<String,Double> evaluateDoubles(Map<String, Number> args){
        Evaluator modelEvaluator = this.getEvaluator();
        Map<String, ?> results = modelEvaluator.evaluate(args);
        List<TargetField> targetFields = modelEvaluator.getTargetFields();
        Map<String,Double> ets = new HashMap<>();
        for (TargetField targetField : targetFields) {
            String targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            ets.put(targetFieldName,this.convertValueToDouble(targetFieldValue));
        }
        return ets;
    }

    /**
     * 概率
     * 针对连续型变量预测
     * 采用回归模型
     *
     * @param results
     * @param fieldName
     * @return
     */
    protected ValueMap<String,Double> get(Map<String, ?> results,String fieldName){
        ProbabilityDistribution speciesField = (ProbabilityDistribution) results.get(fieldName);
        if(speciesField!=null){
            return speciesField.getValues();
        }
        return null;
    }
}
