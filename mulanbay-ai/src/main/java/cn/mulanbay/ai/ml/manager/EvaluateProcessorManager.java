package cn.mulanbay.ai.ml.manager;

import cn.mulanbay.ai.ml.processor.AbstractEvaluateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模型处理器管理
 *
 * @author fenghong
 * @create 2023-06-23
 */
@Component
public class EvaluateProcessorManager {

    @Autowired
    private List<AbstractEvaluateProcessor> processorList;

    public List<AbstractEvaluateProcessor> getProcessorList() {
        return processorList;
    }

    public void setProcessorList(List<AbstractEvaluateProcessor> processorList) {
        this.processorList = processorList;
    }
}
