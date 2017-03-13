package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connection.AppConnection;
import javafx.fxml.FXML;
import model.dataReader.UserLogin;
import model.dataWriter.ActiveUser;
import model.dataWriter.CredentialsWriter;
import model.dataWriter.Logger;
import utils.AlertUtils;

/**
 * Created by John on 3/6/2017.
 */
public class SettingsController {

    @FXML
    private JFXTextField oldUsernameTF, newUsernameTF;
    @FXML
    private JFXPasswordField oldPasswordTF, newPasswordTF;

    @FXML
    private JFXButton updateB;

    @FXML
    public void listenToUpdate() {
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        UserLogin login = new UserLogin(conn);

        if (login.credentialMatch(oldUsernameTF.getText(), oldPasswordTF.getText())) {
            boolean result = CredentialsWriter.updateCredentials(oldUsernameTF.getText(), oldPasswordTF.getText(),
                    newUsernameTF.getText(), newPasswordTF.getText());

            if (result) {
                Logger.log(Logger.LogType.UPDATE_USER_PASS, ActiveUser.getUsername(), "Updated username and password from " +
                        oldUsernameTF.getText() + " AND " + oldPasswordTF.getText() + " to " +
                        newUsernameTF.getText() + " AND " + newPasswordTF.getText());
                AlertUtils.showAlert("Update Success.", "Your username and password has been updated.");
            } else {
                AlertUtils.showAlert("Update failed.", "Please verify your input.");
            }
        } else {
            AlertUtils.showAlert("Invalid credentials.", "Please verify your existing username and password.");
        }
    }
}
