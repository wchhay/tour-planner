package at.technikum.tourplanner.injection;

import at.technikum.tourplanner.dashboard.service.TourDialogService;
import at.technikum.tourplanner.dashboard.view.*;
import at.technikum.tourplanner.dashboard.viewmodel.*;

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
    private final TourDialogService tourDialogService;

    private final DashboardViewModel dashboardViewModel;


    private ControllerFactory() {
        searchbarViewModel = new SearchbarViewModel();
        logsViewModel = new LogsViewModel();
        tourListViewModel = new TourListViewModel();
        tourDetailsViewModel = new TourDetailsViewModel();
        tourDialogViewModel = new TourDialogViewModel();
        tourDialogService = new TourDialogService();

        dashboardViewModel = new DashboardViewModel(tourListViewModel, tourDetailsViewModel, tourDialogViewModel);

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
        addControllerCreator(TourlistController.class, () -> new TourlistController(tourListViewModel, tourDialogService));
        addControllerCreator(TourDetailsController.class, () -> new TourDetailsController(tourDetailsViewModel));
        addControllerCreator(TourDialogController.class, () -> new TourDialogController(tourDialogViewModel, tourDialogService));
        addControllerCreator(DashboardController.class, () -> new DashboardController(dashboardViewModel));
    }
}
