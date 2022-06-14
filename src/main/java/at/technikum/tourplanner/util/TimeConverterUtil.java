package at.technikum.tourplanner.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverterUtil {

    private TimeConverterUtil() {
    }

    public static String convertToTimeString(Long totalSeconds) {
        int hours = (int) (totalSeconds / 3600);
        int minutes = (int) (totalSeconds % 3600) / 60;
        int seconds = (int) (totalSeconds % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static Long parseTime(String timeString) {
        LocalTime localTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        return (long) localTime.toSecondOfDay();
    }
}
