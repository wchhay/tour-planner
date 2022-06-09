package at.technikum.tourplanner.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDto {
    private LocalDateTime date;
    private Long totalTime;
    private Integer difficulty;
    private Integer rating;
    private String comment;
}
