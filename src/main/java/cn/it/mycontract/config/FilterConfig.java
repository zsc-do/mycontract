package cn.it.mycontract.config;

import cn.it.mycontract.security.CsrfFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private CsrfFilter csrfFilter;

//    @Bean
//    public FilterRegistrationBean registerAuthFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(csrfFilter);
//        registration.addUrlPatterns("/*");
//        registration.setName("csrfFilter");
//        registration.setOrder(1);  //值越小，Filter越靠前。
//        return registration;
//    }

    //如果有多个Filter，再写一个public FilterRegistrationBean registerOtherFilter(){...}即可。
}
