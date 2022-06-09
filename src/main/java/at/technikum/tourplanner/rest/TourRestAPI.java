package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.dto.LogDto;
import at.technikum.tourplanner.rest.dto.TourDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface TourRestAPI {

    @GET("/tours")
    Call<List<Tour>> getAll();

    @GET("/tours/{id}")
    Call<Tour> getById(@Path("id") UUID uuid);

    @POST("/tours")
    Call<Tour> create(@Body TourDto tour);

    @PUT("/tours/{id}")
    Call<Tour> updateTour(@Path("id") UUID uuid, @Body TourDto tour);

    @POST("/tours/{id}/logs")
    Call<Log> createLog(@Path("id") UUID uuid, @Body LogDto log);

    @PUT("/tours/{tourId}/logs/{logId}")
    Call<Log> updateLog(@Path("tourId") UUID tourId, @Path("logId") UUID logId, @Body LogDto log);

    @DELETE("/tours/{id}")
    Call<Void> delete(@Path("id") UUID uuid);
}
