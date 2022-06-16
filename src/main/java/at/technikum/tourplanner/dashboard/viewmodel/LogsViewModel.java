package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.AsyncTourService;
import at.technikum.tourplanner.service.TourDialogService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class LogsViewModel {

    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    private final ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();

    private final ObservableList<Log> logsList = FXCollections.observableArrayList();
    private final Observable<Log> logDialogOpenedObservable = new Observable<>();

    private final TourDialogService tourDialogService;
    private final AsyncTourService tourService;

    public LogsViewModel(TourDialogService tourDialogService, AsyncTourService tourService) {
        this.tourDialogService = tourDialogService;
        this.tourService = tourService;

        subscribeToLogCreation();
        subscribeToFetchingLogs();
        subscribeToLogUpdate();
        subscribeToDeletingTours();
    }

    public void openLogCreationDialog() {
        logDialogOpenedObservable.notifyListeners(null);
        tourDialogService.openLogCreationDialog();
    }

    public void openLogUpdateDialog() {
        logDialogOpenedObservable.notifyListeners(selectedLog.get());
        tourDialogService.openLogUpdateDialog();
    }

    public void openLogDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Log");
        alert.setHeaderText("Delete Log?");
        alert.setContentText("Do you want to delete this log?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && ButtonType.OK == result.get()) {
            deleteLog(selectedLog.get());
        }
    }

    public void fetchLogs() {
        if (null != selectedTour.get()) {
            tourService.fetchLogs(selectedTour.get().getId());
            log.info("Fetching logs from " + selectedTour.get());
        }
    }

    public void createLog(Log log) {
        if (null != selectedTour.get()) {
            tourService.createLog(selectedTour.get().getId(), log);
        }
    }

    public void updateLog(Log log) {
        if (null != selectedTour.get()) {
            tourService.updateLog(selectedTour.get().getId(), log);
        }
    }

    public void deleteLog(Log log) {
        if (null != selectedTour.get()) {
            removeFromTourLogs(log);
            tourService.deleteLog(selectedTour.get().getId(), log);
        }
    }

    public void subscribeToLogDialogOpened(Listener<Log> listener) {
        logDialogOpenedObservable.subscribe(listener);
    }

    public void unsubscribeFromLogDialogOpened(Listener<Log> listener) {
        logDialogOpenedObservable.unsubscribe(listener);
    }

    public ChangeListener<Log> getSelectionChangeListener() {
        return ((observable, oldValue, newValue) -> setSelectedLog(newValue));
    }

    public ObservableList<Log> getLogsList() {
        return logsList;
    }

    public ObjectProperty<Tour> selectedTourProperty() {
        return selectedTour;
    }

    public ObjectProperty<Log> selectedLogProperty() {
        return selectedLog;
    }

    public void setSelectedTour(Tour tour) {
        selectedTour.setValue(tour);
        if (null != tour) {
            setLogsObservableList(tour.getLogs());
        }
    }

    public void setSelectedLog(Log log) {
        selectedLog.setValue(log);
    }

    private void subscribeToFetchingLogs() {
        tourService.subscribeToFetchLogs(this::setSelectedTourLogs, this::showErrorAlert);
    }

    private void subscribeToLogCreation() {
        tourService.subscribeToCreateLog(this::addToTourLogs, this::showErrorAlert);
    }

    private void subscribeToLogUpdate() {
        tourService.subscribeToUpdateLog(newValue -> fetchLogs(), this::showErrorAlert);
    }

    private void subscribeToDeletingTours() {
        tourService.subscribeToDeleteLog(null, this::showErrorAlert);
    }

    private void addToTourLogs(Log log) {
        logsList.add(log);
        if (null != selectedTour.get()) {
            selectedTour.get().getLogs().add(log);
        }
    }

    private void removeFromTourLogs(Log log) {
        logsList.remove(log);
        selectedTour.get().getLogs().remove(log);
    }

    private void setSelectedTourLogs(List<Log> logs) {
        setLogsObservableList(logs);
        if (null != selectedTour.get()) {
            selectedTour.get().setLogs(logs);
        }
    }

    private void setLogsObservableList(List<Log> logs) {
        logsList.clear();
        if (null != logs) {
            logsList.setAll(logs);
        }
    }

    private void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage());
        alert.showAndWait();
    }
}
