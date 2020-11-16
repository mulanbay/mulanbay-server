package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户图表配置模板
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "chart_config")
public class ChartConfig implements java.io.Serializable {

    private static final long serialVersionUID = 4564004216708394161L;
    private Long id;
    private String name;
    private String title;
    //请求参数
    private String para;
    private CommonStatus status;
    private Short orderIndex;
    //关联的bean，可以根据这个在各个其他界面来查询本业务管理的计划
    private String relatedBeans;
    private Integer level;
    //API的链接地址（查询图表数据的API）
    private String url;
    //详情的跳转地址（该图表所对应的业务数据的跳转URL，可能是路由中的name）
    private String detailUrl;
    private ChartType chartType;
    //是否支持时间查询，有些图表其实不支持时间查询
    private Boolean supportDate;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "para")
    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    @Basic
    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
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
    @Column(name = "related_beans")
    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "detail_url")
    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Basic
    @Column(name = "chart_type")
    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    @Basic
    @Column(name = "support_date")
    public Boolean getSupportDate() {
        return supportDate;
    }

    public void setSupportDate(Boolean supportDate) {
        this.supportDate = supportDate;
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
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getChartTypeName() {
        if (chartType != null) {
            return chartType.getName();
        } else {
            return null;
        }
    }

}
