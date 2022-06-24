package at.technikum.tourplanner.service.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonConverterException extends Exception {
    public static final String MESSAGE = "Processing JSON failed.";

    public JsonConverterException(JsonProcessingException e) {
        super(MESSAGE, e);
    }
}
