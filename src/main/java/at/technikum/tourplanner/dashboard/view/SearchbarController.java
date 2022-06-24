package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.SearchbarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchbarController {

    @FXML
    TextField searchField;

    @FXML
    Button searchButton;

    private final SearchbarViewModel searchbarViewModel;

    public SearchbarController(SearchbarViewModel searchbarViewModel) {
        this.searchbarViewModel = searchbarViewModel;
    }

    @FXML
    void initialize() {
        searchField.textProperty().bindBidirectional(searchbarViewModel.searchStringProperty());
        searchButton.disableProperty().bind(searchbarViewModel.searchDisabledBinding());
    }

    public void search() {
        searchbarViewModel.search();
    }

    public void cancelSearch() {
        searchbarViewModel.cancelSearch();
    }
}
