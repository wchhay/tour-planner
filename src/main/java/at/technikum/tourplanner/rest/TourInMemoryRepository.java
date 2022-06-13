package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.*;

public class TourInMemoryRepository implements TourRepository {

    private final List<Tour> tourList = new ArrayList<>();
    
    private final Random random = new Random();
    
    @Override
    public List<Tour> getAll() {
        return tourList;
    }

    @Override
    public Optional<Tour> getById(UUID uuid) {
        return tourList.stream().filter(tour -> tour.getId() == uuid).findFirst();
    }

    @Override
    public Optional<Tour> create(Tour tour) {
        tour.setId(UUID.randomUUID());
        tour.setDistance(random.nextDouble());
        tour.setEstimatedTime(random.nextLong());

        tourList.add(tour);
        return Optional.of(tour);
    }

    @Override
    public Optional<Tour> updateTour(Tour tour) {
        if (delete(tour.getId())) {
            return create(tour);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Log> createLog(UUID tourId, Log log) {
        return getById(tourId).map(savedTour -> {
            savedTour.getLogs().add(log);
            return log;
        });
    }

    @Override
    public Optional<Log> updateLog(UUID tourId, UUID logId, Log log) {
        Optional<Tour> foundTour = getById(tourId);
        
        if (foundTour.isEmpty()) {
            return Optional.empty();
        }

        List<Log> logList = foundTour.get().getLogs();
        if (logList.removeIf(foundLog -> foundLog.getId() == logId)) {
            logList.add(log);
            return Optional.of(log);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(UUID uuid) {
        return tourList.removeIf(tour -> tour.getId() == uuid);
    }
}
