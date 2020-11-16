package cn.mulanbay.pms.web.aop;

import cn.mulanbay.common.aop.BindFamily;
import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.TokenHandler;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.FamilyMode;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.web.bean.request.PageSearch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 全局Aop
 * 登录、权限验证及用户信息的自动注入
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
@Aspect
public class ControllerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    @Value("${system.pageSearch.maxPageSize}")
    int maxPageSize;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    protected TokenHandler tokenHandler;

    @Autowired
    CacheHandler cacheHandler;

    private HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * controller包的子包里面任何方法
     */
    @Pointcut("execution(public * cn.mulanbay.pms.web.controller.*.*(..))")
    public void setUserInfo() {
    }

    /**
     * controller包的子包里面任何方法
     */
    @Pointcut("execution(public * cn.mulanbay.pms.web.controller.*.*(..))")
    public void doLog() {
    }

    @Before("setUserInfo()")
    public void beforeBuss(JoinPoint joinPoint) {
        try {
            //检查功能点是否启用
            HttpServletRequest request = this.getRequest();
            String url = request.getServletPath();
            String method = request.getMethod();
            SystemFunction sf = systemConfigHandler.getFunction(url, method);
            if (sf == null) {
                logger.warn("url:" + url + ",method:" + method + "未配置功能定义");
                throw new ApplicationException(ErrorCode.FUNCTION_UN_DEFINE);
            } else if (sf.getStatus() == CommonStatus.DISABLE) {
                throw new ApplicationException(PmsErrorCode.SYSTEM_FUNCTION_DISABLED);
            }
            boolean autoLoad = !sf.getSecAuth();
            LoginUser loginUser = tokenHandler.getLoginUser(request, autoLoad);
            if (sf.getLoginAuth()) {
                if (loginUser == null) {
                    throw new ApplicationException(ErrorCode.USER_NOT_LOGIN);
                }
                //刷新用户
                tokenHandler.verifyToken(loginUser);
                if (sf.getRequestLimit()) {
                    String key = "request_limit:" + loginUser.getUserId() + ":" + url;
                    //请求限制
                    String s = cacheHandler.getForString(key);
                    if (s != null) {
                        throw new ApplicationException(ErrorCode.USER_REQUEST_TOO_FREQ);
                    } else {
                        cacheHandler.set(key, "123", sf.getRequestLimitPeriod());
                    }
                }
                if (sf.getDayLimit() > 0) {
                    String key = "request_limit_day:" + DateUtil.getToday(DateUtil.FormatDay1) + ":" + loginUser.getUserId() + ":" + url;
                    //请求限制
                    Integer s = cacheHandler.get(key, Integer.class);
                    if (s != null) {
                        if (s.intValue() < sf.getDayLimit()) {
                            s = s + 1;
                            cacheHandler.set(key, s, 24 * 3600);
                        } else {
                            throw new ApplicationException(ErrorCode.USER_FUNCTION_TOO_FREQ);
                        }
                    } else {
                        cacheHandler.set(key, 1, 24 * 3600);
                    }
                }
                if (sf.getPermissionAuth()) {
                    // 权限认证
                    Long roleId = loginUser.getRoleId();
                    if (roleId == null) {
                        throw new ApplicationException(ErrorCode.USER_NOT_AUTH);
                    }
                    boolean b = systemConfigHandler.isRoleAuth(roleId, sf.getId());
                    if (!b) {
                        throw new ApplicationException(ErrorCode.USER_NOT_AUTH);
                    }
                }
            }
            //设置用户等信息
            handleRequestInfoSet(joinPoint, loginUser);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("do beforeBuss error", e);
        }
    }

    @AfterReturning(value = "doLog()", returning = "resultMap")
    public void afterBuss(JoinPoint joinpoint, Object resultMap) {
    }

    /**
     * 抛出异常时才调用
     */
    @AfterThrowing("doLog()")
    public void afterThrowing() {
        //System.out.println("校验token出现异常了......");
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public void handleRequestInfoSet(JoinPoint joinPoint, LoginUser loginUser) {
        Object[] arguments = joinPoint.getArgs();
        for (Object arg : arguments) {
            if (arg != null) {
                if (loginUser != null) {
                    if (arg instanceof BindUserLevel) {
                        BindUserLevel bu = (BindUserLevel) arg;
                        bu.setLevel(loginUser.getLevel());
                    }
                    // 个人模式下，直接设置为当前用户
                    if (arg instanceof BindUser && loginUser.getFamilyMode() == FamilyMode.P) {
                        BindUser bu = (BindUser) arg;
                        bu.setUserId(loginUser.getUserId());
                    }
                    if (loginUser.getFamilyMode() == FamilyMode.F) {
                        //家庭模式下设置家庭组员
                        if (arg instanceof BindFamily) {
                            BindFamily bf = (BindFamily) arg;
                            bf.setUserIdList(loginUser.getUserIdList());
                            //假如是要支持家庭模式，判断用户编号是否在家庭组员里面
                            if (arg instanceof BindUser) {
                                BindUser bu = (BindUser) arg;
                                Long userId = bu.getUserId();
                                if (userId != null) {
                                    boolean b = loginUser.userInFamily(userId);
                                    if (!b) {
                                        throw new ApplicationException(ErrorCode.USER_NOT_IN_FAMILY);
                                    }
                                }
                            }
                        }
                    }
                }
                // 判断分页数据中的最大数
                if (arg instanceof PageSearch) {
                    PageSearch bu = (PageSearch) arg;
                    if (bu.getPageSize() > maxPageSize) {
                        throw new ApplicationException(ErrorCode.PAGE_SIZE_OVER_MAX);
                    }
                }
                //时间查询类添加23:59:59
                if (arg instanceof FullEndDateTime) {
                    FullEndDateTime qwu = (FullEndDateTime) arg;
                    Date endDate = qwu.getEndDate();
                    if (endDate != null) {
                        qwu.setEndDate(DateUtil.getTodayTillMiddleNightDate(endDate));
                    }
                }
            }

        }
    }
}
