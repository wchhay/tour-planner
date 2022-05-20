package at.technikum.tourplanner.dashboard.service;

import at.technikum.tourplanner.dashboard.view.TourDialogController;
import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TourDialogService {

    private Stage stage;

    public void openDialog() {
        try {
            Parent parent = FXMLDependencyInjection.load(TourDialogController.class.getResource("tour-dialog-view.fxml"));

            Scene scene = new Scene(parent, 550, 250);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
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
