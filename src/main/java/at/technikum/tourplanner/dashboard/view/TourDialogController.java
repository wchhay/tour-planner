package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.service.TourDialogService;
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

    @FXML
    TextField tourDistance;

    @FXML
    TextField tourTime;

    @FXML
    TextField tourInfo;

    private final TourDialogViewModel tourDialogViewModel;

    private final TourDialogService tourDialogService;

    public TourDialogController(TourDialogViewModel tourDialogViewModel, TourDialogService tourDialogService) {
        this.tourDialogViewModel = tourDialogViewModel;
        this.tourDialogService = tourDialogService;
    }

    @FXML
    void initialize() {
        tourName.textProperty().bindBidirectional(tourDialogViewModel.tourNameProperty());
        tourFrom.textProperty().bindBidirectional(tourDialogViewModel.tourRouteProperty());
        tourTo.textProperty().bindBidirectional(tourDialogViewModel.tourRouteProperty());
        tourDescription.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourTransportType.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourDistance.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourTime.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourInfo.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
    }

    public void onCreate() {
        tourDialogViewModel.validateAndBuildTour();
        tourDialogService.closeDialog();
    }
}
