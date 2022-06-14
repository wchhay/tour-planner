package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.ImageService;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.service.exception.FailedLogCreationException;
import at.technikum.tourplanner.service.exception.FailedTourCreationException;
import at.technikum.tourplanner.service.exception.FailedTourUpdateException;
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
    public boolean deleteTour(UUID uuid) {
        return tourRepository.delete(uuid);
    }

    @Override
    public Log createLog(UUID tourId, Log log) {
        return tourRepository.createLog(tourId, DtoMapper.toLogDto(log))
                .orElseThrow(FailedLogCreationException::new);
    }

    @Override
    public Image downloadTourMapImage(UUID tourId) {
        return imageService.downloadTourMapImage(tourId);
    }
}
