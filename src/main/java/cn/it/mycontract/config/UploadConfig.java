package cn.it.mycontract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class UploadConfig {

    //配置文件上传解析器
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();

        long maxSize = 1024*1024*20;//就是20MB
        resolver.setMaxUploadSize(maxSize);//设置文件最大容量

        resolver.setDefaultEncoding("UTF-8");//设置编码
        return resolver;
    }
}
