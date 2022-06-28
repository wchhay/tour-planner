package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.log.AsyncLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LogsViewModelTest {
    
    @Mock
    private DialogService dialogService;
    
    @Mock
    private AsyncLogService logService;
    
    @Mock
    private AlertService alertService;
    
    @InjectMocks
    private LogsViewModel logsViewModel;

    @Test
    void GIVEN_tour_WHEN_setTourAndLogs_THEN_tour_and_log_observable_list_are_set() {
        Tour tour = buildTour();

        logsViewModel.setTourAndLogs(tour);

        assertThat(logsViewModel.selectedTourProperty().get()).isEqualTo(tour);
        assertThat(logsViewModel.getLogsList()).isEqualTo(tour.getLogs());
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .logs(Collections.emptyList())
                .build();
    }
}