package at.technikum.tourplanner.injection;

import at.technikum.tourplanner.dashboard.view.SearchbarController;
import at.technikum.tourplanner.dashboard.viewmodel.SearchbarViewModel;
import at.technikum.tourplanner.dashboard.view.TourlistController;
import at.technikum.tourplanner.dashboard.viewmodel.TourViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    private static final Map<Class<?>, ControllerCreator> controllerCreators = new HashMap<>();

    private static ControllerFactory instance;

    private final SearchbarViewModel searchbarViewModel = new SearchbarViewModel();
    private final TourViewModel tourViewModel = new TourViewModel();

    private ControllerFactory() {
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
        addControllerCreator(TourlistController.class, () -> new TourlistController(tourViewModel));
    }
}
