package at.technikum.tourplanner.dashboard.view;


import at.technikum.tourplanner.dashboard.viewmodel.LogDialogViewModel;

public class LogUpdateDialogController {

    private final LogDialogViewModel logDialogViewModel;

    public LogUpdateDialogController(LogDialogViewModel logDialogViewModel) {
        this.logDialogViewModel = logDialogViewModel;
    }

    public void onLogUpdateClicked() {
        logDialogViewModel.updateLog();
        logDialogViewModel.closeDialog();
    }
}
