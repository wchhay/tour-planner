package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.report.FailedPdfGenerationException;
import at.technikum.tourplanner.service.report.ReportService;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MenuBarViewModel {

    private final DialogService dialogService;
    private final ReportService reportService;
    private final TourDataStoreService tourDataStoreService;
    private final AlertService alertService;

    public MenuBarViewModel(DialogService dialogService, ReportService reportService, TourDataStoreService tourDataStoreService, AlertService alertService) {
        this.dialogService = dialogService;
        this.reportService = reportService;
        this.tourDataStoreService = tourDataStoreService;
        this.alertService = alertService;
    }

    public void openFileImportDialog() {
        dialogService.openFileImportDialog();
    }

    public void openFileExportDialog() {
        dialogService.openFileExportDialog();
    }

    public void generateSummarizeReport() {
        logger.info("Generating summarize report for all tours");
        try {
            reportService.generateSummarizeReport(tourDataStoreService.getTourObservableList());
        } catch (FailedPdfGenerationException e) {
            logger.warn(e);
            alertService.showErrorAlert(e);
        }
    }
}
