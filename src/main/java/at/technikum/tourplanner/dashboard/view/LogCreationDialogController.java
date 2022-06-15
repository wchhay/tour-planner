package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.LogDialogViewModel;

public class LogCreationDialogController {

    private final LogDialogViewModel logDialogViewModel;

    public LogCreationDialogController(LogDialogViewModel logDialogViewModel) {
        this.logDialogViewModel = logDialogViewModel;
    }

    public void onLogCreationClicked() {
        logDialogViewModel.createLog();
        logDialogViewModel.closeDialog();
    }
}
