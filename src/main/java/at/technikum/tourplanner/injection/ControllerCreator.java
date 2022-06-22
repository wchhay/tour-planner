package at.technikum.tourplanner.injection;

public interface ControllerCreator<T> {
    T create();
}
