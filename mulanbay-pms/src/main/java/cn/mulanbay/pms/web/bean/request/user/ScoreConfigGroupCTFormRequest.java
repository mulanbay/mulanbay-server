package cn.mulanbay.pms.web.bean.request.user;

public class ScoreConfigGroupCTFormRequest {

    //模板的ID
    private Long templateId;

    //新的分组的名称
    private String name;

    private String code;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
