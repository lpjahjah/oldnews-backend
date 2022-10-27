package com.oldnews.backend.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperUtil {
    public ObjectMapperUtil() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public ObjectMapper mapper = new ObjectMapper();

    /**
     * @param object Object to map
     * @param clazz  Class of the desired object
     * @param <T>    Type of the object to map
     * @param <K>    Type of the desired object to receive the mapping
     * @return Mapped object
     */
    public <T, K> K map(T object, Class<K> clazz) {
        return mapper.convertValue(object, clazz);
    }
}
