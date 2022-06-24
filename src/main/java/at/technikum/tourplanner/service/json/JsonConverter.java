package at.technikum.tourplanner.service.json;

import com.fasterxml.jackson.core.type.TypeReference;

public interface JsonConverter {

    <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonConverterException;

    <T> T fromJson(String json, Class<T> type) throws JsonConverterException;

    String toJson(Object object) throws JsonConverterException;
}
