package at.technikum.tourplanner.service.exception;

public class FailedTourDeletionException extends RuntimeException {

    public static final String MESSAGE = "Failed to delete tour.";

    public FailedTourDeletionException() {
        super(MESSAGE);
    }
}
