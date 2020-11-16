package cn.mulanbay.pms.web.bean;

import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.enums.FamilyMode;

import java.util.List;

/**
 * 登陆用户信息
 *
 * @author fenghong
 */
public class LoginUser implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6230252639677382337L;

    //默认是个人
    private FamilyMode familyMode = FamilyMode.P;

    //登录token
    private String loginToken;

    private User user;

    //家长模式下家庭组下的用户列表
    private List<Long> userIdList;

    //权限角色编号
    private Long roleId;

    private long loginTime;

    private long expireTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FamilyMode getFamilyMode() {
        return familyMode;
    }

    public void setFamilyMode(FamilyMode familyMode) {
        this.familyMode = familyMode;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public Long getUserId() {
        return user.getId();
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 成员是否在家庭组里面
     *
     * @param userId
     * @return
     */
    public boolean userInFamily(Long userId) {
        for (Long u : userIdList) {
            if (u.longValue() == userId) {
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 获取用户级别
     *
     * @return
     */
    public Integer getLevel() {
        return user.getLevel();
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
