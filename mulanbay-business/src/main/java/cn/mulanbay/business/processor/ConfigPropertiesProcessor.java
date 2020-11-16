package cn.mulanbay.business.processor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 把配置文件里面的数据加载到内存中
 * 配置文件处理类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ConfigPropertiesProcessor extends PropertyPlaceholderConfigurer {

    private Map<String, String> ctxPropertiesMap;

    @Override
    protected void processProperties( ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public String getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public String getOrDefaultProperty(String name,String defaultValue) {
        return ctxPropertiesMap.getOrDefault(name,defaultValue);
    }


    public void putProperty(String name,String value) {
        ctxPropertiesMap.put(name, value);
    }

    /**
     * 获取配置
     * @return
     */
    public Map<String, String> getConfigMap(){
        return ctxPropertiesMap;
    }
}
