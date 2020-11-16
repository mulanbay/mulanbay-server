package cn.mulanbay.pms.web.bean.request.system;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.QaResultType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class QaConfigFormRequest {

    private Long id;

    //名称
    @NotEmpty(message = "{validate.qaConfig.name.notEmpty}")
    private String name;

    private Long parentId;

    //匹配类型
    @NotNull(message = "{validate.qaConfig.resultType.NotNull}")
    private QaResultType resultType;
    //匹配表达式
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
    @NotEmpty(message = "{validate.qaConfig.handleCode.notEmpty}")
    private String handleCode;
    //是否缓存
    @NotNull(message = "{validate.qaConfig.matchCache.NotNull}")
    private Boolean matchCache;
    @NotNull(message = "{validate.qaConfig.orderIndex.NotNull}")
    private Short orderIndex;
    //账户状态
    @NotNull(message = "{validate.qaConfig.status.NotNull}")
    private CommonStatus status;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public QaResultType getResultType() {
        return resultType;
    }

    public void setResultType(QaResultType resultType) {
        this.resultType = resultType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getReplayContent() {
        return replayContent;
    }

    public void setReplayContent(String replayContent) {
        this.replayContent = replayContent;
    }

    public String getReplayTitle() {
        return replayTitle;
    }

    public void setReplayTitle(String replayTitle) {
        this.replayTitle = replayTitle;
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public Long getReferQaId() {
        return referQaId;
    }

    public void setReferQaId(Long referQaId) {
        this.referQaId = referQaId;
    }

    public String getHandleCode() {
        return handleCode;
    }

    public void setHandleCode(String handleCode) {
        this.handleCode = handleCode;
    }

    public Boolean getMatchCache() {
        return matchCache;
    }

    public void setMatchCache(Boolean matchCache) {
        this.matchCache = matchCache;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
