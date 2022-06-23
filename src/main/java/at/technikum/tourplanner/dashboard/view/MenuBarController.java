package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.MenuBarViewModel;

public class MenuBarController {

    private final MenuBarViewModel menuBarViewModel;

    public MenuBarController(MenuBarViewModel menuBarViewModel) {
        this.menuBarViewModel = menuBarViewModel;
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
