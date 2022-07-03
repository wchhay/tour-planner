package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.MenuBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;

public class MenuBarController {

    private final MenuBarViewModel menuBarViewModel;

    @FXML
    RadioMenuItem darkModeMenuItem;

    @FXML
    MenuBar menuBar;

    public MenuBarController(MenuBarViewModel menuBarViewModel) {
        this.menuBarViewModel = menuBarViewModel;
    }

    @FXML
    void initialize() {
        darkModeMenuItem.selectedProperty().addListener((observable, oldValue, selected) -> {
            if (Boolean.TRUE.equals(selected)) {
                menuBarViewModel.enableDarkTheme(menuBar.getScene());
            } else {
                menuBarViewModel.disableDarkTheme(menuBar.getScene());
            }
        });
    }

    // Import File
    public void showImportDialog() {
        menuBarViewModel.openFileImportDialog();
    }

    // Export File
    public void showExportDialog() {
        menuBarViewModel.openFileExportDialog();
    }

    public void generateSummarizeReport() {
        menuBarViewModel.generateSummarizeReport();
    }
}
