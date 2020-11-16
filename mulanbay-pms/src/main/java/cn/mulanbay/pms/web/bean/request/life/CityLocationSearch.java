package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class CityLocationSearch extends PageSearch {

    @Query(fieldName = "location", op = Parameter.Operator.LIKE)
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
