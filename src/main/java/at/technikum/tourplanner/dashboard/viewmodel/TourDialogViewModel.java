package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.service.TourDialogService;
import javafx.beans.property.*;

import java.util.UUID;

public class TourDialogViewModel {

    private UUID tourUUID;
    private final StringProperty tourName = new SimpleStringProperty();
    private final StringProperty tourFrom = new SimpleStringProperty();
    private final StringProperty tourTo = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final ObjectProperty<TransportType> tourTransportType = new SimpleObjectProperty<>();

    private final Observable<Tour> tourCreationObservable = new Observable<>();
    private final Observable<Tour> tourUpdateObservable = new Observable<>();

    private final TourDialogService tourDialogService;

    public TourDialogViewModel(TourDialogService tourDialogService) {
        this.tourDialogService = tourDialogService;
    }

    public void subscribeToTourCreation(Listener<Tour> listener) {
        tourCreationObservable.subscribe(listener);
    }

    public void unsubscribeFromTourCreation(Listener<Tour> listener) {
        tourCreationObservable.unsubscribe(listener);
    }

    public void subscribeToTourUpdate(Listener<Tour> listener) {
        tourUpdateObservable.subscribe(listener);
    }

    public void unsubscribeFromTourUpdate(Listener<Tour> listener) {
        tourUpdateObservable.unsubscribe(listener);
    }

    public void createTour() {
        tourCreationObservable.notifyListeners(buildTour());
    }

    public void updateTour() {
        tourUpdateObservable.notifyListeners(buildTour());
    }

    public void setTour(Tour tour) {
        if (null != tour) {
            tourUUID = tour.getId();
            tourName.setValue(tour.getName());
            tourFrom.setValue(tour.getFrom());
            tourTo.setValue(tour.getTo());
            tourDescription.setValue(tour.getDescription());
            tourTransportType.setValue(tour.getTransportType());
        }
    }

    public void clearTour() {
        tourUUID = null;
        tourName.setValue("");
        tourFrom.setValue("");
        tourTo.setValue("");
        tourDescription.setValue("");
        tourTransportType.setValue(TransportType.FASTEST);
    }

    public void closeDialog() {
        tourDialogService.closeDialog();
    }

    private Tour buildTour() {
        // TODO: Validate User input
        return Tour.builder()
                .id(tourUUID)
                .name(tourName.get())
                .from(tourFrom.get())
                .to(tourTo.get())
                .description(tourDescription.get())
                .transportType(tourTransportType.get())
                .build();
    }


    public StringProperty tourNameProperty() {
        return tourName;
    }

    public StringProperty tourFromProperty() {
        return tourFrom;
    }

    public StringProperty tourToProperty() {
        return tourTo;
    }

    public StringProperty tourDescriptionProperty() {
        return tourDescription;
    }

    public ObjectProperty<TransportType> tourTransportTypeProperty() {
        return tourTransportType;
    }
}
