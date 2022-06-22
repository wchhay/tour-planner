package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.dashboard.viewmodel.validation.Validator;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.util.TimeConverterUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static at.technikum.tourplanner.util.TimeConverterUtil.parseTime;

public class LogDialogViewModel {

    public static final String INITIAL_TIME_VALUE = "00:00:00";
    public static final int LOWER_BOUND = 1;
    public static final int UPPER_BOUND = 5;

    private UUID selectedLogId;

    private final StringProperty time = new SimpleStringProperty(INITIAL_TIME_VALUE);
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty totalTime = new SimpleStringProperty(INITIAL_TIME_VALUE);
    private final ObjectProperty<Integer> difficulty = new SimpleObjectProperty<>(LOWER_BOUND);
    private final ObjectProperty<Integer> rating = new SimpleObjectProperty<>(LOWER_BOUND);
    private final StringProperty comment = new SimpleStringProperty();

    private final BooleanProperty isValidTime = new SimpleBooleanProperty(true);
    private final BooleanProperty isValidDate = new SimpleBooleanProperty(true);
    private final BooleanProperty isValidTotalTime = new SimpleBooleanProperty(true);
    private final BooleanProperty isValidDifficulty = new SimpleBooleanProperty(true);
    private final BooleanProperty isValidRating = new SimpleBooleanProperty(true);

    private final Validator validator;

    private final Observable<Log> logCreationObservable = new Observable<>();
    private final Observable<Log> logUpdateObservable = new Observable<>();

    private final DialogService dialogService;

    public LogDialogViewModel(DialogService dialogService, Validator validator) {
        this.dialogService = dialogService;
        this.validator = validator;

        setUpValidations();
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
        dialogService.closeDialog();
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

    public BooleanBinding validLogUserInputBinding() {
        return isValidTime
                .and(isValidDate)
                .and(isValidTotalTime)
                .and(isValidDifficulty)
                .and(isValidRating);
    }

    public void setUpValidations() {
        time.addListener((observable, oldValue, newValue) ->
                isValidTime.set(validator.isValidTimeString(newValue)));

        totalTime.addListener((observable, oldValue, newValue) ->
                isValidTotalTime.set(validator.isValidTimeString(newValue)));

        date.addListener((observable, oldValue, newValue) ->
                isValidDate.set(validator.isValidDate(newValue)));

        difficulty.addListener((observable, oldValue, newValue) ->
                isValidDifficulty.set(validator.isIntegerInRange(newValue, LOWER_BOUND, UPPER_BOUND)));

        rating.addListener((observable, oldValue, newValue) ->
                isValidRating.set(validator.isIntegerInRange(newValue, LOWER_BOUND, UPPER_BOUND)));
    }

    private void clearLog() {
        selectedLogId = null;
        time.setValue(INITIAL_TIME_VALUE);
        date.setValue(LocalDate.now());
        totalTime.setValue(INITIAL_TIME_VALUE);
        difficulty.setValue(LOWER_BOUND);
        rating.setValue(LOWER_BOUND);
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
