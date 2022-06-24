package at.technikum.tourplanner.service.report;

import java.io.IOException;

public class FailedPdfGenerationException extends RuntimeException {
    public static final String MESSAGE = "Failed to generate PDF.";

    public FailedPdfGenerationException(IOException e) {
        super(MESSAGE, e);
    }
}
