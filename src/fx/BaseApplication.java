package fx;

import javafx.application.Application;
import javafx.scene.control.Alert;

/**
 * Created by Paul on 12/11/2016.
 */
public abstract class BaseApplication extends Application {

    void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.show();
    }
}
