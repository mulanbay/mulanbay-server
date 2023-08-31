package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户评分评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class UserScoreEvaluateProcessor extends AbstractEvaluateProcessor {

    public UserScoreEvaluateProcessor() {
        super("用户评分评估处理","userScore");
    }

    /**
     * 创建参数
     * @param preScore
     * @return
     */
    private Map<String, Number> createArgs(int preScore){
        Map<String, Number> args = new HashMap<>();
        args.put("preScore", preScore);
        return args;
    }

    /**
     * 评估
     * @return
     */
    public Double evaluate(int preScore){
        Map<String, Number> args = this.createArgs(preScore);
        return this.evaluateDouble(args, "score");
    }


}
