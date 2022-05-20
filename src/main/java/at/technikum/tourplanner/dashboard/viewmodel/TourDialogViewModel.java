package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourDialogViewModel extends Publisher<Tour> {

    private final StringProperty tourName = new SimpleStringProperty();

    private final StringProperty tourFrom = new SimpleStringProperty();

    private final StringProperty tourTo = new SimpleStringProperty();

    private final StringProperty tourDescription = new SimpleStringProperty();

    private final StringProperty tourTransportType = new SimpleStringProperty();

    private final StringProperty tourDistance = new SimpleStringProperty();

    private final StringProperty tourTime = new SimpleStringProperty();

    private final StringProperty tourInfo = new SimpleStringProperty();

    public void validateAndBuildTour() {
        // TODO: Validate User input
        Tour tour = Tour.builder()
                .name(tourName.get())
                .from(tourFrom.get())
                .to(tourTo.get())
                .description(tourDescription.get())
                .transportType(tourTransportType.get())
                .distance(tourDistance.get())
                .time(tourTime.get())
                .info(tourInfo.get())
                .build();

        notifyListeners(tour);
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

    public String getTourDistance() {
        return tourDistance.get();
    }

    public StringProperty tourDistanceProperty() {
        return tourDistance;
    }

    public String getTourTime() {
        return tourTime.get();
    }

    public StringProperty tourTimeProperty() {
        return tourTime;
    }

    public String getInfoDistance() {
        return tourInfo.get();
    }

    public StringProperty tourInfoProperty() {
        return tourInfo;
    }
}
