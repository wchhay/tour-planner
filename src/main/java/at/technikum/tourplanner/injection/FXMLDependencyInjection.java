package at.technikum.tourplanner.injection;


import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class FXMLDependencyInjection {

    private FXMLDependencyInjection() {}

    public static Parent load(URL location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    private static FXMLLoader getLoader(URL location) {
        return new FXMLLoader(
                location,
                null,
                new JavaFXBuilderFactory(),
                controllerClass -> ControllerFactory.getInstance().create(controllerClass)
                );
    }
}
