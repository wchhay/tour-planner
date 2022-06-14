package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.LogCreationDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.DateTimeStringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;

public class LogCreationDialogController {

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

    private final LogCreationDialogViewModel logCreationDialogViewModel;

    public LogCreationDialogController(LogCreationDialogViewModel logCreationDialogViewModel) {
        this.logCreationDialogViewModel = logCreationDialogViewModel;
    }

    @FXML
    void initialize() {
        disableFutureDates();
        setTimeFormat(logTime);
        setTimeFormat(logTotalTime);

        setUpBindings();
    }

    public void onLogCreationClicked() {
        logCreationDialogViewModel.createLog();
        logCreationDialogViewModel.closeDialog();
    }

    private void setUpBindings() {
        logTime.textProperty().bindBidirectional(logCreationDialogViewModel.timeProperty());
        logDatePicker.valueProperty().bindBidirectional(logCreationDialogViewModel.dateProperty());
        logTotalTime.textProperty().bindBidirectional(logCreationDialogViewModel.totalTimeProperty());
        logDifficulty.getValueFactory().valueProperty().bindBidirectional(logCreationDialogViewModel.difficultyProperty());
        logRating.getValueFactory().valueProperty().bindBidirectional(logCreationDialogViewModel.ratingProperty());
        logComment.textProperty().bindBidirectional(logCreationDialogViewModel.commentProperty());
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
            DateFormat timeFormat = logCreationDialogViewModel.getTimeFormat();
            textField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(timeFormat), timeFormat.parse("00:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
