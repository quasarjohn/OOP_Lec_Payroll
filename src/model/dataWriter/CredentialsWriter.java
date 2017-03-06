package model.dataWriter;

import connection.AppConnection;

import java.sql.SQLException;

/**
 * Created by John on 3/6/2017.
 */
public class CredentialsWriter {

    public static boolean updateCredentials(String oldUsername, String oldPassword, String username, String password) {

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("update user_account set username = '"  + username + "', " +
        "password = '"  + password + "' where username = '" + oldUsername + "' AND password = '" +
        oldPassword + "'");
        try {
            conn.update();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
