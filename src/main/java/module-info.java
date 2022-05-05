module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.technikum.tourplanner to javafx.fxml;
    exports at.technikum.tourplanner;

    exports at.technikum.tourplanner.dashboard.viewmodel;
    opens at.technikum.tourplanner.dashboard.viewmodel to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.view;
    opens at.technikum.tourplanner.dashboard.view to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.model;
    opens at.technikum.tourplanner.dashboard.model to javafx.fxml;
}