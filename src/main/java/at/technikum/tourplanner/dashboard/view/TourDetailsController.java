package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class TourDetailsController {

    @FXML
    TextField title;

    @FXML
    TextField from;

    @FXML
    TextField to;

    @FXML
    TextField transportType;

    @FXML
    TextArea description;

    @FXML
    TextField distance;

    @FXML
    TextField time;

    @FXML
    ImageView tourMapImage;

    @FXML
    Button editButton;

    private final TourDetailsViewModel tourDetailsViewModel;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    @FXML
    void initialize() {
        title.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        from.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        to.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        description.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        transportType.textProperty().bindBidirectional(tourDetailsViewModel.transportTypeProperty());

        tourMapImage.imageProperty().bind(tourDetailsViewModel.tourMapImageProperty());
        distance.textProperty().bind(tourDetailsViewModel.distanceProperty());
        time.textProperty().bind(tourDetailsViewModel.timeProperty());

        editButton.disableProperty().bind(tourDetailsViewModel.idProperty().isNull());
    }

    public void editTour() {
        tourDetailsViewModel.editTour();
    }
}
