package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TourDetailsController {

    @FXML
    TextField title;

    @FXML
    TextArea route;

    @FXML
    TextArea description;

    private final TourDetailsViewModel tourDetailsViewModel;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    @FXML
    void initialize() {
        title.textProperty().bind(tourDetailsViewModel.nameProperty());
        description.textProperty().bind(tourDetailsViewModel.descriptionProperty());
    }
}
