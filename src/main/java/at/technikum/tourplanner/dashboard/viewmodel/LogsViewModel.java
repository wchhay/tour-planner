package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogsViewModel {

    ObservableList<Log> logsList = FXCollections.observableArrayList();

    public void addTourLog() {
        Log log = new Log(UUID.randomUUID(), LocalDateTime.now(), 0L, 0, 0, "");
        logsList.add(log);
    }

    public ObservableList<Log> getLogsList() {
        return logsList;
    }
}
