package cn.mulanbay.persistent.query;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 通用查询的查询类构建规则类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class QueryBuilder {

    /**
     * 创建查询
     * @return
     */
    public PageRequest buildQuery(){
        return build(this,0);
    }/**
     * 创建查询
     * @return
     */
    public PageRequest buildQuery(int firstIndex){
        return build(this,firstIndex);
    }

    /**
     *
     * @param o
     * @param firstIndex
     * Hibernate 5开始 不支持Legacy-style,需要在后面添加数字下标或者变量名
     * 否则会报出异常：Legacy-style query parameters (`?`) are no longer supported
     * @return
     */
    public PageRequest build(Object o,int firstIndex) {
        try {
            PageRequest pr = new PageRequest();
            pr.setFirstIndex(firstIndex);
            Field[] fs = o.getClass().getDeclaredFields();
            for (Field f : fs) {
                Query q = f.getAnnotation(Query.class);
                if (q != null) {
                    f.setAccessible(true);
                    Object v = f.get(o);
                    f.setAccessible(false);
                    if (q.supportNullValue()==false&&(v == null|| "".equals(v))) {
                        continue;
                    }
                    if(v instanceof List || v instanceof Set){
                        //也有可能为空集合
                        Collection cc = (Collection) v;
                        if(cc.isEmpty()){
                            continue;
                        }
                    }
                    // 如果类型是REFER类型，那么fieldName为手动指定名称
                    String fieldName = q.fieldName();
                    String referFieldName =q.referFieldName();
                    Parameter.Operator op = q.op();
                    if(referFieldName!=null&&!referFieldName.isEmpty()){
                        Field fie = o.getClass().getDeclaredField(referFieldName);
                        fie.setAccessible(true);
                        if(q.referType()==ReferType.FIELD_REFER){
                            //查询字段
                            fieldName = fie.get(o).toString();
                        }else{
                            //操作类型
                            op = (Parameter.Operator) fie.get(o);
                        }
                        fie.setAccessible(false);
                    }
                    Parameter p = new Parameter(fieldName, op);
                    p.setValue(v);
                    p.setCrossType(q.crossType());
                    pr.addParameter(p);
                }
            }
            return pr;
        } catch (Exception e) {
            throw new PersistentException(
                    ErrorCode.QUERY_BUILD_ERROR, "构建查询类PageRequest异常",e);
        }
    }
}
