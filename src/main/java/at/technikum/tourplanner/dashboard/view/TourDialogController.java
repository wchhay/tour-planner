package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TourDialogController {

    @FXML
    TextField tourName;

    @FXML
    TextField tourFrom;

    @FXML
    TextField tourTo;

    @FXML
    TextField tourDescription;

    @FXML
    TextField tourTransportType;

    private final TourDialogViewModel tourDialogViewModel;

    private final TourDialogService tourDialogService;

    public TourDialogController(TourDialogViewModel tourDialogViewModel, TourDialogService tourDialogService) {
        this.tourDialogViewModel = tourDialogViewModel;
        this.tourDialogService = tourDialogService;
    }

    @FXML
    void initialize() {
        tourName.textProperty().bindBidirectional(tourDialogViewModel.tourNameProperty());
        tourFrom.textProperty().bindBidirectional(tourDialogViewModel.tourFromProperty());
        tourTo.textProperty().bindBidirectional(tourDialogViewModel.tourToProperty());
        tourDescription.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourTransportType.textProperty().bindBidirectional(tourDialogViewModel.tourTransportTypeProperty());
    }

    public void onCreate() {
        tourDialogViewModel.validateAndBuildTour();
        tourDialogService.closeDialog();
    }
}
