package at.technikum.tourplanner.service.search;


import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SearchServiceImplTest {

    private SearchServiceImpl searchService;

    private Tour tour;
    private List<Tour> tours;

    @BeforeEach
    void setUp() {
        searchService = new SearchServiceImpl();

        tour = buildTour();
        tours = List.of(tour);
    }

    @Test
    void GIVEN_tour_name_contains_searchString_WHEN_search_THEN_should_find_tour() {
        String searchString = "nam";

        List<Tour> foundTours = searchService.search(searchString, tours);

        assertThat(foundTours).contains(tour);
    }

    @Test
    void GIVEN_tour_does_not_contain_searchString_WHEN_search_THEN_should_find_tour() {
        String searchString = "hallo";

        List<Tour> foundTours = searchService.search(searchString, tours);

        assertThat(foundTours).isEmpty();
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .name("name")
                .from("from")
                .to("to")
                .description("description")
                .logs(List.of(buildLog()))
                .build();

    }

    private Log buildLog() {
        return Log.builder()
                .comment("comment")
                .build();
    }
}