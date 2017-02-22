package model.dataWriter;

import connection.AppConnection;
import model.dataStructure.Earning;
import values.Strings;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by John on 1/28/2017.
 */
public class DashboardWriter {

    private static AppConnection conn;

    public static void addWorkDone(Earning earning) {
        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        System.out.println(earning.getPre_empno());
        System.out.println(earning.getPost_empno());
        System.out.println(earning.getAmount());
        System.out.println(earning.getCommissioPercentage());
        System.out.println(earning.getCommission());
        System.out.println(earning.getCategory());
        System.out.println(earning.getNotes());

        conn.doSomething("insert into earnings values(" +
                        earning.getPre_empno() + ", " +
                        earning.getPost_empno() + ",'" +
                        Strings.getDateFormat().format(new Date()) + "'," +
                        earning.getAmount() + "," +
                earning.getCommissioPercentage() + "," +
                earning.getCommission() + "," +
                "null" + ",'" + earning.getCategory() + "','" +
                earning.getNotes() + "')"
        );

        try {
            conn.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
    }
}
