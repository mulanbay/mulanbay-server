package cn.mulanbay.schedule;

/**
 * 调度触发器的参数检查结果
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ParaCheckResult {

	private int errorCode;
	
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
