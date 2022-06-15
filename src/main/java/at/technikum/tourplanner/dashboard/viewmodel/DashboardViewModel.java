package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final LogsViewModel logsViewModel;
    private final LogDialogViewModel logDialogViewModel;

    public DashboardViewModel(
            TourListViewModel tourListViewModel,
            TourDetailsViewModel tourDetailsViewModel,
            TourDialogViewModel tourDialogViewModel,
            LogsViewModel logsViewModel,
            LogDialogViewModel logDialogViewModel
    ) {
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.tourDialogViewModel = tourDialogViewModel;
        this.logsViewModel = logsViewModel;
        this.logDialogViewModel = logDialogViewModel;

        this.tourListViewModel.subscribeToSelection(this::selectTour);
        this.tourDialogViewModel.subscribeToTourCreation(this::createTour);
        this.tourDialogViewModel.subscribeToTourUpdate(this::updateTour);
        this.tourListViewModel.subscribeToCreateTourClicked(this::resetCreationDialog);
        this.logDialogViewModel.subscribeToLogCreation(this::createLog);
        this.logDialogViewModel.subscribeToLogUpdate(this::updateLog);
        this.logsViewModel.subscribeToLogDialogOpened(this::selectLog);
    }

    private void selectLog(Log log) {
        logDialogViewModel.setLog(log);
    }

    private void createLog(Log log) {
        logsViewModel.createLog(log);
    }

    private void updateLog(Log log) {
        logsViewModel.updateLog(log);
    }

    private void resetCreationDialog(Boolean createTourClicked) {
        if (Boolean.TRUE.equals(createTourClicked)) {
            tourDialogViewModel.clearTour();
        }
    }

    private void selectTour(Tour tour) {
        if (null != tour) {
            tourDetailsViewModel.setTour(tour);
            logsViewModel.setSelectedTour(tour);
            logsViewModel.setLogsList(tour.getLogs());
            tourDialogViewModel.setTour(tour);
        }
    }

    private void createTour(Tour tour) {
        tourListViewModel.createTour(tour);
    }

    private void updateTour(Tour tour) {
        tourListViewModel.updateTour(tour);
    }
}
