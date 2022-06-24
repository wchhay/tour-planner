package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.json.JsonConverterException;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileExportDialogViewModel {

    private final FileService fileService;
    private final JsonConverter jsonConverter;
    private final TourDataStoreService tourDataStoreService;
    private final DialogService dialogService;
    private final AlertService alertService;

    public FileExportDialogViewModel(FileService fileService, JsonConverter jsonConverter, TourDataStoreService tourDataStoreService, DialogService dialogService, AlertService alertService) {
        this.fileService = fileService;
        this.jsonConverter = jsonConverter;
        this.tourDataStoreService = tourDataStoreService;
        this.dialogService = dialogService;
        this.alertService = alertService;
    }

    public void exportToFile() {
        try{
            String json = jsonConverter.toJson(tourDataStoreService.getTourObservableList());
            fileService.exportToFile(json);
            closeDialog();
        } catch (JsonConverterException e) {
            logger.info(e);
            alertService.showErrorAlert(e);
        }
    }

    public void closeDialog() {
        dialogService.closeDialog();
    }
}
