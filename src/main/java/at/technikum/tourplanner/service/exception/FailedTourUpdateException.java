package at.technikum.tourplanner.service.exception;

public class FailedTourUpdateException extends RuntimeException {
    public static final String MESSAGE = "Failed to update tour.";

    public FailedTourUpdateException() {
        super(MESSAGE);
    }
}
