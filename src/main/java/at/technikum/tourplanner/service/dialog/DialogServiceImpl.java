package at.technikum.tourplanner.service.dialog;

import at.technikum.tourplanner.dashboard.view.TourDialogController;
import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class DialogServiceImpl implements DialogService {

    private Stage stage;

    @Override
    public void openCreationDialog() {
        openDialog("Create new Tour", "tour-creation-dialog-view.fxml");
    }

    @Override
    public void openUpdateDialog() {
        openDialog("Update Tour", "tour-update-dialog-view.fxml");
    }

    @Override
    public void openLogCreationDialog() {
        openDialog("Create Tour log", "log-creation-dialog-view.fxml");
    }

    @Override
    public void openLogUpdateDialog() {
        openDialog("Update Tour log", "log-update-dialog-view.fxml");
    }

    @Override
    public void openFileImportDialog() {
        openDialog("Import Tour data", "file-import-dialog-view.fxml");
    }

    @Override
    public void openFileExportDialog() {
        openDialog("Export Tour data", "file-export-dialog-view.fxml");
    }

    private void openDialog(String title, String fxmlFile) {
        try {
            Parent parent = FXMLDependencyInjection.load(TourDialogController.class.getResource(fxmlFile));

            Scene scene = new Scene(parent, 550, 250);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait();
        } catch (IOException e) {
            logger.error("Failed to load fxmlFile={}", fxmlFile);
        }
    }

    @Override
    public void closeDialog() {
        if (null != stage) {
            stage.close();
        }
    }
}
