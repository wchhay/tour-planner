package at.technikum.tourplanner.dashboard.view;


import at.technikum.tourplanner.dashboard.component.TourlistitemCell;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class TourlistController {

    @FXML
    ProgressBar loadingBar;

    @FXML
    Button addTourButton;

    @FXML
    ListView<Tour> tourList;

    private final TourListViewModel tourListViewModel;

    public TourlistController(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }

    @FXML
    void initialize() {
        tourList.setItems(tourListViewModel.getTourList());
        tourList.setCellFactory(param -> new TourlistitemCell(tourListViewModel));
        tourList.getSelectionModel().selectedItemProperty().addListener(tourListViewModel.getSelectionChangeListener());
        loadingBar.visibleProperty().bind(tourListViewModel.isLoadingProperty());

        fetchTours();
    }

    public void addTour() {
        tourListViewModel.openCreationDialog();
    }

    public void fetchTours() {
        tourListViewModel.fetchTours();
    }
}
