package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class StatisticsServiceImpl implements StatisticsService {

    public static final int MAX_CHILD_FRIENDLY_DISTANCE = 10000;
    public static final double MAX_CHILD_FRIENDLY_DIFFICULTY = 3.0;
    public static final double POPULARITY_LOWER_BOUND_FACTOR = 0.5;
    public static final double POPULARITY_UPPER_BOUND_FACTOR = 1.5;

    private final TourDataStoreService tourDataStoreService;

    public StatisticsServiceImpl(TourDataStoreService tourDataStoreService) {
        this.tourDataStoreService = tourDataStoreService;
    }

    @Override
    public void calculateAndSetComputedAttributes() {
        List<Tour> tours = tourDataStoreService.getTourObservableList();
        calculateAndSetPopularity(tours);
        tours.forEach(this::calculateAndSetChildFriendliness);
    }

    private void calculateAndSetChildFriendliness(Tour tour) {
        List<Log> logs = tour.getLogs();

        if (logs.isEmpty()) {
            return;
        }

        int timeFactor = calculateAverageTime(logs) < tour.getEstimatedTime() ? 1 : 0;
        int distanceFactor = MAX_CHILD_FRIENDLY_DISTANCE > tour.getDistance() ? 1 : 0;
        int difficultyFactor = MAX_CHILD_FRIENDLY_DIFFICULTY > calculateAverageDifficulty(logs) ? 1 : 0;

        int childFriendliness = timeFactor + distanceFactor + difficultyFactor;
        tour.setChildFriendliness(childFriendliness);
    }

    @Override
    public double calculateAverageDifficulty(List<Log> logs) {
        return calculateIntegerAverage(logs.stream().map(Log::getDifficulty).toList());
    }

    @Override
    public double calculateAverageTime(List<Log> logs) {
        return calculateLongAverage(logs.stream().map(Log::getTotalTime).toList());
    }

    @Override
    public double calculateAverageRating(List<Log> logs) {
        return calculateIntegerAverage(logs.stream().map(Log::getRating).toList());
    }

    private void calculateAndSetPopularity(List<Tour> tours) {
        double averageLogCount = calculateAverageLogCount(tours);

        tours.forEach(tour -> {
            int logCount = tour.getLogs().size();
            tour.setPopularity(calculatePopularity(logCount, averageLogCount));
        });
    }

    private int calculatePopularity(int logCount, double average) {
        double lowerBound = average * POPULARITY_LOWER_BOUND_FACTOR;
        double upperBound = average * POPULARITY_UPPER_BOUND_FACTOR;

        if (lowerBound > logCount) {
            return 1;
        } else if (lowerBound < logCount && upperBound > logCount) {
            return 2;
        }
        return 3;
    }

    private double calculateAverageLogCount(List<Tour> tours) {
        return calculateIntegerAverage(tours.stream().map(tour -> tour.getLogs().size()).toList());
    }

    private double calculateIntegerAverage(List<Integer> integers) {
        return integers.isEmpty() ? 0.0 : (double) integers.stream().reduce(0, Integer::sum) / integers.size();
    }

    private double calculateLongAverage(List<Long> integers) {
        return integers.isEmpty() ? 0.0 : (double) integers.stream().reduce(0L, Long::sum) / integers.size();
    }

}
