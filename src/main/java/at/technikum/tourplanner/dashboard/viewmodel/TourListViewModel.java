package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.tour.AsyncTourService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.tour.StatisticsService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class TourListViewModel {

    private final Observable<Tour> tourSelectionObservable = new Observable<>();
    private final Observable<Boolean> tourCreationClickedObservable = new Observable<>();

    private final AsyncTourService tourService;
    private final DialogService dialogService;
    private final TourDataStoreService tourDataStoreService;
    private final StatisticsService statisticsService;

    public TourListViewModel(AsyncTourService tourService, DialogService dialogService, TourDataStoreService tourDataStoreService, StatisticsService statisticsService) {
        this.tourService = tourService;
        this.dialogService = dialogService;
        this.tourDataStoreService = tourDataStoreService;
        this.statisticsService = statisticsService;

        subscribeToFetchingTours();
        subscribeToCreatingTours();
        subscribeToDeletingTours();
        subscribeToUpdatingTours();
    }

    public void subscribeToSelection(Listener<Tour> listener) {
        tourSelectionObservable.subscribe(listener);
    }

    public void subscribeToCreateTourClicked(Listener<Boolean> listener) {
        tourCreationClickedObservable.subscribe(listener);
    }

    public ObservableList<Tour> getTourList() {
        return tourDataStoreService.getTourObservableList();
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> tourSelectionObservable.notifyListeners(newValue));
    }

    public void openCreationDialog() {
        tourCreationClickedObservable.notifyListeners(true);
        dialogService.openCreationDialog();
    }

    public void openUpdateDialog() {
        dialogService.openUpdateDialog();
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
        tourService.fetchTours();
    }

    public void createTour(Tour tour) {
        tourService.createTour(tour);
    }

    public void updateTour(Tour tour) {
        tourService.updateTour(tour);
    }

    public void deleteTour(Tour tour) {
        tourDataStoreService.remove(tour);
        tourService.deleteTour(tour);
    }

    private void subscribeToFetchingTours() {
        tourService.subscribeToFetchTours(tours -> {
            tourDataStoreService.setTours(tours);
            statisticsService.calculateAndSetComputedAttributes();
        }, this::showErrorAlert);
    }

    private void subscribeToCreatingTours() {
        tourService.subscribeToCreateTour(tour -> {
            tourDataStoreService.add(tour);
            statisticsService.calculateAndSetComputedAttributes();
        }, this::showErrorAlert);
    }

    private void subscribeToUpdatingTours() {
        tourService.subscribeToUpdateTour(tour -> {
            tourDataStoreService.update(tour);
            statisticsService.calculateAndSetComputedAttributes();
        }, this::showErrorAlert);
    }

    private void subscribeToDeletingTours() {
        tourService.subscribeToDeleteTour(
                success -> statisticsService.calculateAndSetComputedAttributes(),
                this::showErrorAlert
        );
    }

    private void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage());
        alert.showAndWait();
    }
}
