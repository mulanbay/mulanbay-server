package cn.mulanbay.persistent.query;

import java.util.List;

/**
 * 分页查询结果定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class PageResult<T> {

	List<T> beanList;

	/**
	 * 最大记录数
	 */
	long maxRow;

	int pageSize;

	int page;

	public PageResult() {
	}

	public PageResult(PageResult from) {
		this.page = from.page;
		this.pageSize = from.pageSize;
		this.maxRow=from.maxRow;
	}

	public PageResult(int page,int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

	public long getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(long maxRow) {
		this.maxRow = maxRow;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
