package at.technikum.tourplanner.service.statistics;


import at.technikum.tourplanner.dashboard.model.Tour;

public interface StatisticsService {

    void calculateAndSetChildFriendliness(Tour tour);

    void calculateAndSetPopularity(Tour tour);

    TourStats calculateTourStats(Tour tour);
}
