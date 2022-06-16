package at.technikum.tourplanner.dashboard.view;


import at.technikum.tourplanner.dashboard.viewmodel.LogDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LogUpdateDialogController {

    @FXML
    Button logUpdateButton;

    private final LogDialogViewModel logDialogViewModel;

    public LogUpdateDialogController(LogDialogViewModel logDialogViewModel) {
        this.logDialogViewModel = logDialogViewModel;
    }

    @FXML
    void initialize() {
        logUpdateButton.disableProperty().bind(logDialogViewModel.validLogUserInputBinding().not());
    }

    public void onLogUpdateClicked() {
        logDialogViewModel.updateLog();
        logDialogViewModel.closeDialog();
    }
}
