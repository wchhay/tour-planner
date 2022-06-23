package at.technikum.tourplanner.service.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourStats {
    private Double averageTime;
    private Double averageDifficulty;
    private Double averageRating;
}
