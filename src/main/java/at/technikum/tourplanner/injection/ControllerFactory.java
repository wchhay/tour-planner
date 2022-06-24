package at.technikum.tourplanner.injection;

import at.technikum.tourplanner.config.ConfigService;
import at.technikum.tourplanner.config.ConfigServiceImpl;
import at.technikum.tourplanner.service.validation.Validator;
import at.technikum.tourplanner.service.validation.ValidatorImpl;
import at.technikum.tourplanner.rest.*;
import at.technikum.tourplanner.dashboard.view.*;
import at.technikum.tourplanner.dashboard.viewmodel.*;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.dialog.AlertServiceImpl;
import at.technikum.tourplanner.service.dialog.DialogService;
import at.technikum.tourplanner.service.dialog.DialogServiceImpl;
import at.technikum.tourplanner.service.file.FileService;
import at.technikum.tourplanner.service.file.FileServiceImpl;
import at.technikum.tourplanner.service.file.FileChooserFactory;
import at.technikum.tourplanner.service.json.JsonConverter;
import at.technikum.tourplanner.service.json.JsonConverterImpl;
import at.technikum.tourplanner.service.json.ObjectMapperFactory;
import at.technikum.tourplanner.service.log.AsyncLogService;
import at.technikum.tourplanner.service.log.AsyncLogServiceImpl;
import at.technikum.tourplanner.service.log.LogService;
import at.technikum.tourplanner.service.log.LogServiceImpl;
import at.technikum.tourplanner.service.report.ReportService;
import at.technikum.tourplanner.service.report.ReportServiceImpl;
import at.technikum.tourplanner.service.search.SearchService;
import at.technikum.tourplanner.service.search.SearchServiceImpl;
import at.technikum.tourplanner.service.statistics.StatisticsService;
import at.technikum.tourplanner.service.statistics.StatisticsServiceImpl;
import at.technikum.tourplanner.service.tour.*;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ControllerFactory {

    private static ControllerFactory instance;

    private final Map<Class<?>, ControllerCreator<?>> controllerCreators = new HashMap<>();

    private final SearchbarViewModel searchbarViewModel;
    private final LogsViewModel logsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final LogDialogViewModel logDialogViewModel;
    private final MenuBarViewModel menuBarViewModel;
    private final FileImportDialogViewModel fileImportDialogViewModel;
    private final FileExportDialogViewModel fileExportDialogViewModel;
    private final DashboardViewModel dashboardViewModel;

    private final TourService tourService;
    private final LogService logService;
    private final AsyncTourService asyncTourService;
    private final AsyncLogService asyncLogService;
    private final DialogService dialogService;
    private final ConfigService configService;
    private final ImageService imageService;
    private final Validator validator;
    private final TourDataStoreService tourDataStoreService;
    private final FileService fileService;
    private final JsonConverter jsonConverter;
    private final StatisticsService statisticsService;
    private final AlertService alertService;
    private final ReportService reportService;
    private final SearchService searchService;

    private ControllerFactory() {
        configService = new ConfigServiceImpl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configService.getKey("rest-base-url"))
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapperFactory.create()))
                .build();

        TourRepository tourRepository = new TourRemoteRepository(retrofit.create(TourRestAPI.class));

        dialogService = new DialogServiceImpl();
        alertService = new AlertServiceImpl();
        validator = new ValidatorImpl();
        statisticsService = new StatisticsServiceImpl();
        tourDataStoreService = new TourDataStoreServiceImpl();
        searchService = new SearchServiceImpl();
        imageService = new ImageDownloadService(configService);
        tourService = new TourServiceImpl(tourRepository);
        logService = new LogServiceImpl(tourRepository);
        asyncTourService = new AsyncTourServiceImpl(tourService);
        asyncLogService = new AsyncLogServiceImpl(logService);
        fileService = new FileServiceImpl(FileChooserFactory.forJson(), configService);
        jsonConverter = new JsonConverterImpl(ObjectMapperFactory.create());
        reportService = new ReportServiceImpl(FileChooserFactory.forPdf(), configService, statisticsService);

        searchbarViewModel = new SearchbarViewModel();
        tourListViewModel = new TourListViewModel(asyncTourService, dialogService, tourDataStoreService, alertService, searchService);
        tourDetailsViewModel = new TourDetailsViewModel(imageService, alertService, reportService, statisticsService);
        tourDialogViewModel = new TourDialogViewModel(dialogService);
        logsViewModel = new LogsViewModel(dialogService, asyncLogService, alertService);
        logDialogViewModel = new LogDialogViewModel(dialogService, validator);
        menuBarViewModel = new MenuBarViewModel(dialogService, reportService, tourDataStoreService, alertService);
        fileImportDialogViewModel = new FileImportDialogViewModel(fileService, jsonConverter, tourDataStoreService, dialogService, alertService);
        fileExportDialogViewModel = new FileExportDialogViewModel(fileService, jsonConverter, tourDataStoreService, dialogService, alertService);
        dashboardViewModel = new DashboardViewModel(tourListViewModel, tourDetailsViewModel, tourDialogViewModel, logsViewModel, logDialogViewModel, searchbarViewModel);

        setUpControllerFactory();
    }

    public static ControllerFactory getInstance() {
        if (null == instance) {
            instance = new ControllerFactory();
        }
        return instance;
    }

    public <T> void addControllerCreator(Class<T> controllerClass, ControllerCreator<T> controllerCreator) {
        controllerCreators.put(controllerClass, controllerCreator);
    }

    public void removeAllControllerCreators() {
        controllerCreators.clear();
    }

    public Object create(Class<?> controllerClass) {
        logger.debug("Creating controller of class={}", controllerClass.getName());
        if (controllerCreators.containsKey(controllerClass)) {
            return controllerCreators.get(controllerClass).create();
        }

        return callDefaultConstructor(controllerClass);
    }

    private Object callDefaultConstructor(Class<?> controllerClass) {
        try {
            return controllerClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private void setUpControllerFactory() {
        addControllerCreator(SearchbarController.class, () -> new SearchbarController(searchbarViewModel));
        addControllerCreator(LogsController.class, () -> new LogsController(logsViewModel));
        addControllerCreator(TourlistController.class, () -> new TourlistController(tourListViewModel));
        addControllerCreator(TourDetailsController.class, () -> new TourDetailsController(tourDetailsViewModel));
        addControllerCreator(TourDialogController.class, () -> new TourDialogController(tourDialogViewModel));
        addControllerCreator(TourCreationDialogController.class, () -> new TourCreationDialogController(tourDialogViewModel));
        addControllerCreator(TourUpdateDialogController.class, () -> new TourUpdateDialogController(tourDialogViewModel));
        addControllerCreator(LogCreationDialogController.class, () -> new LogCreationDialogController(logDialogViewModel));
        addControllerCreator(LogUpdateDialogController.class, () -> new LogUpdateDialogController(logDialogViewModel));
        addControllerCreator(LogDialogController.class, () -> new LogDialogController(logDialogViewModel));
        addControllerCreator(MenuBarController.class, () -> new MenuBarController(menuBarViewModel));
        addControllerCreator(FileImportDialogController.class, () -> new FileImportDialogController(fileImportDialogViewModel));
        addControllerCreator(FileExportDialogController.class, () -> new FileExportDialogController(fileExportDialogViewModel));
        addControllerCreator(DashboardController.class, () -> new DashboardController(dashboardViewModel));
    }
}
