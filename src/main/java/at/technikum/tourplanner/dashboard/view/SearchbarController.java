package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.SearchbarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchbarController {

    @FXML
    TextField searchField;

    private final SearchbarViewModel searchbarViewModel;

    public SearchbarController(SearchbarViewModel searchbarViewModel) {
        this.searchbarViewModel = searchbarViewModel;
    }

    @FXML
    void initialize() {
        searchField.textProperty().bindBidirectional(searchbarViewModel.searchStringProperty());
    }

    public void search() {
        searchbarViewModel.search();
    }
}
