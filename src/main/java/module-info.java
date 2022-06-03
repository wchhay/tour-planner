open module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires retrofit2;
    requires retrofit2.converter.jackson;
    exports at.technikum.tourplanner;
}