package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class FileImportDialogViewModel {

    private final StringProperty fileImportContent = new SimpleStringProperty();

    private final FileService fileService;
    private final JsonConverter jsonConverter;
    private final TourDataStoreService tourDataStoreService;
    private final DialogService dialogService;

    public FileImportDialogViewModel(FileService fileService, JsonConverter jsonConverter, TourDataStoreService tourDataStoreService, DialogService dialogService) {
        this.fileService = fileService;
        this.jsonConverter = jsonConverter;
        this.tourDataStoreService = tourDataStoreService;
        this.dialogService = dialogService;
    }

    public void importFile() {
        fileImportContent.setValue(fileService.importFromFile());
    }

    public void confirmImport() {
        if (fileImportContent.isNotEmpty().get()) {
            List<Tour> tours = jsonConverter.fromJson(fileImportContent.get(), new TypeReference<>(){});
            tourDataStoreService.setTours(tours);
            dialogService.closeDialog();
        }
    }

    public StringProperty fileImportContentProperty() {
        return fileImportContent;
    }

}
