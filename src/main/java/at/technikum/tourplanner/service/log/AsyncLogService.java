package at.technikum.tourplanner.service.log;

import at.technikum.tourplanner.dashboard.model.Log;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface AsyncLogService {
    void fetchLogs(UUID tourId);

    void createLog(UUID tourId, Log log);

    void updateLog(UUID tourId, Log log);

    void deleteLog(UUID tourId, Log log);

    void subscribeToFetchLogs(Consumer<List<Log>> onSuccess, Consumer<Throwable> onError);

    void subscribeToCreateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError);

    void subscribeToUpdateLog(Consumer<Log> onSuccess, Consumer<Throwable> onError);

    void subscribeToDeleteLog(Consumer<Void> onSuccess, Consumer<Throwable> onError);
}
