package at.technikum.tourplanner.dashboard.rest;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourDataService {

    List<Tour> getAll();

    Optional<Tour> getById(UUID uuid);

    Optional<Tour> create(Tour tour);

    Optional<Tour> update(Tour tour);

    void delete(UUID uuid);
}
