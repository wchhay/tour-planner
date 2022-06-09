package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    List<Tour> getAll();

    Optional<Tour> getById(UUID uuid);

    Optional<Tour> create(Tour tour);

    Optional<Tour> updateTour(Tour tour);

    Optional<Log> createLog(UUID tourId, Log log);

    Optional<Log> updateLog(UUID tourId, UUID logId, Log log);

    boolean delete(UUID uuid);
}
