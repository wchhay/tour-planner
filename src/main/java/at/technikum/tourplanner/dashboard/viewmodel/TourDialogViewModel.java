package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.observer.Listener;
import at.technikum.tourplanner.observer.Observable;
import at.technikum.tourplanner.observer.Publisher;
import javafx.beans.property.*;

public class TourDialogViewModel implements Publisher<Tour> {

    private final StringProperty tourName = new SimpleStringProperty();
    private final StringProperty tourFrom = new SimpleStringProperty();
    private final StringProperty tourTo = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final StringProperty tourTransportType = new SimpleStringProperty();

    private final Observable<Tour> tourObservable = new Observable<>();

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

    public String getTourTransportType() {
        return tourTransportType.get();
    }

    public StringProperty tourTransportTypeProperty() {
        return tourTransportType;
    }
}
