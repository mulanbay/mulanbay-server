package cn.mulanbay.ai.ml;

import cn.mulanbay.ai.ml.processor.BudgetConsumeYEvaluateProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 *
 * @author fenghong
 * @create 2023-06-21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestBudgetConsumeYEvaluateProcessor {

    @Autowired
    BudgetConsumeYEvaluateProcessor evaluateProcessor;

    private float min = -0.2f ;

    private float max = 1.5f;

    @Before
    public void init(){
        System.out.println("init...");
        evaluateProcessor.init();
    }

    @Test
    public void testEvaluate(){
        float evMin = max;
        float evMax = min;
        for(int score = 1;score<=100;score=score*10){
            for(int dayIndex = 1;dayIndex<=366;dayIndex++){
                Float v = evaluateProcessor.evaluate(score,dayIndex);
                String vs = "score="+score+",dayIndex="+dayIndex+",预测值="+v;
                Assert.notNull(v,"预测值为空,"+vs);
                Assert.isTrue(v>=min,"预测值低于最低值,"+vs);
                Assert.isTrue(v<=max,"预测值大于最高值,"+vs);
                System.out.println(vs);
                if(v<evMin){
                    evMin = v;
                }
                if(v>evMax){
                    evMax = v;
                }
            }
            System.out.println("预测最小值:"+evMin+"预测最大值:"+evMax);
        }
    }
}
