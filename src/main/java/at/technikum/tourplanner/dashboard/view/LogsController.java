package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.LogsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;

public class LogsController {

    @FXML
    TableColumn<Log, LocalDateTime> logDate;

    @FXML
    TableColumn<Log, String> logComment;

    @FXML
    TableColumn<Log, Integer> logDifficulty;

    @FXML
    TableColumn<Log, Long> logTotalTime;

    @FXML
    TableColumn<Log, Integer> logRating;

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

        logTotalTime.setCellFactory(cell -> new TableCell<>() {
            @Override
            public void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty && null != item) {
                    setText(convertToTimeString(item));
                } else {
                    setText("");
                }
            }
        });

        logTotalTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        logRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        logTable.setItems(logsViewModel.getLogsList());
    }

    public void addTourLog() {
      logsViewModel.openLogDialog();
    }


}
