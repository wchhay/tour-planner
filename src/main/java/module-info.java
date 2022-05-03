module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.technikum.tourplanner to javafx.fxml;
    exports at.technikum.tourplanner;

    opens at.technikum.tourplanner.dashboard to javafx.fxml;
    exports at.technikum.tourplanner.dashboard;
}