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
    TextArea info;

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
        description.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
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
        distance.setEditable(editable);
        time.setEditable(editable);
        info.setEditable(editable);
    }
}
