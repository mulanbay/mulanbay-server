package cn.mulanbay.common.aop;

/**
 * 在系统中，经常需要得到当前用户的用户等级
 * 通过用户等级来判断用户能用那些资源（比如等级越高能看到的资源越多）
 * 定义这个接口通过aop来统一注入，从而不需要每个请求方法里来获取
 *
 * 实现过程参考
 * @see cn.mulanbay.pms.web.aop.ControllerHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface BindUserLevel {

    /**
     * 设置用户级别
     * @param level
     */
    public void setLevel(Integer level);
}
