package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.search.SearchService;
import at.technikum.tourplanner.service.tour.AsyncTourService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourListViewModelTest {

    @Mock
    private AsyncTourService asyncTourService;

    @Mock
    private DialogService dialogService;

    @Mock
    private TourDataStoreService tourDataStoreService;

    @Mock
    private AlertService alertService;

    @Mock
    private SearchService searchService;

    private TourListViewModel tourListViewModel;

    private Tour tour;
    private Tour otherTour;

    @BeforeEach
    void setUp() {
        tour = buildTour();
        otherTour = buildTour();

        when(tourDataStoreService.getTourObservableList()).thenReturn(FXCollections.observableArrayList(tour, otherTour));
        lenient().when(searchService.search(anyString(), anyList())).thenReturn(List.of(tour));

        tourListViewModel = new TourListViewModel(asyncTourService, dialogService, tourDataStoreService, alertService, searchService);
    }

    @Test
    void GIVEN_non_empty_searchString_WHEN_search_THEN_set_tourList_to_search_result() {
        tourListViewModel.search("non-empty string");

        assertThat(tourListViewModel.getTourList()).contains(tour);
        assertThat(tourListViewModel.getTourList()).doesNotContain(otherTour);
    }

    @Test
    void GIVEN_empty_searchString_WHEN_search_THEN_reset_search() {
        tourListViewModel.search("");

        assertThat(tourListViewModel.getTourList()).contains(tour);
        assertThat(tourListViewModel.getTourList()).contains(otherTour);
    }

    private Tour buildTour() {
        return Tour.builder()
                .id(UUID.randomUUID())
                .build();
    }
}