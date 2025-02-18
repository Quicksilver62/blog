package ru.yandex.practicum.blog.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.driver}")
  String driver;

  @Value("${spring.datasource.url}")
  String dbUrl;

  @Value("${spring.datasource.user}")
  String dbUser;

  @Value("${spring.datasource.password}")
  String dbPassword;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(dbUrl);
    dataSource.setUsername(dbUser);
    dataSource.setPassword(dbPassword);
    return dataSource;
  }
}
