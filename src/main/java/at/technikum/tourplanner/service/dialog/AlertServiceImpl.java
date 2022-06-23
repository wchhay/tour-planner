package at.technikum.tourplanner.service.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertServiceImpl implements AlertService {

    @Override
    public void showErrorAlert(Throwable throwable) {
        showErrorAlert(throwable.getMessage());
    }

    @Override
    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @Override
    public boolean getUserConfirmation(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && ButtonType.OK == result.get();
    }
}
