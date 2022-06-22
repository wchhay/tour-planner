package at.technikum.tourplanner.service.json;

import com.fasterxml.jackson.core.type.TypeReference;

public interface JsonConverter {

    <T> T fromJson(String json, TypeReference<T> typeReference);

    <T> T fromJson(String json, Class<T> type);

    String toJson(Object object);
}
