package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.validation.Validator;
import at.technikum.tourplanner.service.validation.ValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogDialogViewModelTest {

    @Spy
    private Validator validator = new ValidatorImpl();

    @Mock
    private DialogService dialogService;

    @InjectMocks
    private LogDialogViewModel logDialogViewModel;

    @Mock
    private Listener<Log> logListener;

    @Captor
    private ArgumentCaptor<Log> logArgumentCaptor;

    @BeforeEach
    void setUp() {
        logDialogViewModel.subscribeToLogCreation(logListener);
        logDialogViewModel.subscribeToLogUpdate(logListener);
        setValidProperties();
    }

    @Test
    void GIVEN_valid_input_WHEN_createLog_THEN_subscribers_notified() {
        logDialogViewModel.createLog();

        verify(logListener, times(1)).update(logArgumentCaptor.capture());
        assertThat(logArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    void GIVEN_valid_input_WHEN_updateLog_THEN_subscribers_notified() {
        logDialogViewModel.updateLog();

        verify(logListener, times(1)).update(logArgumentCaptor.capture());
        assertThat(logArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    void GIVEN_invalid_input_WHEN_createLog_THEN_no_subscribers_notified() {
        logDialogViewModel.timeProperty().set("not a time string");

        logDialogViewModel.createLog();

        verify(logListener, never()).update(any());
    }

    @Test
    void GIVEN_invalid_input_WHEN_updateLog_THEN_no_subscribers_notified() {
        logDialogViewModel.timeProperty().set("not a time string");

        logDialogViewModel.updateLog();

        verify(logListener, never()).update(any());
    }

    private void setValidProperties() {
        logDialogViewModel.dateProperty().set(LocalDate.now());
        logDialogViewModel.timeProperty().set("10:00:00");
        logDialogViewModel.totalTimeProperty().set("01:30:34");
        logDialogViewModel.difficultyProperty().set(2);
        logDialogViewModel.ratingProperty().set(4);
    }

}