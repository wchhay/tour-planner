package at.technikum.tourplanner.dashboard.view;

import at.technikum.tourplanner.dashboard.viewmodel.TourDialogViewModel;

public class TourUpdateDialogController {

    private final TourDialogViewModel tourDialogViewModel;

    public TourUpdateDialogController(TourDialogViewModel tourDialogViewModel) {
        this.tourDialogViewModel = tourDialogViewModel;
    }

    public void onUpdateTourClicked() {
        tourDialogViewModel.updateTour();
        tourDialogViewModel.closeDialog();
    }
}
