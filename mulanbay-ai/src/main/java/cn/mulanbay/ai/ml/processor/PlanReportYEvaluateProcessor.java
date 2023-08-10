package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 年度计划报告评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class PlanReportYEvaluateProcessor extends AbstractEvaluateProcessor {

    public PlanReportYEvaluateProcessor() {
        super("年度计划报告评估处理","planReport.y");
    }

    /**
     * 创建参数
     * @param planConfigId
     * @param score
     * @param dayIndex
     * @return
     */
    private Map<String, Number> createArgs(long planConfigId,int score,int dayIndex){
        Map<String, Number> args = new HashMap<>();
        args.put("planConfigId", planConfigId);
        args.put("score", score);
        args.put("dayIndex", dayIndex);
        return args;
    }

    /**
     * 评估(多标签)
     * @return
     */
    public Map<String,Float> evaluateMulti(long planConfigId,int score,int dayIndex){
        Map<String, Number> args = this.createArgs(planConfigId,score,dayIndex);
        return this.evaluateFloats(args);
    }

}
