package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 城市信息
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "city")
public class City implements java.io.Serializable {

    private static final long serialVersionUID = -7957057082541835L;
    private Integer id;
    private String name;
    private String mapName;
    private Integer provinceId;
    private String zipCode;

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "map_name", length = 32)
    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @Column(name = "province_id")
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "zip_code", nullable = false, length = 32)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Transient
    public String getText() {
        return name;
    }
}
