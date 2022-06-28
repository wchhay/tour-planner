package at.technikum.tourplanner.util;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TimeConverterUtilTest {

    private static Stream<Arguments> provideTimeValues() {
        return Stream.of(
            Arguments.of(3625L, "01:00:25"),
            Arguments.of(10000L, "02:46:40"),
            Arguments.of(7200L, "02:00:00")
        );
    }

    public static Stream<Arguments> provideTimeStrings() {
        return Stream.of(
            Arguments.of("01:00:25", 3625L),
            Arguments.of("02:46:40", 10000L),
            Arguments.of("02:00:00", 7200L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTimeValues")
    void test_correct_conversion_to_timeString(long timeInSeconds, String expectedTimeString) {
        String actualTimeString = TimeConverterUtil.convertToTimeString(timeInSeconds);

        assertThat(actualTimeString).isEqualTo(expectedTimeString);
    }

    @ParameterizedTest
    @MethodSource("provideTimeStrings")
    void test_correct_parsing_of_timeString(String timeString, long expectedTimeInSeconds) {
        long actualTimeInSeconds = TimeConverterUtil.parseTime(timeString);

        assertThat(actualTimeInSeconds).isEqualTo(expectedTimeInSeconds);
    }
}