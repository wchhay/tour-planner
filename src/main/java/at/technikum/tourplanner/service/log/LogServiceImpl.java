package at.technikum.tourplanner.service.log;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.service.exception.FailedLogCreationException;
import at.technikum.tourplanner.service.exception.FailedLogDeletionException;
import at.technikum.tourplanner.service.exception.FailedLogUpdateException;

import java.util.List;
import java.util.UUID;

public class LogServiceImpl implements LogService {

    private final TourRepository tourRepository;

    public LogServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public Log createLog(UUID tourId, Log log) {
        return tourRepository.createLog(tourId, DtoMapper.toLogDto(log))
                .orElseThrow(FailedLogCreationException::new);
    }

    @Override
    public Log updateLog(UUID tourId, UUID logId, Log log) {
        return tourRepository.updateLog(tourId, logId, DtoMapper.toLogDto(log))
                .orElseThrow(FailedLogUpdateException::new);
    }

    @Override
    public List<Log> fetchLogs(UUID tourId) {
        return tourRepository.getLogsFromTour(tourId);
    }

    @Override
    public void deleteLog(UUID tourId, UUID logId) {
        if (!tourRepository.deleteLog(tourId, logId)) {
            throw new FailedLogDeletionException();
        }
    }
}
