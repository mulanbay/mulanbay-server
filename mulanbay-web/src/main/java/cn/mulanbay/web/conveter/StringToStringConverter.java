package cn.mulanbay.web.conveter;

import org.springframework.core.convert.converter.Converter;

/**
 * 解决界面传入空字符串的问题（备用）
 *
 * @author fenghong
 *
 */
public class StringToStringConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if (source == null || "".equals(source)) {
			return null;
		} else {
			return source;
		}
	}

}
