package ru.sfu.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Data Configuration class
 * @author Agapchenko V.V.
 */
@Configuration
@ComponentScan("ru.sfu")
@EnableJpaRepositories("ru.sfu.repository")
@EnableTransactionManagement
@PropertySource("classpath:data.properties")
public class DataConfig {

    private final Environment env;

    /**
     * Constructor
     * @param env Environment
     */
    @Autowired
    private DataConfig(Environment env) {
        this.env = env;
    }

    /**
     * Initialize Data Source
     * @return DataSource Object
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setDriverClassName(
                Objects.requireNonNull(
                        env.getProperty("dataSource.driverClassName")
                )
        );
        dataSource.setUrl(
                env.getProperty("dataSource.url")
        );
        dataSource.setUsername(
                env.getProperty("dataSource.username")
        );
        dataSource.setPassword(
                env.getProperty("dataSource.password")
        );

        return dataSource;
    }

    /**
     * Initialize Manager Factory
     * @return Manager Factory Object
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.sfu.entity");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /**
     * Initialize Transaction Manager
     * @return Transaction Manager Object
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory());
        return manager;
    }
}
