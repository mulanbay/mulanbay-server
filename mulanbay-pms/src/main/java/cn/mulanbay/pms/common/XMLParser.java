package cn.mulanbay.pms.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * XML处理
 * Copied from wxpay-scanpay-java-sdk-1.0
 *
 * @author rizenguo
 * @create 2014-11-01 21:44
 * @see https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=11_1
 */
public class XMLParser {

    private static final Logger logger = LoggerFactory.getLogger(XMLParser.class);

    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !"".equals(sInputString.trim())) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    public static Map<String, Object> getMapFromXML(String xmlString) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        /**
         * 禁止外部访问，修复XXE漏洞
         * https://mp.weixin.qq.com/s?__biz=MzI4Njc5NjM1NQ==&mid=2247485701&idx=1&sn=b1fc4f37dc692cec3063aff3d8777600&chksm=ebd63629dca1bf3f7ef774251786a0ed35579f9a80daeac4f45533e03ab6c238a93ffeb65712&scene=0#rd
         */
        documentBuilderFactory.setExpandEntityReferences(false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        InputStream stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        } catch (Exception ex) {
            logger.error("getMapFromXML error", ex);
        }
        return data;
    }

    /**
     * XML转换为Bean
     *
     * @param xmlString
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getBeanFromXml(String xmlString, Class t) throws Exception {
        JAXBContext context = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T bean = (T) unmarshaller.unmarshal(new StringReader(xmlString));
        return bean;
    }

    /**
     * 转换对象为xml对象，该方法对于Map类型无法达到微信的请求参数格式要求，需要先封装自己的实体类才可以
     *
     * @param xmlObj
     * @return
     */
    public static String parseBeanToXml(Object xmlObj) {
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String dataXML = xStreamForRequestPostData.toXML(xmlObj);
        return dataXML;
    }

    /**
     * 转换对象为xml对象
     *
     * @param xmlObj
     * @return
     */

    public static String parseMapToString(Map<String, Object> xmlObj) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = xmlObj.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (v == null) {
                //控制的不加入数据
                continue;
            }
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString().trim();
    }


}
