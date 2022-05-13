package at.technikum.tourplanner.dashboard.component;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class TourlistitemCell extends ListCell<Tour> {

    private final HBox hbox = new HBox();
    private final Label tourName = new Label();
    private final Pane pane = new Pane();
    private final Button deleteButton = new Button("x");


    public TourlistitemCell() {
        super();

        deleteButton.setOnAction(event -> getListView().getItems().remove(getItem()));
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
}
