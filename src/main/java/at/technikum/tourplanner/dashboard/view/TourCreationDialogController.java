package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;

public class TourCreationDialogController {

    private final TourDialogViewModel tourDialogViewModel;

    public TourCreationDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    public void onCreateTourClicked() {
        tourDialogViewModel.createTour();
        tourDialogViewModel.closeDialog();
    }
}
