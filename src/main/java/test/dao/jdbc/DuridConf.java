package test.dao.jdbc;

//import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DuridConf {
    @Autowired
    private Environment env;

    /*@Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }*/

    @Bean
    public DataSource getDataSource() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("url", env.getProperty("spring.datasource.url"));
        properties.setProperty("username", env.getProperty("spring.datasource.username"));
        properties.setProperty("password", env.getProperty("spring.datasource.password"));
        return BasicDataSourceFactory.createDataSource(properties);
    }
}
