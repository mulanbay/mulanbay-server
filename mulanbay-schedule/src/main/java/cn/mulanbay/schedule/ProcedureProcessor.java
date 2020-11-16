package cn.mulanbay.schedule;

import java.util.Date;

/**
 * 存储结果处理接口
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface ProcedureProcessor {

    /**
     * 执行存储过程
     * @param procedureName
     * @param startDate
     * @param endDate
     */
    void executeProcedure(String procedureName, Date startDate, Date endDate);

    /**
     * 执行存储过程
     * @param procedureName
     * @param date
     */
    void executeProcedure(String procedureName, Date date);

    /**
     * 执行存储过程
     * @param procedureName
     */
    void executeProcedure(String procedureName);

}
