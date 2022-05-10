package at.technikum.tourplanner.dashboard.model;

import java.time.LocalDateTime;

public class Log {
    private LocalDateTime date;
    private Long duration;
    private Long distance;

    public Log(LocalDateTime date, Long duration, Long distance) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
