package at.technikum.tourplanner.dashboard.model;

public enum TransportType {
    BICYCLE("bicycle"),
    PEDESTRIAN("pedestrian"),
    FASTEST("fastest"),
    SHORTEST("shortest");

    public final String value;

    TransportType(String value) {
        this.value = value;
    }
}
