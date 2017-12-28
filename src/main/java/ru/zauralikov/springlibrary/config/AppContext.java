package ru.zauralikov.springlibrary.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ru.zauralikov.springlibrary")
@EnableTransactionManagement
public class AppContext {

    private static final Logger log = LoggerFactory.getLogger(AppContext.class);

    @Bean
    public DataSource dataSource() throws NamingException {
        JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
        log.debug("dataSource: " + dataSource);
        dataSource.setJndiName("jdbc/Library");
        return (DataSource) dataSource.getObject();
    }

    @Bean
    public SessionFactory sessionFactory() throws NamingException {
        return new LocalSessionFactoryBuilder(dataSource())
            .scanPackages("ru.zauralikov.springlibrary.entities")
            .addProperties(hibernateProperties())
            .buildSessionFactory();
    }

    @Bean
    Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.connection.datasource", "jdbc/Library");
//        properties.setProperty("hibernate.connection.username", "root");
//        properties.setProperty("hibernate.connection.password", "root");
//        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/springlibrary");
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws NamingException {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        log.debug("transactionManager: " + txManager);
        txManager.setSessionFactory(sessionFactory());
        return txManager;
    }

}
