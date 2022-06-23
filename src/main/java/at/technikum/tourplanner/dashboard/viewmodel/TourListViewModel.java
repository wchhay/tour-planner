package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.tour.AsyncTourService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class TourListViewModel {

    private final Observable<Tour> tourSelectionObservable = new Observable<>();
    private final Observable<Boolean> tourCreationClickedObservable = new Observable<>();

    private final AsyncTourService tourService;
    private final DialogService dialogService;
    private final TourDataStoreService tourDataStoreService;
    private final AlertService alertService;

    public TourListViewModel(AsyncTourService tourService, DialogService dialogService, TourDataStoreService tourDataStoreService, AlertService alertService) {
        this.tourService = tourService;
        this.dialogService = dialogService;
        this.tourDataStoreService = tourDataStoreService;
        this.alertService = alertService;

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
        if (alertService.getUserConfirmation("Delete Tour", "Do you want to delete this tour?")) {
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
        tourService.subscribeToFetchTours(tourDataStoreService::setTours, alertService::showErrorAlert);
    }

    private void subscribeToCreatingTours() {
        tourService.subscribeToCreateTour(tourDataStoreService::add, alertService::showErrorAlert);
    }

    private void subscribeToUpdatingTours() {
        tourService.subscribeToUpdateTour(tourDataStoreService::update, alertService::showErrorAlert);
    }

    private void subscribeToDeletingTours() {
        tourService.subscribeToDeleteTour(null, alertService::showErrorAlert);
    }
}
