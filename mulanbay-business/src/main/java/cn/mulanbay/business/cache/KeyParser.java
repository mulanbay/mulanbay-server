package cn.mulanbay.business.cache;

import cn.mulanbay.common.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/6/18
 */
public class KeyParser {

    /**
     * 解析Spel表达式
     *
     * @param expression
     * @param point
     * @return
     */
    public static String parseExpression(String expression, JoinPoint point) {
        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer localVariableTable = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = localVariableTable.getParameterNames(method);
        if (StringUtil.isEmpty(expression) || parameterNames == null || args == null
                || parameterNames.length != args.length) {
            return expression;
        }
        SpelExpression spelExpression = new SpelExpressionParser().parseRaw(expression);
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }
        spelExpression.setEvaluationContext(evaluationContext);
        return spelExpression.getValue(String.class);
    }
}
