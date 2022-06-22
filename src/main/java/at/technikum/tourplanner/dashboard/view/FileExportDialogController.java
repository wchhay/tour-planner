package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.FileExportDialogViewModel;

public class FileExportDialogController {

    private final FileExportDialogViewModel fileExportDialogViewModel;

    public FileExportDialogController(FileExportDialogViewModel fileExportDialogViewModel) {
        this.fileExportDialogViewModel = fileExportDialogViewModel;
    }

    public void export() {
        fileExportDialogViewModel.exportToFile();
    }
}
