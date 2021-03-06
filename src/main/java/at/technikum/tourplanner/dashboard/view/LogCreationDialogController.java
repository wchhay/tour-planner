package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.LogDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LogCreationDialogController {

    @FXML
    Button createLogButton;

    private final LogDialogViewModel logDialogViewModel;

    public LogCreationDialogController(LogDialogViewModel logDialogViewModel) {
        this.logDialogViewModel = logDialogViewModel;
    }

    @FXML
    void initialize() {
        createLogButton.disableProperty().bind(logDialogViewModel.validLogUserInputBinding().not());
    }

    public void onLogCreationClicked() {
        logDialogViewModel.createLog();
        logDialogViewModel.closeDialog();
    }
}
