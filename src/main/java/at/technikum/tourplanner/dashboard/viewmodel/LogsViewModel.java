package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LogsViewModel {

    ObservableList<Log> logsList = FXCollections.observableArrayList();

    public void setLogsList(List<Log> logs) {
        logsList.clear();
        if (null != logs) {
            logsList.setAll(logs);
        }
    }

    public ObservableList<Log> getLogsList() {
        return logsList;
    }
}
