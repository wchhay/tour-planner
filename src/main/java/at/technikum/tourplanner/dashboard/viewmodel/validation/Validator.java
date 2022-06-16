package at.technikum.tourplanner.dashboard.viewmodel.validation;

import java.time.LocalDate;

public interface Validator {

    boolean isValidTimeString(String timeString);

    boolean isValidDate(LocalDate date);

    boolean isIntegerInRange(Integer value, int lowerBound, int upperBound);
}
