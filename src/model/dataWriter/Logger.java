package model.dataWriter;

import connection.AppConnection;
import utils.DateUtils;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by John on 2/17/2017.
 */
public class Logger {

    public enum LogType {
        LOGIN_SUCCESS, LOGIN_ATTEMPT, LOGOUT,
        EMP_ADD, EMP_DELETE, EMP_UPDATE, EMP_GEN_BADGE,
        PAY_ADD_ITEM, PAY_GENERATE
    }

    public static void log(LogType logtype, String username, String details) {
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        System.out.println(DateUtils.getCurrentDate() + "\n" + username + "\n" + details + "\n" + logtype.toString());

        conn.doSomething("insert into logs values('" +
                username + "','" +
                DateUtils.getCurrentDate() + "','" +
                DateUtils.getCurrentTime() + "','" +
                logtype.toString() + "','" +
                details + "')");
        try {
            conn.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
    }
}
