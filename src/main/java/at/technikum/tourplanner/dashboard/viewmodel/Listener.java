package at.technikum.tourplanner.dashboard.viewmodel;

public interface Listener<T> {
    void update(T context);
}
