package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.LogDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.DateTimeStringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class LogDialogController {

    @FXML
    TextField logTime;
    @FXML
    DatePicker logDatePicker;
    @FXML
    TextField logTotalTime;
    @FXML
    Spinner<Integer> logDifficulty;
    @FXML
    Spinner<Integer> logRating;
    @FXML
    TextArea logComment;

    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private final LogDialogViewModel logDialogViewModel;

    public LogDialogController(LogDialogViewModel logDialogViewModel) {
        this.logDialogViewModel = logDialogViewModel;
    }

    @FXML
    void initialize() {
        disableFutureDates();
        setTimeFormat(logTime);
        setTimeFormat(logTotalTime);

        setUpBindings();
    }

    private void setUpBindings() {
        logTime.textProperty().bindBidirectional(logDialogViewModel.timeProperty());
        logDatePicker.valueProperty().bindBidirectional(logDialogViewModel.dateProperty());
        logTotalTime.textProperty().bindBidirectional(logDialogViewModel.totalTimeProperty());
        logDifficulty.getValueFactory().valueProperty().bindBidirectional(logDialogViewModel.difficultyProperty());
        logRating.getValueFactory().valueProperty().bindBidirectional(logDialogViewModel.ratingProperty());
        logComment.textProperty().bindBidirectional(logDialogViewModel.commentProperty());
    }

    private void disableFutureDates() {
        logDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    private void setTimeFormat(TextField textField) {
        try {
            textField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(timeFormat), timeFormat.parse("00:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
