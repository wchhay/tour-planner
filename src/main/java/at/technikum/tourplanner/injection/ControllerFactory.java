package at.technikum.tourplanner.injection;

import at.technikum.tourplanner.config.ConfigService;
import at.technikum.tourplanner.config.ConfigServiceImpl;
import at.technikum.tourplanner.rest.*;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.dashboard.view.*;
import at.technikum.tourplanner.dashboard.viewmodel.*;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.service.TourServiceImpl;
import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    private static final Map<Class<?>, ControllerCreator> controllerCreators = new HashMap<>();

    private static ControllerFactory instance;

    private final SearchbarViewModel searchbarViewModel;
    private final LogsViewModel logsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final LogDialogViewModel logDialogViewModel;
    private final TourService tourService;
    private final TourDialogService tourDialogService;
    private final DashboardViewModel dashboardViewModel;
    private final ConfigService configService;
    private final ImageService imageService;

    private ControllerFactory() {
        configService = new ConfigServiceImpl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configService.getKey("rest-base-url"))
                .addConverterFactory(JacksonConverterFactory.create(JsonMapper.builder().findAndAddModules().build()))
                .build();

        TourRepository tourRepository = new TourRemoteRepository(retrofit.create(TourRestAPI.class));

        tourDialogService = new TourDialogService();
        imageService = new ImageDownloadService(configService);
        tourService = new TourServiceImpl(tourRepository, imageService);
        searchbarViewModel = new SearchbarViewModel();
        tourListViewModel = new TourListViewModel(tourService, tourDialogService);
        tourDetailsViewModel = new TourDetailsViewModel(tourService);
        tourDialogViewModel = new TourDialogViewModel(tourDialogService);
        logsViewModel = new LogsViewModel(tourDialogService, tourService);
        logDialogViewModel = new LogDialogViewModel(tourDialogService);
        dashboardViewModel = new DashboardViewModel(tourListViewModel, tourDetailsViewModel, tourDialogViewModel, logsViewModel, logDialogViewModel);

        setUpControllerFactory();
    }

    public static ControllerFactory getInstance() {
        if (null == instance) {
            instance = new ControllerFactory();
        }
        return instance;
    }

    public void addControllerCreator(Class<?> controllerClass, ControllerCreator controllerCreator) {
        controllerCreators.put(controllerClass, controllerCreator);
    }

    public void removeControllerCreator(Class<?> controllerClass) {
        controllerCreators.remove(controllerClass);
    }

    public void removeAllControllerCreators() {
        controllerCreators.clear();
    }

    public Object create(Class<?> controllerClass) {
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
        addControllerCreator(DashboardController.class, () -> new DashboardController(dashboardViewModel));
    }
}
