package at.technikum.tourplanner.dashboard.view;


import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TourlistController {

    @FXML
    ListView<Tour> tourList;

    private final TourViewModel tourViewModel;

    public TourlistController(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
    }

    @FXML
    void initialize() {
        tourList.setItems(tourViewModel.getTourList());
        tourList.setCellFactory(param -> new TourlistitemCell());
        tourList.getSelectionModel().selectedItemProperty().addListener(tourViewModel.getChangeListener());
    }

    public void addTour() {
        tourViewModel.addTour();
    }
}
