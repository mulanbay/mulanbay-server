package cn.mulanbay.persistent.query;

/**
 * 排序定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class Sort {

	public static final String DESC="desc";
	
	public static final String ASC="asc";
	
	
	public Sort(String fieldName, String sortType) {
		super();
		this.fieldName = fieldName;
		this.sortType = sortType;
	}

	String fieldName;
	
	String sortType;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public String getSortString(){
		return fieldName+" "+sortType;
	}
}
