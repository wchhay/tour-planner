package at.technikum.tourplanner.dashboard.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = "id")
public class Tour {
    private UUID id;
    private String name;
    private String from;
    private String to;
    private TransportType transportType;
    private Double distance;
    private Long estimatedTime;
    private String description;
    private List<Log> logs;
}
