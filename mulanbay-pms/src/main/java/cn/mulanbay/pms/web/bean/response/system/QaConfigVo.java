package cn.mulanbay.pms.web.bean.response.system;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/24
 */
public class QaConfigVo {
    private Long id;

    private String name;

    private int level =1;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
