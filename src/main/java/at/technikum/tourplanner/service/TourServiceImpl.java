package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.service.exception.FailedTourCreationException;

import java.util.List;

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
}
