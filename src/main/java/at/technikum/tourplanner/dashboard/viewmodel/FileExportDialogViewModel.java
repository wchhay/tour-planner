package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.tour.TourDataStoreService;

public class FileExportDialogViewModel {

    private final FileService fileService;
    private final JsonConverter jsonConverter;
    private final TourDataStoreService tourDataStoreService;
    private final DialogService dialogService;

    public FileExportDialogViewModel(FileService fileService, JsonConverter jsonConverter, TourDataStoreService tourDataStoreService, DialogService dialogService) {
        this.fileService = fileService;
        this.jsonConverter = jsonConverter;
        this.tourDataStoreService = tourDataStoreService;
        this.dialogService = dialogService;
    }

    public void exportToFile() {
        String json = jsonConverter.toJson(tourDataStoreService.getTourObservableList());
        fileService.exportToFile(json);
        closeDialog();
    }

    public void closeDialog() {
        dialogService.closeDialog();
    }
}
