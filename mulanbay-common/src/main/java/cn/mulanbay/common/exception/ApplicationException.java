package cn.mulanbay.common.exception;

import java.io.PrintStream;

/**
 * 系统自定义的运行期异常
 * 统一定义了错误代码
 * 最外层处理参考:
 * @see cn.mulanbay.web.common.ApiExceptionHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected Throwable myException;

	protected int errorCode;

	protected String message = "";


	/**
	 * 默认的构造方法
	 *
	 */
	public ApplicationException() {
		// ...
	}

	/**
	 * 带一个参数String参数的构造方法
	 *
	 * @param errorCode
	 */
	public ApplicationException(int errorCode) {
		this.errorCode=errorCode;
	}

	/**
	 * 带一个参数String参数的构造方法
	 *
	 * @param errorCode
	 */
	public ApplicationException(int errorCode, Throwable cause) {
		this.errorCode=errorCode;
		this.myException = cause;
	}

	/**
	 * 带一个String和一个Throwable参数的构造方法
	 *
	 * @param msg
	 * @param cause
	 */
	public ApplicationException(String msg, Throwable cause) {
		super(msg, cause);
		this.myException = cause;
	}

	/**
	 * 带一个Throwable的构造方法
	 *
	 * @param cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public ApplicationException( int errorCode,
			String message,Throwable myException) {
		super(message);
		this.myException = myException;
		this.errorCode = errorCode;
		this.message = message;
	}

	public Throwable getMyException() {
		return myException;
	}

	public void setMyException(Throwable myException) {
		this.myException = myException;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageDetail() {
		String errorInfo = "";

		if(getErrorCode() != 0){
			errorInfo += "错误代码：" + getErrorCode() + "<br>\n";
		}

		if (myException != null) {
			errorInfo += "错误描述：" + getMessage() + "<br>\n详细信息："+myException.getMessage();
		} else {
			errorInfo += "错误描述：" + getMessage();
		}
		return errorInfo;
	}

	@Override
	public void printStackTrace() {
		if (myException != null){
			myException.printStackTrace();
		}
	}
}
