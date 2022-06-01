package at.technikum.tourplanner.observer;

public interface Listener<T> {
    void update(T context);
}
