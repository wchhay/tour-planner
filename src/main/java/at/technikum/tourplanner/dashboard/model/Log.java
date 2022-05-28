package at.technikum.tourplanner.dashboard.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    private UUID id;
    private LocalDateTime date;
    private Long totalTime;
    private Integer difficulty;
    private Integer rating;
    private String comment;
}
