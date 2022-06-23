open module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires retrofit2;
    requires retrofit2.converter.jackson;
    requires org.apache.logging.log4j;
    requires kernel;
    requires layout;
    requires io;
    exports at.technikum.tourplanner;
}