package ru.alexkvs.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class SpringWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppApplication.class, args);
    }

    /**
     * Можно явно создать бин,
     * а можно указать параметр в application.properties
     * spring.mvc.hiddenmethod.filter.enabled=true
     */
    /*@Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }*/

    /**
     * Пример регистрации собственного фильтра
     * @return
     */
    @Bean
    public FilterRegistrationBean<TestFilter> loggingFilter() {
        FilterRegistrationBean<TestFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TestFilter());
        registrationBean.addUrlPatterns("/customer/*");
        return registrationBean;
    }

    /**
     * Пример регистрации фильтра {@link CommonsRequestLoggingFilter} из состава Spring
     * для логирования всех запросов до и после обработки
     * @return
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
