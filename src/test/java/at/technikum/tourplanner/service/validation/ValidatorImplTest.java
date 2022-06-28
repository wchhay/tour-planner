package at.technikum.tourplanner.service.validation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorImplTest {

    private ValidatorImpl validator;

    @BeforeEach
    void setUp() {
        validator = new ValidatorImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00:00", "23:59:59", "10:23:45"})
    void test_isValidTimeString_returns_true(String timeString) {
        assertTrue(validator.isValidTimeString(timeString));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00:60", "24:59:59", "10:60:45"})
    void test_isValidTimeString_returns_false(String timeString) {
        assertFalse(validator.isValidTimeString(timeString));
    }

    @Test
    void GIVEN_date_in_the_past_WHEN_isValidDate_THEN_returns_true() {
        LocalDate localDate = LocalDate.now().minusDays(1);

        assertTrue(validator.isValidDate(localDate));
    }


    @Test
    void GIVEN_date_in_the_future_WHEN_isValidDate_THEN_returns_false() {
        LocalDate localDate = LocalDate.now().plusDays(1);

        assertFalse(validator.isValidDate(localDate));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void test_isIntegerInRange_returns_true(int integer) {
        assertTrue(validator.isIntegerInRange(integer, 1, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6, 7})
    void test_isIntegerInRange_returns_false(int integer) {
        assertFalse(validator.isIntegerInRange(integer, 1, 5));
    }
}