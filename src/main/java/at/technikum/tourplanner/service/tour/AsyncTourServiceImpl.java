package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.AsyncService;

import java.util.List;
import java.util.function.Consumer;

public class AsyncTourServiceImpl implements AsyncTourService {

    private final TourService tourService;

    private final AsyncService<List<Tour>> tourFetchingService = new AsyncService<>();
    private final AsyncService<Tour> tourCreationService = new AsyncService<>();
    private final AsyncService<Tour> tourUpdateService = new AsyncService<>();
    private final AsyncService<Void> tourDeleteService = new AsyncService<>();

    public AsyncTourServiceImpl(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public void fetchTours() {
        tourFetchingService.setSupplier(tourService::fetchTours);
        tourFetchingService.restart();
    }

    @Override
    public void createTour(Tour tour) {
        tourCreationService.setSupplier(() -> tourService.createTour(tour));
        tourCreationService.restart();
    }

    @Override
    public void updateTour(Tour tour) {
        tourUpdateService.setSupplier(() -> tourService.updateTour(tour));
        tourUpdateService.restart();
    }

    @Override
    public void deleteTour(Tour tour) {
        tourDeleteService.setSupplier(() -> {
            tourService.deleteTour(tour.getId());
            return null;
        });
        tourDeleteService.restart();
    }

    @Override
    public void subscribeToFetchTours(Consumer<List<Tour>> onSuccess, Consumer<Throwable> onError) {
        tourFetchingService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToCreateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError) {
        tourCreationService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToUpdateTour(Consumer<Tour> onSuccess, Consumer<Throwable> onError) {
        tourUpdateService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToDeleteTour(Consumer<Void> onSuccess, Consumer<Throwable> onError) {
        tourDeleteService.subscribe(onSuccess, onError);
    }
}
