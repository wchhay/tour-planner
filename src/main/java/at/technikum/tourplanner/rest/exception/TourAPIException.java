package at.technikum.tourplanner.rest.exception;


public class TourAPIException extends RuntimeException {

    public static final String MESSAGE = "Failed to fetch data from backend.";

    public TourAPIException() {
        super(MESSAGE);
    }
}
