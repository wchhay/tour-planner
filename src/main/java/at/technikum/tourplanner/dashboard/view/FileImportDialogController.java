package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.FileImportDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FileImportDialogController {

    @FXML
    Button confirmButton;

    @FXML
    TextArea textAreaImport;

    private final FileImportDialogViewModel fileImportDialogViewModel;

    @FXML
    void initialize() {
        textAreaImport.textProperty().bind(fileImportDialogViewModel.fileImportContentProperty());
        confirmButton.disableProperty().bind(fileImportDialogViewModel.fileImportContentProperty().isEmpty());
    }

    public FileImportDialogController(FileImportDialogViewModel fileImportDialogViewModel) {
        this.fileImportDialogViewModel = fileImportDialogViewModel;
    }

    public void importFile() {
        fileImportDialogViewModel.importFile();
    }

    public void confirmImport() {
        fileImportDialogViewModel.confirmImport();
    }
}
