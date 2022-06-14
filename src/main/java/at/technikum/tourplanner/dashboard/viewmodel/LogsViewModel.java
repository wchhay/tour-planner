package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.AsyncService;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.service.TourService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LogsViewModel {

    private Tour selectedTour;

    private final ObservableList<Log> logsList = FXCollections.observableArrayList();

    private final TourDialogService tourDialogService;

    private final TourService tourService;

    private final AsyncService<Log> logCreationService;

    public LogsViewModel(TourDialogService tourDialogService, TourService tourService) {
        this.tourDialogService = tourDialogService;
        this.tourService = tourService;

        this.logCreationService = new AsyncService<>();

        subscribeToLogCreation();
    }

    public void openLogDialog() {
        tourDialogService.openLogDialog();
    }

    public void setLogsList(List<Log> logs) {
        if (null != logs) {
            logsList.setAll(logs);
        }
    }

    public ObservableList<Log> getLogsList() {
        return logsList;
    }

    public void createLog(Log log) {
        if (null != selectedTour) {
            logCreationService.setSupplier(() -> tourService.createLog(selectedTour.getId(), log));
            logCreationService.restart();
        }
    }

    public void setSelectedTour(Tour tour) {
        selectedTour = tour;
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
}
