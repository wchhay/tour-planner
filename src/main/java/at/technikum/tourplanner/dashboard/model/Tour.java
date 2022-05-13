package at.technikum.tourplanner.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    private final UUID uuid = UUID.randomUUID();
    private String name;
    private String description;
}
