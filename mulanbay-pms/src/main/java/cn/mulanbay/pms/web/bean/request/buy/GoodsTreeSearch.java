package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class GoodsTreeSearch extends PageSearch implements BindUser {

    @Query(fieldName = "pid", op = Parameter.Operator.EQ)
    private Integer pid;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    private Boolean needRoot;

    private RootType rootType;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public RootType getRootType() {
        return rootType;
    }

    public void setRootType(RootType rootType) {
        this.rootType = rootType;
    }

    public enum RootType {
        //一共通用的，适合其他的页面调用（默认情况），一种是自定义的，在商品类型编辑页面使用
        COMMON, SELF;
    }
}
