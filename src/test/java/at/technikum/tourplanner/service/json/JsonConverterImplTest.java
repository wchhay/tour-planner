package at.technikum.tourplanner.service.json;


import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterImplTest {

    public static final String UUID_STRING = "6dffeb68-3433-4249-b8f3-ac1f9f7bde43";

    public static final String TOUR_JSON = "{" +
            "\"id\": \"6dffeb68-3433-4249-b8f3-ac1f9f7bde43\"," +
            "\"name\": \"Wien-Graz\"," +
            "\"from\": \"Wien\"," +
            "\"to\": \"Graz\"," +
            "\"transportType\": \"FASTEST\"," +
            "\"logs\": []" +
            "}";

    private JsonConverterImpl jsonConverter;

    @BeforeEach
    void setUp() {
        jsonConverter = new JsonConverterImpl(ObjectMapperFactory.create());
    }

    @Test
    void GIVEN_valid_json_WHEN_fromJson_THEN_tour_object_created() throws JsonConverterException {
        Tour tour = jsonConverter.fromJson(TOUR_JSON, Tour.class);

        assertThat(tour).isNotNull();
        assertThat(tour.getId()).isNotNull();
        assertThat(tour.getName()).isEqualTo("Wien-Graz");
        assertThat(tour.getFrom()).isEqualTo("Wien");
        assertThat(tour.getTo()).isEqualTo("Graz");
        assertThat(tour.getTransportType()).isEqualTo(TransportType.FASTEST);
        assertThat(tour.getDistance()).isNull();
        assertThat(tour.getEstimatedTime()).isNull();
        assertThat(tour.getLogs()).isEmpty();
    }

    @Test
    void GIVEN_tour_WHEN_toJson_THEN_correct_json() throws JsonConverterException {
        Tour tour = buildTour();

        String json = jsonConverter.toJson(tour);

        assertThat(json).startsWith("{\"id\":\"" + UUID_STRING);
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.fromString(UUID_STRING))
                .build();
    }
}