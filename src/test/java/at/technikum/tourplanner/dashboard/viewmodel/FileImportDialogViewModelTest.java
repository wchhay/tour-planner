package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.json.JsonConverterException;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileImportDialogViewModelTest {

    @Mock
    private FileService fileService;

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private TourDataStoreService tourDataStoreService;

    @Mock
    private DialogService dialogService;

    @Mock
    private AlertService alertService;

    @InjectMocks
    private FileImportDialogViewModel fileImportDialogViewModel;

    @Test
    void GIVEN_fileImportContent_empty_WHEN_confirmImport_THEN_not_setting_tours() {
        fileImportDialogViewModel.fileImportContentProperty().setValue("");

        fileImportDialogViewModel.confirmImport();

        verify(tourDataStoreService, never()).setTours(anyList());
    }

    @Test
    void GIVEN_fileImportContent_not_empty_WHEN_confirmImport_THEN_setting_tours() throws JsonConverterException {
        when(jsonConverter.fromJson(anyString(), ArgumentMatchers.<TypeReference<List<Tour>>>any())).thenReturn(Collections.emptyList());
        fileImportDialogViewModel.fileImportContentProperty().setValue("some tour data");

        fileImportDialogViewModel.confirmImport();

        verify(tourDataStoreService, times(1)).setTours(anyList());
    }
}