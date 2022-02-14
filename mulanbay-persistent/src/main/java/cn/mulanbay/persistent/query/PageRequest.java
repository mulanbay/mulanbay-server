package cn.mulanbay.persistent.query;

import cn.mulanbay.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 分页查询基础封装类
 * 针对统一通用的查询
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class PageRequest {

	/**
	 * 不分页的页码定义
	 */
	public static final int NO_PAGE=0;

	/**
	 * 第一个参数下标，针对Hibernate5升级
	 */
	int firstIndex=0;

	/**
	 * 默认不分页
	 */
	int page = NO_PAGE;

	int pageSize;

	/**
	 * 获取对象（HQL模式下有效）
	 */
	private Class beanClass;

	private String dataRule;

	/**
	 * 是否需要where语句，默认有，因为有些情况下主查询已经包含where语句
	 */
	private boolean needWhere=true;

	private boolean needTotal;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return beanClass;
	}

	@SuppressWarnings("rawtypes")
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

	/**
	 * 获取下一个的索引值，一般是给外层额外的SQL拼接语句
	 * @return
	 */
	public int getNextIndex() {
		return firstIndex;
	}

	/**
	 * 设置参数绑定的第一个索引值，默认0
	 * @param firstIndex
	 */
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public boolean isNeedTotal() {
		return needTotal;
	}

	public void setNeedTotal(boolean needTotal) {
		this.needTotal = needTotal;
	}

	List<Sort> sortList;

	List<Parameter> parameterList;

	public PageRequest() {
		super();
	}

	public PageRequest(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public void addSort(Sort s) {
		if (sortList == null) {
			sortList = new ArrayList<Sort>();
		}
		sortList.add(s);
	}

	public void addParameter(Parameter p) {
		if (parameterList == null) {
			parameterList = new ArrayList<Parameter>();
		}
		parameterList.add(p);
	}

	public void addParameter(Parameter p,int index) {
		if (parameterList == null) {
			parameterList = new ArrayList<Parameter>();
		}
		parameterList.add(index,p);
	}

	public String getSortString() {
		if (sortList == null || sortList.isEmpty()) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(" order by ");
			int size = sortList.size();
			for (int i = 0; i < size; i++) {
				sb.append(sortList.get(i).getSortString());
				if (i < (size - 1) && size > 1) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
	}

	public boolean hasParameter() {
		if (parameterList == null || parameterList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public String getParameterString() {
		StringBuffer sb = new StringBuffer();
		boolean hasPara = this.hasParameter();
		if(needWhere&&hasPara){
			sb.append(" where ");
		}
		if (hasPara) {
			int n = parameterList.size();
			for (int i = 0; i < n; i++) {
				Parameter pp = parameterList.get(i);
				if(n>1&&i>0){
					sb.append(" and ");
				}
				sb.append(pp.getParameterString(firstIndex));
				firstIndex+=pp.getParas();
			}
		}
		if (StringUtil.isNotEmpty(dataRule)) {
			if(needWhere&&hasPara){
				sb.append(" " + dataRule);
			}else{
				sb.append(" and " + dataRule);
			}
		}

		return sb.toString();
	}

	public Object[] getParameterValue() {
		return this.getParameterValueList().toArray();
	}

	public List getParameterValueList() {
		if (!hasParameter()) {
			return new ArrayList();
		} else {
			List<Object> os = new ArrayList<Object>();
			for (Parameter p : parameterList) {
				if (p.getValue() != Parameter.NULL_VALUE) {
					if(p.getValue() instanceof List||p.getValue() instanceof Set){
						//可能为IN,NOTIN这些绑定
						Collection cc = (Collection) p.getValue();
						for(Object oc : cc){
							os.add(oc);
						}
					}else{
						os.add(p.getValue());
					}
				}
			}
			return os;
		}
	}

	public void setNeedWhere(boolean needWhere) {
		this.needWhere = needWhere;
	}
}
