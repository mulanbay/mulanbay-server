package cn.mulanbay.common.http.ssl;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

/**
 * https连接操作
 *
 * 参考网站： @link https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
 * @author fenghong
 * @create 2017-11-02 21:31
 **/
public class ClientCustomSSL {

    private static final Logger logger = LoggerFactory.getLogger(ClientCustomSSL.class);

    /**
     *
     * @param type 类型，比如：PKCS12
     * @param password 证书密码
     * @param caFilePath 证书路径，全路径
     * @return
     */
    public static CloseableHttpClient createCustomSSLClient(String type,String password,String caFilePath,String supportedProtocol) {
        FileInputStream instream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(type);
            instream = new FileInputStream(new File(caFilePath));
            keyStore.load(instream, password.toCharArray());

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore,password.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{supportedProtocol},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            return httpclient;
        } catch (Exception e) {
            logger.error("创建包含证书的client异常",e);
            throw new ApplicationException(ErrorCode.CREATE_CUSTOM_SSL_ERROR);
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    logger.error("关闭流异常",e);
                }
            }
        }
    }
}
