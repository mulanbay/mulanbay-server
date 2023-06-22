package cn.mulanbay.ai.ml.processor;

import org.dmg.pmml.FieldName;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ml.pmml.file.budgetConsume.m}")
    protected String moduleFile;

    public BudgetConsumeMEvaluateProcessor() {
        super("预算和消费的月度比例评估处理");
    }

    /**
     * 评估
     * @return
     */
    public Float evaluate(int month,int score,int dayIndex){
        Map<FieldName, Number> args = new HashMap<>();
        args.put(FieldName.create("month"), month);
        args.put(FieldName.create("score"), score);
        args.put(FieldName.create("dayIndex"), dayIndex);
        return this.evaluate(args,"rate");
    }

    @Override
    public String getModuleFile() {
        return moduleFile;
    }
}
