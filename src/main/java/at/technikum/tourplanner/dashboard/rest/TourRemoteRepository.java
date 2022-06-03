package at.technikum.tourplanner.dashboard.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourRemoteRepository implements TourRepository {

    public static final String REST_API_BASE_URL = "http://localhost:8080";

    private final TourRestAPI tourRestAPI;

    public TourRemoteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.tourRestAPI = retrofit.create(TourRestAPI.class);
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
        return executeCall(tourRestAPI.create(tour));
    }

    @Override
    public Optional<Tour> updateTour(Tour tour) {
        return executeCall(tourRestAPI.updateTour(tour));
    }

    @Override
    public Optional<Log> updateLog(UUID tourId, UUID logId, Log log) {
        return executeCall(tourRestAPI.updateLog(tourId, logId, log));
    }

    @Override
    public void delete(UUID uuid) {
        tourRestAPI.delete(uuid);
    }

    private <T> Optional<T> executeCall(Call<T> call) {
        try {
            return Optional.ofNullable(call.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
