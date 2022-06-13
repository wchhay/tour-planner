package at.technikum.tourplanner.injection;

import at.technikum.tourplanner.rest.TourInMemoryRepository;
import at.technikum.tourplanner.rest.TourRemoteRepository;
import at.technikum.tourplanner.rest.TourRepository;
import at.technikum.tourplanner.rest.TourRestAPI;
import at.technikum.tourplanner.service.TourDialogService;
import at.technikum.tourplanner.dashboard.view.*;
import at.technikum.tourplanner.dashboard.viewmodel.*;
import at.technikum.tourplanner.service.TourServiceImpl;
import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    public static final String REST_API_BASE_URL = "http://localhost:8080";

    private static final Map<Class<?>, ControllerCreator> controllerCreators = new HashMap<>();

    private static ControllerFactory instance;

    private final SearchbarViewModel searchbarViewModel;
    private final LogsViewModel logsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final TourDialogService tourDialogService;
    private final DashboardViewModel dashboardViewModel;

    private ControllerFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(JsonMapper.builder().findAndAddModules().build()))
                .build();

        // TourRepository tourRepository = new TourRemoteRepository(retrofit.create(TourRestAPI.class));
        TourRepository tourRepository = new TourInMemoryRepository();

        tourDialogService = new TourDialogService();
        searchbarViewModel = new SearchbarViewModel();
        logsViewModel = new LogsViewModel();
        tourListViewModel = new TourListViewModel(new TourServiceImpl(tourRepository), tourDialogService);
        tourDetailsViewModel = new TourDetailsViewModel();
        tourDialogViewModel = new TourDialogViewModel(tourDialogService);
        dashboardViewModel = new DashboardViewModel(tourListViewModel, tourDetailsViewModel, tourDialogViewModel, logsViewModel);

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
        addControllerCreator(DashboardController.class, () -> new DashboardController(dashboardViewModel));
    }
}
