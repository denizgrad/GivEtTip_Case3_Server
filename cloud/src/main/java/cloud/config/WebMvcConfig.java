package cloud.config;

import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({"cloud"})
@EnableTransactionManagement
@EnableJpaRepositories("cloud.repository")
@PropertySource("classpath:${app.properties}")
public class WebMvcConfig {
	@Autowired
	Environment env;
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(100000);
	    return new CommonsMultipartResolver();
	}
	@Bean
	public Filter loggingFilter(){
	    AbstractRequestLoggingFilter f = new AbstractRequestLoggingFilter() {

	        @Override
	        protected void beforeRequest(HttpServletRequest request, String message) {
	            logger.info(message);
	        }

	        @Override
	        protected void afterRequest(HttpServletRequest request, String message) {
	            logger.info(message);
	        }
	    };
	    f.setIncludeClientInfo(true);
	    f.setIncludePayload(true);
	    f.setIncludeQueryString(true);

	    f.setBeforeMessagePrefix("BEFORE REQUEST  [");
	    f.setAfterMessagePrefix("AFTER REQUEST    [");
	    f.setAfterMessageSuffix("]\n");
	    return f;
	}
	
//    @Bean(name = "messageSource")
//    public ResourceBundleMessageSource getMessageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        String env = "dev";
//        if(StringUtils.isNotBlank(System.getProperty("env"))){
//        	env = System.getProperty("env");
//        }
//        messageSource.setBasename( env + "/app_messages.properties");//TODO deniz
//        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setUseCodeAsDefaultMessage(true);
//        return messageSource;
//    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));

        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan("cloud.model");
        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect"  , env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql" , env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto" , env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.connection.CharSet" , env.getRequiredProperty("hibernate.connection.CharSet"));
        properties.put("hibernate.connection.characterEncoding" , env.getRequiredProperty("hibernate.connection.characterEncoding"));
        properties.put("hibernate.connection.useUnicode" , env.getRequiredProperty("hibernate.connection.useUnicode"));
        //c3p0
        properties.put("hibernate.c3p0.acquire_increment" , env.getRequiredProperty("hibernate.c3p0.acquire_increment"));
        properties.put("hibernate.c3p0.idle_test_period" , env.getRequiredProperty("hibernate.c3p0.idle_test_period"));
        properties.put("hibernate.c3p0.max_size" , env.getRequiredProperty("hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.max_statements" , env.getRequiredProperty("hibernate.c3p0.max_statements"));
        properties.put("hibernate.c3p0.min_size" , env.getRequiredProperty("hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.timeout" , env.getRequiredProperty("hibernate.c3p0.timeout"));
        properties.put("hibernate.c3p0.validate" , env.getRequiredProperty("hibernate.c3p0.validate"));
        properties.put("hibernate.c3p0.preferredTestQuery" , env.getRequiredProperty("hibernate.c3p0.preferredTestQuery"));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
