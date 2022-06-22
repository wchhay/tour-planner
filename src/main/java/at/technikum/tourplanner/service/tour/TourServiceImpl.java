package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.rest.dto.DtoMapper;
import at.technikum.tourplanner.service.exception.*;

import java.util.List;
import java.util.UUID;

public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
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
}
