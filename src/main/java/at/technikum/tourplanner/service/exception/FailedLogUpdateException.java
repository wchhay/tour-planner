package at.technikum.tourplanner.service.exception;

public class FailedLogUpdateException extends RuntimeException {
    public static final String MESSAGE = "Failed to update log.";

    public FailedLogUpdateException() {
        super(MESSAGE);
    }
}
