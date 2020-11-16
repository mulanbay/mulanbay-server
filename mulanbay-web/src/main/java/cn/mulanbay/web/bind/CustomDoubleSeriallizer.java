package cn.mulanbay.web.bind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * json返回时使用（jackson）
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CustomDoubleSeriallizer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		// 保留两位小数
		DecimalFormat df = new DecimalFormat("#0.00");
		jgen.writeString(df.format(value));
	}

}
