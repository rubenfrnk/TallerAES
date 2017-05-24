package com.aes.modelado.pagos.util;

import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.MediaTypes;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonJdk8 {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonJdk8.class);
    private static final ObjectMapper mapper;

    public JacksonJdk8() {
    }

    public static ObjectMapper mapper() {
        return mapper;
    }

    public static String json(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T value(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> Unmarshaller<HttpEntity, T> unmarshaller(Class<T> expectedType) {
        return Unmarshaller.forMediaType(MediaTypes.APPLICATION_JSON, Unmarshaller.entityToString()).thenApply((s) -> {
            try {
                return value(s, expectedType);
            } catch (IOException var4) {
                var4.printStackTrace();
                LOG.error("There was an error unmarshaling the JSON", var4);
                throw new IllegalArgumentException("Invalid request body", var4);
            }
        });
    }

    public static <T> Unmarshaller<String, T> parameterUnmarshaller(Class<T> expectedType) {
        return Unmarshaller.sync((json) -> {
            try {
                return value(json, expectedType);
            } catch (IOException var4) {
                var4.printStackTrace();
                LOG.error("There was an error unmarshaling the JSON", var4);
                throw new IllegalArgumentException("Invalid request body", var4);
            }
        });
    }

    static {
        mapper = (new ObjectMapper()).registerModules(new Module[]{new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule(Mode.PROPERTIES)}).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}