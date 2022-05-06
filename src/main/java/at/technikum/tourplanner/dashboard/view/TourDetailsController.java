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
    TextArea route;

    @FXML
    TextArea description;

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
        route.setEditable(editable);
        description.setEditable(editable);
    }
}
