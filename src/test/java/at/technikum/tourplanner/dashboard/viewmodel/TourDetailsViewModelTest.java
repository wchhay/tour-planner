package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.service.tour.ImageService;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.report.ReportService;
import at.technikum.tourplanner.service.statistics.StatisticsService;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourDetailsViewModelTest {

    public static final String TOUR_NAME = "name";
    public static final String TOUR_FROM = "from";
    public static final String TOUR_TO = "to";
    public static final TransportType TRANSPORT_TYPE = TransportType.FASTEST;
    public static final String TOUR_DESCRIPTION = "description";
    public static final long ESTIMATED_TIME = 3600L;
    public static final String ESTIMATED_TIME_STRING = "01:00:00";
    public static final double DISTANCE = 200.0;
    public static final String DISTANCE_STRING = "200,00 km";
    public static final int POPULARITY = 2;
    public static final String POPULARITY_STRING = "2";
    public static final int CHILD_FRIENDLINESS = 3;
    public static final String CHILD_FRIENDLINESS_STRING = "3";
    @Mock
    private ImageService imageService;

    @Mock
    private AlertService alertService;

    @Mock
    private ReportService reportService;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private TourDetailsViewModel tourDetailsViewModel;

    @Mock
    private Image image;

    @BeforeEach
    void setUp() {
        when(imageService.downloadTourMapImage(any(UUID.class))).thenReturn(image);
    }

    @Test
    void GIVEN_tour_WHEN_setTour_THEN_properties_are_set() {
        Tour tour = buildTour();

        tourDetailsViewModel.setTour(tour);

        assertThat(tourDetailsViewModel.selectedTourProperty().get()).isEqualTo(tour);
        assertThat(tourDetailsViewModel.nameProperty().get()).isEqualTo(TOUR_NAME);
        assertThat(tourDetailsViewModel.fromProperty().get()).isEqualTo(TOUR_FROM);
        assertThat(tourDetailsViewModel.toProperty().get()).isEqualTo(TOUR_TO);
        assertThat(tourDetailsViewModel.transportTypeProperty().get()).isEqualTo(TRANSPORT_TYPE.value);
        assertThat(tourDetailsViewModel.descriptionProperty().get()).isEqualTo(TOUR_DESCRIPTION);
        assertThat(tourDetailsViewModel.timeProperty().get()).isEqualTo(ESTIMATED_TIME_STRING);
        assertThat(tourDetailsViewModel.distanceProperty().get()).isEqualTo(DISTANCE_STRING);
        assertThat(tourDetailsViewModel.popularityProperty().get()).isEqualTo(POPULARITY_STRING);
        assertThat(tourDetailsViewModel.childFriendlinessProperty().get()).isEqualTo(CHILD_FRIENDLINESS_STRING);
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .name(TOUR_NAME)
                .from(TOUR_FROM)
                .to(TOUR_TO)
                .transportType(TRANSPORT_TYPE)
                .description(TOUR_DESCRIPTION)
                .estimatedTime(ESTIMATED_TIME)
                .distance(DISTANCE)
                .popularity(POPULARITY)
                .childFriendliness(CHILD_FRIENDLINESS)
                .build();
    }
}