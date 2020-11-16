package cn.mulanbay.web.bean.response;

/**
 * 接口返回的基类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ResultBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -817891526003929459L;

	private int code = 0;

	private String message = "操作成功";

	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


}
