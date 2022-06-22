package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.service.dialog.DialogService;

public class MenuBarViewModel {

    private final DialogService dialogService;

    public MenuBarViewModel(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    public void openFileImportDialog() {
        dialogService.openFileImportDialog();
    }

    public void openFileExportDialog() {
        dialogService.openFileExportDialog();
    }
}
