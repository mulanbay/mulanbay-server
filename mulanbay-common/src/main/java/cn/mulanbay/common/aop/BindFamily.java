package cn.mulanbay.common.aop;

import java.util.List;

/**
 * 家长模式定义时使用
 * 设置当前家庭下的用户ID列表
 *
 * 实现过程参考
 * @see cn.mulanbay.pms.web.aop.ControllerHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface BindFamily {

    /**
     * 用户列表
     * @return
     */
    public List<Long> getUserIdList();

    /**
     * 设置用户列表
     * @param userIdList
     */
    public void setUserIdList(List<Long> userIdList);

}
