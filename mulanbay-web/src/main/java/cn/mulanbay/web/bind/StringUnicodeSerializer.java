package cn.mulanbay.web.bind;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Unicode转换，用于json数据的序列号
 * 可以通过打在域上单个实现，也可以全局实现，参见 @see JsonUtil
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class StringUnicodeSerializer extends JsonSerializer<String> {

	private final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	private final int[] ESCAPE_CODES = CharTypes.get7BitOutputEscapes();

	private void writeUnicodeEscape(JsonGenerator gen, char c)
			throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw('u');
		gen.writeRaw(HEX_CHARS[(c >> 12) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 8) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 4) & 0xF]);
		gen.writeRaw(HEX_CHARS[c & 0xF]);
	}

	private void writeShortEscape(JsonGenerator gen, char c) throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw(c);
	}

	@Override
	public void serialize(String str, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		int status = ((JsonWriteContext) gen.getOutputContext()).writeValue();
		switch (status) {
		case JsonWriteContext.STATUS_OK_AFTER_COLON:
			gen.writeRaw(':');
			break;
		case JsonWriteContext.STATUS_OK_AFTER_COMMA:
			gen.writeRaw(',');
			break;
		case JsonWriteContext.STATUS_EXPECT_NAME:
			throw new JsonGenerationException("Can not write string value here");
		default:
		}
		gen.writeRaw('"');// 写入JSON中字符串的开头引号
		for (char c : str.toCharArray()) {
			if (c >= 0x80) {
				writeUnicodeEscape(gen, c); // 为所有非ASCII字符生成转义的unicode字符
			} else {
				// 为ASCII字符中前128个字符使用转义的unicode字符
				int code = (c < ESCAPE_CODES.length ? ESCAPE_CODES[c] : 0);
				if (code == 0) {
					gen.writeRaw(c); // 此处不用转义
				} else if (code < 0) {
					writeUnicodeEscape(gen, (char) (-code - 1)); // 通用转义字符
				} else {
					writeShortEscape(gen, (char) code); // 短转义字符 (\n \t ...)
				}
			}
		}
		gen.writeRaw('"');// 写入JSON中字符串的结束引号
	}

}
