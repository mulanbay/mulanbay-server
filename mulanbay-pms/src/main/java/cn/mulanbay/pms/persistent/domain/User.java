package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.AuthType;
import cn.mulanbay.pms.persistent.enums.FamilyMode;
import cn.mulanbay.pms.persistent.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

    private static final long serialVersionUID = -8290503768108861704L;

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String phone;
    //邮件发送
    private String email;
    //生日（计算最大心率使用到）
    private Date birthday;
    private Integer level;
    private Integer points;
    //二次认证类型(针对某些功能点)
    private AuthType secAuthType;
    //最后的登陆token
    private String lastLoginToken;
    //最后登录的模式
    private FamilyMode lastFamilyMode;
    private Date lastLoginTime;
    private String lastLoginIp;
    //用户状态
    private UserStatus status;
    //用户过期时间
    private Date expireTime;
    //头像地址
    private String avatar;
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

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Basic
    @Column(name = "sec_auth_type")
    public AuthType getSecAuthType() {
        return secAuthType;
    }

    public void setSecAuthType(AuthType secAuthType) {
        this.secAuthType = secAuthType;
    }

    @Basic
    @Column(name = "last_login_token")
    public String getLastLoginToken() {
        return lastLoginToken;
    }

    public void setLastLoginToken(String lastLoginToken) {
        this.lastLoginToken = lastLoginToken;
    }

    @Basic
    @Column(name = "last_family_mode")
    public FamilyMode getLastFamilyMode() {
        return lastFamilyMode;
    }

    public void setLastFamilyMode(FamilyMode lastFamilyMode) {
        this.lastFamilyMode = lastFamilyMode;
    }

    @Basic
    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "status")
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "expire_time")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public String getStatusName() {
        if (this.status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getSecAuthTypeName() {
        if (this.secAuthType != null) {
            return secAuthType.getName();
        } else {
            return null;
        }
    }
}
