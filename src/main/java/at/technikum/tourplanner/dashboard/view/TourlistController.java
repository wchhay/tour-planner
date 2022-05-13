package at.technikum.tourplanner.dashboard.view;


import at.technikum.tourplanner.dashboard.component.TourlistitemCell;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.service.TourDialogService;
import at.technikum.tourplanner.dashboard.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TourlistController {

    @FXML
    ListView<Tour> tourList;

    private final TourListViewModel tourListViewModel;

    private final TourDialogService tourDialogService;

    public TourlistController(TourListViewModel tourListViewModel, TourDialogService tourDialogService) {
        this.tourListViewModel = tourListViewModel;
        this.tourDialogService = tourDialogService;
    }

    @FXML
    void initialize() {
        tourList.setItems(tourListViewModel.getTourList());
        tourList.setCellFactory(param -> new TourlistitemCell());
        tourList.getSelectionModel().selectedItemProperty().addListener(tourListViewModel.getChangeListener());
    }

    public void addTour() {
        tourDialogService.openDialog();
    }
}
