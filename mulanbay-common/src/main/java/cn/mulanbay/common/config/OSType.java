package cn.mulanbay.common.config;

/**
 * 操作系统类型
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public enum OSType {

	UNKNOWN(-1), LINUX(0), WINDOWS(1);

	private int value = 0;

	private OSType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
