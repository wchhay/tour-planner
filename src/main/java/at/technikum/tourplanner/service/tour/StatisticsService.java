package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Log;

import java.util.List;

public interface StatisticsService {

    void calculateAndSetComputedAttributes();

    double calculateAverageDifficulty(List<Log> logs);

    double calculateAverageTime(List<Log> logs);

    double calculateAverageRating(List<Log> logs);

}
