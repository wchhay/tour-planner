package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.TourDialogService;
import javafx.beans.property.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LogCreationDialogViewModel {

    private final StringProperty time = new SimpleStringProperty("00:00:00");
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty totalTime = new SimpleStringProperty("00:00:00");
    private final ObjectProperty<Integer> difficulty = new SimpleObjectProperty<>(1);
    private final ObjectProperty<Integer> rating = new SimpleObjectProperty<>(1);
    private final StringProperty comment = new SimpleStringProperty();

    private final Observable<Log> logCreationObservable = new Observable<>();
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private final TourDialogService tourDialogService;

    public LogCreationDialogViewModel(TourDialogService tourDialogService) {
        this.tourDialogService = tourDialogService;
    }

    public void subscribeToLogCreation(Listener<Log> listener) {
        logCreationObservable.subscribe(listener);
    }

    public void unsubscribeFromLogCreation(Listener<Log> listener) {
        logCreationObservable.unsubscribe(listener);
    }

    public void createLog() {
        Log log = buildLog();
        logCreationObservable.notifyListeners(log);
    }

    public void closeDialog() {
        tourDialogService.closeDialog();
    }

    private Log buildLog() {
        return Log.builder()
                .date(LocalDateTime.of(date.get(), LocalTime.parse(time.get())))
                .totalTime(parseTime(totalTime.get()))
                .difficulty(difficulty.get())
                .rating(rating.get())
                .comment(comment.get())
                .build();
    }

    private Long parseTime(String timeString) {
        try {
            return timeFormat.parse(timeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public DateFormat getTimeFormat() {
        return timeFormat;
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
