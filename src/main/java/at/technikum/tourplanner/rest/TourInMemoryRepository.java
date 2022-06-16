package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.rest.dto.LogDto;
import at.technikum.tourplanner.rest.dto.TourDto;

import java.util.*;
import java.util.function.Consumer;

public class TourInMemoryRepository implements TourRepository {

    private final List<Tour> tourList = new ArrayList<>();
    
    private final Random random = new Random();
    
    @Override
    public List<Tour> getAll() {
        return tourList;
    }

    @Override
    public Optional<Tour> getById(UUID uuid) {
        return tourList.stream().filter(tour -> tour.getId().equals(uuid)).findFirst();
    }

    @Override
    public Optional<Tour> create(TourDto dto) {
        Tour tour = DtoMapper.fromDto(dto);

        tour.setId(UUID.randomUUID());
        tour.setDistance(random.nextDouble(0.0, 10000.0));
        tour.setEstimatedTime(random.nextLong(0L, 10000L));
        tour.setLogs(new ArrayList<>());

        tourList.add(tour);
        return Optional.of(tour);
    }

    @Override
    public Optional<Tour> updateTour(UUID tourId, TourDto dto) {
        for (Tour tour : tourList) {
            if (tour.getId().equals(tourId)) {
                updateTour(tour, dto);
                return Optional.of(tour);
            }
        }
        return Optional.empty();
    }

    private void updateTour(Tour tour, TourDto dto) {
        setIfNotNull(dto.getName(), tour::setName);
        setIfNotNull(dto.getFrom(), tour::setFrom);
        setIfNotNull(dto.getTo(), tour::setTo);
        setIfNotNull(dto.getDescription(), tour::setDescription);
        setIfNotNull(dto.getTransportType(), tour::setTransportType);
    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (null != value) {
            setter.accept(value);
        }
    }

    @Override
    public List<Log> getLogsFromTour(UUID tourId) {
        Optional<Tour> tour = getById(tourId);

        if (tour.isEmpty()) {
            return Collections.emptyList();
        }

        return tour.get().getLogs();
    }

    @Override
    public Optional<Log> createLog(UUID tourId, LogDto dto) {
        Log log = DtoMapper.fromDto(dto);

        return getById(tourId).map(savedTour -> {
            savedTour.getLogs().add(log);
            return log;
        });
    }

    @Override
    public Optional<Log> updateLog(UUID tourId, UUID logId, LogDto dto) {
        Optional<Tour> foundTour = getById(tourId);
        
        if (foundTour.isEmpty()) {
            return Optional.empty();
        }

        List<Log> logList = foundTour.get().getLogs();
        for (Log log : logList) {
            if (log.getId().equals(logId)) {
                updateLog(log, dto);
                return Optional.of(log);
            }
        }
        return Optional.empty();
    }

    private void updateLog(Log log, LogDto dto) {
        setIfNotNull(dto.getComment(), log::setComment);
        setIfNotNull(dto.getDate(), log::setDate);
        setIfNotNull(dto.getDifficulty(), log::setDifficulty);
        setIfNotNull(dto.getRating(), log::setRating);
        setIfNotNull(dto.getTotalTime(), log::setTotalTime);
    }

    @Override
    public boolean deleteTour(UUID uuid) {
        return tourList.removeIf(tour -> tour.getId().equals(uuid));
    }

    @Override
    public boolean deleteLog(UUID tourId, UUID logId) {
        return getById(tourId)
                .map(tour -> tour.getLogs().removeIf(log -> log.getId().equals(logId)))
                .orElse(false);
    }
}
