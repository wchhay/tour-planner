package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.dto.LogDto;
import at.technikum.tourplanner.rest.dto.TourDto;
import at.technikum.tourplanner.rest.exception.TourAPIException;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourRemoteRepository implements TourRepository {
    private final TourRestAPI tourRestAPI;

    public TourRemoteRepository(TourRestAPI tourRestAPI) {
        this.tourRestAPI = tourRestAPI;
    }

    @Override
    public List<Tour> getAll() {
        logger.info("Requesting all tours");
        return executeCall(tourRestAPI.getAll()).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Tour> getById(UUID uuid) {
        logger.info("Requesting tour with tourId={}", uuid);
        return executeCall(tourRestAPI.getById(uuid));
    }

    @Override
    public Optional<Tour> create(TourDto tour) {
        logger.info("Requesting tour creation");
        return executeCall(tourRestAPI.create(tour));
    }

    @Override
    public Optional<Tour> updateTour(UUID tourId, TourDto tour) {
        logger.info("Requesting tour update for tourId={}", tourId);
        return executeCall(tourRestAPI.updateTour(tourId, tour));
    }

    @Override
    public List<Log> getLogsFromTour(UUID tourId) {
        logger.info("Requesting logs for tour with tourId={}", tourId);
        return executeCall(tourRestAPI.getLogsFromTour(tourId)).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Log> createLog(UUID tourId, LogDto log) {
        logger.info("Requesting log creation for tour with tourId={}", tourId);
        return executeCall(tourRestAPI.createLog(tourId, log));
    }

    @Override
    public Optional<Log> updateLog(UUID tourId, UUID logId, LogDto log) {
        logger.info("Requesting log update for log with logId={}, tourId={}", logId, tourId);
        return executeCall(tourRestAPI.updateLog(tourId, logId, log));
    }

    @Override
    public boolean deleteTour(UUID uuid) {
        logger.info("Requesting tour deletion for tourId={}", uuid);
        try {
            return tourRestAPI.deleteTour(uuid).execute().isSuccessful();
        } catch (IOException e) {
            throw new TourAPIException();
        }
    }

    @Override
    public boolean deleteLog(UUID tourId, UUID logId) {
        logger.info("Requesting log deletion for log with logId={}, tourId={}", logId, tourId);
        try {
            return tourRestAPI.deleteLog(tourId, logId).execute().isSuccessful();
        } catch (IOException e) {
            throw new TourAPIException();
        }
    }

    private <T> Optional<T> executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            return Optional.ofNullable(response.body());
        } catch (IOException e) {
            throw new TourAPIException();
        }
    }
}
