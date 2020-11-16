package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 省份信息
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "province")
public class Province implements java.io.Serializable {

    private static final long serialVersionUID = 7063829351544112618L;
    private Integer id;
    private String name;
    //地图显示使用，百度的echarts的省份等名称和第三方的有点不一样，比如第三方：浙江省，百度的echarts需要的只是：浙江
    private String mapName;

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

    @Transient
    public String getText() {
        return name;
    }
}
