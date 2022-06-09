package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;

public interface TourService {

    List<Tour> fetchTours();

    Tour createTour(Tour tour);
}
