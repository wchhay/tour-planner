package at.technikum.tourplanner.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Listener<T>> listeners = new ArrayList<>();

    public void subscribe(Listener<T> listener) {
        listeners.add(listener);
    }

    public void unsubscribe(Listener<T> listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(T context) {
        listeners.forEach(listener -> listener.update(context));
    }
}
