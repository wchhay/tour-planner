package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.search.SearchService;
import at.technikum.tourplanner.service.tour.AsyncTourService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class TourListViewModel {

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();

    private final Observable<Tour> tourSelectionObservable = new Observable<>();
    private final Observable<Boolean> tourCreationClickedObservable = new Observable<>();

    private final AsyncTourService tourService;
    private final DialogService dialogService;
    private final TourDataStoreService tourDataStoreService;
    private final AlertService alertService;
    private final SearchService searchService;

    public TourListViewModel(AsyncTourService tourService, DialogService dialogService, TourDataStoreService tourDataStoreService, AlertService alertService, SearchService searchService) {
        this.tourService = tourService;
        this.dialogService = dialogService;
        this.tourDataStoreService = tourDataStoreService;
        this.alertService = alertService;
        this.searchService = searchService;

        subscribeToFetchingTours();
        subscribeToCreatingTours();
        subscribeToDeletingTours();
        subscribeToUpdatingTours();

        subscribeToTourObservableListChanged(tourDataStoreService);
        fetchTours();
    }

    public void subscribeToSelection(Listener<Tour> listener) {
        tourSelectionObservable.subscribe(listener);
    }

    public void subscribeToCreateTourClicked(Listener<Boolean> listener) {
        tourCreationClickedObservable.subscribe(listener);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
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
        logger.info("Fetching tours");
        tourService.fetchTours();
    }

    public void createTour(Tour tour) {
        logger.info("Creating new tour");
        tourService.createTour(tour);
    }

    public void updateTour(Tour tour) {
        logger.info("Updating {}", tour);
        tourService.updateTour(tour);
    }

    public void deleteTour(Tour tour) {
        logger.info("Removing {}", tour);
        tourDataStoreService.remove(tour);
        tourService.deleteTour(tour);
    }

    public void search(String searchString) {
        if (searchString.isEmpty()) {
            logger.debug("Clearing search");
            refreshTourList();
        } else {
            logger.debug("Performing search");
            tourList.setAll(searchService.search(searchString, tourDataStoreService.getTourObservableList()));
        }
    }

    private void subscribeToFetchingTours() {
        tourService.subscribeToFetchTours(tourDataStoreService::setTours, alertService::showErrorAlert);
    }

    private void subscribeToCreatingTours() {
        tourService.subscribeToCreateTour(tourDataStoreService::add, this::refetchToursOnError);
    }

    private void subscribeToUpdatingTours() {
        tourService.subscribeToUpdateTour(tourDataStoreService::update, this::refetchToursOnError);
    }

    private void subscribeToDeletingTours() {
        tourService.subscribeToDeleteTour(null, this::refetchToursOnError);
    }

    private void refreshTourList() {
        tourList.setAll(tourDataStoreService.getTourObservableList());
    }

    private void subscribeToTourObservableListChanged(TourDataStoreService tourDataStoreService) {
        tourDataStoreService.getTourObservableList().addListener((ListChangeListener<Tour>) c -> refreshTourList());
    }

    private void refetchToursOnError(Throwable e) {
        logger.info("Re-fetching tours because an error occurred", e);
        fetchTours();
        alertService.showErrorAlert(e);
    }
}
