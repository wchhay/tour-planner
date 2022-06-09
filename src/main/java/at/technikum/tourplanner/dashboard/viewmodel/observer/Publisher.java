package at.technikum.tourplanner.dashboard.viewmodel.observer;

public interface Publisher<T> {

    void addListener(Listener<T> listener);

    void removeListener(Listener<T> listener);
}
