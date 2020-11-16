package cn.mulanbay.pms.web.bean.response.fund;

public class UserSharesWarnDetailVo {

    private String indexValue;

    private int[] values = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    /**
     * 增加值
     *
     * @param warnType
     * @param v
     */
    public void addValue(int warnType, int v) {
        int iv = values[warnType];
        iv += v;
        values[warnType] = iv;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }
}
