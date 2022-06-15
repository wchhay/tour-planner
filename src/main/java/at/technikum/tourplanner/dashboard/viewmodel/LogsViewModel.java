package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.AsyncService;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class LogsViewModel {

    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    private final ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();

    private final ObservableList<Log> logsList = FXCollections.observableArrayList();
    private final Observable<Log> logDialogOpenedObservable = new Observable<>();

    private final TourDialogService tourDialogService;
    private final TourService tourService;

    private final AsyncService<List<Log>> logFetchingService;
    private final AsyncService<Log> logCreationService;
    private final AsyncService<Log> logUpdateService;
    private final AsyncService<Boolean> logDeleteService;

    public LogsViewModel(TourDialogService tourDialogService, TourService tourService) {
        this.tourDialogService = tourDialogService;
        this.tourService = tourService;

        this.logFetchingService = new AsyncService<>();
        this.logCreationService = new AsyncService<>();
        this.logUpdateService = new AsyncService<>();
        this.logDeleteService = new AsyncService<>();

        subscribeToLogCreation();
        subscribeToFetchingLogs();
        subscribeToLogUpdate();
        subscribeToDeletingTours();
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

    public void openLogCreationDialog() {
        logDialogOpenedObservable.notifyListeners(null);
        tourDialogService.openLogCreationDialog();
    }

    public void openLogUpdateDialog() {
        logDialogOpenedObservable.notifyListeners(selectedLog.getValue());
        tourDialogService.openLogUpdateDialog();
    }

    public void openLogDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Log");
        alert.setHeaderText("Delete Log?");
        alert.setContentText("Do you want to delete this log?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && ButtonType.OK == result.get()) {
            deleteLog(selectedLog.getValue());
        }
    }

    public void fetchLogs() {
        if (null != selectedTour.getValue()) {
            logFetchingService.setSupplier(() -> tourService.fetchLogs(selectedTour.getValue().getId()));
            logFetchingService.restart();
        }
    }

    public void createLog(Log log) {
        if (null != selectedTour.getValue()) {
            logCreationService.setSupplier(() -> tourService.createLog(selectedTour.getValue().getId(), log));
            logCreationService.restart();
        }
    }

    public void updateLog(Log log) {
        if (null != selectedTour.getValue()) {
            logUpdateService.setSupplier(() -> tourService.updateLog(selectedTour.getValue().getId(), log.getId(), log));
            logUpdateService.restart();
        }
    }

    private void deleteLog(Log log) {
        if (null != selectedTour.getValue()) {
            logsList.remove(log);
            logDeleteService.setSupplier(() -> tourService.deleteLog(selectedTour.getValue().getId(), log.getId()));
            logDeleteService.restart();
        }
    }

    private void subscribeToLogCreation() {
        logCreationService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                logsList.add(newValue);
            }
        });
        logCreationService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                // TODO: show alert
            }
        });
    }

    private void subscribeToLogUpdate() {
        logUpdateService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                fetchLogs();
            }
        });
        logUpdateService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                // TODO: show alert
            }
        });
    }

    private void subscribeToFetchingLogs() {
        logFetchingService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setLogsList(newValue);
            }
        });
    }

    private void subscribeToDeletingTours() {
        logDeleteService.valueProperty().addListener((observable, oldValue, isSucessful) -> {
            if (Boolean.FALSE.equals(isSucessful)) {
                // TODO: show alert
            }
        });
        logDeleteService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                // TODO: show alert
            }
        });
    }

    public ObservableList<Log> getLogsList() {
        return logsList;
    }

    public void setLogsList(List<Log> logs) {
        logsList.clear();
        if (null != logs) {
            logsList.setAll(logs);
        }
    }

    public void setSelectedTour(Tour tour) {
        selectedTour.setValue(tour);
    }

    public void setSelectedLog(Log log) {
        selectedLog.setValue(log);
    }

    public ObjectProperty<Tour> selectedTourProperty() {
        return selectedTour;
    }

    public ObjectProperty<Log> selectedLogProperty() {
        return selectedLog;
    }
}
