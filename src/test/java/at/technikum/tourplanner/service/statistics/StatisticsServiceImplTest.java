package at.technikum.tourplanner.service.statistics;


import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StatisticsServiceImplTest {

    public static final double ERROR_OFFSET = 0.001;
    private StatisticsServiceImpl statisticsService;

    private Tour tour;

    @BeforeEach
    void setUp() {
        tour = buildTour();
        statisticsService = new StatisticsServiceImpl();
    }

    @Test
    void GIVEN_tour_WHEN_calculateAndSetChildFriendliness_THEN_correct_value() {
        statisticsService.calculateAndSetChildFriendliness(tour);

        assertThat(tour.getChildFriendliness()).isEqualTo(1);
    }

    @Test
    void GIVEN_tour_WHEN_calculateAndSetPopularity_THEN_correct_value() {
        statisticsService.calculateAndSetPopularity(tour);

        assertThat(tour.getPopularity()).isEqualTo(2);
    }

    @Test
    void GIVEN_tour_WHEN_calculateTourStats_THEN_correct_values() {
        TourStats stats = statisticsService.calculateTourStats(tour);

        assertThat(stats.getAverageTime()).isCloseTo(1000.333, Offset.offset(ERROR_OFFSET));
        assertThat(stats.getAverageDifficulty()).isCloseTo(2.333, Offset.offset(ERROR_OFFSET));
        assertThat(stats.getAverageRating()).isCloseTo(3.666, Offset.offset(ERROR_OFFSET));
    }

    private Tour buildTour() {
        return Tour.builder()
                .estimatedTime(1000L)
                .distance(10.0)
                .logs(List.of(
                    buildLog(1000L, 2, 3),
                    buildLog(501L, 3, 4),
                    buildLog(1500L, 2, 4)
                ))
                .build();
    }

    private Log buildLog(long totalTime, int difficulty, int rating) {
        return Log.builder()
                .totalTime(totalTime)
                .difficulty(difficulty)
                .rating(rating)
                .build();
    }
}