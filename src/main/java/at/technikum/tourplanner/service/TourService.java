package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.UUID;

public interface TourService {

    List<Tour> fetchTours();

    Tour createTour(Tour tour);

    Tour updateTour(Tour tour);

    boolean deleteTour(UUID uuid);

    Log createLog(UUID tourId, Log log);
}
