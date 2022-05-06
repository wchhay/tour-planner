package at.technikum.tourplanner.dashboard.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class Publisher<T> {

    private final List<Listener<T>> listeners = new ArrayList<>();

    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener<T> listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners(T context) {
        listeners.forEach(listener -> listener.update(context));
    }
}
