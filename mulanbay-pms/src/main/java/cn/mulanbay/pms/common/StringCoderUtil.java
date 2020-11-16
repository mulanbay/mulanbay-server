package cn.mulanbay.pms.common;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;

/**
 * 字符的解码
 * 主要针对调度触发器的参数处理
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class StringCoderUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringCoderUtil.class);

    /**
     * 解码
     *
     * @param s
     * @return
     */
    public static String decodeJson(String s) {
        try {
            if (StringUtil.isEmpty(s)) {
                return null;
            } else {
                return URLDecoder.decode(s, "utf-8");
            }
        } catch (Exception e) {
            logger.error("解码异常", e);
            throw new ApplicationException(PmsErrorCode.URL_DECODE_ERROR, e);
        }
    }

}
