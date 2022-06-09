package at.technikum.tourplanner.dashboard.viewmodel.observer;

public interface Listener<T> {
    void update(T context);
}
