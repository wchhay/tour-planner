package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.rest.ImageService;
import at.technikum.tourplanner.service.dialog.AlertService;
import at.technikum.tourplanner.service.report.FailedPdfGenerationException;
import at.technikum.tourplanner.service.report.ReportService;
import at.technikum.tourplanner.service.statistics.StatisticsService;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;


import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;


@Log4j2
public class TourDetailsViewModel {

    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final ObjectProperty<Image> tourMapImage = new SimpleObjectProperty<>();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childFriendliness = new SimpleStringProperty();

    private final Observable<Boolean> tourEditClickedObservable = new Observable<>();

    private final ImageService imageService;
    private final AlertService alertService;
    private final ReportService reportService;
    private final StatisticsService statisticsService;

    public TourDetailsViewModel(ImageService imageService, AlertService alertService, ReportService reportService, StatisticsService statisticsService) {
        this.imageService = imageService;
        this.alertService = alertService;
        this.reportService = reportService;
        this.statisticsService = statisticsService;
    }

    public ObjectProperty<Tour> selectedTourProperty() {
        return selectedTour;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public ObjectProperty<Image> tourMapImageProperty() {
        return tourMapImage;
    }

    public StringProperty popularityProperty() {
        return popularity;
    }

    public StringProperty childFriendlinessProperty() {
        return childFriendliness;
    }

    public BooleanBinding pdfReportDisabled() {
        return selectedTour.isNull().or(tourMapImage.isNull());
    }

    public void subscribeToTourEditClicked(Listener<Boolean> listener) {
        tourEditClickedObservable.subscribe(listener);
    }

    public void setTour(Tour tour) {
        logger.debug("Setting selected tour to {}", tour);
        selectedTour.set(tour);
        downloadImage(tour);
        setProperties(tour);
    }

    public void reloadSelectedTour() {
        setProperties(selectedTour.get());
    }

    public void editTour() {
        tourEditClickedObservable.notifyListeners(true);
    }

    public void generateTourReport() {
        if (selectedTour.isNotNull().get() && tourMapImage.isNotNull().get()) {
            logger.info("Generating tour report for {}", selectedTour.get());
            try {
                reportService.generateTourReport(selectedTour.get(), tourMapImage.get().getUrl());
            } catch (FailedPdfGenerationException e) {
                logger.warn(e);
                alertService.showErrorAlert(e);
            }
        }
    }

    private void setProperties(Tour tour) {
        if (null != tour) {
            logger.debug("Calculating computed attributes for {}", tour);
            statisticsService.calculateAndSetPopularity(tour);
            statisticsService.calculateAndSetChildFriendliness(tour);

            name.set(tour.getName());
            from.set(tour.getFrom());
            to.set(tour.getTo());
            description.set(tour.getDescription());
            transportType.set(tour.getTransportType().value);
            distance.set(convertNullableNumberToString(tour.getDistance()));
            time.set(convertToTimeString(tour.getEstimatedTime()));
            popularity.set(convertNullableNumberToString(tour.getPopularity()));
            childFriendliness.set(convertNullableNumberToString(tour.getChildFriendliness()));

            logger.debug("Setting tour details for {}", tour);
        } else {
            name.set("");
            from.set("");
            to.set("");
            description.set("");
            transportType.set("");
            distance.set("");
            time.set("");
            popularity.set("");
            childFriendliness.set("");

            logger.debug("Clearing tour details");
        }
    }

    private String convertNullableNumberToString(Number number) {
        if (null != number) {
            return number.toString();
        }
        return "";
    }

    private void downloadImage(Tour tour) {
        if (null != tour) {
            Image image = imageService.downloadTourMapImage(tour.getId());
            image.exceptionProperty().addListener((observable, oldValue, newValue) -> {
                if (null != newValue) {
                    alertService.showErrorAlert("Cannot download image");
                    logger.warn("Cannot download image for {}", tour);
                }
            });
            tourMapImage.set(image);
            logger.debug("Downloading and setting image for {}", tour);
        } else {
            tourMapImage.set(null);
        }
    }
}
