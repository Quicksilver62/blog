package ru.yandex.practicum.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "ru.yandex.practicum")
@PropertySource("classpath:application.properties")
@Import({DbConfig.class, ThymeleafConfig.class})
public class AppConfig {
}
