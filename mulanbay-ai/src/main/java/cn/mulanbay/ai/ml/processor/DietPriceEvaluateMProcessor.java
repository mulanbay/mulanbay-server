package cn.mulanbay.ai.ml.processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 月度饮食价格评估处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
@Component
public class DietPriceEvaluateMProcessor extends AbstractEvaluateProcessor {

    public DietPriceEvaluateMProcessor() {
        super("月度饮食价格评估处理","dietPrice.m");
    }

    /**
     * 创建参数
     * @param month
     * @param smRate 自己做比例
     * @param rtRate 餐馆比例
     * @param toRate 外卖比例
     * @param mkRate 超市比例
     * @param otRate 其他比例
     * @return
     */
    private Map<String, Number> createArgs(int month,double smRate,double rtRate,double toRate,double mkRate,double otRate){
        Map<String, Number> args = new HashMap<>();
        args.put("month", month);
        return args;
    }

    /**
     * 评估
     * @return
     */
    public Double evaluate(int month,double smRate,double rtRate,double toRate,double mkRate,double otRate){
        Map<String, Number> args = this.createArgs(month,smRate,rtRate,toRate,mkRate,otRate);
        return this.evaluateDouble(args, "price");
    }


}
