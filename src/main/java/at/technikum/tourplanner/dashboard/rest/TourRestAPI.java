package at.technikum.tourplanner.dashboard.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;
import java.util.UUID;

public interface TourRestAPI {

    @GET("/tours")
    Call<List<Tour>> getAll();

    @GET("/tours/{id}")
    Call<Tour> getById(UUID uuid);

    @POST("/tours")
    Call<Tour> create(Tour tour);

    @PUT("/tours/{id}")
    Call<Tour> updateTour(Tour tour);

    @PUT("/tours/{tourId}/logs/{logId}")
    Call<Log> updateLog(UUID tourId, UUID logId, Log log);

    @DELETE("/tours/{id}")
    void delete(UUID uuid);
}
