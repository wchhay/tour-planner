package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.AsyncTourService;
import at.technikum.tourplanner.service.TourDialogService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class TourListViewModel {

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();
    private final Observable<Tour> tourSelectionObservable = new Observable<>();
    private final Observable<Boolean> tourCreationClickedObservable = new Observable<>();

    private final AsyncTourService tourService;
    private final TourDialogService tourDialogService;

    public TourListViewModel(AsyncTourService tourService, TourDialogService tourDialogService) {
        this.tourService = tourService;
        this.tourDialogService = tourDialogService;

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
        tourService.fetchTours();
    }

    public void createTour(Tour tour) {
        tourService.createTour(tour);
    }

    public void updateTour(Tour tour) {
        tourService.updateTour(tour);
    }

    public void deleteTour(Tour tour) {
        tourList.remove(tour);
        tourService.deleteTour(tour);
    }

    private void setTours(List<Tour> tours) {
        tourList.clear();
        if (null != tours) {
            tourList.setAll(tours);
        }
    }

    private void subscribeToFetchingTours() {
        tourService.subscribeToFetchTours(this::setTours, this::showErrorAlert);
    }

    private void subscribeToCreatingTours() {
        tourService.subscribeToCreateTour(tourList::add, this::showErrorAlert);
    }

    private void subscribeToUpdatingTours() {
        tourService.subscribeToUpdateTour(this::updateTourInTourList, this::showErrorAlert);
    }

    private void subscribeToDeletingTours() {
        tourService.subscribeToDeleteTour(null, this::showErrorAlert);
    }

    private void updateTourInTourList(Tour tour) {
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getId().equals(tour.getId())) {
                tourList.set(i, tour);
                log.debug(tour + " updating in TourList");
                return;
            }
        }
    }

    private void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage());
        alert.showAndWait();
    }
}
