package at.technikum.tourplanner.service.search;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    @Override
    public List<Tour> search(String searchString, List<Tour> tours) {
        return tours.stream()
                .filter(tour -> searchAnyStringInTour(searchString, tour))
                .toList();
    }

    private boolean searchAnyStringInTour(String searchString, Tour tour) {
        return containsSearchString(searchString, tour.getName())
                || containsSearchString(searchString, tour.getFrom())
                || containsSearchString(searchString, tour.getTo())
                || containsSearchString(searchString, tour.getDescription())
                || searchAnyStringInLog(searchString, tour.getLogs());
    }

    private boolean searchAnyStringInLog(String searchString, List<Log> logs) {
        return logs.stream()
                .map(log -> containsSearchString(searchString, log.getComment()))
                .reduce(false, (a, b) -> a || b);
    }

    private boolean containsSearchString(String searchString, String other) {
        return other.toLowerCase().contains(searchString.toLowerCase());
    }
}
