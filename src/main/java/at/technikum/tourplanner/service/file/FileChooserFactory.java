package at.technikum.tourplanner.service.file;

import javafx.stage.FileChooser;


public class FileChooserFactory {

    private FileChooserFactory() {
    }

    public static FileChooser forJson() {
        return buildFileChooserWithExtensionFilter("JSON (*.json)", "*.json");
    }

    public static FileChooser forPdf() {
        return buildFileChooserWithExtensionFilter("PDF (*.pdf)", "*.pdf");
    }

    private static FileChooser buildFileChooserWithExtensionFilter(String description, String extension) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser;
    }
}
