package ru.sfu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Web Configuration class
 * @author Agapchenko V.V.
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.sfu.controller")
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    /**
     * Application Context dependency injection
     * @param applicationContext ApplicationContext
     */
    @Autowired
    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Configure resource locations
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
    }

    /**
     * Configure Template Resolver
     * @return TemplateResolver
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("HTML");
        return templateResolver;
    }

    /**
     * Configure Template Engine
     * @return TemplateEngine
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * Configure View Resolver
     * @param registry ViewResolverRegistry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    /**
     * Configure Content Negotiation
     * @param configurer ContentNegotiationConfigurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(true)
                .ignoreAcceptHeader(false)
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaTypes(
                        new HashMap<>() {{
                            put("json", MediaType.APPLICATION_JSON);
                            put("html", MediaType.TEXT_HTML);
                            put("form", MediaType.APPLICATION_FORM_URLENCODED);
                        }}
                );
    }

    /**
     * Configure Content Negotiating View Resolver
     * @param cnManager ContentNegotiationManager
     * @param templateEngine TemplateEngine
     * @return ViewResolver
     */
    @Bean
    @Autowired
    public ContentNegotiatingViewResolver viewResolver(
            ContentNegotiationManager cnManager,
            SpringTemplateEngine templateEngine
    ) {
        ContentNegotiatingViewResolver cnResolver = new ContentNegotiatingViewResolver();
        cnResolver.setContentNegotiationManager(cnManager);

        ThymeleafViewResolver htmlResolver = new ThymeleafViewResolver();
        htmlResolver.setTemplateEngine(templateEngine);

        List<ViewResolver> resolvers = new ArrayList<>() {{
            add(new ThymeleafViewResolver() {{
                setTemplateEngine(templateEngine);
            }});
        }};

        cnResolver.setViewResolvers(resolvers);
        return cnResolver;
    }
}
