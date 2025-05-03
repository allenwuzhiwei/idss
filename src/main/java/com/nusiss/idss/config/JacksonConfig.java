package com.nusiss.idss.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    /*@Bean
    public Jackson2ObjectMapperBuilderCustomizer offsetDateTimeCustomizer() {
        return builder -> builder.serializerByType(OffsetDateTime.class,
                new JsonSerializer<OffsetDateTime>() {
                    @Override
                    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers)
                            throws IOException {
                        if (value != null) {
                            OffsetDateTime sgTime = value.withOffsetSameInstant(ZoneOffset.ofHours(8));
                            String formatted = sgTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            gen.writeString(formatted);
                        } else {
                            gen.writeNull();
                        }
                    }
                });
    }*/
}