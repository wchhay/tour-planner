package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.service.exception.FailedTourCreationException;

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
        return tourRepository.create(tour).orElseThrow(FailedTourCreationException::new);
    }

    @Override
    public boolean deleteTour(UUID uuid) {
        return tourRepository.delete(uuid);
    }
}
