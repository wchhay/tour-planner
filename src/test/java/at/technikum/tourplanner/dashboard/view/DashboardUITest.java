package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class DashboardUITest {

    @Start
    private void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load(
                getClass().getResource("dashboard-view.fxml")
        );
        stage.setScene(new Scene(root, 600 , 400));
        stage.show();
    }

    @Test
    void should_contain_listView(FxRobot robot) {
        ListView<?> tourList = robot.lookup("#tourList").queryListView();

        assertThat(tourList).isEmpty();
    }
}