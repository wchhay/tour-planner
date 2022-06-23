package at.technikum.tourplanner.service.statistics;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class StatisticsServiceImpl implements StatisticsService {

    public static final int MAX_CHILD_FRIENDLY_DISTANCE = 10000;
    public static final double MAX_CHILD_FRIENDLY_DIFFICULTY = 3.0;
    public static final double POPULARITY_FIRST_LIMIT = 3;
    public static final double POPULARITY_SECOND_LIMIT = 6;

    @Override
    public void calculateAndSetChildFriendliness(Tour tour) {
        List<Log> logs = tour.getLogs();

        if (logs.isEmpty()) {
            tour.setChildFriendliness(0);
            return;
        }

        int timeFactor = calculateAverageTime(logs) < tour.getEstimatedTime() ? 1 : 0;
        int distanceFactor = MAX_CHILD_FRIENDLY_DISTANCE > tour.getDistance() ? 1 : 0;
        int difficultyFactor = MAX_CHILD_FRIENDLY_DIFFICULTY > calculateAverageDifficulty(logs) ? 1 : 0;

        tour.setChildFriendliness(timeFactor + distanceFactor + difficultyFactor);
    }

    @Override
    public void calculateAndSetPopularity(Tour tour) {
        int logCount = tour.getLogs().size();
        tour.setPopularity(calculatePopularity(logCount));
    }

    @Override
    public TourStats calculateTourStats(Tour tour) {
        return TourStats.builder()
                .averageTime(calculateAverageTime(tour.getLogs()))
                .averageDifficulty(calculateAverageDifficulty(tour.getLogs()))
                .averageRating(calculateAverageRating(tour.getLogs()))
                .build();
    }

    private double calculateAverageDifficulty(List<Log> logs) {
        return calculateIntegerAverage(logs.stream().map(Log::getDifficulty).toList());
    }

    private double calculateAverageTime(List<Log> logs) {
        return calculateLongAverage(logs.stream().map(Log::getTotalTime).toList());
    }

    private double calculateAverageRating(List<Log> logs) {
        return calculateIntegerAverage(logs.stream().map(Log::getRating).toList());
    }

    private int calculatePopularity(int logCount) {
        if (0 == logCount) {
            return 0;
        }
        if (POPULARITY_FIRST_LIMIT > logCount) {
            return 1;
        }
        if (POPULARITY_SECOND_LIMIT > logCount) {
            return 2;
        }
        return 3;
    }

    private double calculateIntegerAverage(List<Integer> integers) {
        return integers.isEmpty() ? 0.0 : (double) integers.stream().reduce(0, Integer::sum) / integers.size();
    }

    private double calculateLongAverage(List<Long> integers) {
        return integers.isEmpty() ? 0.0 : (double) integers.stream().reduce(0L, Long::sum) / integers.size();
    }

}
