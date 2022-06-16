package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourUpdateDialogController {

    @FXML
    Button updateTourButton;

    private final TourDialogViewModel tourDialogViewModel;

    public TourUpdateDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    @FXML
    void initialize() {
        updateTourButton.disableProperty().bind(tourDialogViewModel.validUserInputProperty().not());
    }

    public void onUpdateTourClicked() {
        tourDialogViewModel.updateTour();
        tourDialogViewModel.closeDialog();
    }
}
