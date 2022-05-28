package at.technikum.tourplanner.dashboard.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    private UUID id;
    private String name;
    private String from;
    private String to;
    private String transportType;
    private Double distance;
    private Long estimatedTime;
    private String description;
    private List<Log> logs;
}
