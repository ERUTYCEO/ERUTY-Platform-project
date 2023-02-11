package ERUTY.platform.configuration;

import ERUTY.platform.interceptor.LoginInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = new LoginInterceptor();


        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(loginInterceptor.loginEssential)
                .excludePathPatterns(loginInterceptor.loginInessential);

    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms =
                new ResourceBundleMessageSource();
        ms.setBasename("message.label");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
