package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.service.dialog.DialogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourDialogViewModelTest {

    @Mock
    private DialogService dialogService;

    @InjectMocks
    private TourDialogViewModel tourDialogViewModel;

    @Mock
    private Listener<Tour> tourListener;

    @Captor
    private ArgumentCaptor<Tour> tourArgumentCaptor;

    @BeforeEach
    void setUp() {
        tourDialogViewModel.subscribeToTourCreation(tourListener);
        tourDialogViewModel.subscribeToTourUpdate(tourListener);

        setValidProperties();
    }

    @Test
    void GIVEN_valid_input_WHEN_createTour_THEN_subscribers_notified() {
        tourDialogViewModel.createTour();

        verify(tourListener, times(1)).update(tourArgumentCaptor.capture());
        assertThat(tourArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    void GIVEN_valid_input_WHEN_updateTour_THEN_subscribers_notified() {
        tourDialogViewModel.updateTour();

        verify(tourListener, times(1)).update(tourArgumentCaptor.capture());
        assertThat(tourArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    void GIVEN_invalid_input_WHEN_createTour_THEN_subscribers_not_notified() {
        tourDialogViewModel.tourNameProperty().set("");

        tourDialogViewModel.createTour();

        verify(tourListener, never()).update(any());
    }

    @Test
    void GIVEN_invalid_input_WHEN_updateTour_THEN_subscribers_not_notified() {
        tourDialogViewModel.tourNameProperty().set("");

        tourDialogViewModel.updateTour();

        verify(tourListener, never()).update(any());
    }

    private void setValidProperties() {
        tourDialogViewModel.tourNameProperty().set("name");
        tourDialogViewModel.tourFromProperty().set("from");
        tourDialogViewModel.tourToProperty().set("to");
        tourDialogViewModel.tourTransportTypeProperty().set(TransportType.FASTEST);
        tourDialogViewModel.tourDescriptionProperty().set("description");
    }
}