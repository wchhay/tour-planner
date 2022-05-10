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
    TableColumn<Log, Long> logDuration;

    @FXML
    TableColumn<Log, Long> logDistance;

    @FXML
    TableView<Log> logTable;

    private final LogsViewModel logsViewModel;

    public LogsController(LogsViewModel logsViewModel) {
        this.logsViewModel = logsViewModel;
    }

    @FXML
    void initialize() {
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        logDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        logDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        logTable.setItems(logsViewModel.getLogsList());
    }

    public void addTourLog() {
        logsViewModel.addTourLog();
    }
}
