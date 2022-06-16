package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AsyncTourService {

    private final TourService tourService;

    private final AsyncService<List<Tour>> tourFetchingService = new AsyncService<>();
    private final AsyncService<Tour> tourCreationService = new AsyncService<>();
    private final AsyncService<Tour> tourUpdateService = new AsyncService<>();
    private final AsyncService<Void> tourDeleteService = new AsyncService<>();

    private final AsyncService<List<Log>> logFetchingService = new AsyncService<>();
    private final AsyncService<Log> logCreationService = new AsyncService<>();
    private final AsyncService<Log> logUpdateService = new AsyncService<>();
    private final AsyncService<Void> logDeleteService = new AsyncService<>();

    public AsyncTourService(TourService tourService) {
        this.tourService = tourService;
    }

    public void fetchTours() {
        tourFetchingService.setSupplier(tourService::fetchTours);
        tourFetchingService.restart();
    }

    public void createTour(Tour tour) {
        tourCreationService.setSupplier(() -> tourService.createTour(tour));
        tourCreationService.restart();
    }

    public void updateTour(Tour tour) {
        tourUpdateService.setSupplier(() -> tourService.updateTour(tour));
        tourUpdateService.restart();
    }

    public void deleteTour(Tour tour) {
        tourDeleteService.setSupplier(() -> {
            tourService.deleteTour(tour.getId());
            return null;
        });
        tourDeleteService.restart();
    }

    public void fetchLogs(UUID tourId) {
        logFetchingService.setSupplier(() -> tourService.fetchLogs(tourId));
        logFetchingService.restart();
    }

    public void createLog(UUID tourId, Log log) {
        logCreationService.setSupplier(() -> tourService.createLog(tourId, log));
        logCreationService.restart();
    }

    public void updateLog(UUID tourId, Log log) {
        logUpdateService.setSupplier(() -> tourService.updateLog(tourId, log.getId(), log));
        logUpdateService.restart();
    }

    public void deleteLog(UUID tourId, Log log) {
        logDeleteService.setSupplier(() -> {
            tourService.deleteLog(tourId, log.getId());
            return null;
        });
        logDeleteService.restart();
    }

    public void subscribeToFetchTours(Consumer<List<Tour>> onSuccess, Consumer<Throwable> onError) {
        tourFetchingService.subscribe(onSuccess, onError);
    }

    public void subscribeToCreateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError) {
        tourCreationService.subscribe(onSuccess, onError);
    }

    public void subscribeToUpdateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError) {
        tourUpdateService.subscribe(onSuccess, onError);
    }

    public void subscribeToDeleteTour(Consumer<Void> onSuccess, Consumer<Throwable> onError) {
        tourDeleteService.subscribe(onSuccess, onError);
    }

    public void subscribeToFetchLogs(Consumer<List<Log>> onSuccess, Consumer<Throwable> onError) {
        logFetchingService.subscribe(onSuccess, onError);
    }

    public void subscribeToCreateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError) {
        logCreationService.subscribe(onSuccess, onError);
    }

    public void subscribeToUpdateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError) {
        logUpdateService.subscribe(onSuccess, onError);
    }

    public void subscribeToDeleteLog(Consumer<Void> onSuccess, Consumer<Throwable> onError) {
        logDeleteService.subscribe(onSuccess, onError);
    }
}
