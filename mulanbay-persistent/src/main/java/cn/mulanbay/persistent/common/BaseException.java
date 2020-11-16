package cn.mulanbay.persistent.common;

import java.io.PrintStream;

/**
 * 基础异常类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private Exception exception;

	protected int errorCode;

	protected boolean fatal;


	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Exception e) {
		this(e, e.getMessage());
	}

	public BaseException(Exception exception, int errorCode) {
		super();
		this.exception = exception;
		this.errorCode = errorCode;
	}

	public BaseException(Exception e, String message) {
		super(message);
		setException(e);
	}

	public BaseException(Exception e, String message, boolean fatal) {
		this(e, message);
		setFatal(fatal);
	}

	public BaseException(String string, Exception e) {
		// TODO Auto-generated constructor stub
	}

	public boolean isFatal() {
		return fatal;
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		if (getException() != null){
			getException().printStackTrace();
		}
	}

	@Override
	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		if (getException() != null){
			getException().printStackTrace(printStream);
		}
	}

	@Override
	public String toString() {
		if (getException() != null){
			return super.toString() + " wraps: [" + getException().toString() + "]";
		}
		else{
			return super.toString();
		}
	}

    /**
     * @param exception the exception to set
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }
}
