package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.report.ReportService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;

public class MenuBarViewModel {

    private final DialogService dialogService;
    private final ReportService reportService;
    private final TourDataStoreService tourDataStoreService;

    public MenuBarViewModel(DialogService dialogService, ReportService reportService, TourDataStoreService tourDataStoreService) {
        this.dialogService = dialogService;
        this.reportService = reportService;
        this.tourDataStoreService = tourDataStoreService;
    }

    public void openFileImportDialog() {
        dialogService.openFileImportDialog();
    }

    public void openFileExportDialog() {
        dialogService.openFileExportDialog();
    }

    public void generateSummarizeReport() {
        reportService.generateSummarizeReport(tourDataStoreService.getTourObservableList());
    }
}
