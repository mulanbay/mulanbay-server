package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.BudgetFeeType;
import cn.mulanbay.pms.persistent.enums.BudgetType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.web.bean.request.PageSearch;

public class BudgetSearch extends PageSearch implements BindUser {

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    //类型
    @Query(fieldName = "type", op = Parameter.Operator.EQ)
    private BudgetType type;

    //周期类型
    @Query(fieldName = "period", op = Parameter.Operator.EQ)
    private PeriodType period;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    @Query(fieldName = "remind", op = Parameter.Operator.EQ)
    private Boolean remind;

    @Query(fieldName = "feeType", op = Parameter.Operator.EQ)
    private BudgetFeeType feeType;

    @Query(fieldName = "goodsTypeId", op = Parameter.Operator.EQ)
    private Integer goodsTypeId;

    @Query(fieldName = "subGoodsTypeId", op = Parameter.Operator.EQ)
    private Integer subGoodsTypeId;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public BudgetFeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(BudgetFeeType feeType) {
        this.feeType = feeType;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getSubGoodsTypeId() {
        return subGoodsTypeId;
    }

    public void setSubGoodsTypeId(Integer subGoodsTypeId) {
        this.subGoodsTypeId = subGoodsTypeId;
    }
}
