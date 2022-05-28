package at.technikum.tourplanner.dashboard.rest;

import at.technikum.tourplanner.dashboard.model.Tour;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourRestService implements TourDataService {

    public static final String REST_API_BASE_URL = "http://localhost:8080";

    private final RestAPIClient restAPIClient;

    public TourRestService(RestAPIClient restAPIClient) {
        this.restAPIClient = restAPIClient;
    }

    @Override
    public Optional<Tour> create(Tour tour) {
       return Optional.ofNullable(restAPIClient.postRequest(REST_API_BASE_URL + "/tours", tour, Tour.class));
    }

    @Override
    public List<Tour> getAll() {
        return restAPIClient.getRequest(REST_API_BASE_URL + "/tours", new TypeReference<>(){});
    }

    @Override
    public Optional<Tour> getById(UUID id) {
        return Optional.ofNullable(restAPIClient.getRequest(REST_API_BASE_URL + "/tours/" + id, Tour.class));
    }

    @Override
    public Optional<Tour> update(Tour tour) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        /* TODO */
    }
}
