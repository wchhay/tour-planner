package at.technikum.tourplanner.service.file;

import javafx.stage.FileChooser;

public class JsonFileChooser {

    private static FileChooser fileChooser;

    private JsonFileChooser() {
    }

    public static FileChooser create() {
        if (null == fileChooser) {
            fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extensionFilter);
        }
        return fileChooser;
    }
}
