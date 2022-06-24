package at.technikum.tourplanner.service.validation;

import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;

public class ValidatorImpl implements Validator {

    public static final String TIME_REGEX = "^(([0-1]\\d)|(2[0-3])):[0-5]\\d:[0-5]\\d$";

    @Override
    public boolean isValidTimeString(String timeString) {
        return Strings.isNotEmpty(timeString) && timeString.matches(TIME_REGEX);
    }

    @Override
    public boolean isValidDate(LocalDate date) {
        return (null != date) && (LocalDate.now().compareTo(date) >= 0);
    }

    @Override
    public boolean isIntegerInRange(Integer value, int lowerBound, int upperBound) {
        return (null != value) && (value >= lowerBound) && (value <= upperBound);
    }
}
