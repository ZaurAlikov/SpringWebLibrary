package ru.zauralikov.springlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.faces.mvc.JsfView;
import org.springframework.faces.webflow.JsfFlowHandlerAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages="ru.zauralikov.springlibrary")
@EnableWebMvc
@Import(WebFlowConfig.class)
public class MvcConfiguration extends WebMvcConfigurerAdapter {

     private WebFlowConfig webFlowConfig;

    @Bean
    public ViewResolver viewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(JsfView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".xhtml");
        return resolver;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(){
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setFlowRegistry(webFlowConfig.flowRegistry());
        return handlerMapping;
    }

    @Bean
    public JsfFlowHandlerAdapter jsfFlowHandlerAdapter(){
        JsfFlowHandlerAdapter handlerAdapter = new JsfFlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(webFlowConfig.flowExecutor());
        return handlerAdapter;
    }

    @Bean
    public MessageSource msg() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/locales/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("ru"));
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Autowired
    public void getWebFlowConfig(WebFlowConfig webFlowConfig){
        this.webFlowConfig = webFlowConfig;
    }
}
