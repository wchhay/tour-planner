package at.technikum.tourplanner;

import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load(
                TourPlannerApplication.class.getResource("dashboard/view/dashboard-view.fxml")
        );
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}