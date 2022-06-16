package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.ImageService;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.service.exception.*;
import javafx.scene.image.Image;

import java.util.List;
import java.util.UUID;

public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final ImageService imageService;

    public TourServiceImpl(TourRepository tourRepository, ImageService imageService) {
        this.tourRepository = tourRepository;
        this.imageService = imageService;
    }

    @Override
    public List<Tour> fetchTours() {
        return tourRepository.getAll();
    }

    @Override
    public Tour createTour(Tour tour) {
        return tourRepository.create(DtoMapper.toTourDto(tour))
                .orElseThrow(FailedTourCreationException::new);
    }

    @Override
    public Tour updateTour(Tour tour) {
        return tourRepository.updateTour(tour.getId(), DtoMapper.toTourDto(tour))
                .orElseThrow(FailedTourUpdateException::new);
    }

    @Override
    public void deleteTour(UUID uuid) {
        if (!tourRepository.deleteTour(uuid)) {
            throw new FailedTourDeletionException();
        }
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

    @Override
    public Image downloadTourMapImage(UUID tourId) {
        return imageService.downloadTourMapImage(tourId);
    }
}
