package at.technikum.tourplanner.service.exception;

public class FailedTourCreationException extends RuntimeException {

    public static final String MESSAGE = "Failed to create tour.";

    public FailedTourCreationException() {
        super(MESSAGE);
    }
}
