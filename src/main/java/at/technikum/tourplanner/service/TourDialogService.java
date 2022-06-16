package at.technikum.tourplanner.service;

public interface TourDialogService {
    void openCreationDialog();

    void openUpdateDialog();

    void openLogCreationDialog();

    void openLogUpdateDialog();

    void closeDialog();
}
