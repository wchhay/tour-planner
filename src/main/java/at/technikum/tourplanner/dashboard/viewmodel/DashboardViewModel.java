package at.technikum.tourplanner.dashboard.viewmodel;


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

        this.tourListViewModel.subscribeToSelection(tourDetailsViewModel::setTour);
        this.tourListViewModel.subscribeToSelection(logsViewModel::setSelectedTour);
        this.tourListViewModel.subscribeToSelection(tourDialogViewModel::setTour);

        this.tourDialogViewModel.subscribeToTourCreation(tourListViewModel::createTour);
        this.tourDialogViewModel.subscribeToTourUpdate(tourListViewModel::updateTour);
        this.tourListViewModel.subscribeToCreateTourClicked(this::resetCreationDialog);
        this.tourDetailsViewModel.subscribeToTourEditClicked(this::openEditDialog);
        this.logDialogViewModel.subscribeToLogCreation(logsViewModel::createLog);
        this.logDialogViewModel.subscribeToLogUpdate(logsViewModel::updateLog);
        this.logsViewModel.subscribeToLogDialogOpened(logDialogViewModel::setLog);
    }

    private void openEditDialog(Boolean editTourClicked) {
        if (Boolean.TRUE.equals(editTourClicked)) {
            tourListViewModel.openUpdateDialog();
        }
    }

    private void resetCreationDialog(Boolean createTourClicked) {
        if (Boolean.TRUE.equals(createTourClicked)) {
            tourDialogViewModel.clearTour();
        }
    }
}
