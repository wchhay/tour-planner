package at.technikum.tourplanner.service.dialog;

public interface DialogService {
    void openCreationDialog();

    void openUpdateDialog();

    void openLogCreationDialog();

    void openLogUpdateDialog();

    void closeDialog();

    void openFileImportDialog();

    void openFileExportDialog();
}
