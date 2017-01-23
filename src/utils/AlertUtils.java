package utils;

import javafx.scene.control.Alert;

/**
 * Created by John on 1/14/2017.
 */
public class AlertUtils {

    public static void showAlert(String str, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setHeaderText(str);
        alert.show();
    }
}
