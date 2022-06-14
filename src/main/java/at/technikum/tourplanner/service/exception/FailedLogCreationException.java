package at.technikum.tourplanner.service.exception;

public class FailedLogCreationException extends RuntimeException {
    public static final String MESSAGE = "Failed to create log.";

    public FailedLogCreationException() {
        super(MESSAGE);
    }
}
