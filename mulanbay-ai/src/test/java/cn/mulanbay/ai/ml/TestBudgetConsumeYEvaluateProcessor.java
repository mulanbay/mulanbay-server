package cn.mulanbay.ai.ml;

import cn.mulanbay.ai.ml.processor.BudgetConsumeYEvaluateProcessor;
import cn.mulanbay.ai.ml.processor.bean.BudgetConsumeER;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author fenghong
 * @create 2023-06-21
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestBudgetConsumeYEvaluateProcessor {

    @Autowired
    BudgetConsumeYEvaluateProcessor evaluateProcessor;

    private double min = 0f;

    private double max = 1.5f;

    //@Before
    public void init() {
        System.out.println("init...");
        evaluateProcessor.init();
    }

    /**
     * 单标签预测,已废弃
     */
    //@Test
    @Deprecated
    public void testEvaluate() {
        double evMin = max;
        double evMax = min;
        for (int score = 0; score <= 100; score++) {
            for (int dayIndex = 1; dayIndex <= 366; dayIndex++) {
                Double v = evaluateProcessor.evaluate(score, dayIndex);
                String vs = "score=" + score + ",dayIndex=" + dayIndex + ",预测值=" + v;
                Assert.notNull(v, "预测值为空," + vs);
                Assert.isTrue(v >= min, "预测值低于最低值," + vs);
                Assert.isTrue(v <= max, "预测值大于最高值," + vs);
                System.out.println(vs);
                if (v < evMin) {
                    evMin = v;
                }
                if (v > evMax) {
                    evMax = v;
                }
            }
            System.out.println("预测最小值:" + evMin + "预测最大值:" + evMax);
        }
    }

    /**
     * 多标签预测
     */
    //@Test
    public void testMultiEvaluate() {
        for (int score = 0; score <= 100; score++) {
            for (int dayIndex = 1; dayIndex <= 366; dayIndex++) {
                BudgetConsumeER er = evaluateProcessor.evaluateMulti(score,dayIndex);
                Double predictRate1 = er.getNcRate();
                Double predictRate2 = er.getBcRate();
                String vs1 = "score=" + score + ",dayIndex=" + dayIndex + ",rate1预测值=" + predictRate1;
                String vs2 = "score=" + score + ",dayIndex=" + dayIndex + ",rate2预测值=" + predictRate2;
                Assert.notNull(predictRate1, "预测值为空," + vs1);
                Assert.notNull(predictRate2, "预测值为空," + vs2);
                Assert.isTrue(predictRate1 >= min, "预测值低于最低值," + vs1);
                Assert.isTrue(predictRate2 <= max, "预测值大于最高值," + vs2);
                Assert.isTrue(predictRate1 >= min, "预测值低于最低值," + vs1);
                Assert.isTrue(predictRate2 <= max, "预测值大于最高值," + vs2);
                System.out.println(vs1);
                System.out.println(vs2);
            }
        }
    }
}
