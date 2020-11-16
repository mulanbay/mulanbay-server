package cn.mulanbay.common.config;

/**
 * 数据类型
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public enum DataType {

	STRING("string"), DATE("date"), DATETIME("datetime"), NUMBER("number"), BOOLEAN("boolean");

	private final String type;

	private DataType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
