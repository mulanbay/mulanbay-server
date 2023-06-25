package cn.mulanbay.ai.ml.processor;

import org.dmg.pmml.FieldName;
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
     * 评估
     * @return
     */
    public Float evaluate(int score,int dayIndex){
        Map<FieldName, Number> args = new HashMap<>();
        args.put(FieldName.create("score"), score);
        args.put(FieldName.create("dayIndex"), dayIndex);
        return this.evaluateFloat(args,"rate");
    }

}
