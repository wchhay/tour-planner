package at.technikum.tourplanner.dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchbarViewModel {

    private final StringProperty searchString = new SimpleStringProperty();

    public StringProperty searchStringProperty() {
        return searchString;
    }

    public void search() {
        System.out.println("Searching " + searchString.get());
    }
}
