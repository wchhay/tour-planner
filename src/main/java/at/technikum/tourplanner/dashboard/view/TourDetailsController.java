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
    TextField popularity;

    @FXML
    TextField childFriendliness;

    @FXML
    Button editButton;

    @FXML
    Button pdfReportButton;

    private final TourDetailsViewModel tourDetailsViewModel;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    @FXML
    void initialize() {
        title.textProperty().bind(tourDetailsViewModel.nameProperty());
        from.textProperty().bind(tourDetailsViewModel.fromProperty());
        to.textProperty().bind(tourDetailsViewModel.toProperty());
        description.textProperty().bind(tourDetailsViewModel.descriptionProperty());
        transportType.textProperty().bind(tourDetailsViewModel.transportTypeProperty());

        tourMapImage.imageProperty().bind(tourDetailsViewModel.tourMapImageProperty());
        distance.textProperty().bind(tourDetailsViewModel.distanceProperty());
        time.textProperty().bind(tourDetailsViewModel.timeProperty());
        popularity.textProperty().bind(tourDetailsViewModel.popularityProperty());
        childFriendliness.textProperty().bind(tourDetailsViewModel.childFriendlinessProperty());

        editButton.disableProperty().bind(tourDetailsViewModel.selectedTourProperty().isNull());
        pdfReportButton.disableProperty().bind(tourDetailsViewModel.pdfReportDisabled());
    }

    public void editTour() {
        tourDetailsViewModel.editTour();
    }

    public void generateReport() {
        tourDetailsViewModel.generateTourReport();
    }
}
