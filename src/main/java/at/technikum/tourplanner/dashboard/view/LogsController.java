package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.LogsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class LogsController {

    @FXML
    TableColumn<Log, LocalDateTime> logDate;

    @FXML
    TableColumn<Log, Long> logComment;

    @FXML
    TableColumn<Log, Long> logDifficulty;

    @FXML
    TableColumn<Log, Long> logTotalTime;

    @FXML
    TableColumn<Log, Long> logRating;

    @FXML
    TableView<Log> logTable;

    private final LogsViewModel logsViewModel;

    public LogsController(LogsViewModel logsViewModel) {
        this.logsViewModel = logsViewModel;
    }

    @FXML
    void initialize() {
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        logComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        logDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        logTotalTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        logRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        logTable.setItems(logsViewModel.getLogsList());
    }

    public void addTourLog() {
        logsViewModel.addTourLog();
    }
}
