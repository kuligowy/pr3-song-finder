package pl.kuligowy.pr3sf.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        System.out.println("MVCONFIG");
//        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = jacksonMessageConverter.getObjectMapper();
//
//        //objectMapper.registerModule(new JodaModule());
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        SerializationConfig cfg = objectMapper.getSerializationConfig();
//        DeserializationConfig dcft = objectMapper.getDeserializationConfig();
//
//
//        objectMapper.setConfig(cfg.with(PropertyNamingStrategy.LOWER_CASE));
//        objectMapper.setConfig(dcft.with(PropertyNamingStrategy.UPPER_CAMEL_CASE));
//        jacksonMessageConverter.setObjectMapper(objectMapper);
//        converters.add(jacksonMessageConverter);
//    }
    /*
 * THYME LEAF CONFIG
 */
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        //templateResolver.setPrefix("/WEB-INF/templates/");
//        templateResolver.setSuffix(".html");
//        //templateResolver.setTemplateMode(TemplateMode.HTML);
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
////        templateEngine.addDialect(new SpringStandardDialect());
//        return templateEngine;
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setOrder(1);
//        viewResolver.setCharacterEncoding("utf-8");
//        return viewResolver;
//    }

}
