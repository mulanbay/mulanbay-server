package cn.mulanbay.schedule.para;

public class ParaDefine {

    private String name;

    private String field;

    private Class DataType;

    private int scale;

    private String desc;

    private EditType editType;

    private String editData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Class getDataType() {
        return DataType;
    }

    public void setDataType(Class dataType) {
        DataType = dataType;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EditType getEditType() {
        return editType;
    }

    public void setEditType(EditType editType) {
        this.editType = editType;
    }

    public String getEditData() {
        return editData;
    }

    public void setEditData(String editData) {
        this.editData = editData;
    }
}
