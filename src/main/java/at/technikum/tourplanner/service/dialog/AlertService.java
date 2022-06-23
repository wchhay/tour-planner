package at.technikum.tourplanner.service.dialog;

public interface AlertService {
    void showErrorAlert(Throwable throwable);

    void showErrorAlert(String message);

    boolean getUserConfirmation(String header, String contentText);
}
