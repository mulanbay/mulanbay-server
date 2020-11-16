package cn.mulanbay.common.aop;

/**
 * 在系统中，经常需要得到当前用户的userId
 * 定义这个接口通过aop来统一注入，从而不需要每个请求方法里来获取
 *
 * 实现过程参考
 * @see cn.mulanbay.pms.web.aop.ControllerHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface BindUser {

    /**
     * 获取用户ID
     * @return
     */
    public Long getUserId();

    /**
     * 设置用户ID
     * @param userId
     */
    public void setUserId(Long userId);

}
