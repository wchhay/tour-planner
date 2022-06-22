package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.UUID;

public interface TourService {

    List<Tour> fetchTours();

    Tour createTour(Tour tour);

    Tour updateTour(Tour tour);

    void deleteTour(UUID uuid);
}
