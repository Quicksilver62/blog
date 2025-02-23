package ru.yandex.practicum.blog.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "ru.yandex.practicum.blog.db")
@EnableTransactionManagement
public class DbConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.jpa.properties.hibernate.dialect}")
  private String hibernateDialect;
  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String hibernateHbm2ddlAuto;
  @Value("${spring.jpa.show-sql}")
  private String hibernateShowSql;
  @Value("${spring.jpa.properties.hibernate.format_sql}")
  private String hibernateFormatSql;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  // Настройка EntityManagerFactory
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource()); // Указываем источник данных
    em.setPackagesToScan("ru.yandex.practicum.blog.db.model"); // Указываем пакет с сущностями

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", hibernateDialect);
    properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto); // Автоматическое создание/обновление таблиц
    properties.setProperty("hibernate.show_sql", hibernateShowSql); // Показывать SQL-запросы в логах
    properties.setProperty("hibernate.format_sql", hibernateFormatSql); // Форматировать SQL-запросы
    em.setJpaProperties(properties);

    return em;
  }

  // Настройка менеджера транзакций
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory); // Указываем EntityManagerFactory
    return transactionManager;
  }
}
