package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    ComboBox<TransportType> tourTransportType;

    private final TourDialogViewModel tourDialogViewModel;

    public TourDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    @FXML
    void initialize() {
        tourTransportType.setItems(FXCollections.observableArrayList(TransportType.values()));

        tourName.textProperty().bindBidirectional(tourDialogViewModel.tourNameProperty());
        tourFrom.textProperty().bindBidirectional(tourDialogViewModel.tourFromProperty());
        tourTo.textProperty().bindBidirectional(tourDialogViewModel.tourToProperty());
        tourDescription.textProperty().bindBidirectional(tourDialogViewModel.tourDescriptionProperty());
        tourTransportType.valueProperty().bindBidirectional(tourDialogViewModel.tourTransportTypeProperty());
    }
}
