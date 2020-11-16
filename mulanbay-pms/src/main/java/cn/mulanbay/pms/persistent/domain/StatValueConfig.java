package cn.mulanbay.pms.persistent.domain;


import cn.mulanbay.pms.persistent.enums.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 所有报表、计划、提醒类型的sql中值的选择在这里配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "stat_value_config")
public class StatValueConfig implements java.io.Serializable {

    private static final long serialVersionUID = 882330393814155329L;
    private Long id;

    private String name;

    private StatValueType type;

    private StatValueSource source;

    private StatValueClass valueClass;

    private Long fid;

    //当source为数据库查询时有效
    private String sqlContent;

    //当source为枚举类时有效
    private String enumClass;

    //当source为枚举类时有效
    private EnumIdType enumIdType;

    //当source为数据字典数据时有效
    private String dictGroupCode;

    //当source为json数据时有效
    private String jsonData;

    //是否记录由上层来决定
    private CasCadeType casCadeType;

    //是否和用户绑定，空表示不绑定
    private String userField;

    private Integer orderIndex;

    //提示信息
    private String promptMsg;

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
    @Column(name = "type")
    public StatValueType getType() {
        return type;
    }

    public void setType(StatValueType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "source")
    public StatValueSource getSource() {
        return source;
    }

    public void setSource(StatValueSource source) {
        this.source = source;
    }

    @Basic
    @Column(name = "value_class")
    public StatValueClass getValueClass() {
        return valueClass;
    }

    public void setValueClass(StatValueClass valueClass) {
        this.valueClass = valueClass;
    }

    @Basic
    @Column(name = "fid")
    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "sql_content")
    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    @Basic
    @Column(name = "enum_class")
    public String getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(String enumClass) {
        this.enumClass = enumClass;
    }

    @Basic
    @Column(name = "enum_id_type")
    public EnumIdType getEnumIdType() {
        return enumIdType;
    }

    public void setEnumIdType(EnumIdType enumIdType) {
        this.enumIdType = enumIdType;
    }

    @Basic
    @Column(name = "dict_group_code")
    public String getDictGroupCode() {
        return dictGroupCode;
    }

    public void setDictGroupCode(String dictGroupCode) {
        this.dictGroupCode = dictGroupCode;
    }

    @Basic
    @Column(name = "json_data")
    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Basic
    @Column(name = "cas_cade_type")
    public CasCadeType getCasCadeType() {
        return casCadeType;
    }

    public void setCasCadeType(CasCadeType casCadeType) {
        this.casCadeType = casCadeType;
    }

    @Basic
    @Column(name = "user_field")
    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    @Basic
    @Column(name = "order_index")
    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Basic
    @Column(name = "prompt_msg")
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }

    @Transient
    public String getTypeName() {
        if (type != null) {
            return type.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getCasCadeTypeName() {
        if (casCadeType != null) {
            return casCadeType.getName();
        } else {
            return null;
        }
    }
}
