package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.util.TimeConverterUtil;
import javafx.beans.property.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static at.technikum.tourplanner.util.TimeConverterUtil.parseTime;

public class LogDialogViewModel {

    public static final String INITIAL_TIME_VALUE = "00:00:00";

    private UUID selectedLogId;

    private final StringProperty time = new SimpleStringProperty(INITIAL_TIME_VALUE);
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty totalTime = new SimpleStringProperty(INITIAL_TIME_VALUE);
    private final ObjectProperty<Integer> difficulty = new SimpleObjectProperty<>(1);
    private final ObjectProperty<Integer> rating = new SimpleObjectProperty<>(1);
    private final StringProperty comment = new SimpleStringProperty();

    private final Observable<Log> logCreationObservable = new Observable<>();
    private final Observable<Log> logUpdateObservable = new Observable<>();

    private final TourDialogService tourDialogService;

    public LogDialogViewModel(TourDialogService tourDialogService) {
        this.tourDialogService = tourDialogService;
    }

    public void subscribeToLogCreation(Listener<Log> listener) {
        logCreationObservable.subscribe(listener);
    }

    public void unsubscribeFromLogCreation(Listener<Log> listener) {
        logCreationObservable.unsubscribe(listener);
    }

    public void subscribeToLogUpdate(Listener<Log> listener) {
        logUpdateObservable.subscribe(listener);
    }

    public void unsubscribeFromLogUpdate(Listener<Log> listener) {
        logUpdateObservable.unsubscribe(listener);
    }

    public void createLog() {
        Log log = buildLog();
        logCreationObservable.notifyListeners(log);
    }

    public void updateLog() {
        Log log = buildLog();
        logUpdateObservable.notifyListeners(log);
    }

    public void closeDialog() {
        tourDialogService.closeDialog();
    }

    public void setLog(Log log) {
        if (null != log) {
            selectedLogId = log.getId();
            time.setValue(log.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            date.setValue(log.getDate().toLocalDate());
            totalTime.setValue(TimeConverterUtil.convertToTimeString(log.getTotalTime()));
            difficulty.setValue(log.getDifficulty());
            rating.setValue(log.getRating());
            comment.setValue(log.getComment());
        } else {
            clearLog();
        }
    }

    private void clearLog() {
        selectedLogId = null;
        time.setValue(INITIAL_TIME_VALUE);
        date.setValue(LocalDate.now());
        totalTime.setValue(INITIAL_TIME_VALUE);
        difficulty.setValue(1);
        rating.setValue(1);
        comment.setValue("");
    }

    private Log buildLog() {
        return Log.builder()
                .id(selectedLogId)
                .date(LocalDateTime.of(date.get(), LocalTime.parse(time.get())))
                .totalTime(parseTime(totalTime.get()))
                .difficulty(difficulty.get())
                .rating(rating.get())
                .comment(comment.get())
                .build();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public StringProperty totalTimeProperty() {
        return totalTime;
    }

    public ObjectProperty<Integer> difficultyProperty() {
        return difficulty;
    }

    public ObjectProperty<Integer> ratingProperty() {
        return rating;
    }

    public StringProperty commentProperty() {
        return comment;
    }
}
