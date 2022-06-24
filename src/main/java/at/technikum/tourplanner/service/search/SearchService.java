package at.technikum.tourplanner.service.search;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;

public interface SearchService {
    List<Tour> search(String searchString, List<Tour> tours);
}
