package at.technikum.tourplanner.service.log;

import at.technikum.tourplanner.dashboard.model.Log;

import java.util.List;
import java.util.UUID;

public interface LogService {

    Log createLog(UUID tourId, Log log);

    Log updateLog(UUID tourId, UUID logId, Log log);

    List<Log> fetchLogs(UUID tourId);

    void deleteLog(UUID tourId, UUID logId);
}
