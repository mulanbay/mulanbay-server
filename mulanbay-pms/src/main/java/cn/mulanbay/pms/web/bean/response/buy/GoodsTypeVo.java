package cn.mulanbay.pms.web.bean.response.buy;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import java.util.List;

public class GoodsTypeVo {

    private Integer id;
    private Integer parentId;
    private String parentName;
    private String name;
    private String behaviorName;
    private CommonStatus status;
    private Short orderIndex;
    // 是否加入统计
    private Boolean statable;
    private List<GoodsTypeVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }

    public List<GoodsTypeVo> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsTypeVo> children) {
        this.children = children;
    }

    /**
     * 支持vant的treeSelect
     * @return
     */
    public String getText(){
        return name;
    }
}
