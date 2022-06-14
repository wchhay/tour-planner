package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.view.TourDialogController;
import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TourDialogService {

    private Stage stage;

    public void openCreationDialog() {
        openDialog("Create new Tour", "tour-creation-dialog-view.fxml");
    }

    public void openUpdateDialog() {
        openDialog("Update Tour", "tour-update-dialog-view.fxml");
    }

    public void openLogDialog() {
        openDialog("Create Tour log", "log-creation-dialog-view.fxml");
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
            e.printStackTrace();
        }
    }

    public void closeDialog() {
        if (null != stage) {
            stage.close();
        }
    }
}
