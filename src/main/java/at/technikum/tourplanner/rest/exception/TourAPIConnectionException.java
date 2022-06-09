package at.technikum.tourplanner.rest.exception;


public class TourAPIConnectionException extends RuntimeException {

    public static final String MESSAGE = "Failed to connect to backend.";

    public TourAPIConnectionException() {
        super(MESSAGE);
    }
}
