module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    exports at.technikum.tourplanner;
    opens at.technikum.tourplanner to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.viewmodel;
    opens at.technikum.tourplanner.dashboard.viewmodel to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.view;
    opens at.technikum.tourplanner.dashboard.view to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.model;
    opens at.technikum.tourplanner.dashboard.model to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.component;
    opens at.technikum.tourplanner.dashboard.component to javafx.fxml;

    exports at.technikum.tourplanner.dashboard.service;
    opens at.technikum.tourplanner.dashboard.service to javafx.fxml;
    exports at.technikum.tourplanner.dashboard.rest;
    opens at.technikum.tourplanner.dashboard.rest to javafx.fxml;
    exports at.technikum.tourplanner.observer;
    opens at.technikum.tourplanner.observer to javafx.fxml;

}