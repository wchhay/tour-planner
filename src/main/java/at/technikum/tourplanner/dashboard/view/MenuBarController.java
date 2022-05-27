package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.injection.FXMLDependencyInjection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MenuBarController {
    FileChooser fileChooser = new FileChooser();

    @FXML
    public TextArea textArea;

    private Stage stage;

    @FXML
    void initialize() {
        // the path needs to be changed separately
        fileChooser.setInitialDirectory(new File("C:\\Users\\jonia\\tour-planner\\src\\main\\java\\files"));
    }

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
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                textArea.appendText(scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

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

    public void showReportDialog() {
    }

    public void showSummarizeDialog() {
    }
}
