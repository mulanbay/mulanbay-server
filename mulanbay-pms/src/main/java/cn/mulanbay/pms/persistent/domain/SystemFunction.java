package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 功能点配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "system_function")
public class SystemFunction implements java.io.Serializable {

    private static final long serialVersionUID = 529618585154626058L;
    private Long id;
    private String name;
    private String supportMethods;
    private String urlAddress;
    private UrlType urlType;
    private FunctionType functionType;
    private FunctionDataType functionDataType;
    private String imageName;
    private SystemFunction parent;
    private CommonStatus status;
    //针对什么类,查询使用
    private String beanName;
    //主键的域,查询使用
    private String idField;
    private IdFieldType idFieldType;
    private Integer orderIndex;
    private Boolean doLog;
    //是否计入触发统计（因为有些是筛选条件，这些并不需要统计）
    private Boolean triggerStat;
    //区分用户,有些公共的功能点不需要区分用户
    private Boolean diffUser;
    //是否登录验证
    private Boolean loginAuth;
    //是否授权认证
    private Boolean permissionAuth;
    //是否IP认证
    private Boolean ipAuth;
    //是否始终显示(针对需要权限认证的目录类型功能点)
    private Boolean alwaysShow;
    //是否请求限制
    private Boolean requestLimit;
    //限制周期
    private Integer requestLimitPeriod;
    //是否每天限制操作数(大于0说明要限制)
    private Integer dayLimit;
    //记录返回数据
    private Boolean recordReturnData;
    //奖励积分(正为加，负为减)
    private Integer rewardPoint;
    //错误代码定义，方便日志监控
    private Integer errorCode;
    //是否树形统计
    private Boolean treeStat;
    //需要二次验证
    private Boolean secAuth;
    //权限标识
    private String perms;
    //组件路径
    private String component;
    //路由地址
    private String path;
    //菜单可见
    private Boolean visible;
    //是不是路由（true时说明有在页面有对应的地址）
    private Boolean router;
    //外链
    private Boolean frame;
    //是否缓存，keep-alive使用
    private Boolean cache;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    List<SystemFunction> children = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "support_methods", nullable = false, length = 64)
    public String getSupportMethods() {
        return supportMethods;
    }

    public void setSupportMethods(String supportMethods) {
        this.supportMethods = supportMethods;
    }

    @Column(name = "url_address", nullable = false, length = 100)
    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Column(name = "url_type", nullable = false)
    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    @Column(name = "function_type", nullable = false)
    public FunctionType getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    @Column(name = "function_data_type")
    public FunctionDataType getFunctionDataType() {
        return functionDataType;
    }

    public void setFunctionDataType(FunctionDataType functionDataType) {
        this.functionDataType = functionDataType;
    }

    @Column(name = "image_name")
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "pid", nullable = true)
    public SystemFunction getParent() {
        return parent;
    }

    public void setParent(SystemFunction parent) {
        this.parent = parent;
    }

    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Column(name = "bean_name")
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Column(name = "id_field")
    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    @Column(name = "id_field_type")
    public IdFieldType getIdFieldType() {
        return idFieldType;
    }

    public void setIdFieldType(IdFieldType idFieldType) {
        this.idFieldType = idFieldType;
    }

    @Column(name = "order_index")
    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "do_log")
    public Boolean getDoLog() {
        return doLog;
    }

    public void setDoLog(Boolean doLog) {
        this.doLog = doLog;
    }

    @Column(name = "trigger_stat")
    public Boolean getTriggerStat() {
        return triggerStat;
    }

    public void setTriggerStat(Boolean triggerStat) {
        this.triggerStat = triggerStat;
    }

    @Column(name = "diff_user")
    public Boolean getDiffUser() {
        return diffUser;
    }

    public void setDiffUser(Boolean diffUser) {
        this.diffUser = diffUser;
    }

    @Column(name = "login_auth")
    public Boolean getLoginAuth() {
        return loginAuth;
    }

    public void setLoginAuth(Boolean loginAuth) {
        this.loginAuth = loginAuth;
    }

    @Column(name = "permission_auth")
    public Boolean getPermissionAuth() {
        return permissionAuth;
    }

    public void setPermissionAuth(Boolean permissionAuth) {
        this.permissionAuth = permissionAuth;
    }

    @Column(name = "ip_auth")
    public Boolean getIpAuth() {
        return ipAuth;
    }

    public void setIpAuth(Boolean ipAuth) {
        this.ipAuth = ipAuth;
    }

    @Column(name = "always_show")
    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    @Column(name = "request_limit")
    public Boolean getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(Boolean requestLimit) {
        this.requestLimit = requestLimit;
    }

    @Column(name = "request_limit_period")
    public Integer getRequestLimitPeriod() {
        return requestLimitPeriod;
    }

    public void setRequestLimitPeriod(Integer requestLimitPeriod) {
        this.requestLimitPeriod = requestLimitPeriod;
    }

    @Column(name = "day_limit")
    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    @Column(name = "record_return_data")
    public Boolean getRecordReturnData() {
        return recordReturnData;
    }

    public void setRecordReturnData(Boolean recordReturnData) {
        this.recordReturnData = recordReturnData;
    }

    @Column(name = "reward_point")
    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    @Column(name = "error_code")
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "tree_stat")
    public Boolean getTreeStat() {
        return treeStat;
    }

    public void setTreeStat(Boolean treeStat) {
        this.treeStat = treeStat;
    }

    @Column(name = "sec_auth")
    public Boolean getSecAuth() {
        return secAuth;
    }

    public void setSecAuth(Boolean secAuth) {
        this.secAuth = secAuth;
    }

    @Column(name = "perms")
    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    @Column(name = "component")
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "visible")
    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Column(name = "router")
    public Boolean getRouter() {
        return router;
    }

    public void setRouter(Boolean router) {
        this.router = router;
    }

    @Column(name = "frame")
    public Boolean getFrame() {
        return frame;
    }

    public void setFrame(Boolean frame) {
        this.frame = frame;
    }

    @Column(name = "cache")
    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "created_time", length = 19)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "last_modify_time", length = 19)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public List<SystemFunction> getChildren() {
        return children;
    }

    public void setChildren(List<SystemFunction> children) {
        this.children = children;
    }

    @Transient
    public String getUrlTypeName() {
        if (urlType != null) {
            return urlType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getFunctionTypeName() {
        if (functionType != null) {
            return functionType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getFunctionDataTypeName() {
        if (functionDataType != null) {
            return functionDataType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public Long getParentId() {
        if (parent != null) {
            return parent.getId();
        } else {
            return null;
        }
    }

    @Transient
    public Boolean getHasChildren() {
        if (functionDataType == FunctionDataType.M || functionDataType == FunctionDataType.C) {
            return true;
        } else {
            return false;
        }
    }

}
