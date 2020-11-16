package cn.mulanbay.schedule.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 调度服务器
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Entity
@Table(name = "task_server")
@DynamicInsert
@DynamicUpdate
public class TaskServer implements java.io.Serializable {

	private static final long serialVersionUID = -6002198656881095798L;

	private Long id;

	//服务器节点
	private String deployId;

	//IP地址
	private String ipAddress;

	private Boolean status;

	private Boolean supportDistri;

	//当前正在运行的job数
	private Integer cejc;

	//被调度的任务数
	private Integer sjc;

	//启动时间
	private Date startTime;

	//最后更新时间
	private Date lastUpdateTime;

	//停止时间
	private Date shutdownTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "deploy_id", unique = true, nullable = false, length = 32)
	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	@Column(name = "ip_address", nullable = false, length = 32)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "support_distri")
	public Boolean getSupportDistri() {
		return supportDistri;
	}

	public void setSupportDistri(Boolean supportDistri) {
		this.supportDistri = supportDistri;
	}

	@Column(name = "cejc")
	public Integer getCejc() {
		return cejc;
	}

	public void setCejc(Integer cejc) {
		this.cejc = cejc;
	}

	@Column(name = "sjc")
	public Integer getSjc() {
		return sjc;
	}

	public void setSjc(Integer sjc) {
		this.sjc = sjc;
	}

	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "last_update_time")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "shutdown_time")
	public Date getShutdownTime() {
		return shutdownTime;
	}

	public void setShutdownTime(Date shutdownTime) {
		this.shutdownTime = shutdownTime;
	}

}