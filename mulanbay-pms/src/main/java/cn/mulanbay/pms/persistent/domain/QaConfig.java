package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.QaResultType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 智能客服的匹配设置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "qa_config")
public class QaConfig implements Serializable {

    private static final long serialVersionUID = 7063829351544112618L;

    private Long id;
    //名称
    private String name;
    //父类，可以多层匹配组成网络
    private Long parentId;
    //返回类型
    private QaResultType resultType;
    //关键字
    private String keywords;
    //回复的内容
    private String replayContent;
    //resultType=SQL时有用
    private String replayTitle;
    //模板，SQL查询出的列表时使用，resultType=SQL时有用
    private String columnTemplate;
    //多个QA可能匹配模式不同但是最后的回复方式一样
    private Long referQaId;
    //处理的代码，由此对应Handler
    private String handleCode;
    //是否缓存
    private Boolean matchCache;
    private Short orderIndex;
    //账户状态
    private CommonStatus status;
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
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "result_type")
    public QaResultType getResultType() {
        return resultType;
    }

    public void setResultType(QaResultType resultType) {
        this.resultType = resultType;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "replay_content")
    public String getReplayContent() {
        return replayContent;
    }

    public void setReplayContent(String replayContent) {
        this.replayContent = replayContent;
    }

    @Basic
    @Column(name = "replay_title")
    public String getReplayTitle() {
        return replayTitle;
    }

    public void setReplayTitle(String replayTitle) {
        this.replayTitle = replayTitle;
    }

    @Basic
    @Column(name = "column_template")
    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    @Basic
    @Column(name = "refer_qa_id")
    public Long getReferQaId() {
        return referQaId;
    }

    public void setReferQaId(Long referQaId) {
        this.referQaId = referQaId;
    }

    @Basic
    @Column(name = "handle_code")
    public String getHandleCode() {
        return handleCode;
    }

    public void setHandleCode(String handleCode) {
        this.handleCode = handleCode;
    }

    @Basic
    @Column(name = "match_cache")
    public Boolean getMatchCache() {
        return matchCache;
    }

    public void setMatchCache(Boolean matchCache) {
        this.matchCache = matchCache;
    }

    @Basic
    @Column(name = "order_index")
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Basic
    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
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
    public String getResultTypeName() {
        return resultType == null ? null : resultType.getName();
    }

    /**
     * 树形菜单使用
     * @return
     */
    @Transient
    public Boolean getHasChildren() {
        return true;
    }
}
