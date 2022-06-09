package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.dashboard.model.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourRemoteRepositoryTest {

    @Mock
    private TourRestAPI tourRestAPI;

    @Mock
    private Call<List<Tour>> mockedCall;

    @InjectMocks
    private TourRemoteRepository tourRepository;


    private List<Tour> tours;

    @BeforeEach
    void setUp() {
        tours = List.of(buildTour());
    }

    @Test
    void should_connect_to_rest_api() throws IOException {
        when(tourRestAPI.getAll()).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(Response.success(tours));

        List<Tour> toursResponse = tourRepository.getAll();

        assertThat(toursResponse)
                .isNotNull()
                .isEqualTo(tours);
    }

    private Tour buildTour() {
        return Tour.builder()
                .name("Awesome tour")
                .from("Vienna")
                .to("Graz")
                .build();
    }
}