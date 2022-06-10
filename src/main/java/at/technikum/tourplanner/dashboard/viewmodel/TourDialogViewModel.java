package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.model.TransportType;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Publisher;
import at.technikum.tourplanner.service.TourDialogService;
import javafx.beans.property.*;

public class TourDialogViewModel implements Publisher<Tour> {

    private final StringProperty tourName = new SimpleStringProperty();
    private final StringProperty tourFrom = new SimpleStringProperty();
    private final StringProperty tourTo = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final ObjectProperty<TransportType> tourTransportType = new SimpleObjectProperty<>();

    private final Observable<Tour> tourObservable = new Observable<>();

    private final TourDialogService tourDialogService;

    public TourDialogViewModel(TourDialogService tourDialogService) {
        this.tourDialogService = tourDialogService;
    }

    @Override
    public void addListener(Listener<Tour> listener) {
        tourObservable.subscribe(listener);
    }

    @Override
    public void removeListener(Listener<Tour> listener) {
        tourObservable.unsubscribe(listener);
    }

    public void validateAndBuildTour() {
        // TODO: Validate User input
        Tour tour = Tour.builder()
                .name(tourName.get())
                .from(tourFrom.get())
                .to(tourTo.get())
                .description(tourDescription.get())
                .transportType(tourTransportType.get())
                .build();

        tourObservable.notifyListeners(tour);
    }

    public void closeDialog() {
        tourDialogService.closeDialog();
    }

    public String getTourName() {
        return tourName.get();
    }

    public StringProperty tourNameProperty() {
        return tourName;
    }

    public String getTourFrom() {
        return tourFrom.get();
    }
    public StringProperty tourFromProperty() {
        return tourFrom;
    }

    public String getTourTo() {
        return tourTo.get();
    }

    public StringProperty tourToProperty() {
        return tourTo;
    }

    public String getTourDescription() {
        return tourDescription.get();
    }

    public StringProperty tourDescriptionProperty() {
        return tourDescription;
    }

    public TransportType getTourTransportType() {
        return tourTransportType.get();
    }

    public ObjectProperty<TransportType> tourTransportTypeProperty() {
        return tourTransportType;
    }

}
