package cn.mulanbay.ai.ml.processor;

import cn.mulanbay.ai.ml.common.MLConstant;
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
    public Double evaluate(int score,int dayIndex){
        Map<String, Number> args = this.createArgs(score,dayIndex);
        return this.evaluateDouble(args, MLConstant.RATE_LABEL);
    }

    /**
     * 评估(多标签)
     * @return
     */
    public Map<String,Double> evaluateMulti(int score,int dayIndex){
        Map<String, Number> args = this.createArgs(score,dayIndex);
        return this.evaluateDoubles(args);
    }
}
