package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchbarViewModel {

    private final StringProperty searchString = new SimpleStringProperty();

    private final Observable<String> searchObservable = new Observable<>();

    public void subscribeToSearch(Listener<String> listener) {
        searchObservable.subscribe(listener);
    }

    public void search() {
        searchObservable.notifyListeners(searchString.get());
    }

    public void cancelSearch() {
        searchObservable.notifyListeners("");
    }

    public StringProperty searchStringProperty() {
        return searchString;
    }

    public BooleanBinding searchDisabledBinding() {
        return searchString.isEmpty();
    }
}
