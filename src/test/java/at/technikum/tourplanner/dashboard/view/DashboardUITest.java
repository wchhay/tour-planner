package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class DashboardUITest {

    public static final String TOUR_NAME = "Test: Vienna-Graz";
    public static final String TOUR_FROM = "Vienna";
    public static final String TOUR_TO = "Graz";

    @Start
    private void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load(
                getClass().getResource("dashboard-view.fxml")
        );
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    @Test
    @Timeout(value = 15)
    void should_create_tour(FxRobot robot) throws TimeoutException {
        String tourDescription = RandomStringUtils.randomAlphabetic(10);

        robot.clickOn("#addTourButton");

        robot.clickOn("#tourName").write(TOUR_NAME);
        robot.clickOn("#tourFrom").write(TOUR_FROM);
        robot.clickOn("#tourTo").write(TOUR_TO);
        robot.clickOn("#tourDescription").write(tourDescription);

        robot.clickOn("#createTourButton");

        ListView<Tour> tourList = robot.lookup("#tourList").queryListView();
        int oldListSize = tourList.getItems().size();

        WaitForAsyncUtils.waitFor(15, TimeUnit.SECONDS, () -> oldListSize < tourList.getItems().size());

        Tour lastTour = getLastItem(tourList.getItems());

        assertThat(lastTour.getName()).isEqualTo(TOUR_NAME);
        assertThat(lastTour.getFrom()).isEqualTo(TOUR_FROM);
        assertThat(lastTour.getTo()).isEqualTo(TOUR_TO);
        assertThat(lastTour.getDescription()).isEqualTo(tourDescription);
    }

    private <T> T getLastItem(List<T> list) {
        return list.get(list.size()-1);
    }
}