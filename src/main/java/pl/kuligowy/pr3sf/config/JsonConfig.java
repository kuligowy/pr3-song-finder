package pl.kuligowy.pr3sf.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Created by mtkl on 2017-06-30.
 */
public class JsonConfig {

//    @Bean
//    @Primary
//    public ObjectMapper mapper(){
//        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
//                .featuresToDisable(MapperFeature.DEFAULT_VIEW_INCLUSION,
//                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .f.build();
//        mapper.setConfig(mapper.getSerializationConfig().with(PropertyNamingStrategy.LOWER_CASE));
//        return mapper;
//    }

}
