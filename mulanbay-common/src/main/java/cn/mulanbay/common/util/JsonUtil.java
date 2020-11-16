package cn.mulanbay.common.util;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON操作工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper objectMapper;

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取ObjectMapper实例
	 *
	 * @param createNew
	 *            方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			objectMapper = createObjectMapper();
		} else if (objectMapper == null) {
			objectMapper = createObjectMapper();
		}
		return objectMapper;
	}

	public static ObjectMapper createObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		// 当找不到对应的序列化器时 忽略此字段
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		// 使Jackson JSON支持Unicode编码非ASCII字符
//		SimpleModule module = new SimpleModule();
//		module.addSerializer(String.class, new StringUnicodeSerializer());
//		om.registerModule(module);
		// 设置null值不参与序列化(字段不被显示)
		//om.setSerializationInclusion(Include.NON_NULL);
		om.setDateFormat(simpleDateFormat);
		return om;
	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param obj
	 *            准备转换的对象
	 * @return json字符串
	 */
	public static String beanToJson(Object o) {
		try {
			if (o == null) {
				return null;
			}
			ObjectMapper objectMapper = getMapperInstance(false);
			String json = objectMapper.writeValueAsString(o);
			return json;
		} catch (Exception e) {
			logger.error("对象[" + o + "]转换为Json异常：", e);
		}
		return null;
	}

	/**
	 * 将json字符串转换成java对象
	 *
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBean(String json, Class<?> cls) {
		try {
			if (json == null || "".equals(json)) {
				return null;
			}
			ObjectMapper objectMapper = getMapperInstance(false);
			Object vo = objectMapper.readValue(json, cls);
			return vo;
		} catch (Exception e) {
			logger.error("json[" + json + "]转换为Bean[" + cls + "]异常：", e);
			throw new ApplicationException(ErrorCode.JSON_PARSE_ERROR,"json[" + json + "]转换为Bean[" + cls + "]异常",e);
		}
	}

	/**
	 * 将json字符串转换成java对象
	 *
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List jsonToBeanList(String json, Class<?> cls) {

		try {
			if (json == null || "".equals(json)) {
				return null;
			}
			JavaType javaType = getCollectionType(ArrayList.class, cls);
			ObjectMapper objectMapper = getMapperInstance(false);
			List list = (List) objectMapper.readValue(json, javaType);
			return list;
		} catch (Exception e) {
			logger.error("json[" + json + "]转换为Bean[" + cls + "]异常：", e);
		}
		return null;
	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

}
