package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Publisher;
import at.technikum.tourplanner.service.AsyncService;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;

public class TourListViewModel implements Publisher<Tour> {

    public static final String FAILED_DELETION_MESSAGE = "An error occurred while deleting the tour.";

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();
    private final Observable<Tour> tourObservable = new Observable<>();
    private final TourService tourService;
    private final TourDialogService tourDialogService;
    private final AsyncService<List<Tour>> tourFetchingService;
    private final AsyncService<Tour> tourCreationService;
    private final AsyncService<Boolean> tourDeletionService;

    public TourListViewModel(TourService tourService, TourDialogService tourDialogService) {
        this.tourService = tourService;
        this.tourDialogService = tourDialogService;

        this.tourFetchingService = new AsyncService<>(tourService::fetchTours);
        this.tourCreationService = new AsyncService<>();
        this.tourDeletionService = new AsyncService<>();

        subscribeToFetchingTours();
        subscribeToUpdatingTours();
        subscribeToDeletingTours();
    }

    @Override
    public void addListener(Listener<Tour> listener) {
        tourObservable.subscribe(listener);
    }

    @Override
    public void removeListener(Listener<Tour> listener) {
        tourObservable.unsubscribe(listener);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> tourObservable.notifyListeners(newValue));
    }

    public void openDialog() {
        tourDialogService.openDialog();
    }

    public void createTour(Tour tour) {
        tourCreationService.setSupplier(() -> tourService.createTour(tour));
        tourCreationService.restart();
    }

    public void fetchTours() {
        tourFetchingService.restart();
    }

    public void deleteTour(Tour tour) {
        tourDeletionService.setSupplier(() -> tourService.deleteTour(tour.getId()));
        tourDeletionService.restart();
    }

    private void subscribeToFetchingTours() {
        tourFetchingService.valueProperty().addListener((observableValue, oldValue, newValue) -> setTours(newValue));
        tourFetchingService.exceptionProperty().addListener((observableValue, oldValue, newValue) -> {
            if (null != newValue) {
                showErrorAlert(newValue.getMessage());
            }
        });
    }

    private void setTours(List<Tour> tours) {
        tourList.clear();
        if (null != tours) {
            tourList.setAll(tours);
        }
    }

    private void subscribeToUpdatingTours() {
        tourCreationService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                addTour(newValue);
            }
        });
        tourCreationService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                showErrorAlert(newValue.getMessage());
            }
        });
    }

    private void addTour(Tour tour) {
        tourList.add(tour);
    }

    private void subscribeToDeletingTours() {
        tourDeletionService.valueProperty().addListener((observable, oldValue, isSuccessful) -> {
            if (Boolean.FALSE.equals(isSuccessful)) {
                showErrorAlert(FAILED_DELETION_MESSAGE);
            }
        });
        tourDeletionService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                showErrorAlert(newValue.getMessage());
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
