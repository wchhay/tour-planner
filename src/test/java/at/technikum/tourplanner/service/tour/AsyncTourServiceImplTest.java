package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class AsyncTourServiceImplTest {

    public static final String ERROR_MESSAGE = "Error message";

    @Mock
    private TourService tourService;

    @InjectMocks
    private AsyncTourServiceImpl asyncTourService;

    @Mock
    private Consumer<Tour> tourConsumer;

    @Mock
    private Consumer<Throwable> errorConsumer;

    @Captor
    private ArgumentCaptor<Tour> tourArgumentCaptor;

    @Captor
    private ArgumentCaptor<Throwable> errorArgumentCaptor;

    private Tour tour;

    @BeforeEach
    void setUp() {
        tour = buildTour();
    }

    @Test
    void GIVEN_subscription_to_creating_tour_WHEN_create_tour_successful_THEN_onSuccess_invoked() {
        when(tourService.createTour(any(Tour.class))).thenReturn(tour);

        asyncTourService.subscribeToCreateTour(tourConsumer, null);
        asyncTourService.createTour(tour);

        verify(tourConsumer, timeout(1000).times(1)).accept(tourArgumentCaptor.capture());
        assertThat(tourArgumentCaptor.getValue()).isEqualTo(tour);
    }

    @Test
    void GIVEN_subscription_to_creating_tour_WHEN_create_tour_failed_THEN_onError_invoked() {
        when(tourService.createTour(any(Tour.class))).thenThrow(new RuntimeException(ERROR_MESSAGE));

        asyncTourService.subscribeToCreateTour(null, errorConsumer);
        asyncTourService.createTour(tour);

        verify(errorConsumer, timeout(1000).times(1)).accept(errorArgumentCaptor.capture());
        assertThat(errorArgumentCaptor.getValue()).hasMessage(ERROR_MESSAGE);
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .build();
    }
}