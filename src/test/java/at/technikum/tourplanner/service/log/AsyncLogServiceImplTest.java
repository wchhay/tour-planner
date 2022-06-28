package at.technikum.tourplanner.service.log;

import at.technikum.tourplanner.dashboard.model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class AsyncLogServiceImplTest {

    public static final String ERROR_MESSAGE = "Error message";

    @Mock
    private LogService logService;

    @InjectMocks
    private AsyncLogServiceImpl asyncLogService;

    @Mock
    private Consumer<List<Log>> logsConsumer;

    @Mock
    private Consumer<Throwable> errorConsumer;

    @Captor
    private ArgumentCaptor<List<Log>> logsArgumentCaptor;

    @Captor
    private ArgumentCaptor<Throwable> errorArgumentCaptor;

    private List<Log> logs;
    private UUID tourId;

    @BeforeEach
    void setUp() {
        tourId = UUID.randomUUID();
        logs = List.of(buildRandomLog());
    }

    @Test
    void GIVEN_subscription_to_fetching_logs_WHEN_fetchLogs_successful_THEN_onSuccess_invoked() {
        when(logService.fetchLogs(tourId)).thenReturn(logs);

        asyncLogService.subscribeToFetchLogs(logsConsumer, null);
        asyncLogService.fetchLogs(tourId);

        verify(logsConsumer, timeout(1000).times(1)).accept(logsArgumentCaptor.capture());
        assertThat(logsArgumentCaptor.getValue()).isEqualTo(logs);
    }

    @Test
    void GIVEN_subscription_to_fetching_logs_WHEN_fetchLogs_failed_THEN_onError_invoked() {
        when(logService.fetchLogs(tourId)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        asyncLogService.subscribeToFetchLogs(null, errorConsumer);
        asyncLogService.fetchLogs(tourId);

        verify(errorConsumer, timeout(1000).times(1)).accept(errorArgumentCaptor.capture());
        assertThat(errorArgumentCaptor.getValue()).hasMessage(ERROR_MESSAGE);
    }

    private Log buildRandomLog() {
        return Log.builder()
                .id(UUID.randomUUID())
                .build();
    }
}