package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.rest.exception.TourAPIException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourRemoteRepository implements TourRepository {
    private final TourRestAPI tourRestAPI;

    public TourRemoteRepository(TourRestAPI tourRestAPI) {
        this.tourRestAPI = tourRestAPI;
    }

    @Override
    public List<Tour> getAll() {
        return executeCall(tourRestAPI.getAll()).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Tour> getById(UUID uuid) {
        return executeCall(tourRestAPI.getById(uuid));
    }

    @Override
    public Optional<Tour> create(Tour tour) {
        return executeCall(tourRestAPI.create(DtoMapper.toTourDto(tour)));
    }

    @Override
    public Optional<Tour> updateTour(Tour tour) {
        return executeCall(tourRestAPI.updateTour(tour.getId(), DtoMapper.toTourDto(tour)));
    }

    @Override
    public Optional<Log> createLog(UUID tourId, Log log) {
        return executeCall(tourRestAPI.createLog(tourId, DtoMapper.toLogDto(log)));
    }

    @Override
    public Optional<Log> updateLog(UUID tourId, UUID logId, Log log) {
        return executeCall(tourRestAPI.updateLog(tourId, logId, DtoMapper.toLogDto(log)));
    }

    @Override
    public boolean delete(UUID uuid) {
        try {
            return tourRestAPI.delete(uuid).execute().isSuccessful();
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
