package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TourDialogController {

    @FXML
    TextField tourName;

    @FXML
    TextField tourRoute;

    @FXML
    TextField tourDescription;

    private final TourDialogViewModel tourDialogViewModel;

    public TourDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    @FXML
    void initialize() {
        tourName.textProperty().bindBidirectional(tourDialogViewModel.tourNameProperty());
        tourRoute.textProperty().bindBidirectional(tourDialogViewModel.tourRouteProperty());
        tourDescription.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
    }

    public void onCreate() {
        tourDialogViewModel.validateAndBuildTour();
    }
}
