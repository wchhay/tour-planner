package at.technikum.tourplanner.service.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;

    private ObjectMapperFactory() {
    }

    public static ObjectMapper create() {
        if (null == objectMapper) {
            objectMapper = JsonMapper.builder().findAndAddModules().build();
        }
        return objectMapper;
    }
}
