package at.technikum.tourplanner.service.json;

public class JsonConverterException extends Exception {
    public static final String MESSAGE = "Processing JSON failed.";

    public JsonConverterException(Exception e) {
        super(MESSAGE, e);
    }
}
