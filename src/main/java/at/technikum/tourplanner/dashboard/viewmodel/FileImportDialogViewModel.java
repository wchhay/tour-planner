package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.json.JsonConverterException;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class FileImportDialogViewModel {

    private final StringProperty fileImportContent = new SimpleStringProperty();

    private final FileService fileService;
    private final JsonConverter jsonConverter;
    private final TourDataStoreService tourDataStoreService;
    private final DialogService dialogService;
    private final AlertService alertService;

    public FileImportDialogViewModel(FileService fileService, JsonConverter jsonConverter, TourDataStoreService tourDataStoreService, DialogService dialogService, AlertService alertService) {
        this.fileService = fileService;
        this.jsonConverter = jsonConverter;
        this.tourDataStoreService = tourDataStoreService;
        this.dialogService = dialogService;
        this.alertService = alertService;
    }

    public void importFile() {
        fileImportContent.setValue(fileService.importFromFile());
    }

    public void confirmImport() {
        if (fileImportContent.isNotEmpty().get()) {
            try {
                List<Tour> tours = jsonConverter.fromJson(fileImportContent.get(), new TypeReference<>(){});
                tourDataStoreService.setTours(tours);
                dialogService.closeDialog();
            } catch (JsonConverterException e) {
                logger.info(e);
                alertService.showErrorAlert(e);
            }
        }
    }

    public StringProperty fileImportContentProperty() {
        return fileImportContent;
    }

}
