package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.rest.ImageService;
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

import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourDetailsViewModelTest {

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
        assertThat(tourDetailsViewModel.nameProperty().get()).isEqualTo(tour.getName());
        assertThat(tourDetailsViewModel.fromProperty().get()).isEqualTo(tour.getFrom());
        assertThat(tourDetailsViewModel.toProperty().get()).isEqualTo(tour.getTo());
        assertThat(tourDetailsViewModel.transportTypeProperty().get()).isEqualTo(tour.getTransportType().value);
        assertThat(tourDetailsViewModel.descriptionProperty().get()).isEqualTo(tour.getDescription());
        assertThat(tourDetailsViewModel.timeProperty().get()).isEqualTo(convertToTimeString(tour.getEstimatedTime()));
        assertThat(tourDetailsViewModel.distanceProperty().get()).isEqualTo(tour.getDistance().toString());
        assertThat(tourDetailsViewModel.popularityProperty().get()).isEqualTo(tour.getPopularity().toString());
        assertThat(tourDetailsViewModel.childFriendlinessProperty().get()).isEqualTo(tour.getChildFriendliness().toString());
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .name("name")
                .from("from")
                .to("to")
                .transportType(TransportType.FASTEST)
                .description("description")
                .estimatedTime(3600L)
                .distance(200.0)
                .popularity(2)
                .childFriendliness(2)
                .build();
    }
}