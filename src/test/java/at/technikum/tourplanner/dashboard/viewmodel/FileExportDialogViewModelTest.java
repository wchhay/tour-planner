package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.json.JsonConverterException;
import at.technikum.tourplanner.service.tour.TourDataStoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileExportDialogViewModelTest {

    public static final String JSON_DATA = "some json data";

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
    private FileExportDialogViewModel fileExportDialogViewModel;

    @Test
    void GIVEN_tour_data_WHEN_exportToFile_THEN_fileService_export_invoked() throws JsonConverterException {
        when(jsonConverter.toJson(any())).thenReturn(JSON_DATA);

        fileExportDialogViewModel.exportToFile();

        verify(fileService, times(1)).exportToFile(JSON_DATA);
    }

    @Test
    void GIVEN_exception_during_serialization_WHEN_exportToFile_THEN_alert_should_be_displayed() throws JsonConverterException {
        when(jsonConverter.toJson(any())).thenThrow(new JsonConverterException(new RuntimeException()));

        fileExportDialogViewModel.exportToFile();

        verify(fileService, never()).exportToFile(JSON_DATA);
        verify(alertService, times(1)).showErrorAlert(any(Throwable.class));
    }
}