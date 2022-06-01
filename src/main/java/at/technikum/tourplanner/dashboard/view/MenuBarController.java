package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MenuBarController {

    public static final String DIRNAME = "files";

    FileChooser fileChooser = new FileChooser();

    @FXML
    TextArea textAreaImport;

    @FXML
    TextArea textAreaExport;

    private Stage stage;

    @FXML
    void initialize() {
        // the path needs to be changed separately
        fileChooser.setInitialDirectory(new File(DIRNAME));
    }

    // Import File
    public void showImportDialog() {
        try {
            Parent parent = FXMLDependencyInjection.load(MenuBarController.class.getResource("fileImport-view.fxml"));

            Scene scene = new Scene(parent, 550, 250);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void getText(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(new Stage());
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()){
                textAreaImport.appendText(scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Export File
    public void showExportDialog() {
        try {
            Parent parent = FXMLDependencyInjection.load(MenuBarController.class.getResource("fileExport-view.fxml"));

            Scene scene = new Scene(parent, 550, 250);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void export() {
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            exportSystem(file,textAreaExport.getText());
        }
    }

    public void exportSystem(File file, String content){
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void showReportDialog() {
    }

    public void showSummarizeDialog() {
    }

}
