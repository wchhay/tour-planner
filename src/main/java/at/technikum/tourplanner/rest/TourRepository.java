package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.dto.LogDto;
import at.technikum.tourplanner.rest.dto.TourDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    List<Tour> getAll();

    Optional<Tour> getById(UUID uuid);

    Optional<Tour> create(TourDto tour);

    Optional<Tour> updateTour(UUID tourId, TourDto tour);

    boolean deleteTour(UUID uuid);

    List<Log> getLogsFromTour(UUID tourId);

    Optional<Log> createLog(UUID tourId, LogDto log);

    Optional<Log> updateLog(UUID tourId, UUID logId, LogDto log);

    boolean deleteLog(UUID tourId, UUID logId);
}
