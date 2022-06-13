package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.AsyncService;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class TourListViewModel {

    public static final String FAILED_DELETION_MESSAGE = "An error occurred while deleting the tour.";

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();
    private final Observable<Tour> tourSelectionObservable = new Observable<>();
    private final Observable<Boolean> tourCreationClickedObservable = new Observable<>();
    private final TourService tourService;
    private final TourDialogService tourDialogService;
    private final AsyncService<List<Tour>> tourFetchingService;
    private final AsyncService<Tour> tourCreationService;
    private final AsyncService<Tour> tourUpdateService;
    private final AsyncService<Boolean> tourDeletionService;

    public TourListViewModel(TourService tourService, TourDialogService tourDialogService) {
        this.tourService = tourService;
        this.tourDialogService = tourDialogService;

        this.tourFetchingService = new AsyncService<>(tourService::fetchTours);
        this.tourCreationService = new AsyncService<>();
        this.tourUpdateService = new AsyncService<>();
        this.tourDeletionService = new AsyncService<>();

        subscribeToFetchingTours();
        subscribeToCreatingTours();
        subscribeToDeletingTours();
        subscribeToUpdatingTours();
    }

    public void subscribeToSelection(Listener<Tour> listener) {
        tourSelectionObservable.subscribe(listener);
    }

    public void unsubscribeFromSelection(Listener<Tour> listener) {
        tourSelectionObservable.unsubscribe(listener);
    }

    public void subscribeToCreateTourClicked(Listener<Boolean> listener) {
        tourCreationClickedObservable.subscribe(listener);
    }

    public void unsubscribeFromCreateTourClicked(Listener<Boolean> listener) {
        tourCreationClickedObservable.unsubscribe(listener);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> tourSelectionObservable.notifyListeners(newValue));
    }

    public void openCreationDialog() {
        tourCreationClickedObservable.notifyListeners(true);
        tourDialogService.openCreationDialog();
    }

    public void openUpdateDialog() {
        tourDialogService.openUpdateDialog();
    }

    public void openDeleteDialog(Tour tour) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Tour");
        alert.setHeaderText("Delete Tour?");
        alert.setContentText("Do you want to delete this tour?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && ButtonType.OK == result.get()) {
            deleteTour(tour);
        }
    }

    public void fetchTours() {
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
        tourDeletionService.setSupplier(() -> tourService.deleteTour(tour.getId()));
        tourDeletionService.restart();

        tourList.remove(tour);
    }

    private void setTours(List<Tour> tours) {
        tourList.clear();
        if (null != tours) {
            tourList.setAll(tours);
        }
    }

    private void subscribeToFetchingTours() {
        tourFetchingService.valueProperty().addListener((observableValue, oldValue, newValue) -> setTours(newValue));
        tourFetchingService.exceptionProperty().addListener((observableValue, oldValue, newValue) -> {
            if (null != newValue) {
                showErrorAlert(newValue.getMessage());
            }
        });
    }

    private void subscribeToCreatingTours() {
        tourCreationService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                this.tourList.add(newValue);
            }
        });
        tourCreationService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                showErrorAlert(newValue.getMessage());
            }
        });
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

    private void subscribeToUpdatingTours() {
        tourUpdateService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                fetchTours();
            }
        });
        tourUpdateService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
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
