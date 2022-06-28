package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.log.AsyncLogService;
import at.technikum.tourplanner.service.dialog.DialogService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class LogsViewModel {

    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    private final ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();

    private final ObservableList<Log> logsList = FXCollections.observableArrayList();
    private final Observable<Log> logDialogOpenedObservable = new Observable<>();
    private final Observable<Boolean> tourReloadRequiredObservable = new Observable<>();

    private final DialogService dialogService;
    private final AsyncLogService logService;
    private final AlertService alertService;

    public LogsViewModel(DialogService dialogService, AsyncLogService logService, AlertService alertService) {
        this.dialogService = dialogService;
        this.logService = logService;
        this.alertService = alertService;

        subscribeToLogCreation();
        subscribeToFetchingLogs();
        subscribeToLogUpdate();
        subscribeToDeletingTours();
    }

    public void openLogCreationDialog() {
        logDialogOpenedObservable.notifyListeners(null);
        dialogService.openLogCreationDialog();
    }

    public void openLogUpdateDialog() {
        logDialogOpenedObservable.notifyListeners(selectedLog.get());
        dialogService.openLogUpdateDialog();
    }

    public void openLogDeleteConfirmationDialog() {
        if (alertService.getUserConfirmation("Delete Log", "Do you want to delete this log?")) {
            deleteLog(selectedLog.get());
        }
    }

    public void fetchLogs() {
        if (null != selectedTour.get()) {
            logService.fetchLogs(selectedTour.get().getId());
            logger.info("Fetching logs from {}", selectedTour.get());
        }
    }

    public void createLog(Log log) {
        if (null != selectedTour.get()) {
            logService.createLog(selectedTour.get().getId(), log);
            logger.info("Creating new log for {}", selectedTour);
        }
    }

    public void updateLog(Log log) {
        if (null != selectedTour.get()) {
            logService.updateLog(selectedTour.get().getId(), log);
            logger.info("Updating {} of {}", log, selectedTour);
        }
    }

    public void deleteLog(Log log) {
        if (null != selectedTour.get()) {
            removeFromTourLogs(log);
            logService.deleteLog(selectedTour.get().getId(), log);
            logger.info("Deleting {} of {}", log, selectedTour);
        }
    }

    public void subscribeToLogDialogOpened(Listener<Log> listener) {
        logDialogOpenedObservable.subscribe(listener);
    }

    public void subscribeToTourReloadRequired(Listener<Boolean> listener) {
        tourReloadRequiredObservable.subscribe(listener);
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

    public void setTourAndLogs(Tour tour) {
        logger.debug("Setting selected tour to {}", tour);
        selectedTour.setValue(tour);
        if (null != tour) {
            setLogsObservableList(tour.getLogs());
        } else {
            logsList.clear();
        }
    }

    public void setSelectedLog(Log log) {
        selectedLog.setValue(log);
        logger.debug("Selecting {}", log);
    }

    private void subscribeToFetchingLogs() {
        logService.subscribeToFetchLogs(this::setSelectedTourLogs, alertService::showErrorAlert);
    }

    private void subscribeToLogCreation() {
        logService.subscribeToCreateLog(this::addToTourLogs, this::refetchLogsOnError);
    }

    private void subscribeToLogUpdate() {
        logService.subscribeToUpdateLog(newValue -> fetchLogs(), this::refetchLogsOnError);
    }

    private void subscribeToDeletingTours() {
        logService.subscribeToDeleteLog(null, this::refetchLogsOnError);
    }

    private void setSelectedTourLogs(List<Log> logs) {
        if (null != selectedTour.get()) {
            selectedTour.get().setLogs(logs);
        }
        setLogsObservableList(logs);
        tourReloadRequiredObservable.notifyListeners(true);
    }

    private void addToTourLogs(Log log) {
        logsList.add(log);
        if (null != selectedTour.get()) {
            selectedTour.get().getLogs().add(log);
        }
        tourReloadRequiredObservable.notifyListeners(true);
    }

    private void removeFromTourLogs(Log log) {
        logsList.remove(log);
        selectedTour.get().getLogs().remove(log);
        tourReloadRequiredObservable.notifyListeners(true);
    }

    private void setLogsObservableList(List<Log> logs) {
        logsList.clear();
        if (null != logs) {
            logsList.setAll(logs);
        }
    }

    private void refetchLogsOnError(Throwable e) {
        logger.info("Re-fetching logs because an error occurred", e);
        fetchLogs();
        alertService.showErrorAlert(e);
    }
}
