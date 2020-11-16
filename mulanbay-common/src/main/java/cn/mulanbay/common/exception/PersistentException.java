package cn.mulanbay.common.exception;

/**
 * 持久层异常
 * 由持久层（Hibernate操作引起抛出）
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class PersistentException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 6716099160862703312L;

	protected Throwable myException;

	protected int errorCode;

	protected String message = "";

	public PersistentException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public PersistentException(int errorCode, String message,
			Throwable myException) {
		super();
		this.myException = myException;
		this.errorCode = errorCode;
		this.message = message;
	}

	public PersistentException(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	public PersistentException(String message, Throwable myException) {
		super();
		this.myException = myException;
		this.message = message;
	}

	public PersistentException(String message) {
		super();
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
    public String getMessage() {
		if (myException != null) {
			return message + myException.getMessage();
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getMyException() {
		return myException;
	}

	public void setMyException(Throwable myException) {
		this.myException = myException;
	}

}
