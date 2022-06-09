package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.UUID;

public interface TourService {

    List<Tour> fetchTours();

    Tour createTour(Tour tour);

    boolean deleteTour(UUID uuid);
}
