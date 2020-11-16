package cn.mulanbay.persistent.query;

/**
 * 查询时横跨多个字段查询
 * 同一个条件可以针对多个域
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public enum CrossType {

    NULL(null),AND("and"),OR("or");

    private String symbol;

    CrossType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
