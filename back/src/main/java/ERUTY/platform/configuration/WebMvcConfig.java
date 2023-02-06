package ERUTY.platform.configuration;

import ERUTY.platform.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = new LoginInterceptor();


//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns(loginInterceptor.loginEssential)
//                .excludePathPatterns(loginInterceptor.loginInessential);

    }
}
