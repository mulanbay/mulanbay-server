package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 预算和消费的年度比例评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class BudgetConsumeYEvaluateProcessor extends AbstractEvaluateProcessor {

    public BudgetConsumeYEvaluateProcessor() {
        super("预算和消费的年度比例评估处理","budgetConsume.y");
    }

    /**
     * 创建参数
     * @param score
     * @param dayIndex
     * @return
     */
    private Map<String, Number> createArgs(int score,int dayIndex){
        Map<String, Number> args = new HashMap<>();
        args.put("score", score);
        args.put("dayIndex", dayIndex);
        return args;
    }
    /**
     * 评估
     * @return
     */
    public Float evaluate(int score,int dayIndex){
        Map<String, Number> args = this.createArgs(score,dayIndex);
        return this.evaluateFloat(args,"rate");
    }

    /**
     * 评估(多标签)
     * @return
     */
    public Map<String,Float> evaluateMulti(int score,int dayIndex){
        Map<String, Number> args = this.createArgs(score,dayIndex);
        return this.evaluateFloats(args);
    }
}
