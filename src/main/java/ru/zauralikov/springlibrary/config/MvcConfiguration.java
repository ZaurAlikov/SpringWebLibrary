package ru.zauralikov.springlibrary.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.faces.mvc.JsfView;
import org.springframework.faces.webflow.JsfFlowHandlerAdapter;
import org.springframework.faces.webflow.JsfResourceRequestHandler;
import org.springframework.web.context.support.ServletContextAttributeExporter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import ru.zauralikov.springlibrary.objects.LibraryFacade;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@EnableWebMvc
@Import(WebFlowConfig.class)
public class MvcConfiguration extends WebMvcConfigurerAdapter {

     private static final Logger log = LoggerFactory.getLogger(MvcConfiguration.class);

     private LibraryFacade libraryFacade;
     private WebFlowConfig webFlowConfig;

    @Bean
    public ViewResolver viewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(JsfView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".xhtml");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(){
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setFlowRegistry(webFlowConfig.flowRegistry());
        handlerMapping.setOrder(2);
        return handlerMapping;
    }

    @Bean
    public JsfResourceRequestHandler jsfResourceRequestHandler() {
        return new JsfResourceRequestHandler();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/javax.faces.resource/**", jsfResourceRequestHandler());

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setUrlMap(urlMap);
        handlerMapping.setOrder(1);
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
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("ru"));
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.error("addInterceptors");
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ServletContextAttributeExporter servletContextAttributeExporter(){
        ServletContextAttributeExporter attributeExporter = new ServletContextAttributeExporter();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("libraryFacade", libraryFacade);
        attributeExporter.setAttributes(attributes);
        return attributeExporter;
    }

    @Autowired
    public void libraryFacade (LibraryFacade libraryFacade){
        this.libraryFacade = libraryFacade;
    }

    @Autowired
    public void getWebFlowConfig(WebFlowConfig webFlowConfig){
        this.webFlowConfig = webFlowConfig;
    }
}
