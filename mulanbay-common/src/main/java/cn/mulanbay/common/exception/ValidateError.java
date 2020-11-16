package cn.mulanbay.common.exception;

/**
 * 表单参数验证异常解析后的封装类
 * 统一为错误代码定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ValidateError implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812069140817373386L;

	private int code; 
	
	private String field;
	
	private String errorInfo;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
}
