package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.function.Consumer;

public interface AsyncTourService {
    void fetchTours();

    void createTour(Tour tour);

    void updateTour(Tour tour);

    void deleteTour(Tour tour);

    void subscribeToFetchTours(Consumer<List<Tour>> onSuccess, Consumer<Throwable> onError);

    void subscribeToCreateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError);

    void subscribeToUpdateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError);

    void subscribeToDeleteTour(Consumer<Void> onSuccess, Consumer<Throwable> onError);
}
