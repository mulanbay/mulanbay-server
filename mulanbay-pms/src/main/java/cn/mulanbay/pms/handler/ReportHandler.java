package cn.mulanbay.pms.handler;

import cn.mulanbay.ai.ml.processor.PlanReportMEvaluateProcessor;
import cn.mulanbay.ai.ml.processor.PlanReportYEvaluateProcessor;
import cn.mulanbay.business.handler.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 报表处理类
 *
 * @author fenghong
 * @create 2023-08-09
 */
@Component
public class ReportHandler extends BaseHandler {

    @Autowired
    PlanReportMEvaluateProcessor mEvaluateProcessor;

    @Autowired
    PlanReportYEvaluateProcessor yEvaluateProcessor;

    @Autowired
    UserScoreHandler userScoreHandler;

    public ReportHandler() {
        super("报表处理类");
    }

    /**
     * 月度预测
     * @param userId
     * @param planConfigId
     * @param month
     * @param score
     * @param dayIndex
     * @return
     */
    public Map<String,Float> predictMonthRate(Long userId,long planConfigId,int month,Integer score,int dayIndex){
        if(score==null){
            score = userScoreHandler.getLatestScore(userId);
        }
        Map<String,Float> pm = mEvaluateProcessor.evaluateMulti(planConfigId,month,score,dayIndex);
        return pm;
    }

    /**
     * 年度预测
     * @param userId
     * @param planConfigId
     * @param score
     * @param dayIndex
     * @return
     */
    public Map<String,Float> predictYearRate(Long userId,long planConfigId,Integer score,int dayIndex){
        if(score==null){
            score = userScoreHandler.getLatestScore(userId);
        }
        Map<String,Float> pm = yEvaluateProcessor.evaluateMulti(planConfigId,score,dayIndex);
        return pm;
    }
}
