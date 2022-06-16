package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourCreationDialogController {

    @FXML
    Button createTourButton;

    private final TourDialogViewModel tourDialogViewModel;

    public TourCreationDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    @FXML
    void initialize() {
        createTourButton.disableProperty().bind(tourDialogViewModel.validUserInputProperty().not());
    }

    public void onCreateTourClicked() {
        tourDialogViewModel.createTour();
        tourDialogViewModel.closeDialog();
    }
}
