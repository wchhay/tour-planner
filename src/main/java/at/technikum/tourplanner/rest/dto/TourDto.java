package at.technikum.tourplanner.rest.dto;


import at.technikum.tourplanner.dashboard.model.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDto {
    private String name;
    private String from;
    private String to;
    private TransportType transportType;
    private String description;
}
