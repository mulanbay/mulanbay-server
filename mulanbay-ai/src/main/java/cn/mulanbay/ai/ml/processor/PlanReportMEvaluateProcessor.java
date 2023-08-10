package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 月度计划报告评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class PlanReportMEvaluateProcessor extends AbstractEvaluateProcessor {

    public PlanReportMEvaluateProcessor() {
        super("月度计划报告评估处理","planReport.m");
    }

    /**
     * 创建参数
     * @param planConfigId
     * @param month
     * @param score
     * @param dayIndex
     * @return
     */
    private Map<String, Number> createArgs(long planConfigId,int month,int score,int dayIndex){
        Map<String, Number> args = new HashMap<>();
        args.put("planConfigId", planConfigId);
        args.put("month", month);
        args.put("score", score);
        args.put("dayIndex", dayIndex);
        return args;
    }

    /**
     * 评估(多标签)
     * @return
     */
    public Map<String,Float> evaluateMulti(long planConfigId,int month,int score,int dayIndex){
        Map<String, Number> args = this.createArgs(planConfigId,month,score,dayIndex);
        return this.evaluateFloats(args);
    }

}
