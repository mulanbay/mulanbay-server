package cn.mulanbay.pms.web.bean.response;

/**
 * 列表数据（主要是适配与EasyUI）
 * 最开始的项目UI是基于EasyUI开发的
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DataGrid {

    private long total;

    private Object rows;

    private int page;

    public DataGrid() {
        super();
    }

    public DataGrid(long total, Object rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
