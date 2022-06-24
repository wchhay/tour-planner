package at.technikum.tourplanner.service.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonConverterImpl implements JsonConverter {

    private final ObjectMapper objectMapper;

    public JsonConverterImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonConverterException {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonConverterException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) throws JsonConverterException {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonConverterException(e);
        }
    }

    @Override
    public String toJson(Object object) throws JsonConverterException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConverterException(e);
        }
    }
}
