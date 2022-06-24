package at.technikum.tourplanner.service.report;

import at.technikum.tourplanner.dashboard.model.Tour;

import java.util.List;

public interface ReportService {
    void generateTourReport(Tour tour, String imageUrl);

    void generateSummarizeReport(List<Tour> tours);
}
