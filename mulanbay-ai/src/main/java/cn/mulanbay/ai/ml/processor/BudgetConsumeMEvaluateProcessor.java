package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 预算和消费的月度比例评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class BudgetConsumeMEvaluateProcessor extends AbstractEvaluateProcessor {

    public BudgetConsumeMEvaluateProcessor() {
        super("预算和消费的月度比例评估处理","budgetConsume.m");
    }

    /**
     * 创建参数
     * @param month
     * @param score
     * @param dayIndex
     * @return
     */
    private Map<String, Number> createArgs(int month,int score,int dayIndex){
        Map<String, Number> args = new HashMap<>();
        args.put("month", month);
        args.put("score", score);
        args.put("dayIndex", dayIndex);
        return args;
    }

    /**
     * 评估
     * @return
     */
    public Double evaluate(int month,int score,int dayIndex){
        Map<String, Number> args = this.createArgs(month,score,dayIndex);
        return this.evaluateDouble(args,"rate");
    }

    /**
     * 评估(多标签)
     * @return
     */
    public Map<String,Double> evaluateMulti(int month,int score,int dayIndex){
        Map<String, Number> args = this.createArgs(month,score,dayIndex);
        return this.evaluateDoubles(args);
    }

}
