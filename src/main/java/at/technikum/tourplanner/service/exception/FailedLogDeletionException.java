package at.technikum.tourplanner.service.exception;

public class FailedLogDeletionException extends RuntimeException {

    public static final String MESSAGE = "Failed to delete log.";

    public FailedLogDeletionException() {
        super(MESSAGE);
    }
}
