package at.technikum.tourplanner.service.log;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.service.AsyncService;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AsyncLogServiceImpl implements AsyncLogService {

    private final LogService logService;

    private final AsyncService<List<Log>> logFetchingService = new AsyncService<>();
    private final AsyncService<Log> logCreationService = new AsyncService<>();
    private final AsyncService<Log> logUpdateService = new AsyncService<>();
    private final AsyncService<Void> logDeleteService = new AsyncService<>();

    public AsyncLogServiceImpl(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void fetchLogs(UUID tourId) {
        logFetchingService.setSupplier(() -> logService.fetchLogs(tourId));
        logFetchingService.restart();
    }

    @Override
    public void createLog(UUID tourId, Log log) {
        logCreationService.setSupplier(() -> logService.createLog(tourId, log));
        logCreationService.restart();
    }

    @Override
    public void updateLog(UUID tourId, Log log) {
        logUpdateService.setSupplier(() -> logService.updateLog(tourId, log.getId(), log));
        logUpdateService.restart();
    }

    @Override
    public void deleteLog(UUID tourId, Log log) {
        logDeleteService.setSupplier(() -> {
            logService.deleteLog(tourId, log.getId());
            return null;
        });
        logDeleteService.restart();
    }

    @Override
    public void subscribeToFetchLogs(Consumer<List<Log>> onSuccess, Consumer<Throwable> onError) {
        logFetchingService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToCreateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError) {
        logCreationService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToUpdateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError) {
        logUpdateService.subscribe(onSuccess, onError);
    }

    @Override
    public void subscribeToDeleteLog(Consumer<Void> onSuccess, Consumer<Throwable> onError) {
        logDeleteService.subscribe(onSuccess, onError);
    }
}
