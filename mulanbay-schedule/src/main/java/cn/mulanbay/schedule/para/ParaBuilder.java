package cn.mulanbay.schedule.para;

import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.schedule.ScheduleErrorCode;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 调度参数构建规则类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ParaBuilder {

    private static Logger logger = Logger.getLogger(ParaBuilder.class);

    /**
     * 构建参数定义
     * @param ps
     * @return
     */
    public static List<ParaDefine> buildDefine(Class ps) {
        try {
            if(ps==null){
                return null;
            }
            List<ParaDefine> list = new ArrayList<>();
            Field[] fs = ps.getDeclaredFields();
            for (Field f : fs) {
                JobParameter jp = f.getAnnotation(JobParameter.class);
                ParaDefine pd = new ParaDefine();
                if(jp!=null){
                    pd.setField(f.getName());
                    pd.setDataType(jp.dataType());
                    pd.setName(jp.name());
                    pd.setScale(jp.scale());
                    pd.setDesc(jp.desc());
                    pd.setEditType(jp.editType());
                    pd.setEditData(jp.editData());
                }else{
                    //默认以string编辑
                    pd.setField(f.getName());
                    pd.setDataType(String.class);
                    pd.setName(f.getName());
                    pd.setScale(0);
                    pd.setDesc("未配置定义");
                    pd.setEditType(EditType.TEXT);
                }
                list.add(pd);
            }
            return list;
        } catch (Exception e) {
            logger.error("构建参数定义异常",e);
            throw new PersistentException(
                    ScheduleErrorCode.JOB_PARA_BUILD_ERROR, "构建参数定义异常",e);
        }
    }
}
