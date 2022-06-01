package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TourDetailsController {

    @FXML
    TextField title;

    @FXML
    TextArea from;

    @FXML
    TextArea to;

    @FXML
    TextArea description;

    @FXML
    TextArea transportType;

    @FXML
    TextArea distance;

    @FXML
    TextArea time;

    @FXML
    Button editButton;

    @FXML
    Button saveButton;

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

        distance.textProperty().bind(tourDetailsViewModel.distanceProperty());
        time.textProperty().bind(tourDetailsViewModel.timeProperty());
    }

    public void onEdit() {
        changeEditableState(true);
    }


    public void onSave() {
        changeEditableState(false);

        tourDetailsViewModel.updateTour();
    }

    private void changeEditableState(boolean editable) {
        editButton.setVisible(!editable);
        saveButton.setVisible(editable);

        title.setEditable(editable);
        from.setEditable(editable);
        to.setEditable(editable);
        description.setEditable(editable);
        transportType.setEditable(editable);
    }
}
