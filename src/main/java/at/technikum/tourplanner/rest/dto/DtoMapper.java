package at.technikum.tourplanner.rest.dto;

import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

public class DtoMapper {
    
    private DtoMapper() {
    }
    
    public static TourDto toTourDto(Tour tour) {
        return TourDto.builder()
                .name(tour.getName())
                .from(tour.getFrom())
                .to(tour.getTo())
                .description(tour.getDescription())
                .transportType(tour.getTransportType())
                .build();
    }

    public static LogDto toLogDto(Log log) {
        return LogDto.builder()
                .date(log.getDate())
                .totalTime(log.getTotalTime())
                .comment(log.getComment())
                .difficulty(log.getDifficulty())
                .rating(log.getRating())
                .build();
    }
}
