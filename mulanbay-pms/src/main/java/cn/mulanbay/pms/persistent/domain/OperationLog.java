package cn.mulanbay.pms.persistent.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 操作日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "operation_log")
public class OperationLog implements java.io.Serializable {

    private static final long serialVersionUID = 1044394976565512347L;
    private Long id;
    private Long userId;
    private String userName;
    private SystemFunction systemFunction;
    private String urlAddress;
    private String method;
    //调用开始时间
    private Date occurStartTime;
    //调用结束时间
    private Date occurEndTime;
    private Date storeTime;
    //调用处理时间=occurEndTime-occurStartTime
    private long handleDuration;
    //存储时间=storeTime-occurEndTime
    private long storeDuration;
    private String ipAddress;
    private String locationCode;
    private String macAddress;
    private String hostIpAddress;
    private String paras;
    private String idValue;
    private String returnData;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

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

    @Column(name = "occur_start_time", length = 19)
    public Date getOccurStartTime() {
        return occurStartTime;
    }

    public void setOccurStartTime(Date occurStartTime) {
        this.occurStartTime = occurStartTime;
    }

    @Column(name = "occur_end_time", length = 19)
    public Date getOccurEndTime() {
        return occurEndTime;
    }

    public void setOccurEndTime(Date occurEndTime) {
        this.occurEndTime = occurEndTime;
    }

    @Column(name = "store_time", length = 19)
    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    @Column(name = "handle_duration")
    public long getHandleDuration() {
        return handleDuration;
    }

    public void setHandleDuration(long handleDuration) {
        this.handleDuration = handleDuration;
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

    @Column(name = "return_data")
    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
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

}
