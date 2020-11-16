package cn.mulanbay.pms.web.config;

import cn.mulanbay.persistent.cache.CacheProcessor;
import cn.mulanbay.persistent.cache.PageCacheManager;
import cn.mulanbay.persistent.service.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Hibernate配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class HibernateConfig {

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    /**
     * 列表数据数据是否缓存
     */
    @Value("${persistent.page.list.cache}")
    private boolean listCache;

    /**
     * 总页数是否缓存
     */
    @Value("${persistent.page.total.cache}")
    private boolean totalCache;

    /**
     * 需要缓存的bean
     */
    @Value("${persistent.page.cacheBeans}")
    private String cacheBeans;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("cn.mulanbay.pms.persistent.domain", "cn.mulanbay.schedule.domain");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.jdbc.fetch_size", "50");
        properties.setProperty("connection.useUnicode", "true");
        properties.setProperty("connection.characterEncoding", "utf8");
        //properties.setProperty("hibernate.hbm2ddl.auto","create");
        properties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }

    @Bean
    public BaseService baseService() {
        return new BaseService();
    }

    /**
     * 列表查询类统一缓存处理
     * @param cacheProcessor
     * @return
     */
    @Bean
    public PageCacheManager pageCacheManager(CacheProcessor cacheProcessor) {
        PageCacheManager pcm =  new PageCacheManager(cacheProcessor);
        pcm.setCacheBeans(cacheBeans);
        pcm.setListCache(listCache);
        pcm.setTotalCache(totalCache);
        return pcm;
    }
}
