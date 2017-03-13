package model.dataReader;

import connection.AppConnection;

import java.sql.SQLException;

/**
 * Created by John on 2/17/2017.
 */
public class UserLogin {

    private AppConnection conn;

    public UserLogin(AppConnection conn) {
        this.conn = conn;
    }

    public boolean credentialMatch(String u, String p) {
        String username = "", password = "";

        conn.doSomething("select username, password from user_account where username = '" + u + "'");
        try {
            conn.query();
            while (conn.getRS().next()) {
                username = conn.getRS().getString(1);
                password = conn.getRS().getString(2);
            }
        } catch (SQLException e) {
            System.out.println("INCORRECT USERNAME OR PASSWORD");
        }

        if(u.equals(username) && p.equals(password)) {
            conn.closeConnection();
            return true;
        }
        return false;
    }
}
