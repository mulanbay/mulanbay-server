package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.enums.CasCadeType;

import java.util.ArrayList;
import java.util.List;

public class StatValueConfigDto {

    private String name;

    private String promptMsg;

    private CasCadeType casCadeType;

    private List<StatValueConfigDetail> list = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }

    public List<StatValueConfigDetail> getList() {
        return list;
    }

    public void setList(List<StatValueConfigDetail> list) {
        this.list = list;
    }

    public void addStatValueConfigDetail(StatValueConfigDetail detail) {
        list.add(detail);
    }

    public CasCadeType getCasCadeType() {
        return casCadeType;
    }

    public void setCasCadeType(CasCadeType casCadeType) {
        this.casCadeType = casCadeType;
    }
}
