package cn.mulanbay.ai.ml.processor.bean;

/**
 * 预测结果集
 *
 * @author fenghong
 * @create 2023-06-21
 */
public class EvaluateTarget {

    private String fieldName;

    private Object value;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
