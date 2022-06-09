package at.technikum.tourplanner.dashboard.component;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.TourListViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Optional;

public class TourlistitemCell extends ListCell<Tour> {

    private final TourListViewModel tourListViewModel;

    private final HBox hbox = new HBox();
    private final Label tourName = new Label();
    private final Pane pane = new Pane();
    private final Button deleteButton = new Button("x");


    public TourlistitemCell(TourListViewModel tourListViewModel) {
        super();

        this.tourListViewModel = tourListViewModel;

        deleteButton.setOnAction(event -> openDeleteDialog(getItem()));
        HBox.setHgrow(pane, Priority.ALWAYS);
        hbox.getChildren().addAll(tourName, pane, deleteButton);
        hbox.setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(Tour item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (null != item && !empty) {
            tourName.setText(item.getName());
            setGraphic(hbox);
        }
    }

    private void openDeleteDialog(Tour tour) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Tour");
        alert.setHeaderText("Delete Tour?");
        alert.setContentText("Do you want to delete this tour?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && ButtonType.OK == result.get()) {
            tourListViewModel.deleteTour(tour);
        }
    }
}
