package at.technikum.tourplanner.tourlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TourlistController {

    @FXML
    ListView<String> tourList;

    @FXML
    public void initialize() {
        ObservableList<String> listItems = FXCollections.observableArrayList("Tour 1", "Tour 2", "Tour 3");
        tourList.setItems(listItems);
    }
}
