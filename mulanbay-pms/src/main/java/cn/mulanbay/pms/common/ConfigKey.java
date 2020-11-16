package cn.mulanbay.pms.common;

/**
 * 系统配置key
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ConfigKey {

    /**
     * 二次认证
     */
    public static final String USER_SEC_AUTH = "user_sec_auth";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * cookie里面使用
     */
    public static final String TOKEN_KEY = "Admin-Token";

    /**
     * 通用的Domain的id字段字段名
     */
    public static final String ID_FIELD_NAME = "id";


    /**
     * 默认管理员用户
     */
    public static final long DEFAULT_ADMIN_USER = 0L;

}
