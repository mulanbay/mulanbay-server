package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.LogLevel;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 系统日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "system_log")
public class SystemLog implements java.io.Serializable {

    private static final long serialVersionUID = 6569869954055287227L;
    private Long id;
    private Long userId;
    private String userName;
    private Integer errorCode;
    private LogLevel logLevel;
    private String title;
    private String content;
    private SystemFunction systemFunction;
    private String urlAddress;
    private String method;
    //调用开始时间
    private Date occurTime;
    private Date storeTime;
    //存储时间=storeTime-occurTime
    private long storeDuration;
    private String ipAddress;
    private String locationCode;
    private String macAddress;
    private String hostIpAddress;
    private String paras;
    private String idValue;
    private String exceptionClassName;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    //临时使用
    private Map paraMap;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "username", nullable = false, length = 32)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "error_code")
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "log_level")
    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "system_function_id", nullable = false)
    public SystemFunction getSystemFunction() {
        return systemFunction;
    }

    public void setSystemFunction(SystemFunction systemFunction) {
        this.systemFunction = systemFunction;
    }

    @Column(name = "url_address")
    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Column(name = "occur_time", length = 19)
    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    @Column(name = "store_time", length = 19)
    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    @Column(name = "store_duration")
    public long getStoreDuration() {
        return storeDuration;
    }

    public void setStoreDuration(long storeDuration) {
        this.storeDuration = storeDuration;
    }

    @Column(name = "ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name = "location_code")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name = "mac_address")
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Column(name = "host_ip_address")
    public String getHostIpAddress() {
        return hostIpAddress;
    }

    public void setHostIpAddress(String hostIpAddress) {
        this.hostIpAddress = hostIpAddress;
    }

    @Column(name = "paras")
    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    @Column(name = "id_value")
    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    @Column(name = "exception_class_name")
    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
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
    public Map getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map paraMap) {
        this.paraMap = paraMap;
    }

    @Transient
    public String getLogLevelName() {
        return logLevel == null ? null : logLevel.getName();
    }
}
